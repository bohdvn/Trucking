import React from 'react';
import {Button, ButtonGroup, Container, FormGroup, Input, Table} from 'reactstrap';
import {Link} from 'react-router-dom';
import Modal from 'react-bootstrap/Modal';
import Pagination from "react-js-pagination";
import axios from 'axios';
import {ACCESS_TOKEN} from "../../constants/auth";
import CheckProductsComponent from "../forms/CheckProductsComponent";
import ActOfLossComponent from "../forms/ActOfLossComponent";


class WaybillListComponent extends React.Component {

    waybillStatusMap = {
        'STARTED': 'Начата',
        'FINISHED': 'Завершена',
        'EXECUTED': 'Оформлен'
    };

    constructor(props) {
        super(props);
        this.state = {
            waybill: '',
            waybills: [],
            query: '',
            activePage: 0,
            totalPages: null,
            itemsCountPerPage: null,
            totalItemsCount: null,
            showExecuteWaybill: false,
            showActOfLoss: false,
            productFormValid: true
        };
        this.handlePageChange = this.handlePageChange.bind(this);
        this.fetchURL = this.fetchURL.bind(this);
        this.remove = this.remove.bind(this);
        this.handleShowExecuteWaybill = this.handleShowExecuteWaybill.bind(this);
        this.handleCloseExecuteWaybill = this.handleCloseExecuteWaybill.bind(this);
        this.handleShowActOfLoss = this.handleShowActOfLoss.bind(this);
        this.handleCloseActOfLoss = this.handleCloseActOfLoss.bind(this);
        this.updateWayBill = this.updateWayBill.bind(this);
        this.queryTimeout = -1;
        this.handleQueryChange = this.handleQueryChange.bind(this);
        this.changeQuery = this.changeQuery.bind(this);
    }

    fetchURL(page, query) {
        axios.get(`/waybill/list/${query}?page=${page}&size=5`, {
            proxy: {
                host: 'http://localhost',
                port: 8080
            },
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
            }
        })
            .then(response => {
                    console.log(response);
                    const totalPages = response.data.totalPages;
                    const itemsCountPerPage = response.data.size;
                    const totalItemsCount = response.data.totalElements;

                    this.setState({totalPages: totalPages});
                    this.setState({totalItemsCount: totalItemsCount});
                    this.setState({itemsCountPerPage: itemsCountPerPage});

                    const results = response.data.content;
                    if (results != null) {
                        this.setState({waybills: results});
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

    async handleShowExecuteWaybill(id) {
        const waybill = await(await
                fetch(`/waybill/${id}`,
                    {headers: {'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)}})
        ).json();
        this.setState({showExecuteWaybill: true, waybill: waybill});
    }

    handleCloseExecuteWaybill() {
        this.setState({showExecuteWaybill: false});
    }

    async handleShowActOfLoss(id) {
        const waybill = await(await
                fetch(`/waybill/${id}`,
                    {headers: {'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)}})
        ).json();
        this.setState({showActOfLoss: true, waybill: waybill});
    }

    handleCloseActOfLoss() {
        this.setState({showActOfLoss: false});
    }

    async updateWayBill() {
        let waybill = this.state.waybill;
        waybill.status = 'EXECUTED';
        this.setState({showExecuteWaybill: false});
        await fetch(`/waybill/`, {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
            },
            body: JSON.stringify(waybill)
        });
        this.setState({waybill: ''});
        window.location.reload();
    }

    validationHandler = (formValid) => {
        this.setState({productFormValid: formValid});
    };

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
        const waybills = this.state.waybills.map(waybill => {
            return <tr key={waybill.id}>
                <td>{waybill.invoice.number}</td>
                <td>{waybill.invoice.request.clientCompanyFrom.address.city}
                    {waybill.invoice.request.clientCompanyFrom.address.street}
                    {waybill.invoice.request.clientCompanyFrom.address.building}</td>
                <td>{waybill.invoice.request.clientCompanyTo.address.city}
                    {waybill.invoice.request.clientCompanyTo.address.street}
                    {waybill.invoice.request.clientCompanyTo.address.building}</td>
                <td>{waybill.dateFrom}</td>
                <td>{this.waybillStatusMap[waybill.status]}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link}
                                to={"/waybill/" + waybill.id}>Редактировать</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(waybill.id)}>Удалить</Button>
                        {waybill.status === 'FINISHED' ?
                            <Button
                                size="sm" color="primary" onClick={() => this.handleShowExecuteWaybill(waybill.id)}>Оформить
                            </Button>
                            : null}
                        {waybill.status === 'EXECUTED' ?
                            <Button
                                size="sm" color="primary"
                                onClick={() => this.handleShowActOfLoss(waybill.id)}> Просмотреть
                            </Button>
                            : null}
                    </ButtonGroup>
                </td>

            </tr>
        });

        return waybills;
    };

    async remove(id) {
        await fetch(`/waybill/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updateWaybills = [...this.state.waybills].filter(i => i.id !== id);
            this.setState({waybills: updateWaybills});
        });
    }


    render() {
        return (
            <div>
                <Container fluid>
                    <FormGroup>
                        <Input type="text" name="searchQuery" id="searchQuery" value={this.state.query}
                               onChange={this.handleQueryChange} autoComplete="searchQuery"/>
                    </FormGroup>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/waybill/create">Добавить</Button>
                    </div>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Номер ТТН</th>
                            <th width="20%">Пункт отправления</th>
                            <th width="20%">Пункт назначения</th>
                            <th width="20%">Дата отправления</th>
                            <th>Статус</th>
                            <th width="10%"></th>
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


                    <Modal size="lg" show={this.state.showExecuteWaybill} onHide={this.handleCloseExecuteWaybill}>
                        <Modal.Header closeButton>
                            <Modal.Title>Выгрузка товаров</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <FormGroup>
                                <CheckProductsComponent
                                    name="CheckProductsComponent"
                                    id="CheckProductsComponent"
                                    waybill={this.state.waybill}
                                    validationHandler={this.validationHandler}
                                />
                            </FormGroup>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button color="primary" onClick={this.updateWayBill}
                                    disabled={!this.state.productFormValid}>
                                Поддтвердить
                            </Button>
                        </Modal.Footer>
                    </Modal>

                    <Modal size="lg" show={this.state.showActOfLoss} onHide={this.handleCloseActOfLoss}>
                        <Modal.Header closeButton>
                            <Modal.Title>Утерянные товары</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <FormGroup>
                                <ActOfLossComponent
                                    name="ActOfLossComponent"
                                    id="ActOfLossComponent"
                                    waybill={this.state.waybill}
                                />
                            </FormGroup>
                        </Modal.Body>
                        <Modal.Footer>
                        </Modal.Footer>
                    </Modal>
                </Container>
            </div>
        );
    }
}

export default WaybillListComponent;