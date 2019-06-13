import React from 'react';
import Container from "reactstrap/es/Container";
import FormGroup from "reactstrap/es/FormGroup";
import {Input, Label} from "reactstrap";

class AddressFields extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            address: props.address,
            formErrors: {city: '', flat: '', building: '', street: ''},
            cityValid: false,
            streetValid: false,
            buildingValid: true,
            flatValid: true,
            formValid: false
        };
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let address = {...this.state.address};
        address[name] = value;
        this.setState({address},
            () => {
                this.validateField(name, value);
                this.props.changeState(this.state);
            });
    }

    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let cityValid = this.state.cityValid;
        let streetValid = this.state.streetValid;
        let buildingValid = this.state.buildingValid;
        let flatValid = this.state.flatValid;

        switch (fieldName) {
            case 'city':
                cityValid = value.length >= 2 && value.length <= 150;
                fieldValidationErrors.city = cityValid ? '' : ' введен неверно';
                break;
            case 'street':
                streetValid = value.length >= 2 && value.length <= 150;
                fieldValidationErrors.street = streetValid ? '' : ' введена неверно';
                break;
            case 'building':
                buildingValid = !!value.match(/^[1-9]([0-9]*?)$/i);
                fieldValidationErrors.building = buildingValid ? '' : ' введен неверно';
                break;
            case 'flat':
                flatValid = !!value.match(/^[1-9]([0-9]*?)$/i);
                fieldValidationErrors.flat = flatValid ? '' : ' введена неверно';
                break;
            default:
                break;
        }
        this.setState({
            formErrors: fieldValidationErrors,
            cityValid: cityValid,
            flatValid:flatValid,
            buildingValid:buildingValid,
            streetValid:streetValid
        }, this.validateForm);
    }

    validateForm() {
        let valid = this.state.streetValid && this.state.flatValid &&
            this.state.buildingValid && this.state.cityValid;
        this.setState({
            formValid: valid
        });
        this.props.validationHandler(valid);

    }
    render() {
        const {address} = this.state;
        return (
            <Container>
                <FormGroup>
                    <Label for="city">Город</Label>
                    <Input type="text" name="city" id="city" value={address.city || ''}
                           onChange={this.handleChange} autoComplete="city"/>
                    <p className={'error-message'}>{(this.state.formErrors.city === '') ? ''
                        : 'Город' + this.state.formErrors.city}</p>

                </FormGroup>

                <FormGroup>
                    <Label for="street">Улица</Label>
                    <Input type="text" name="street" id="street" value={address.street || ''}
                           onChange={this.handleChange} autoComplete="street"/>
                    <p className={'error-message'}>{(this.state.formErrors.street === '') ? ''
                        : 'Улица' + this.state.formErrors.street}</p>

                </FormGroup>

                <FormGroup>
                    <Label for="building">Дом</Label>
                    <Input type="number" name="building" id="building" value={address.building || ''}
                           onChange={this.handleChange} autoComplete="building" min="1"/>
                    <p className={'error-message'}>{(this.state.formErrors.building === '') ? ''
                        : 'Дом' + this.state.formErrors.building}</p>

                </FormGroup>

                <FormGroup>
                    <Label for="flat">Помещение/Квартира</Label>
                    <Input type="number" name="flat" id="flat" value={address.flat || ''}
                           onChange={this.handleChange} autoComplete="flat" min="1"/>
                    <p className={'error-message'}>{(this.state.formErrors.flat === '') ? ''
                        : 'Квартира' + this.state.formErrors.flat}</p>

                </FormGroup>
            </Container>
        );
    }
}

export default AddressFields;