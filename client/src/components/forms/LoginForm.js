import React from 'react';
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";

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

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let loginRequest = {...this.state.loginRequest};
        loginRequest[name] = value;
        this.setState({loginRequest});
        console.log(this.state);
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {loginRequest} = this.state;
        await fetch( '/user/login', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginRequest),
        });
        this.props.history.push('/users');
    }

    render() {
        const {loginRequest} =this.state;
        return (
            <Container className="col-3">
                <Form>
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

export default LoginForm;