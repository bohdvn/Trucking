import React from 'react';
import {Router, Route} from 'react-router'
import createBrowserHistory  from './helpers/history';
import UserComponent from "./components/UserComponent";

class App extends React.Component {
    render() {
        return (
            <div>
                <Router history={createBrowserHistory}>
                    <Route path="/users/:id" component={UserComponent}/>
                </Router>
            </div>
        );
    }
}

export default App;
