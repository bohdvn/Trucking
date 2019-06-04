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
import ClientListComponent from "./components/pages/ClientListComponent";
import WaybillListComponent from "./components/pages/WaybillListComponent";
import WarehouseListComponent from "./components/pages/WarehouseListComponent";
import WarehouseComponent from "./components/forms/WarehouseComponent";

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
                    <Route path="/warehouse/:id" component={WarehouseComponent}/>
                    <Route path="/clients" component={ClientListComponent}/>
                    <Route path="/warehouses" component={WarehouseListComponent}/>
                    <Route path="/waybills" component={WaybillListComponent}/>
                </Router>
            </div>
        );
    }
}

export default App;
