import React from 'react';
import {Button, ButtonGroup, Container, FormGroup, Input, Table} from 'reactstrap';
import {Link} from 'react-router-dom';
import axios from 'axios';
import Pagination from "react-js-pagination";

class ClientListComponent extends React.Component {

    clientTypeMap = {
        'INDIVIDUAL': 'Физическое лицо',
        'LEGAL': 'Юридическое лицо'
    };

    clientStatusMap = {
        'ACTIVE': 'Активен',
        'BLOCKED': 'Заблокирован'
    };

    constructor(props) {
        super(props);
        this.state = {
            clients: [],
            activePage: 0,
            query: '',
            totalPages: null,
            itemsCountPerPage: null,
            totalItemsCount: null,
            client: {
                companyType: 'CLIENT'
            }
        };
        this.handlePageChange = this.handlePageChange.bind(this);
        this.fetchURL = this.fetchURL.bind(this);
        this.removeChecked = this.removeChecked.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.queryTimeout = -1;
        this.handleQueryChange = this.handleQueryChange.bind(this);
        this.changeQuery = this.changeQuery.bind(this);
    }

    handleChange = e => {
        const id = e.target.id;
        console.log(this.state);
        this.setState(prevState => {
            return {
                clients: prevState.clients.map(
                    client => (client.id === +id ? {
                        ...client, value: !client.value
                    } : client)
                )
            }
        })
    };

    fetchURL(page, query) {
        axios.get(`/client/list/${query}?page=${page}&size=5&companyType=${this.state.client.companyType}`)
            .then(response => {
                    const totalPages = response.data.totalPages;
                    const itemsCountPerPage = response.data.size;
                    const totalItemsCount = response.data.totalElements;

                    this.setState({
                        totalPages: totalPages,
                        totalItemsCount: totalItemsCount,
                        itemsCountPerPage: itemsCountPerPage,
                        clients: response.data.content
                    });
                }, error => {
                    const {status, statusText} = error.response;
                    const data = {status, statusText}
                    this.props.history.push('/error', {error: data});
                }
            );
    }

    componentDidMount() {
        this.fetchURL(this.state.activePage, this.state.query)
    }

    handlePageChange(pageNumber) {
        console.log(`active page is ${pageNumber}`);
        this.setState({activePage: pageNumber});
        this.fetchURL(pageNumber - 1, this.state.query)
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
        const clients = this.state.clients.map(client => {
            return <tr key={client.id}>
                <td><Input
                    type="checkbox"
                    id={client.id || ''}
                    name="selectedClients"
                    value={client.id || ''}
                    checked={client.value || ''}
                    onChange={this.handleChange}/></td>
                <td style={{whiteSpace: 'nowrap'}}><Link to={"/client/" + client.id}>{client.name}</Link></td>
                <td>{this.clientTypeMap[client.type]}</td>
                <td>{this.clientStatusMap[client.status]}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/client/" + client.id}>Редактировать</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(client.id)}>Удалить</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return clients
    };

    async remove(id) {
        await fetch(`/client/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
            }
        }).then(() => {
            let updateClients = [...this.state.clients].filter(i => i.id !== id);
            this.setState({clients: updateClients});
            this.handlePageChange(0);
        });
    }

    getSelected=()=>{
        return Array.apply(this,
            document.getElementsByName("selectedClients")).filter(function (el) {
            return el.checked === true
        }).map(function (el) {
            return el.value
        });
    };

    async removeChecked() {
        const selectedClients = this.getSelected();
        console.log(selectedClients);
        await fetch(`/client/${selectedClients}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
            }
        }).then(() => {
            let updateClients = [...this.state.clients].filter(client => !client.value);
            this.setState({clients: updateClients});
            this.handlePageChange(0);
        });
    }


    render() {
        const check = !this.state.clients.length;
        return (
            <Container className="text-center" fluid>
                <FormGroup>
                    <Input type="text" name="searchQuery" id="searchQuery" value={this.state.query}
                           onChange={this.handleQueryChange} autoComplete="searchQuery"/>
                </FormGroup>
                <div className="float-right">
                    <ButtonGroup>
                        <Button color="success" tag={Link} to="/client/create">Добавить</Button>
                        <Button color="danger" onClick={() => this.removeChecked()}>Удалить выбранные</Button>
                    </ButtonGroup>
                </div>
                {check ? <h3>Список пуст</h3> :
                    <div>
                        <Table className="mt-4">
                            <thead>
                            <tr>
                                <th width="5%"></th>
                                <th width="30%">Название</th>
                                <th width="30%">Тип</th>
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
                    </div>}
            </Container>
        );
    }
}

export default ClientListComponent;