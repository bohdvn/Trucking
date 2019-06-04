import React from 'react';
import {Button, ButtonGroup, Container, Input, Table} from 'reactstrap';
import {Link} from 'react-router-dom';
import axios from 'axios';
import Pagination from "react-js-pagination";
import {ACCESS_TOKEN} from "../../constants/auth";


class UserListComponent extends React.Component {

    userRoles = [
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
            itemsCountPerPage: null,
            totalItemsCount: null
        };
        this.handlePageChange = this.handlePageChange.bind(this);
        this.fetchURL = this.fetchURL.bind(this);
        this.removeChecked = this.removeChecked.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    fetchURL(page) {
        console.log(localStorage.getItem(ACCESS_TOKEN));
        axios.get(`/user/list?page=${page}&size=5`, {
            proxy: {
                host: 'http://localhost',
                port: 8080
            },
            headers:{
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
                    console.log(this.state);

                    this.setState({users: results});
                    console.log(results);
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
        this.fetchURL(pageNumber-1)
    }

    handleChange = e => {
        const id = e.target.id;
        this.setState(prevState => {
            return {
                users: prevState.users.map(
                    user => (user.id === +id ? {
                        ...user, value: !user.value
                    } : user)
                )
            }
        })
    };

    populateRowsWithData = () => {
        const users = this.state.users.map(user => {
            return <tr key={user.id}>
                <td><Input
                    type="checkbox"
                    id={user.id || ''}
                    name="selected_users"
                    value={user.id || ''}
                    checked={user.value || ''}
                    onChange={this.handleChange}/></td>
                <td style={{whiteSpace: 'nowrap'}}><Link
                    to={"/user/" + user.id}>{user.surname} {user.name} {user.patronymic}</Link></td>
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
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
            }
        }).then(() => {
            let updateUsers = [...this.state.users].filter(i => i.id !== id);
            this.setState({users: updateUsers});
            this.handlePageChange(0);
        });
    }

    async removeChecked() {
        const selectedUsers = Array.apply(null,
            document.users.selected_users).filter(function (el) {
            return el.checked === true
        }).map(function (el) {
            return el.value
        });
        console.log(selectedUsers);
        await fetch(`/user/${selectedUsers}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
            }
        }).then(() => {
                let updateUsers = [...this.state.users].filter(user => !user.value);
                this.setState({users: updateUsers});
                this.handlePageChange(0);
        });
    }

    render() {
        return (
            <form name="users">
            <div>
                <Container fluid>
                    <div className="float-right">
                        <ButtonGroup>
                        <Button color="success" tag={Link} to="/user/create">Добавить</Button>
                            <Button color="danger" onClick={() => this.removeChecked()}>Удалить выбранные</Button>
                        </ButtonGroup>
                    </div>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th></th>
                            <th width="40%">Имя</th>
                            <th width="15%">Роль</th>
                            <th width="15%">Дата рождения</th>
                            <th>Логин</th>
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
            </form>
        );
    }
}

export default UserListComponent;