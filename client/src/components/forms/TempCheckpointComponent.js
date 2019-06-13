import React from 'react';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';


class TempCheckpointComponent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            checkpoint: props.checkpoint,
            formErrors: {name: '', latitude: '', longitude: '', date: ''},
            nameValid: false,
            latitudeValid: false,
            longitudeValid: true,
            dateValid: true,
            formValid: false
        };
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let checkpoint = {...this.state.checkpoint};
        checkpoint[name] = value;
        this.setState({checkpoint},
            () => {
                this.validateField(name, value)
            });
        this.props.changeFieldHandler(checkpoint);
        // const state = this.state;
        // this.props.changeState(state);
    }

    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let nameValid = this.state.nameValid;
        let latitudeValid = this.state.latitudeValid;
        let dateValid = this.state.dateValid;
        let longitudeValid = this.state.longitudeValid;
        switch (fieldName) {
            case 'name':
                nameValid = value.length >= 2 && value.length <= 150;
                fieldValidationErrors.name = nameValid ? '' : ' введено неверно';
                break;
            case 'latitude':
                latitudeValid = !!value.match(/^-?[0-9]+[.[0-9]+]?$/i);
                fieldValidationErrors.latitude = latitudeValid ? '' : ' введена неверно';
                break;
            case 'date':
                dateValid = !!value.match(/^[0-9]{4,}-[0-9]{2}-[0-9]{2}$/i);
                ;
                fieldValidationErrors.date = dateValid ? '' : ' введена неверно';
                break;
            case 'longitude':
                longitudeValid = !!value.match(/^-?[0-9]+[.[0-9]+]?$/i);
                fieldValidationErrors.longitude = longitudeValid ? '' : ' введена неверно';
                break;
            default:
                break;
        }
        this.setState({
            formErrors: fieldValidationErrors,
            latitudeValid: latitudeValid,
            nameValid: nameValid,
            dateValid: dateValid,
            longitudeValid: longitudeValid
        }, this.validateForm);
    }

    validateForm() {
        let valid = this.state.nameValid && this.state.latitudeValid &&
            this.state.dateValid && this.state.longitudeValid;
        this.setState({
            formValid: valid
        });
        this.props.validationHandlerCheckpoint(valid);
    }

    render() {
        const {checkpoint} = this.state;
        return (
            <Container className="col-3">
                <h1>Контрольная точка</h1>
                <Form>
                    <FormGroup>
                        <Label for="name">Название</Label>
                        <Input type="text" name="name" id="name" value={checkpoint.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                        <p className={'error-message'}>{(this.state.formErrors.name === '') ? ''
                            : 'Название' + this.state.formErrors.name}</p>

                    </FormGroup>

                    <FormGroup>
                        <Label for="date">Дата прохождения</Label>
                        <Input type="date" name="date" id="date" value={checkpoint.date || ''}
                               onChange={this.handleChange} autoComplete="date" min="1"/>
                        <p className={'error-message'}>{(this.state.formErrors.date === '') ? ''
                            : 'Дата прохождения' + this.state.formErrors.date}</p>
                    </FormGroup>

                    <FormGroup>
                        <Label for="latitude">Широта</Label>
                        <Input type="text" name="latitude" id="latitude" value={checkpoint.latitude || ''}
                               onChange={this.handleChange} autoComplete="latitude"/>
                        <p className={'error-message'}>{(this.state.formErrors.latitude === '') ? ''
                            : 'Широта' + this.state.formErrors.latitude}</p>
                    </FormGroup>

                    <FormGroup>
                        <Label for="longitude">Долгота</Label>
                        <Input type="text" name="longitude" id="longitude" value={checkpoint.longitude || ''}
                               onChange={this.handleChange} autoComplete="longitude" min="0"/>
                        <p className={'error-message'}>{(this.state.formErrors.longitude === '') ? ''
                            : 'Долгота' + this.state.formErrors.longitude}</p>
                    </FormGroup>

                    <FormGroup>
                        <Label for="status">Статус</Label>
                        <Input type="select" name="status" id="status" value={checkpoint.status || ''}
                               onChange={this.handleChange} autoComplete="status">
                            <option value="PASSED">Пройдена</option>
                            <option value="NOT_PASSED">Не пройдена</option>
                        </Input>
                    </FormGroup>
                </Form>
            </Container>
        );
    }
}

export default TempCheckpointComponent;