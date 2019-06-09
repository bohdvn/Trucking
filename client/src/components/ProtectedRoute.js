import React from 'react';
import { connect } from 'react-redux';
import { Redirect, Route } from 'react-router';

const ProtectedRoute = ({ component: Component, ...rest }) => (
    <Route {...rest} render={props => (
        (rest.allowed.includes(rest.loggedIn.roles[0].authority)) ? (
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