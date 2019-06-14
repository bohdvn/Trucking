import React from 'react';
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import {login} from '../../utils/APIUtils';
import {connect} from "react-redux";
import {changeLoggedIn} from "../../actions/user";
import {setToken} from "../../utils/tokenParser";

class LoginForm extends React.Component {
    loginRequest = {
        loginOrEmail: '',
        password: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            loginRequest: this.loginRequest
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange = (event) => {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let loginRequest = {...this.state.loginRequest};
        loginRequest[name] = value;
        this.setState({loginRequest});
    };

    handleSubmit = (event) => {
        event.preventDefault();
        const {loginRequest} = this.state;
        login('/user/login', loginRequest)
            .then(response =>
                response.json().then(json => {
                    if (!response.ok) {
                        return Promise.reject(json);
                    }
                    return json;
                })
            )
            .then(data => {
                const token = data.accessToken;
                const loggedIn=setToken(token);
                console.log(loggedIn);
                this.props.changeLoggedIn(loggedIn);
                console.log(localStorage.getItem('accessToken'));
                this.props.history.push('/home');
                alert(`${this.state.loginRequest.loginOrEmail}, добро пожаловать!`);
            });
    };

    render() {
        const {loginRequest} = this.state;
        return (
            <Container className="col-3">
                <Form onSubmit={this.handleSubmit}>
                    <h1>Вход</h1>
                    <FormGroup>
                        <Label for="loginOrEmail">Логин/email</Label>
                        <Input type="text" name="loginOrEmail" id="loginOrEmail" value={loginRequest.loginOrEmail || ''}
                               onChange={this.handleChange} autoComplete="loginOrEmail"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="password">Пароль</Label>
                        <Input type="password" name="password" id="password" value={loginRequest.password || ''}
                               onChange={this.handleChange} autoComplete="password"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Войти</Button>{' '}
                    </FormGroup>
                </Form>
            </Container>
        );
    }
}

export default connect(
    state => ({
        loggedIn: state.loggedIn,
    }), {
        changeLoggedIn,
    }
)(LoginForm)