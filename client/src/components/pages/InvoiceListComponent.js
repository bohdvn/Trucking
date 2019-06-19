import React from 'react';
import {Container, FormGroup, Input, Table, Button, ButtonGroup} from 'reactstrap';
import axios from 'axios';
import Pagination from "react-js-pagination";
import {ACCESS_TOKEN} from "../../constants/auth";
import {Link} from "react-router-dom";
import {connect} from "react-redux";
import {MANAGER} from "../../constants/userConstants";
import Modal from 'react-bootstrap/Modal';
import InvoiceComponent from '../forms/InvoiceComponent';

class InvoiceListComponent extends React.Component {

    carStatusMap = {
        'AVAILABLE': 'Доступен',
        'UNAVAILABLE': 'Недоступен'
    };
    carTypeMap = {
        'TILT': 'Крытый кузов',
        'TANKER': 'Автоцистерна',
        'FRIDGE': 'Рефрижератор'
    };
    invoiceStatusMap = {
        'COMPLETED': 'Оформлена',
        'CHECKED': 'Проверена',
        'CHECKED_BY_DRIVER': 'Проверена водителем',
        'DELIVERED': 'Доставлена'
    };

    constructor(props) {
        super(props);
        this.state = {
            invoice: {},
            loggedIn: props.loggedIn,
            invoices: [],
            query: '',
            activePage: 0,
            totalPages: null,
            itemsCountPerPage: null,
            totalItemsCount: null,
            show: false,
        };
        this.handlePageChange = this.handlePageChange.bind(this);
        this.fetchURL = this.fetchURL.bind(this);
        this.remove = this.remove.bind(this);

        this.queryTimeout = -1;
        this.handleQueryChange = this.handleQueryChange.bind(this);
        this.changeQuery = this.changeQuery.bind(this);
    }

    fetchURL(page, query) {
        axios.get(`/invoice/list/${query}?page=${page}&size=5`)
            .then(response => {
                    console.log(response);
                    const totalPages = response.data.totalPages;
                    const itemsCountPerPage = response.data.size;
                    const totalItemsCount = response.data.totalElements;

                    this.setState({totalPages: totalPages});
                    this.setState({totalItemsCount: totalItemsCount});
                    this.setState({itemsCountPerPage: itemsCountPerPage});

                    const results = response.data.content;
                    console.log(this.state);

                    if (results != null) {
                        this.setState({invoices: results});
                        console.log(results);
                    }

                    console.log(this.state.activePage);
                    console.log(this.state.itemsCountPerPage);
                }
            );
    }

    componentDidMount() {
        this.fetchURL(this.state.activePage, this.state.query);
    }

    handlePageChange(pageNumber) {
        console.log(`active page is ${pageNumber}`);
        this.setState({activePage: pageNumber});
        this.fetchURL(pageNumber - 1, this.state.query);
    }

    changeQuery() {
        this.fetchURL(0, this.state.query);
    }

    handleQueryChange(event) {
        clearTimeout(this.queryTimeout);
        const target = event.target;
        const value = target.value;
        this.setState(() => ({
            query: value
        }));
        this.queryTimeout = setTimeout(this.changeQuery, 1000);
    }

    populateRowsWithData = () => {
        const {roles} = this.state.loggedIn.claims;
        return this.state.invoices.map(invoice => {
            return <tr key={invoice.id}>
                <td>{invoice.number}</td>
                <td style={{whiteSpace: 'nowrap'}}>{invoice.request.car.name}</td>
                <td>{this.carTypeMap[invoice.request.car.carType]}</td>
                <td>{this.carStatusMap[invoice.request.car.status]}</td>
                <td>{this.invoiceStatusMap[invoice.status]}</td>
                <td>{invoice.request.driver.name}</td>
                {roles.includes(MANAGER) && invoice.status === 'COMPLETED' ?
                    <td>
                        <ButtonGroup>
                            <Button size="sm" color="primary" tag={Link}
                                    onClick={() => this.handleShow(invoice.id)}>Проверить</Button>
                        </ButtonGroup>
                    </td>
                    : null}

            </tr>
        });

    };

    async handleShow(id) {
        const invoice = await (await
                fetch(`/invoice/${id}`,
                    {headers: {'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)}})
        ).json();
        this.setState({show: true, invoice: invoice});
        console.log(this.state);
    }

    rejectRequest = () => {
        this.setState({show: false});
        const {invoice} = this.state;
        invoice.request.status = 'REJECTED';
        console.log(invoice);
        axios.put('/invoice/', invoice)
            .then(response => {
                console.log(response);
            });
    };

    handleClose = () => {
        this.setState({show: false});
    };

    async remove(id) {
        await fetch(`/car/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updateCars = [...this.state.cars].filter(i => i.id !== id);
            this.setState({cars: updateCars});
        });
    }

    createWaybill = () => {
        const {invoice} = this.state;
        this.props.history.push("/waybill/create", {invoice: invoice});
    };


    render() {
        const {roles} = this.state.loggedIn.claims;
        const check = !this.state.invoices.length;
        return (
            <Container className="text-center" fluid>
                <FormGroup>
                    <Input type="text" name="searchQuery" id="searchQuery" value={this.state.query}
                           onChange={this.handleQueryChange} autoComplete="searchQuery"/>
                </FormGroup>
                {check ? <h3>Список пуст</h3> :
                    <div>
                        <Table className="mt-4">
                            <thead>
                            <tr>
                                <th width="16%">Номер</th>
                                <th width="16%">Машина</th>
                                <th width="16%">Тип машины</th>
                                <th width="16%">Статус машины</th>
                                <th>Статус ТТН</th>
                                <th width="16%">Водитель</th>
                            </tr>
                            </thead>
                            <tbody>
                            {this.populateRowsWithData()}
                            </tbody>

                        </Table>

                        <div className="d-flex justify-content-center">
                            <Pagination
                                activePage={this.state.activePage}
                                itemsCountPerPage={this.state.itemsCountPerPage}
                                totalItemsCount={this.state.totalItemsCount}
                                itemClass='page-item'
                                linkClass='btn btn-light'
                                onChange={this.handlePageChange}

                            />
                        </div>

                        {roles.includes(MANAGER) ? <Modal size="lg" show={this.state.show} onHide={this.handleClose}>
                            <Modal.Header closeButton>
                                <Modal.Title>ТТН</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <FormGroup>
                                    <InvoiceComponent
                                        name="InvoiceComponent"
                                        id="InvoiceComponent"
                                        invoice={this.state.invoice}
                                    />
                                </FormGroup>
                            </Modal.Body>
                            <Modal.Footer>
                                <Button color="primary" onClick={this.createWaybill}>
                                    Подтвердить
                                </Button>
                                <Button color="danger" onClick={this.rejectRequest}>
                                    Отклонить
                                </Button>
                            </Modal.Footer>
                        </Modal> : null}
                    </div>}
            </Container>
        );
    }
}

export default connect(
    state => ({
        loggedIn: state.loggedIn,
    })
)(InvoiceListComponent)