import React from 'react';
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import {connect} from "react-redux";
import {changeLoggedIn} from "../../actions/user";
import {setToken} from "../../utils/tokenParser";
import axios from 'axios';

const ACCESS_DENIED_INFO = "accessDeniedInfo";

class LoginForm extends React.Component {

    state={
        loginRequest : {
            loginOrEmail: '',
            password: ''
        }
    };

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
        axios.post('/user/login', loginRequest)
            .then(response => {
                    const token = response.data.accessToken;
                    const loggedIn = setToken(token);
                    this.props.changeLoggedIn(loggedIn);
                    this.props.history.push('/home');
                }, error => {
                    if(error.response.status===401){
                        const accessDeniedBox = document.getElementById(ACCESS_DENIED_INFO);
                        accessDeniedBox.innerText = "Неправильные логин и пароль";
                    }
                    else{
                        const {status, statusText} = error.response;
                        const data={status, statusText}
                        this.props.history.push('/error',{error:data});
                    }
                }
            );
    };

    render() {
        const {loginRequest} = this.state;
        return (
            <Container className="col-3">
                <Form onSubmit={this.handleSubmit}>
                    <h1 className="text-center">Вход</h1>
                    <p className="text-center error-message" id={ACCESS_DENIED_INFO}></p>
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
                    <FormGroup className="text-center">
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