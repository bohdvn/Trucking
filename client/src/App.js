import React from 'react';
import {Router, Route} from 'react-router'
import createBrowserHistory  from './helpers/history';
import UserComponent from "./components/forms/UserComponent";
import CarComponent from "./components/forms/CarComponent";
import CarListComponent from "./components/pages/CarListComponent";
import ProductComponent from "./components/forms/ProductComponent";

class App extends React.Component {
    render() {
        return (
            <div>
                <Router history={createBrowserHistory}>
                    <Route path="/user/:id" component={UserComponent}/>
                    <Route path="/car/:id" component={CarComponent}/>
                    <Route path="/product/:id" component={ProductComponent}/>
                    <Route path="/cars" component={CarListComponent}/>
                </Router>
            </div>
        );
    }
}

export default App;
