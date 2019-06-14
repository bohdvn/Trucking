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
                    this.setState({
                        message: "Success! Account verified."
                    });
                    alert(this.state.message);
                    return this.props.history.push('/home');
                }
                if (resp.status === 403) {
                    this.setState({
                        message: "The link is invalid or your account has been already confirmed."
                    });
                    alert(this.state.message);
                    return this.props.history.push('/login');
                } else {
                    this.setState({
                        message: "Error.Something went wrong, please contact your administrator"
                    });
                    alert(this.state.message);
                    this.props.history.push('/login');
                }
            })
    };

    render = () =>
        <p>{this.state.message}</p>
}

export default Confirm;