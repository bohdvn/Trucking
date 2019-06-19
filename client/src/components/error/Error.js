import React from 'react';

function Error(props) {
    const {error} = props.location.state;
    return (
        <div className="text-center">
            <h1 className="error-message">{error.status}</h1>
            <h3>{error.statusText}</h3>
        </div>
    );
}

export default Error;