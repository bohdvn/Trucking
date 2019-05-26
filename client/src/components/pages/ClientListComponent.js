import React from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
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
            totalPages: null,
            itemsCountPerPage: null,
            totalItemsCount: null
        };
        this.handlePageChange = this.handlePageChange.bind(this);
        this.fetchURL = this.fetchURL.bind(this);
        this.remove = this.remove.bind(this);
    }

    fetchURL(page) {
        axios.get(`/client/list?page=${page}&size=5`, {
            proxy: {
                host: 'http://localhost',
                port: 8080
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
                    console.log(this.state);

                    if (results != null) {
                        this.setState({clients: results});
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
        const clients = this.state.clients.map(client => {
            return <tr key={client.id}>
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
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updateClients = [...this.state.clients].filter(i => i.id !== id);
            this.setState({clients: updateClients});
        });
    }


    render() {
        return (
            <div>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/client/create">Добавить</Button>
                    </div>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Название</th>
                            <th width="20%">Тип</th>
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
                </Container>
            </div>
        );
    }
}

export default ClientListComponent;