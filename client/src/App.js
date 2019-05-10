import React from 'react';
import {Router, Route} from 'react-router'
import createBrowserHistory  from './helpers/history';
import UserComponent from "./components/UserComponent";
import CarComponent from "./components/CarComponent";
import ProductComponent from "./components/ProductComponent";

class App extends React.Component {
    render() {
        return (
            <div>
                <Router history={createBrowserHistory}>
                    <Route path="/users/:id" component={UserComponent}/>
                    <Route path="/cars/:id" component={CarComponent}/>
                    <Route path="/products/:id" component={ProductComponent}/>
                </Router>
            </div>
        );
    }
}

export default App;
