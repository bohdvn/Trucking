import React from 'react';
import Navbar from "reactstrap/es/Navbar";
import NavItem from "reactstrap/es/NavItem";
import NavLink from "reactstrap/es/NavLink";
import Nav from "reactstrap/es/Nav";
import {connect} from "react-redux";
import {changeLoggedIn} from "../actions/user";
import * as ROLE from "../constants/userConstants";

class Navigation extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loggedIn: props.loggedIn
        };
        console.log(this.state.loggedIn);
    }

    componentWillReceiveProps({loggedIn}) {
        console.log(loggedIn);
        this.setState({loggedIn: loggedIn});
    }

    logout = () => {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('loggedIn');
    };

    getNavs = () => {
        const navs = [];
        const roles = this.state.loggedIn.claims.roles;
        console.log(roles);
        const logout = <NavItem><NavLink onClick={this.logout} href="/home">Выйти</NavLink></NavItem>;
        roles.forEach(role => {
            switch (role) {
                case ROLE.SYSADMIN:
                    navs.push(
                        <NavItem>
                            <NavLink href="/clients">Клиенты</NavLink>
                        </NavItem>,
                        <NavItem>
                            <NavLink href="/cars">Автомобили</NavLink>
                        </NavItem>,
                        <NavItem>
                            <NavLink href="/drivers">Водители</NavLink>
                        </NavItem>
                    );
                    break;

                case ROLE.ADMIN:
                    navs.push(
                        <NavItem>
                            <NavLink href="/users">Пользователи</NavLink>
                        </NavItem>
                    );
                    break;

                case ROLE.DISPATCHER:
                    navs.push(
                        <NavItem>
                            <NavLink href="/invoices">Список ТТН</NavLink>
                        </NavItem>
                    );
                    break;

                case ROLE.MANAGER:
                    navs.push(
                        <NavItem>
                            <NavLink href="/invoices">Список ТТН</NavLink>
                        </NavItem>
                    );
                    break;

                case ROLE.DRIVER:
                    navs.push(
                        <NavItem>
                            <NavLink href="/waybills">Путевые листы</NavLink>
                        </NavItem>
                    );
                    break;

                case ROLE.OWNER:
                    navs.push(
                        <NavItem>
                            <NavLink href="/requests">Запросы</NavLink>
                        </NavItem>,
                        <NavItem>
                            <NavLink href="/invoices">Список ТТН</NavLink>
                        </NavItem>,
                        <NavItem>
                            <NavLink href="/report">Отчет</NavLink>
                        </NavItem>
                    );
                    break;
                default:
                    navs.push(
                        <NavItem>
                            <NavLink href="/login">Войти</NavLink>
                        </NavItem>
                    );
            }
        });
        navs.push(logout);
        return navs;
    };

    render() {
        return (
            <Navbar light className="bg-light">
                <Nav>
                    {this.getNavs()}
                </Nav>
            </Navbar>
        );
    }
}

export default connect(
    state => ({
        loggedIn: state.loggedIn,
    }), {
        changeLoggedIn,
    }
)(Navigation);