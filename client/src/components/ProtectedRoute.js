import React from 'react';
import { connect } from 'react-redux';
import { Redirect, Route } from 'react-router';

const ProtectedRoute = ({ component: Component, ...rest }) => (
    <Route {...rest} render={props => (
        (rest.loggedIn.roles[0].authority===rest.allowed) ? (
            <Component {...props} />
        ) : (
            <Redirect to={{
                pathname: '/login',
                state: { from: props.location }
            }} />
        )
    )} />
);

export default connect(
    state => ({
        loggedIn: state.loggedIn,
    })
)(ProtectedRoute);