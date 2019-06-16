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
import RequestComponent from "./components/forms/RequestComponent";
import RequestListComponent from "./components/pages/RequestListComponent";
import InvoiceListComponent from "./components/pages/InvoiceListComponent";
import WaybillComponent from "./components/forms/WaybillComponent";
import "@kenshooui/react-multi-select/dist/style.css"
import Confirm from './components/forms/Confirm';
import Navigation from './components/Navigation'
import ReportComponent from './components/forms/ReportComponent';
import SendEmail from "./components/forms/SendEmail";
import * as ROLE from './constants/userConstants';

class App extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Router history={createBrowserHistory}>
                <Navigation/>

                <Route path="/home" component={Home}/>
                <ProtectedRoute exact path="/driver/:id" allowed={[ROLE.SYSADMIN]} component={UserComponent}/>
                <ProtectedRoute exact path="/drivers" allowed={[ROLE.SYSADMIN]} component={UserListComponent}/>
                <ProtectedRoute exact path="/clients" allowed={[ROLE.SYSADMIN]} component={ClientListComponent}/>
                <ProtectedRoute exact path="/cars" allowed={[ROLE.SYSADMIN]} component={CarListComponent}/>
                <ProtectedRoute exact path="/car/:id" allowed={[ROLE.SYSADMIN]} component={CarComponent}/>
                <ProtectedRoute exact path="/admin/:id" allowed={[ROLE.SYSADMIN]} component={UserComponent}/>

                {/*ADMIN*/}
                <ProtectedRoute exact path="/users" allowed={[ROLE.ADMIN]} component={UserListComponent}/>
                <ProtectedRoute exact path="/user/:id" allowed={[ROLE.ADMIN]} component={UserComponent}/>

                {/*OWNER*/}
                <ProtectedRoute exact path='/request/:id' allowed={[ROLE.OWNER,ROLE.DISPATCHER]} component={RequestComponent}/>
                <ProtectedRoute exact path='/report' allowed={[ROLE.OWNER]} component={ReportComponent}/>
                <ProtectedRoute exact path="/requests" allowed={ROLE.OWNER} component={RequestListComponent}/>
                {/*DISPATCHER*/}
                <ProtectedRoute exact path="/notviewedrequests" allowed={ROLE.DISPATCHER} component={RequestListComponent}/>

                {/*DRIVER*/}
                <ProtectedRoute exact path="/waybills" allowed='DRIVER' component={WaybillListComponent}/>
                <ProtectedRoute exact path="/waybill/:id" allowed='DRIVER' component={WaybillComponent}/>

                {/*MANAGER*/}




                {/*<Route path="/user/:id" component={UserComponent}/>*/}
                <Route path="/car/:id" component={CarComponent}/>
                <Route path="/product/:id" component={ProductComponent}/>
                <Route path="/products" component={ProductListComponent}/>
                <Route path="/client/:id" component={ClientComponent}/>
                <Route path="/login" component={LoginForm}/>
                <Route path="/waybills" component={WaybillListComponent}/>

                {/*<Route path="/request/:id" component={RequestComponent}/>*/}
                <Route path="/invoices" component={InvoiceListComponent}/>

                <Route path="/waybill/:id" component={WaybillComponent}/>
                <Route path='/confirm/:id' component={Confirm}/>
                <Route path='/email' component={SendEmail}/>
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