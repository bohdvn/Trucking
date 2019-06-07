import React from 'react';
import {Router, Route} from 'react-router'
import createBrowserHistory  from './helpers/history';
import UserComponent from "./components/forms/UserComponent";
import CarComponent from "./components/forms/CarComponent";
import CarListComponent from "./components/pages/CarListComponent";
import ProductListComponent from "./components/pages/ProductListComponent";
import UserListComponent from "./components/pages/UserListComponent";
import ProductComponent from "./components/forms/ProductComponent";
import ClientComponent from "./components/forms/ClientComponent";
import RequestComponent from "./components/forms/RequestComponent";
import Example from "./components/forms/Example";
import RequestListComponent from "./components/pages/RequestListComponent";
import InvoiceComponent from "./components/forms/InvoiceComponent";
import InvoiceListComponent from "./components/pages/InvoiceListComponent";
import WaybillListComponent from "./components/pages/WaybillListComponent";
import WaybillComponent from "./components/forms/WaybillComponent";

class App extends React.Component {
    render() {
        return (
            <div>
                <Router history={createBrowserHistory}>
                    <Route path="/user/:id" component={UserComponent}/>
                    <Route path="/car/:id" component={CarComponent}/>
                    <Route path="/product/:id" component={ProductComponent}/>
                    <Route path="/cars" component={CarListComponent}/>
                    <Route path="/users" component={UserListComponent}/>
                    <Route path="/products" component={ProductListComponent}/>
                    <Route path="/client/:id" component={ClientComponent}/>
                    <Route path="/request/:id" component={RequestComponent}/>
                    <Route path="/example" component={Example}/>
                    <Route path="/requests" component={RequestListComponent}/>
                    <Route path="/invoices" component={InvoiceListComponent}/>
                    <Route path="/waybill/:id" component={WaybillComponent}/>
                    <Route path="/waybills" component={WaybillListComponent}/>
                </Router>
            </div>
        );
    }
}

export default App;
