import React from 'react';
import AddressFields from "./AddressFields";
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import "react-datepicker/dist/react-datepicker.css";
import {connect} from "react-redux";
import axios from 'axios';
import RoleSelect from "../RoleSelect";

class UserComponent extends React.Component {
    emptyUser = {
        id: '',
        surname: '',
        name: '',
        patronymic: '',
        passportNumber: '',
        passportIssued: '',
        dateOfBirth: '',
        email: '',
        roles: [],
        login: '',
        password: '',
        address: '',
    };

    address = {
        id: '',
        city: '',
        street: '',
        building: '1',
        flat: '1'
    };

    constructor(props) {
        super(props);
        console.log(props);
        this.state = {
            loggedIn: props.loggedIn,
            user: this.emptyUser,
            formErrors: {
                name: '', surname: '', patronymic: '', passportNumber: '', passportIssued: '',
                dateOfBirth: '', email: '', login: '', password: ''
            },
            surnameValid: false,
            nameValid: false,
            patronymicValid: false,
            passportNumberValid: false,
            passportIssuedValid: false,
            dateOfBirthValid: false,
            emailValid: false,
            loginValid: false,
            passwordValid: false,
            addressValid: false,
            userFormValid: false,
        };
        // const client=this.props.location.state.client;
        console.log(this.props);
        const locationState = this.props.location.state;
        const client = locationState ? locationState.client : null;
        console.log(client);
        this.state['client'] = client;
        console.log(this.state);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        if (this.props.match.params.id !== 'create') {
            axios.get(`/user/${this.props.match.params.id}`)
                .then(response => {
                    this.setState({
                        user: response.data, surnameValid: true, nameValid: true, patronymicValid: true,
                        passportNumberValid: true, passportIssuedValid: true, dateOfBirthValid: true, emailValid: true,
                        loginValid: true, passwordValid: true, addressValid: true, userFormValid: true
                    });
                }, error => {
                    const {status, statusText} = error.response;
                    const data = {status, statusText}
                    this.props.history.push('/error', {error: data});
                });
        } else {
            const user = this.state.user;
            user['address'] = this.address;
            this.setState({user});
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let user = {...this.state.user};
        user[name] = value;
        this.setState({user},
            () => {
                this.validateField(name, value)
            });
    }

    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let nameValid = this.state.nameValid;
        let surnameValid = this.state.surnameValid;
        let patronymicValid = this.state.patronymicValid;
        let passportNumberValid = this.state.passportNumberValid;
        let passportIssuedValid = this.state.passportIssuedValid;
        let dateOfBirthValid = this.state.dateOfBirthValid;
        let emailValid = this.state.emailValid;
        let loginValid = this.state.loginValid;
        let passwordValid = this.state.passwordValid;

        switch (fieldName) {
            case 'name':
                nameValid = value.length >= 1 && value.length <= 150;
                fieldValidationErrors.name = nameValid ? '' : ' введено неверно';
                break;
            case 'surname':
                surnameValid = value.length >= 2 && value.length <= 150;
                fieldValidationErrors.surname = surnameValid ? '' : ' введена неверно';
                break;
            case 'patronymic':
                patronymicValid = value.length >= 2 && value.length <= 150;
                fieldValidationErrors.patronymic = patronymicValid ? '' : ' введено неверно';
                break;
            case 'passportNumber':
                passportNumberValid = !!value.match(/^[A-Z]{2}[0-9]{7}$/i);
                fieldValidationErrors.passportNumber = passportNumberValid ? '' : ' введен неверно';
                break;
            case 'passportIssued':
                passportIssuedValid = value.length >= 2 && value.length <= 150;
                fieldValidationErrors.passportIssued = passportIssuedValid ? '' : ' введено неверно';
                break;
            case 'dateOfBirth':
                dateOfBirthValid = !!value.match(/^[0-9]{4,}-[0-9]{2}-[0-9]{2}$/i);
                if (!dateOfBirthValid) {
                    fieldValidationErrors.dateOfBirth = 'Дата введена неверно';
                    break;
                }
                let fieldDate = new Date(value);
                let currentDate = new Date();
                let minDate = new Date(currentDate.getFullYear() - 18, currentDate.getMonth(), currentDate.getDate());
                let maxDate = new Date(currentDate.getFullYear() - 100, currentDate.getMonth(), currentDate.getDate());
                if (fieldDate > minDate) {
                    fieldValidationErrors.dateOfBirth = 'Вам нет 18';
                    dateOfBirthValid = false;
                } else if (fieldDate < maxDate) {
                    fieldValidationErrors.dateOfBirth = 'Вы слишком стары для этого всего';
                    dateOfBirthValid = false;
                } else {
                    fieldValidationErrors.dateOfBirth = '';
                    dateOfBirthValid = true;
                }
                break;
            case 'email':
                emailValid = value.match(/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i);
                fieldValidationErrors.email = emailValid ? '' : ' введен неверно';
                break;
            case 'login':
                loginValid = value.length >= 2 && value.length <= 150;
                fieldValidationErrors.login = loginValid ? '' : ' введен неверно';
                break;
            case 'password':
                passwordValid = value.length >= 2 && value.length <= 150;
                fieldValidationErrors.password = passwordValid ? '' : ' введен неверно';
                break;
            default:
                break;
        }
        this.setState({
            formErrors: fieldValidationErrors,
            surnameValid: surnameValid,
            nameValid: nameValid,
            patronymicValid: patronymicValid,
            passportNumberValid: passportNumberValid,
            passportIssuedValid: passportIssuedValid,
            dateOfBirthValid: dateOfBirthValid,
            emailValid: emailValid,
            loginValid: loginValid,
            passwordValid: passwordValid
        }, this.validateUserForm);
    }

    validateUserForm() {
        this.setState({
            userFormValid: this.state.nameValid && this.state.surnameValid && this.state.patronymicValid
                && this.state.dateOfBirthValid && this.state.emailValid && this.state.passportIssuedValid
                && this.state.passportNumberValid && this.state.loginValid && this.state.passwordValid
                && this.state.addressValid
        });

    }

    changeAddressFields(value) {
        let user = {...this.state.user};
        user['address'] = value.address;
        this.setState({user: user});
    };

    validationHandler = (formValid) => {
        let pam = this.state.nameValid && this.state.surnameValid && this.state.patronymicValid
            && this.state.dateOfBirthValid && this.state.emailValid && this.state.passportIssuedValid
            && this.state.passportNumberValid && this.state.loginValid && this.state.passwordValid;
        this.setState({addressValid: formValid, userFormValid: pam && formValid});
    };

    handleRoleChange = roles => {
        let user = {...this.state.user};
        user['roles'] = roles;
        this.setState({user: user});
        console.log(this.state);
    };

    handleSubmit(event) {
        event.preventDefault();
        const {user} = this.state;
        const {client} = this.state;

        if (client) {
            client.users = [];
            client.users.push(user);
            axios.post('/client/', client)
                .then(repsonse => {
                    this.props.history.push('/clients');
                }, error => {
                    const {status, statusText} = error.response;
                    const data = {status, statusText};
                    this.props.history.push('/error', {error: data});
                });
        } else {
            axios({url: '/user/', data: user, method: user.id ? 'PUT' : 'POST'})
                .then(resp => {
                    if (resp.status === 400) {
                        return resp.json();
                    } else {
                        this.props.history.push('/home');
                        return null;
                    }
                }).then(data => {
                if (data) {
                    let s = '';
                    for (const k in data) {
                        s += data[k] + '\n';
                    }
                    alert(s);
                }
            });
            // this.props.history.push('/home');
        }
    }

    render() {
        const {user} = this.state;
        return (
            <Container className="col-3">
                <h1>Пользователь</h1>
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="surname">Фамилия</Label>
                        <Input type="text" name="surname" id="surname" value={user.surname || ''}
                               onChange={this.handleChange} autoComplete="surname"/>
                        <p className={'error-message'}>{(this.state.formErrors.surname === '') ? ''
                            : 'Фамилия' + this.state.formErrors.surname}</p>
                    </FormGroup>

                    <FormGroup>
                        <Label for="name">Имя</Label>
                        <Input type="text" name="name" id="name" value={user.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                        <p className={'error-message'}>{(this.state.formErrors.name === '') ? ''
                            : 'Имя' + this.state.formErrors.name}</p>
                    </FormGroup>

                    <FormGroup>
                        <Label for="patronymic">Отчество</Label>
                        <Input type="text" name="patronymic" id="patronymic" value={user.patronymic || ''}
                               onChange={this.handleChange} autoComplete="patronymic"/>
                        <p className={'error-message'}>{(this.state.formErrors.patronymic === '') ? ''
                            : 'Отчество' + this.state.formErrors.patronymic}</p>
                    </FormGroup>

                    <FormGroup>
                        <Label for="passportNumber">Номер паспорта</Label>
                        <Input type="text" name="passportNumber" id="passportNumber" value={user.passportNumber || ''}
                               onChange={this.handleChange} autoComplete="passportNumber"/>
                        <p className={'error-message'}>{(this.state.formErrors.passportNumber === '') ? ''
                            : 'Номер паспорта' + this.state.formErrors.passportNumber}</p>
                    </FormGroup>

                    <FormGroup>
                        <Label for="passportIssued">Паспорт выдан</Label>
                        <Input type="text" name="passportIssued" id="passportIssued" value={user.passportIssued || ''}
                               onChange={this.handleChange} autoComplete="passportIssued"/>
                        <p className={'error-message'}>{(this.state.formErrors.passportIssued === '') ? ''
                            : 'Поле' + this.state.formErrors.passportIssued}</p>
                    </FormGroup>

                    <FormGroup>
                        <Label for="dateOfBirth">Дата рождения</Label>
                        <Input type="date" name="dateOfBirth" id="birthDate" value={user.dateOfBirth || ''}
                               onChange={this.handleChange} autoComplete="dateOfBirth"/>
                        <p className={'error-message'}>{(this.state.formErrors.dateOfBirth === '') ? ''
                            : this.state.formErrors.dateOfBirth}</p>
                    </FormGroup>

                    <FormGroup>
                        <Label for="email">Email</Label>
                        <Input type="email" name="email" id="email" value={user.email || ''}
                               onChange={this.handleChange} autoComplete="email"/>
                        <p className={'error-message'}>{(this.state.formErrors.email === '') ? ''
                            : 'Email' + this.state.formErrors.email}</p>
                    </FormGroup>

                    <FormGroup>
                        {user.address ? <AddressFields
                            name="address"
                            id="addressFields"
                            validationHandler={this.validationHandler}
                            changeState={this.changeAddressFields.bind(this)}
                            address={user.address}/> : null}
                    </FormGroup>

                    <RoleSelect
                        roles={user.roles}
                        client={this.state.client}
                        onChange={this.handleRoleChange.bind(this)}
                    />


                    <FormGroup>
                        <Label for="login">Логин</Label>
                        <Input type="text" name="login" id="login" value={user.login || ''}
                               onChange={this.handleChange} autoComplete="login"/>
                        <p className={'error-message'}>{(this.state.formErrors.login === '') ? ''
                            : 'Логин' + this.state.formErrors.login}</p>
                    </FormGroup>

                    <FormGroup>
                        <Label for="password">Пароль</Label>
                        <Input type="password" name="password" id="password" value={user.password || ''}
                               onChange={this.handleChange} autoComplete="password"/>
                        <p className={'error-message'}>{(this.state.formErrors.password === '') ? ''
                            : 'Пароль' + this.state.formErrors.password}</p>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit"
                                disabled={!this.state.userFormValid}>Сохранить</Button>{' '}
                    </FormGroup>
                </Form>
            </Container>
        );
    }
}

export default connect(
    state => ({
        loggedIn: state.loggedIn,
    }),
)(UserComponent)