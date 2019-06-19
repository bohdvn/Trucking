import React from 'react';
import {Button, ButtonGroup, Container, FormGroup, Input, Table} from 'reactstrap';
import {Link} from 'react-router-dom';
import axios from 'axios';
import Pagination from "react-js-pagination";
import {ACCESS_TOKEN} from "../../constants/auth";
import * as ROLE from "../../constants/userConstants";

class UserListComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            activePage: 0,
            query: '',
            totalPages: null,
            itemsCountPerPage: null,
            totalItemsCount: null,
            selectedUsers: [],
            resultMessage: '',
            url: props.location.pathname
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

    getUserUrl = () => {
        const {url} = this.state;
        switch (url) {
            case '/drivers':
                return 'driver';

            case '/users':
                return 'user';

            default:
                return 'admin';
        }
    };


    fetchURL(page, query) {
        console.log(localStorage.getItem(ACCESS_TOKEN));
        const {url} = this.state.url;
        console.log(url);
        let apiUrl;
        console.log(url);
        switch (this.state.url) {
            case '/drivers':
                apiUrl = 'driverList';
                break;
            case '/users':
                apiUrl = 'list';
                break;
            default:
                return;
        }

        axios.get(`/user/${apiUrl}/${query}?page=${page}&size=5`)
            .then(response => {
                    console.log(response);
                    const totalPages = response.data.totalPages;
                    const itemsCountPerPage = response.data.size;
                    const totalItemsCount = response.data.totalElements;

                    this.setState({
                        totalPages: totalPages,
                        totalItemsCount: totalItemsCount,
                        itemsCountPerPage: itemsCountPerPage,
                        users: response.data.content
                    });
                    console.log(this.state)
                }, error => {
                    const {status, statusText} = error.response;
                    const data = {status, statusText}
                    this.props.history.push('/error', {error: data});
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
        console.log(this.state);
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

    getSelected=()=>{
        return Array.apply(this,
            document.getElementsByName("selectedUsers")).filter(function (el) {
            return el.checked === true
        }).map(function (el) {
            return el.value
        });
    };

    sendEmail = () => {
        const selectedIds = this.getSelected();
        if (selectedIds.length > 0) {
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
        } else {
            this.setState({
                resultMessage: "Не выбрано ни одного получателя! Пожалуйста выберите одного или несколько получателей."
            }, () => {
                alert(this.state.resultMessage);
            });
        }
    };

    getRoles=roles=>{
        let res='';
        roles.forEach(role=> {
            res+=ROLE[`${role}_RU`]+" ";
        });
        return res;
    };

    populateRowsWithData = () => {
        const users = this.state.users.map(user => {
            console.log(user.roles);
            return <tr key={user.id}>
                <td><Input
                    type="checkbox"
                    id={user.id || ''}
                    name="selectedUsers"
                    value={user.id || ''}
                    checked={user.value || ''}
                    onChange={this.handleChange}/></td>
                <td style={{whiteSpace: 'nowrap'}}><Link
                    to={`/${this.getUserUrl()}/${user.id}`}>{user.surname} {user.name} {user.patronymic}</Link></td>
                <td>{this.getRoles(user.roles)}</td>
                <td>{user.dateOfBirth}</td>
                <td>{user.login}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link}
                                to={`/${this.getUserUrl()}/${user.id}`}>Редактировать</Button>
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
        const selectedUsers = this.getSelected();
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
        const check = !this.state.users.length;
        console.log(check);
        return (
            <Container className="text-center" fluid>
                <FormGroup>
                    <Input type="text" name="searchQuery" id="searchQuery" value={this.state.query}
                           onChange={this.handleQueryChange} autoComplete="searchQuery"/>
                </FormGroup>
                <div className="float-right">
                    <ButtonGroup>
                        <Button color="success" tag={Link} to={`/${this.getUserUrl()}/create`}>Добавить</Button>
                        <Button color="info" onClick={() => this.sendEmail()}>Отправить письмо</Button>
                        <Button color="danger" onClick={() => this.removeChecked()}>Удалить выбранные</Button>
                    </ButtonGroup>
                </div>
                {check ? <h3>Список пуст</h3> :
                    <div>
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
                    </div>
                }
            </Container>
        );
    }
}

export default UserListComponent;