import React from 'react';
import { Route, Redirect } from 'react-router';

const ProtectedRoute = ({ component: Component, role,allowedRole, ...rest }) => (
    <Route
        {...rest}
        render={props => (
            role===allowedRole
                ? <Component {...props} />
                : <Redirect to="/login" />
        )}
    />
);

export default ProtectedRoute;