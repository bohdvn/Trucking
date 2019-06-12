//Dmitry Gorlach
import React, {Component} from 'react';
import {Button, Form, FormGroup, Input, Label} from 'reactstrap';
import './../../styles.css';
import {ACCESS_TOKEN} from "../../constants/auth";

const RECIPIENTS = "Кому:";
const SUBJECT = "Тема:";
const MESSAGE = "Сообщение:";
const SEND = "Отправить";

class SendEmail extends Component {

    emptyEmail = {
        recipients: this.props.location.state.users,
        subject: '',
        message: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            email: this.emptyEmail,
            resultMessage: ''
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let email = {...this.state.email};
        email[name] = value;
        this.setState({email});
        // this.setState({value: event.target.value});
        // console.log(event.target.value)
        console.log(this.state);
    }

    setRecipientsNames = () => {
        console.log(this.state.email.selectedUsers);
        const recipients = this.state.email.recipients.map(user => {
            return <li className="Success" key={user.id}>
                {user.surname} {user.name} {user.patronymic} ({user.email})
            </li>
        });
        return recipients;
    };

    async handleSubmit(event) {
        event.preventDefault();
        const {email} = this.state;
        console.log(email);
        await fetch('/email', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
            },
            body: JSON.stringify(email)
        }).then(resp => {
            if (resp.status === 200) {
                const message = "Success! Email was sent.";
                this.setState({
                    resultMessage: message
                });
                alert(message);
                return this.props.history.push('/users');
            } else {
                const message = "Error.Something went wrong, please try send email again.";
                this.setState({
                    message: message
                });
                alert(message);
                this.props.history.push('/users');
            }
        })
    }

    render() {
        return (
            <Form className="Email" onSubmit={this.handleSubmit}>
                <br/>
                <p> {RECIPIENTS}</p>
                {this.setRecipientsNames()}
                <FormGroup>
                    <Label for="subject">{SUBJECT}</Label>
                    <Input
                        type="text"
                        name="subject"
                        onChange={this.handleChange}/>
                </FormGroup>
                <FormGroup>
                    <Label for="message">{MESSAGE}</Label>
                    <Input
                        type="textarea"
                        name="message"
                        onChange={this.handleChange}/>
                </FormGroup>
                <Button>{SEND}</Button>
            </Form>
        )
    }
}

export default SendEmail;