import React from 'react';
import "../../styles.css";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import AddressFields from "./AddressFields";

class ClientComponent extends React.Component {
    emptyClient = {
        id: '',
        name: '',
        address: '',
        type: 'LEGAL',
        status: 'ACTIVE',
        companyType: 'CLIENT',
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
        this.state = {
            client: this.emptyClient,
            formErrors: {name: ''},
            nameValid: false,
            formValid: false,
            addressValid: false,
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.validateField = this.validateField.bind(this);
        this.validateForm = this.validateForm.bind(this);
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let client = {...this.state.client};
        client[name] = value;
        this.setState({client},
            () => {
                this.validateField(name, value)
            });
    }

    validateField(fieldName, value) {
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
    }

    validateForm() {
        this.setState({
            formValid: this.state.nameValid,
            addressValid: this.state.addressValid
        });
    }

    changeAddressFields(value) {
        console.log(value);
        let client = {...this.state.client};
        client['address'] = value.address;
        this.setState({client: client});
    };

    async handleSubmit(event) {
        event.preventDefault();
        const {client} = this.state;
        await fetch('/client/', {
            method: client.id ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(client)
        }).then(resp => {
            if (resp.status === 400) {
                return resp.json();
            }
            else {
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
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'create') {
            const newClient = await (await fetch(`/client/${this.props.match.params.id}`)).json();
            this.setState({client: newClient, nameValid: true, formValid: true, addressValid: true});
        } else {
            const client = this.state.client;
            client['address'] = this.address;
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
                        <Label for="name">Имя/название</Label>
                        <Input type="text" name="name" id="name" value={client.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                        <p className={'error-message'}>{(this.state.formErrors.name === '') ? ''
                            : 'Имя/название' + this.state.formErrors.name}</p>
                    </FormGroup>
                    <FormGroup>
                        <Label for="type">Тип</Label>
                        <Input type="select" name="type" id="type" value={client.type || ''}
                               onChange={this.handleChange} autoComplete="type">
                            <option value="INDIVIDUAL">Физическое лицо</option>
                            <option value="LEGAL">Юридическое лицо</option>
                        </Input>
                    </FormGroup>
                    <FormGroup>
                        <Label for="status">Статус</Label>
                        <Input type="select" name="status" id="status" value={client.status || ''}
                               onChange={this.handleChange} autoComplete="status">
                            <option value="ACTIVE">Активен</option>
                            <option value="BLOCKED">Заблокирован</option>
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
                        <Button color="primary" type="submit"
                                disabled={!this.state.formValid}>Сохранить</Button>{' '}
                    </FormGroup>
                </Form>
            </Container>
        );
    }
}

export default ClientComponent;