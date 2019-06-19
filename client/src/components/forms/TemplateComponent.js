//Dmitry Gorlach
import React, {Component} from 'react';
import {Button, Form, FormGroup, Input, Label} from 'reactstrap';
import './../../styles.css';
import {ChromePicker} from 'react-color';
import * as TEMPLATE from '../../constants/email/templates'
import {TEMPLATES} from '../../constants/email/templates'
import {ACCESS_TOKEN} from "../../constants/auth";

class TemplateComponent extends Component {


    static defaultProps = {
        defaultColor: "#999999",
        labelStyle: {
            paddingBottom: "7px",
            fontSize: "11px"
        },

        colorTextBoxStyle: {
            height: "35px",
            border: "none",
            borderBottom: '1px solid lightgray',
            paddingLeft: "35px"
        }

    };

    constructor(props) {
        super(props);
        console.log('emptyTemplate');
        this.state = {
            email: {
                recipients: props.recipients,
                subject: '',
                message: '',
                template: '',
                backgroundColor: ''
            },
            resultMessage: '',
            date: '',
            message: '',
            subject: '',
            formErrors: {date: '', subject: ''},
            dateValid: false,
            subjectValid: false,
            formValid: false,
            displayColorPicker: false,
            defaultColor: "#33D1FF",
            changeColor: props.backgroundColor,
            color: {
                r: "0",
                g: "9",
                b: "153",
                a: "1"
            }
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleTemplateSelect = this.handleTemplateSelect.bind(this);
        this.onHandleShowColorPicker = this.onHandleShowColorPicker.bind(this);
        this.onHandleCloseColorPicker = this.onHandleCloseColorPicker.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        console.log(this.state.changeColor)
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let email = {...this.state.email};
        email[name] = value;
        this.setState({email},
            () => {
                this.validateField(name, value)
            });
        this.props.changeFieldHandler(email);
    }

    handleTemplateSelect(event) {
        const {email} = {...this.state};
        const currentState = email;
        const {name, value} = event.target;
        currentState[name] = value;

        this.setState({email: currentState});
    }

    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let dateValid = this.state.dateValid;
        let subjectValid = this.state.subjectValid;

        switch (fieldName) {
            case 'date':
                dateValid = !!value.match(/^[0-9]{4,}-[0-9]{2}-[0-9]{2}$/i);
                if (!dateValid) {
                    fieldValidationErrors = 'Дата введена неверно';
                    break;
                }
                let fieldDate = new Date(value);
                let currentDate = new Date();
                let minDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate());
                let maxDate = new Date(currentDate.getFullYear() - 100, currentDate.getMonth(), currentDate.getDate());
                if (fieldDate > minDate) {
                    fieldValidationErrors.dateValid = 'Вы не можете создать шаблон из будущего';
                    dateValid = false;
                } else if (fieldDate < maxDate) {
                    fieldValidationErrors.dateValid = 'Вы еще не родились в это время.';
                    dateValid = false;
                } else {
                    fieldValidationErrors.dateValid = '';
                    dateValid = true;
                }
                break;
            case 'subject':
                subjectValid = value.length >= 2 && value.length <= 150;
                fieldValidationErrors.subject = subjectValid ? '' : ' - тема не заполнена (от 1 до 150 символов)';
                break;
            default:
                break;
        }
        this.setState({
            formErrors: fieldValidationErrors,
            dateValid: dateValid,
            subjectValid: subjectValid
        }, this.validateForm);
    }

    validateForm() {
        let valid = this.state.dateValid && this.state.subjectValid;
        this.setState({
            formValid: valid
        });
        this.props.validationHandlerTemplate(valid);
    }

    errorClass(error) {
        console.log(error);
        return (error.length === 0 ? '' : 'has-error');
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {email} = this.state;
        await fetch('/email', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
                },
                body: JSON.stringify(email)
            }, () => console.log(email)
        ).then(resp => {
            if (resp.status === 200) {
                this.setState({
                    resultMessage: "Success! Email was sent.",
                }, () => {
                    alert(this.state.resultMessage);
                });
            } else {
                this.setState({
                    resultMessage: "Error.Something went wrong, please try send email again."
                }, () => {
                    alert(this.state.resultMessage);
                });
            }
        })
    }

    onHandleShowColorPicker = () => {
        this.setState({displayColorPicker: true});
    };

    onHandleCloseColorPicker = () => {
        this.setState({displayColorPicker: false});
    };

    onChangeColorPicker = color => {
        const email = {...this.state.email};
        email.backgroundColor = color.hex;
        this.setState({email});
        this.setState({color: color.rgb, changeColor: color.hex}, () =>
            console.log(this.state.changeColor));
        this.onHandleCloseColorPicker();
    };

    render() {
        const {email} = this.state;
        console.log(email.template);
        console.log(this.state);
        return (
            <Form className="Template" onSubmit={this.handleSubmit}>
                <br/>
                <FormGroup>
                    <Label for="subject">{TEMPLATE.SUBJECT}</Label>
                    <Input
                        type="text"
                        name="subject"
                        placeholder={'Тема сообщения'}
                        onChange={this.handleChange}/>
                    <p className={'error-message'}>{(this.state.formErrors.subjectValid === '') ? ''
                        : this.state.formErrors.subject}</p>
                </FormGroup>
                <FormGroup>
                    <Label for="template">{TEMPLATE.CHOOSE_TEMPLATE}</Label>
                    <Input type="select" name="template"
                           onChange={this.handleTemplateSelect}>
                        <option value="UNAVAILABLE">{TEMPLATE.UNAVAILABLE}</option>
                        <option value="HAPPY_BIRTHDAY">{TEMPLATE.HAPPY_BIRTHDAY}</option>
                    </Input>
                </FormGroup>
                <FormGroup>
                    <div className={"color-picker-container"}>
                        <Label for="color">{TEMPLATE.BACKGROUND_COLOR}</Label>
                        <div style={this.props.labelStyle}/>
                    </div>
                    <div style={{backgroundColor: this.state.changeColor}}
                         className={"color-picker-color-background"}/>
                    <div className={"color-text-with-popup"}>
                        <input
                            readOnly
                            style={this.props.colorTextBoxStyle}
                            className={"color-picker-text"}
                            type="text"
                            name="color-picker-text"
                            value={this.state.email.backgroundColor}
                            placeholder={'Выберите цвет'}
                            onClick={this.onHandleShowColorPicker}/>
                        {this.state.displayColorPicker && (
                            <div className={"color-picker-palette"}>
                                <div className={"color-picker-cover"}
                                     onClick={this.onHandleShowColorPicker}/>
                                <ChromePicker
                                    color={this.state.color}
                                    onChange={this.onChangeColorPicker}/>
                            </div>
                        )}
                    </div>
                </FormGroup>
                <FormGroup>
                    <Label for="date">{TEMPLATE.DATE}</Label>
                    <Input
                        type="date"
                        name="date"
                        onChange={this.handleChange}/>
                    <p className={'error-message'}>{(this.state.formErrors.dateValid === '') ? ''
                        : this.state.formErrors.dateValid}</p>
                </FormGroup>
                <FormGroup>
                    <Label for="message">{TEMPLATE.MESSAGE}</Label>
                    <Input readOnly
                           type="textarea"
                           name="message"
                           value={TEMPLATES[email.template] ? TEMPLATES[email.template].text : ''}
                           rows={5}/>
                </FormGroup>

                <Button type="submit" className="btn btn-info" disabled={!this.state.formValid}>{TEMPLATE.SEND}</Button>
                {/*<Button type="submit" className="btn btn-info" disabled={!this.state.formValid}>{TEMPLATE.SAVE}</Button>*/}
            </Form>
        )
    }

}

export default TemplateComponent;