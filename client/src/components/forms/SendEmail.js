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
        message: '',
        object: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            email: this.emptyEmail,
            resultMessage: '',
            subject: '',
            message: '',
            formErrors: {subject: '', message: ''},
            subjectValid: false,
            messageValid: false,
            formValid: false
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange = (e) => {
        const name = e.target.name;
        const value = e.target.value;
        let email = {...this.state.email};
        email[name] = value;
        this.setState({email}, () => {
            this.validateField(name, value);
            console.log("New state in ASYNC callback:", this.state.email)
        });
    };

    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let subjectValid = this.state.subjectValid;
        let messageValid = this.state.messageValid;

        switch (fieldName) {
            case 'subject':
                subjectValid = value.length >= 2 && value.length <= 150;
                fieldValidationErrors.subject = subjectValid ? '' : ' - тема не заполнена (от 1 до 150 символов)';
                break;
            case 'message':
                messageValid = value.length >= 5 && value.length <= 500;
                fieldValidationErrors.message = messageValid ? '' : ' - слишком короткое сообщение (от 5 до 500 символов)';
                break;
            default:
                break;
        }
        this.setState({
            formErrors: fieldValidationErrors,
            subjectValid: subjectValid,
            messageValid: messageValid
        }, this.validateForm);
    }

    validateForm() {
        this.setState({
            formValid:
                this.state.subjectValid &&
                this.state.messageValid
        });
    }

    errorClass(error) {
        return (error.length === 0 ? '' : 'has-error');
    }

    setRecipientsNames = () => {
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
                this.setState({
                    resultMessage: "Success! Email was sent.",
                }, () => {
                    alert(this.state.resultMessage);
                });
                return this.props.history.push('/users');
            } else {
                this.setState({
                    resultMessage: "Error.Something went wrong, please try send email again."
                }, () => {
                    alert(this.state.resultMessage);
                });
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
                <br/>
                <FormGroup>
                    <div className={`form-group ${this.errorClass(this.state.formErrors.subject)}`}>
                        <Label for="subject">{SUBJECT}</Label>
                        <Input
                            type="text"
                            name="subject"
                            onChange={this.handleChange}/>
                    </div>
                    <p className={'error-message'}>{(this.state.formErrors.subjectValid === '') ? ''
                        : this.state.formErrors.subject}</p>
                </FormGroup>
                <FormGroup>
                    <div className={`form-group ${this.errorClass(this.state.formErrors.message)}`}>
                        <Label for="message">{MESSAGE}</Label>
                        <Input
                            type="textarea"
                            name="message"
                            onChange={this.handleChange}/>
                    </div>
                    <p className={'error-message'}>{(this.state.formErrors.messageValid === '') ? ''
                        : this.state.formErrors.message}</p>
                </FormGroup>
                <Button type="submit" className="btn btn-info" disabled={!this.state.formValid}>{SEND}</Button>
            </Form>
        )
    }
}

export default SendEmail;