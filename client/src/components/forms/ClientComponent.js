import React from 'react';
// import "../../styles.css";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import AddressFields from "./AddressFields";
import * as CLIENT from "../../constants/clientConstants";
import axios from 'axios';

class ClientComponent extends React.Component {
    address = {
        id: '',
        city: '',
        street: '',
        building: '1',
        flat: '1'
    };

    state = {
        client: {
            id: '',
            name: '',
            address: '',
            type: CLIENT.LEGAL,
            status: CLIENT.ACTIVE,
        },
        formErrors: {name: ''},
    };

    handleChange = (event) => {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let client = {...this.state.client};
        client[name] = value;
        this.setState({client},
            () => {
                this.validateField(name, value)
            });
        console.log(this.state);
    };

    validateField = (fieldName, value) => {
        let fieldValidationErrors = this.state.formErrors;
        let nameValid = this.state.nameValid;
        switch (fieldName) {
            case 'name':
                nameValid = value.length >= 2 && value.length <= 150;
                fieldValidationErrors.name = nameValid ? '' : ' введено неверно';
                break;
            default:
                break;
        }
        this.setState({
            formErrors: fieldValidationErrors,
            nameValid: nameValid
        }, this.validateForm);
    };

    validateForm = () => {
        this.setState({
            formValid: this.state.nameValid,
            addressValid: this.state.addressValid,
            userValid: this.state.userValid
        });
    };

    changeAddressFields = value => {
        console.log(value);
        let client = {...this.state.client};
        client['address'] = value.address;
        this.setState({client: client});
    };

    buttonClick = (event) => {
        const {client} = this.state;
        console.log(client);
        console.log(this.props);
        const urlId = this.props.match.params.id;
        console.log(urlId);
        if (urlId === 'create') {
            this.props.history.push({
                pathname: "/admin/create",
                state: {client: client}
            });
        } else {
            this.handleSubmit(event);
        }
    };

    handleSubmit = (event) => {
        event.preventDefault();
        const {client} = this.state;
        // axios.post('/client/',{client})
        fetch('/client/', {
            method: client.id ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
            },
            body: JSON.stringify(client)
        })
            .then(resp => {
                if (resp.status === 400) {
                    return resp.json();
                } else {
                    this.props.history.push('/clients');
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
    };

    componentDidMount() {
        if (this.props.match.params.id !== 'create') {
            axios.get(`/client/${this.props.match.params.id}`)
                .then(response => {
                    this.setState({client: response.data, nameValid: true, formValid: true, addressValid: true});
                }, error => {
                    const {status, statusText} = error.response;
                    const data = {status, statusText}
                    this.props.history.push('/error', {error: data});
                });
        } else {
            const client = this.state.client;
            client['address'] = this.address;
            console.log(client);
            this.setState({client});
        }
    }

    render() {
        const {client} = this.state;
        return (
            <Container className="col-3">
                <h1>Клиент</h1>
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label htmlFor="name">Имя/название</Label>
                        <Input type="text" name="name" id="name" value={client.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                        <p className={'error-message'}>{(this.state.formErrors.name === '') ? ''
                            : 'Имя/название' + this.state.formErrors.name}</p>
                    </FormGroup>
                    <FormGroup>
                        <Label htmlFor="type">Тип</Label>
                        <Input type="select" name="type" id="type" value={client.type || ''}
                               onChange={this.handleChange} autoComplete="type">
                            <option value={CLIENT.INDIVIDUAL}>{CLIENT.INDIVIDUAL_RU}</option>
                            <option value={CLIENT.LEGAL}>{CLIENT.LEGAL_RU}</option>
                        </Input>
                    </FormGroup>
                    <FormGroup>
                        <Label htmlFor="status">Статус</Label>
                        <Input type="select" name="status" id="status" value={client.status || ''}
                               onChange={this.handleChange} autoComplete="status">
                            <option value={CLIENT.ACTIVE}>{CLIENT.ACTIVE_RU}</option>
                            <option value={CLIENT.BLOCKED}>{CLIENT.BLOCKED_RU}</option>
                        </Input>
                    </FormGroup>

                    <FormGroup>
                        {client.address ? <AddressFields
                            name="address"
                            id="addressFields"
                            validationHandler={this.validateForm}
                            changeState={this.changeAddressFields.bind(this)}
                            address={client.address}/> : null}
                    </FormGroup>

                    <FormGroup>
                        <Button color="primary" type="button"
                                disabled={!this.state.formValid} onClick={this.buttonClick}>Сохранить</Button>{' '}
                    </FormGroup>
                </Form>
            </Container>
        );
    }
}

export default ClientComponent;