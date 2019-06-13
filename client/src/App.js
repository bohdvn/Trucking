import React from 'react';
import {Route, Router} from 'react-router'
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
import {connect} from 'react-redux';
import {changeLoggedIn} from './actions/user';
import ProtectedRoute from "./components/ProtectedRoute";
import ClientListComponent from "./components/pages/ClientListComponent";
import WaybillListComponent from "./components/pages/WaybillListComponent";
import WarehouseListComponent from "./components/pages/WarehouseListComponent";
import WarehouseComponent from "./components/forms/WarehouseComponent";
import Navigation from "./components/Navigation";
import RequestComponent from "./components/forms/RequestComponent";
import RequestListComponent from "./components/pages/RequestListComponent";
import InvoiceListComponent from "./components/pages/InvoiceListComponent";
import WaybillComponent from "./components/forms/WaybillComponent";
import Confirm from './components/forms/Confirm';

class App extends React.Component {
    constructor(props){
        super(props);
    }
    render() {
        console.log(this.props.loggedIn);
        const {role}=this.props.loggedIn.roles[0].authority;
        console.log(this.props.loggedIn.roles[0].authority);
        console.log(role);
        return (
            <Router history={createBrowserHistory}>
                <Navigation/>
                <Route path="/home" component={Home}/>
                <Route path="/user/:id" component={UserComponent}/>
                <Route path="/car/:id" component={CarComponent}/>
                <Route path="/product/:id" component={ProductComponent}/>
                <Route path="/cars" component={CarListComponent}/>
                <Route path="/products" component={ProductListComponent}/>
                <Route path="/client/:id" component={ClientComponent}/>
                <Route path="/login" component={LoginForm}/>
                <Route path="/clients" component={ClientListComponent}/>
                <Route path="/waybills" component={WaybillListComponent}/>
                <ProtectedRoute exact path="/users" allowed='SYSADMIN' component={UserListComponent}/>
                <Route path="/request/:id" component={RequestComponent}/>
                <Route path="/requests" component={RequestListComponent}/>
                <Route path="/invoices" component={InvoiceListComponent}/>
                <Route path="/waybill/:id" component={WaybillComponent}/>
                <Route path="/warehouse/:id" component={WarehouseComponent}/>
                <Route path="/warehouses" component={WarehouseListComponent}/>
                <Route path='/confirm/:id' component={Confirm}/>
            </Router>
        );
    }
}

export default connect(
    state => ({
        loggedIn: state.loggedIn,
    }), {
        changeLoggedIn,
    }
)(App);