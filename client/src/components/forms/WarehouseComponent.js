//Dmitry Gorlach
import React from 'react';
import "../../styles.css";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import AddressFields from "./AddressFields";

class WarehouseComponent extends React.Component {
    emptyWarehouse = {
        id: '',
        name: '',
        address: '',
        type: 'LEGAL',
        status: 'ACTIVE',
        companyType: 'WAREHOUSE'
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
            warehouse: this.emptyWarehouse,
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
        let warehouse = {...this.state.warehouse};
        warehouse[name] = value;
        this.setState({warehouse: warehouse},
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
        let warehouse = {...this.state.warehouse};
        warehouse['address'] = value.address;
        this.setState({warehouse: warehouse});
    };

    async handleSubmit(event) {
        event.preventDefault();
        const {warehouse} = this.state;
        await fetch('/client/', {
            method: warehouse.id ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(warehouse)
        }).then(resp => {
            if (resp.status === 400) {
                return resp.json();
            } else {
                this.props.history.push('/warehouses');
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
            const newWarehouse = await (await fetch(`/client/${this.props.match.params.id}`)).json();
            this.setState({warehouse: newWarehouse, nameValid: true, formValid: true, addressValid: true});
        } else {
            const warehouse = this.state.warehouse;
            warehouse['address'] = this.address;
            this.setState({warehouse});
        }
    }

    render() {
        const {warehouse} = this.state;
        return (
            <Container className="col-3">
                <h1>Получатель</h1>
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Имя/название</Label>
                        <Input type="text" name="name" id="name" value={warehouse.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                        <p className={'error-message'}>{(this.state.formErrors.name === '') ? ''
                            : 'Имя/название' + this.state.formErrors.name}</p>
                    </FormGroup>
                    <FormGroup>
                        {warehouse.address ? <AddressFields
                            name="address"
                            id="addressFields"
                            validationHandler={this.validateForm}
                            changeState={this.changeAddressFields.bind(this)}
                            address={warehouse.address}/> : null}
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

export default WarehouseComponent;