import React from 'react';
import { Router, Route } from 'react-router';
import createBrowserHistory from './helpers/history';
import UserComponent from "./components/forms/UserComponent";
import CarComponent from "./components/forms/CarComponent";
import CarListComponent from "./components/pages/CarListComponent";
import UserListComponent from "./components/pages/UserListComponent";
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
import Error from './components/error/Error';

class App extends React.Component {
    render() {
        return (
            <Router history={createBrowserHistory}>
                <Navigation/>

                <Route exact path="/home" component={Home}/>
                <ProtectedRoute exact path="/driver/:id" allowed={[ROLE.SYSADMIN]} component={UserComponent}/>
                <ProtectedRoute exact path="/drivers" allowed={[ROLE.SYSADMIN]} component={UserListComponent}/>
                <ProtectedRoute exact path="/clients" allowed={[ROLE.SYSADMIN]} component={ClientListComponent}/>
                <ProtectedRoute exact path="/client/:id" allowed={[ROLE.SYSADMIN]} component={ClientComponent}/>
                <ProtectedRoute exact path="/cars" allowed={[ROLE.SYSADMIN]} component={CarListComponent}/>
                <ProtectedRoute exact path="/car/:id" allowed={[ROLE.SYSADMIN]} component={CarComponent}/>
                <ProtectedRoute exact path="/admin/:id" allowed={[ROLE.SYSADMIN]} component={UserComponent}/>
                <ProtectedRoute exact path='/report' allowed={[ROLE.SYSADMIN]} component={ReportComponent}/>
                <ProtectedRoute exact path='/email' allowed={[ROLE.SYSADMIN,ROLE.ADMIN]} component={SendEmail}/>


                {/*ADMIN*/}
                <ProtectedRoute exact path="/users" allowed={[ROLE.ADMIN]} component={UserListComponent}/>
                <ProtectedRoute exact path="/user/:id" allowed={[ROLE.ADMIN]} component={UserComponent}/>

                {/*OWNER*/}
                <ProtectedRoute exact path='/request/:id' allowed={[ROLE.OWNER,ROLE.DISPATCHER]} component={RequestComponent}/>
                <ProtectedRoute exact path="/requests" allowed={ROLE.OWNER} component={RequestListComponent}/>
                {/*DISPATCHER*/}
                <ProtectedRoute exact path="/notviewedrequests" allowed={ROLE.DISPATCHER} component={RequestListComponent}/>

                {/*MANAGER*/}
                <ProtectedRoute exact path="/waybill/:id" allowed={[ROLE.MANAGER, ROLE.DRIVER]} component={WaybillComponent}/>
                <ProtectedRoute exact path="/invoices" allowed={ROLE.MANAGER} component={InvoiceListComponent}/>


                {/*DRIVER*/}
                <ProtectedRoute exact path="/waybills" allowed={ROLE.DRIVER} component={WaybillListComponent}/>

                {/*MANAGER*/}

                <Route path="/login" component={LoginForm}/>
                <Route path='/confirm/:id' component={Confirm}/>
                <Route path='/error' component={Error}/>
            </Router>
        );
    }
}

export default connect(
    state => ({
        loggedIn: state.loggedIn,
    })
)(App);