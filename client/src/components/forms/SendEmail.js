//Dmitry Gorlach
import React, {Component} from 'react';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import './../../styles.css';
import {ACCESS_TOKEN} from "../../constants/auth";
import Modal from "react-bootstrap/Modal";
import TemplateComponent from "./TemplateComponent";

const RECIPIENTS = "Кому:";
const SUBJECT = "Тема:";
const MESSAGE = "Сообщение:";
const SEND = "Отправить";
const ADD_TEMPLATE = "Создать шаблон";
const CREATE_TEMPLATE = "Создание шаблона";

class SendEmail extends Component {

    emptyTemplate = {
        recipients: this.props.location.state.users,
        date: '',
        message: '',
        backgroundColor: '',
        image: ''
    };

    emptyEmail = {
        recipients: this.props.location.state.users,
        subject: '',
        message: '',
        template: 'template',
        backgroundColor: '#ADFF2F',
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
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
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

    handleClose() {
        this.setState({show: false});
    }


    handleShow() {
        this.setState({show: true, template: this.emptyTemplate});
    }

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
                return this.props.history.push('/email');
            } else {
                this.setState({
                    resultMessage: "Error.Something went wrong, please try send email again."
                }, () => {
                    alert(this.state.resultMessage);
                });
                this.props.history.push('/email');
            }
        })
    }

    validationHandlerTemplate = (formValid) => {
        this.setState({templateValid: formValid});
    };

    changeFieldHandler = (template) => {
        console.log(template);
        this.setState({template: template});
    };

    saveTemplate = () => {
        this.setState({show: false});
        let template = this.state.template;
        let email = {...this.state.email};
        email['template'] = template;
        this.setState({email: email, template: this.emptyTemplate});
    };

    render() {
        console.log(this.state.email.recipients);
        return (
            <Container>
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
                    <FormGroup>
                        <Button color="primary" onClick={this.handleShow}>{ADD_TEMPLATE}</Button>
                    </FormGroup>
                    <Button type="submit" className="btn btn-info" disabled={!this.state.formValid}>{SEND}</Button>
                </Form>


                <Modal size="lg" show={this.state.show} onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>{CREATE_TEMPLATE}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <FormGroup>
                            <TemplateComponent
                                name="TemplateComponent"
                                id="TemplateComponent"
                                recipients={this.state.email.recipients}
                                validationHandlerTemplate={this.validationHandlerTemplate}
                                changeFieldHandler={this.changeFieldHandler}
                            />
                        </FormGroup>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button color="primary"
                                disabled={!this.state.templateValid}
                                onClick={this.saveTemplate}>
                            Закрыть
                        </Button>
                    </Modal.Footer>
                </Modal>
            </Container>

        )
    }
}

export default SendEmail;