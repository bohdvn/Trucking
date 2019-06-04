import React from 'react';
import Navbar from "reactstrap/es/Navbar";
import NavItem from "reactstrap/es/NavItem";
import NavLink from "reactstrap/es/NavLink";
import Nav from "reactstrap/es/Nav";

class Navigation extends React.Component{
    constructor(props){
        super(props);
        this.state={
            role:props.role
        };
    }

    componentWillReceiveProps({role}) {
        this.setState({role:role});
    }

    getNavs(){
        const navs=[];
        const role=this.state.role;
        console.log(role);
        switch (role) {
            case 'SYSADMIN':
                navs.push(
                    <NavItem>
                        <NavLink href="/user/create">Создать пользователя</NavLink>
                    </NavItem>);
                break;

            case 'ADMIN':
                navs.push(
                    <NavItem>
                        <NavLink href="/users">Пользователи</NavLink>
                    </NavItem>);
                break;

            case 'DISPATCHER':
                break;

            case 'MANAGER':
                break;

            case 'DRIVER':
                break;

            case 'OWNER':
                break;
        }
        return navs;
    };

    render(){
        return (
            <Navbar>
                <Nav>
                    <NavItem>
                        <NavLink href="/login">Войти</NavLink>
                    </NavItem>
                    {this.getNavs()}
                    {/*<NavItem>*/}
                        {/*<NavLink href="/home" onClick={this.props.logout}>Выйти</NavLink>*/}
                    {/*</NavItem>*/}
                </Nav>
            </Navbar>
        );
    }
}

export default Navigation;