import React from 'react';
import {Button, ButtonGroup, Container, FormGroup, Input, Table} from 'reactstrap';
import {Link} from 'react-router-dom';
import axios from 'axios';
import Pagination from "react-js-pagination";
import {ACCESS_TOKEN} from "../../constants/auth";
import {getSelected} from "../../utils/APIUtils";

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
            activePage: 0,
            query: '',
            totalPages: null,
            itemsCountPerPage: null,
            totalItemsCount: null,
            selectedUsers: []
        };
        this.queryTimeout = -1;
        this.handlePageChange = this.handlePageChange.bind(this);
        this.fetchURL = this.fetchURL.bind(this);
        this.removeChecked = this.removeChecked.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.sendEmail = this.sendEmail.bind(this);
        this.handleQueryChange = this.handleQueryChange.bind(this);
        this.changeQuery = this.changeQuery.bind(this);
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

    fetchURL(page, query) {
        console.log(localStorage.getItem(ACCESS_TOKEN));
        axios.get(`/user/list/${query}?page=${page}&size=5`, {
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
                    console.log(this.state);

                    this.setState({users: results});
                    console.log(results);
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

    sendEmail = () => {
        const selectedIds = getSelected();
        const selectedUsers = [];
        const {users} = this.state;
        for (let selected = 0; selected < selectedIds.length; selected++) {
            for (let userId = 0; userId < users.length; userId++) {
                const user = users[userId];
                if (selectedIds[selected] == user.id) {
                    selectedUsers.push(user);
                }
            }
        }
        this.props.history.push({
            pathname: "/email",
            state: {users: selectedUsers}
        });
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
                        <FormGroup>
                            <Input type="text" name="searchQuery" id="searchQuery" value={this.state.query}
                                   onChange={this.handleQueryChange} autoComplete="searchQuery"/>
                        </FormGroup>
                        <div className="float-right">
                            <ButtonGroup>
                                <Button color="success" tag={Link} to="/user/create">Добавить</Button>
                                <Button color="info" onClick={() => this.sendEmail()}>Отправить письмо</Button>
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
                        {/*<SendEmail email={this.state.selectedUsers}/>*/}
                    </Container>
                </div>
            </form>
        );
    }
}

export default UserListComponent;