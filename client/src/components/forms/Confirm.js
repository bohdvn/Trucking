import React, {Component} from 'react'
import {getUserById} from "../../utils/APIUtils";

class Confirm extends Component {

    constructor(props) {
        super(props);
        this.state = {
            message: ''
        };
    }

    componentDidMount = () => {
        const {id} = this.props.match.params;
        getUserById(`/user/confirm-account/${id}`)
            .then(resp => {
                if (resp.status === 200) {
                    const message = "Success! Account verified.";
                    this.setState({
                        message: message
                    });
                    alert(message);
                    return this.props.history.push('/home');
                }
                if (resp.status === 403) {
                    const message = "The link is invalid or your account has been already confirmed.";
                    this.setState({
                        message: message
                    });
                    alert(message);
                    return this.props.history.push('/login');
                } else {
                    const message = "Error.Something went wrong, please contact your administrator";
                    this.setState({
                        message: message
                    });
                    alert(message);
                    this.props.history.push('/login');
                }
            })
    };

    render = () =>
        <p>{this.state.message}</p>
}

export default Confirm;