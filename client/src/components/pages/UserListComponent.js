import React from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import {Link} from 'react-router-dom';
import axios from 'axios';
import Pagination from "react-js-pagination";


class UserListComponent extends React.Component {

    userRoles=[
        'Системный администратор',
        'Администратор',
        'Менеджер',
        'Диспетчер',
        'Водитель',
        'Владелец'
    ];

    constructor(props) {
        super(props);
        this.state = {
            users: [],
            activePage:0,
            totalPages: null,
            itemsCountPerPage:null,
            totalItemsCount:null
        };
        this.handlePageChange = this.handlePageChange.bind(this);
        this.fetchURL = this.fetchURL.bind(this);
        this.remove = this.remove.bind(this);
    }

    fetchURL(page) {
        axios.get(`/user/list?page=${page}&size=5`, {
            proxy: {
                host: 'http://localhost',
                port: 8080}
        })
            .then( response => {
                    console.log(response);
                    const totalPages = response.data.totalPages;
                    const itemsCountPerPage = response.data.size;
                    const totalItemsCount = response.data.totalElements;

                    this.setState({totalPages: totalPages});
                    this.setState({totalItemsCount: totalItemsCount});
                    this.setState({itemsCountPerPage: itemsCountPerPage});

                    const results = response.data.content;
                    console.log(this.state);

                    this.setState({users: results});
                    console.log(results);
                    console.log(this.state.activePage);
                    console.log(this.state.itemsCountPerPage);
                }
            );
    }

    componentDidMount () {
        this.fetchURL(this.state.activePage)
    }

    handlePageChange(pageNumber) {
        console.log(`active page is ${pageNumber}`);
        this.setState({activePage: pageNumber});
        this.fetchURL(pageNumber-1)
    }

    populateRowsWithData = () => {
        const users = this.state.users.map(user => {
            return <tr key={user.id}>
                <td style={{whiteSpace: 'nowrap'}}>{user.surname} {user.name} {user.patronymic}</td>
                <td>{this.userRoles[user.role]}</td>
                <td>{user.dateOfBirth}</td>
                <td>{user.login}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/user/" + user.id}>Редактировать</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(user.id)}>Удалить</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return users
    };

    async remove(id) {
        await fetch(`/user/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updateUsers = [...this.state.users].filter(i => i.id !== id);
            this.setState({users: updateUsers});
        });
    }


    render() {
        return (
            <div>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/car/create">Добавить</Button>
                    </div>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="40%">Имя</th>
                            <th width="15%">Роль</th>
                            <th width="15%">Расход топлива</th>
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
                            hideNavigation
                            activePage={this.state.activePage}
                            itemsCountPerPage={this.state.itemsCountPerPage}
                            totalItemsCount={this.state.totalItemsCount}
                            pageRangeDisplayed={10}
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

export default UserListComponent;