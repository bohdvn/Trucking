import React from 'react';
import {Button, ButtonGroup, Container, FormGroup, Table} from 'reactstrap';
import {Link} from 'react-router-dom';
import axios from 'axios';
import Pagination from "react-js-pagination";
import Modal from 'react-bootstrap/Modal';
import InvoiceComponent from "../forms/InvoiceComponent";
import {ACCESS_TOKEN} from "../../constants/auth";
import {connect} from "react-redux";
import {DISPATCHER} from "../../constants/userConstants";

class RequestListComponent extends React.Component {

    requestStatusMap = {
        'NOT_VIEWED': 'Не просмотрена',
        'REJECTED': 'Отклонена',
        'ISSUED': 'Оформлена'
    };

    constructor(props) {
        super(props);
        this.state = {
            request: '',
            requests: [],
            activePage: 0,
            totalPages: null,
            itemsCountPerPage: null,
            totalItemsCount: null,
            show: false,
            loggedIn:props.loggedIn
        };
        this.handlePageChange = this.handlePageChange.bind(this);
        this.fetchURL = this.fetchURL.bind(this);
        this.remove = this.remove.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
        console.log(props);
    }

    fetchURL(page) {
        let url='';
        switch(this.props.location.pathname){
            case '/requests':
                url='list';
                break;

            case '/notviewedrequests':
                url='notviewed';
                break;

            default:
                return;
        }
        axios.get(`/request/${url}?page=${page}&size=5`)
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
        this.fetchURL(this.state.activePage)
    }

    handlePageChange(pageNumber) {
        console.log(`active page is ${pageNumber}`);
        this.setState({activePage: pageNumber});
        this.fetchURL(pageNumber - 1)
    }

    populateRowsWithData = () => {
        const {roles}=this.state.loggedIn.claims;
        const requests = this.state.requests.map(request => {
            return <tr key={request.id}>
                <td>{request.car.name}</td>
                <td>{request.driver.name} {request.driver.surname}</td>
                <td>{this.requestStatusMap[request.status]}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link}
                                to={"/request/" + request.id}>Редактировать</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(request.id)}>Удалить</Button>
                        {roles.includes(DISPATCHER)?<Button size="sm" color="primary" onClick={() => this.handleShow(request.id)}
                        >ТТН</Button>:null}
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
        let date = new Date();
        var dateStr = date.getFullYear() + '-' + ((date.getMonth().toString().length === 1)
            ? ('0' + (+date.getMonth() + 1)) : (+date.getMonth() + 1)) + '-' + ((date.getDate().toString().length === 1)
            ? ('0' + date.getDate()) : date.getDate());
        let invoice = {};
        let request = this.state.request;
        request.status = 'ISSUED';
        invoice.request = request;
        invoice.status = 'COMPLETED';
        invoice.number = this.state.request.id;
        invoice.dateOfIssue = dateStr;
        console.log(invoice);
        this.setState({request: ''});
        this.saveInvoice(invoice);
        this.saveRequest(request);
        // window.location.reload();
    };

    async saveInvoice(invoice) {
        await axios({
            method:invoice.id?'PUT':'POST',
            url:'/invoice/',
            data:invoice
        }).then(response=>{
            console.log(response);
        });
        console.log('invoice');
        // await fetch('/invoice/', {
        //     method: invoice.id?'PUT':'POST',
        //     headers: {
        //         'Accept': 'application/json',
        //         'Content-Type': 'application/json',
        //         'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
        //     },
        //     body: JSON.stringify(invoice)
        // }).then(response=>{
        //         console.log(response);
        //     });
    }

    async saveRequest(request) {
        await axios({
            method:request.id?'PUT':'POST',
            url:'/request/',
            data:request
        }).then(response=>{
            console.log(response);
        });
        // await fetch('/request/', {
        //     method: 'PUT',
        //     headers: {
        //         'Accept': 'application/json',
        //         'Content-Type': 'application/json',
        //         'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
        //     },
        //     body: JSON.stringify(request)
        // });
    }

    async handleShow(id) {
        const request = await(await
                fetch(`/request/${id}`,
                    {headers: {'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)}})
        ).json();
        this.setState({show: true, request: request});
    }

    handleClose() {
        this.setState({show: false});
    }

    render() {
        const {roles}=this.state.loggedIn.claims;
        console.log(roles);
        return (
            <div>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/request/create">Добавить</Button>
                    </div>
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

                    {roles.includes(DISPATCHER)?<Modal size="lg" show={this.state.show} onHide={this.handleClose}>
                        <Modal.Header closeButton>
                            <Modal.Title>ТТН</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <FormGroup>
                                <InvoiceComponent
                                    name="InvoiceComponent"
                                    id="InvoiceComponent"
                                    request={this.state.request}
                                />
                            </FormGroup>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button color="primary" onClick={this.createInvoice}>
                                Создать ТТН
                            </Button>
                        </Modal.Footer>
                    </Modal>:null}

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