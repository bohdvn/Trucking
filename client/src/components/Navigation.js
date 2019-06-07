import React from 'react';
import Navbar from "reactstrap/es/Navbar";
import NavItem from "reactstrap/es/NavItem";
import NavLink from "reactstrap/es/NavLink";
import Nav from "reactstrap/es/Nav";
import {connect} from "react-redux";
import {changeLoggedIn} from "../actions/user";

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

    logout(){
        localStorage.removeItem('accessToken');
        localStorage.removeItem('loggedIn');
    }

    getNavs() {
        const navs = [];
        const role = this.state.loggedIn.roles[0].authority;
        console.log(role);
        const logout= <NavItem><NavLink onClick={this.logout} href="/home">Выйти</NavLink></NavItem>;
        switch (role) {
            case 'SYSADMIN':
                navs.push(
                    <NavItem>
                        <NavLink href="/client/create">Клиенты</NavLink>
                    </NavItem>,
                    <NavItem>
                        <NavLink href="/cars">Автомобили</NavLink>
                    </NavItem>,
                    <NavItem>
                        <NavLink href="/users">Водители</NavLink>
                    </NavItem>,
                    logout
                );
                break;

            case 'ADMIN':
                navs.push(
                    <NavItem>
                        <NavLink href="/users">Пользователи</NavLink>
                    </NavItem>,
                    logout
                );
                break;

            case 'DISPATCHER':
                navs.push(
                    <NavItem>
                        <NavLink href="/invoices">Список ТТН</NavLink>
                    </NavItem>,
                    logout
                );
                break;

            case 'MANAGER':
                navs.push(
                    <NavItem>
                        <NavLink href="/invoices">Список ТТН</NavLink>
                    </NavItem>,
                    logout
                );
                break;

            case 'DRIVER':
                navs.push(
                    <NavItem>
                        <NavLink href="/waybills">Путевые листы</NavLink>
                    </NavItem>,
                    logout
                );
                break;

            case 'OWNER':
                navs.push(
                    <NavItem>
                        <NavLink href="/">Заявки</NavLink>
                    </NavItem>,
                    <NavItem>
                        <NavLink href="/invoices">Список ТТН</NavLink>
                    </NavItem>,
                    <NavItem>
                        <NavLink href="/users">Водители</NavLink>
                    </NavItem>,
                    <NavItem>
                        <NavLink href="/invoices">Список ТТН</NavLink>
                    </NavItem>,
                    logout
                );
                break;
            default:
                navs.push(
                    <NavItem>
                        <NavLink href="/login">Войти</NavLink>
                    </NavItem>
                );
        }
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

// export default Navigation;

export default connect(
    state => ({
        loggedIn: state.loggedIn,
    }), {
        changeLoggedIn,
    }
)(Navigation);