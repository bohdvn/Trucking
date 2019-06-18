import React from 'react';
import {Button, ButtonGroup, Container, FormGroup, Table} from 'reactstrap';
import {Link} from 'react-router-dom';
import axios from 'axios';
import Pagination from "react-js-pagination";
import Modal from 'react-bootstrap/Modal';
import InvoiceComponent from "../forms/InvoiceComponent";
import {ACCESS_TOKEN} from "../../constants/auth";
import {connect} from "react-redux";
import * as ROLE from "../../constants/userConstants";
import {currentTime} from "../../utils/currentTime";

class RequestListComponent extends React.Component {

    requestStatusMap = {
        'NOT_VIEWED': 'Не просмотрена',
        'REJECTED': 'Отклонена',
        'ISSUED': 'Оформлена'
    };

    constructor(props) {
        super(props);
        this.state = {
            invoice: {
                request: {}
            },
            requests: [],
            activePage: 0,
            query: '',
            totalPages: null,
            itemsCountPerPage: null,
            totalItemsCount: null,
            show: false,
            roles: props.loggedIn.claims.roles
        };
        this.handlePageChange = this.handlePageChange.bind(this);
        this.fetchURL = this.fetchURL.bind(this);
        this.remove = this.remove.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);

        this.queryTimeout = -1;
        this.handleQueryChange = this.handleQueryChange.bind(this);
        this.changeQuery = this.changeQuery.bind(this);
    }

    fetchURL(page, query) {
        let url = '';
        switch (this.props.location.pathname) {
            case '/requests':
                url = 'list';
                break;

            case '/notviewedrequests':
                url = 'notviewed';
                break;

            default:
                return;
        }
        axios.get(`/request/${url}/${query}?page=${page}&size=5`)
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
                        this.setState({requests: results});
                        console.log(results);
                    }

                    console.log(this.state.activePage);
                    console.log(this.state.itemsCountPerPage);
                }
            );
    }

    componentDidMount() {
        this.fetchURL(this.state.activePage, this.state.query)
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
        const {roles} = this.state;
        const requests = this.state.requests.map(request => {
            return <tr key={request.id}>
                <td>{request.car.name}</td>
                <td>{request.driver.name} {request.driver.surname}</td>
                <td>{this.requestStatusMap[request.status]}</td>
                <td>
                    <ButtonGroup>
                        {roles.includes(ROLE.OWNER) && request.status!='ISSUED' ?
                            <Button size="sm" color="primary" tag={Link}
                                    to={"/request/" + request.id}>Редактировать
                            </Button>
                            : null}
                        {roles.includes(ROLE.DISPATCHER) ?
                            <Button size="sm" color="primary"
                                    onClick={() => this.handleShow(request.id)}>ТТН
                            </Button>
                            : null}
                    </ButtonGroup>
                </td>

            </tr>
        });

        return requests;
    };

    async remove(id) {
        await fetch(`/request/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
            }
        }).then(() => {
            let updateRequests = [...this.state.requests].filter(i => i.id !== id);
            this.setState({requests: updateRequests});
        });
    }

    createInvoice = () => {
        this.setState({show: false});
        const dateStr = currentTime();
        const {invoice} = this.state;
        console.log(invoice);
        let request = this.state.invoice.request;
        request.status = 'ISSUED';
        console.log(request);
        invoice.status = 'COMPLETED';
        invoice.dateOfIssue = dateStr;
        console.log(invoice);
        this.setState({invoice: ''});
        this.saveInvoice(invoice)
            .then(response=>{
                console.log(response);
            });
        window.location.reload();
    };

    async saveInvoice(invoice) {
        await axios({
            method: invoice.id ? 'PUT' : 'POST',
            url: '/invoice/',
            data: invoice
        });
    }

    async handleShow(id) {
        const request = await (await
                fetch(`/request/${id}`,
                    {headers: {'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)}})
        ).json();
        this.setState({show: true, invoice: {request: request}});
    }

    handleClose() {
        this.setState({show: false});
    }

    handleInvoiceNumberChange = value => {
        console.log(value);
        let invoice = {...this.state.invoice};
        invoice['number'] = value;
        this.setState({invoice: invoice});
        console.log(this.state);
    };

    render() {
        const {roles} = this.state;
        return (
            <div>
                <Container fluid>
                    {roles.includes(ROLE.OWNER) ?
                        <div className="float-right">
                            <Button color="success" tag={Link} to="/request/create">Добавить</Button>
                        </div>
                        : null}
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Машина</th>
                            <th width="20%">Водитель</th>
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

                    {roles.includes(ROLE.DISPATCHER) ?
                        <Modal size="lg" show={this.state.show} onHide={this.handleClose}>
                            <Modal.Header closeButton>
                                <Modal.Title>ТТН</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <FormGroup>
                                    <InvoiceComponent
                                        name="InvoiceComponent"
                                        id="InvoiceComponent"
                                        invoice={this.state.invoice}
                                        handleChange={this.handleInvoiceNumberChange.bind(this)}
                                    />
                                </FormGroup>
                            </Modal.Body>
                            <Modal.Footer>
                                <Button color="primary" onClick={this.createInvoice}>
                                    Создать ТТН
                                </Button>
                            </Modal.Footer>
                        </Modal> : null}

                </Container>
            </div>
        );
    }
}

export default connect(
    state => ({
        loggedIn: state.loggedIn,
    })
)(RequestListComponent)