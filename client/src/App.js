import React from 'react';
import {Router, Route, Redirect} from 'react-router'
import createBrowserHistory from './helpers/history';
import UserComponent from "./components/forms/UserComponent";
import CarComponent from "./components/forms/CarComponent";
import CarListComponent from "./components/pages/CarListComponent";
import ProductListComponent from "./components/pages/ProductListComponent";
import UserListComponent from "./components/pages/UserListComponent";
import ProductComponent from "./components/forms/ProductComponent";
import ClientComponent from "./components/forms/ClientComponent";
import LoginForm from "./components/forms/LoginForm";
import Home from "./components/home/Home"
import Navigation from "./components/Navigation"
import {ACCESS_TOKEN} from "./constants/auth";
import ProtectedRoute from './components/ProtectedRoute';

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            role: ''
        }
    }

    setRole(role) {
        this.setState({
            role: role
        });
        console.log(this.state);
    }

    handleLogout(){
        this.setState({
            role:''
        });
        localStorage.removeItem(ACCESS_TOKEN);
    }

    render() {
        const {role} = this.state;
        console.log(role);
        return (
            <Router history={createBrowserHistory}>
                <Navigation role={role} logout={this.handleLogout.bind(this)}/>
                <Route path="/home" component={Home}/>
                <ProtectedRoute path="/users" role={role} allowedRole='SYSADMIN' component={UserListComponent}/>
                <Route path="/user/:id" component={UserComponent}/>
                <Route path="/home" component={Home}/>
                <Route path="/user/:id" component={UserComponent}/>
                <Route path="/car/:id" component={CarComponent}/>
                <Route path="/product/:id" component={ProductComponent}/>
                <Route path="/cars" component={CarListComponent}/>
                {/*<Route path="/users" component={UserListComponent}/>*/}
                <Route path="/products" component={ProductListComponent}/>
                <Route path="/client/:id" component={ClientComponent}/>
                <Route path="/login" component={(props) => <LoginForm {...props} setRole={this.setRole.bind(this)}/>}/>
            </Router>
        );
    }
}


export default App;
