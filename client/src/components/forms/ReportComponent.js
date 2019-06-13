import React from 'react';
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import {ACCESS_TOKEN} from "../../constants/auth";

export default class ReactComponent extends React.Component {

    datePeriod = {
        startDate: '',
        endDate: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            datePeriod: this.datePeriod,
            formErrors: {startDate: '', endDate: ''},
            startDateValid: false,
            endDateValid: false,
            formValid: false
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let datePeriod = {...this.state.datePeriod};
        datePeriod[name] = value;
        this.setState({datePeriod},
            () => {
                this.validateField(name, value)
            });
    }

    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let startDateValid = this.state.startDateValid;
        let endDateValid = this.state.endDateValid;
        let currentDate = new Date();
        let maxDate = new Date(currentDate.getFullYear() - 100, currentDate.getMonth(), currentDate.getDate());
        switch (fieldName) {
            case 'startDate':
                startDateValid = !!value.match(/^[0-9]{4,}-[0-9]{2}-[0-9]{2}$/i);
                if (!startDateValid) {
                    fieldValidationErrors.startDate = 'Дата введена неверно';
                    break;
                }
                let startDate = new Date(value);
                if (startDate > currentDate) {
                    fieldValidationErrors.startDate = 'Нельзя ввести дату из будущего';
                    startDateValid = false;
                } else if (startDate < maxDate) {
                    fieldValidationErrors.startDate = 'У нас нет таких старых данных';
                    startDateValid = false;
                } else {
                    fieldValidationErrors.startDate = '';
                    startDateValid = true;
                }
                break;
            case 'endDate':
                endDateValid = !!value.match(/^[0-9]{4,}-[0-9]{2}-[0-9]{2}$/i);
                if (!endDateValid) {
                    fieldValidationErrors.endDate = 'Дата введена неверно';
                    break;
                }
                let endDate = new Date(value);
                if (endDate > currentDate) {
                    fieldValidationErrors.endDate = 'Нельзя ввести дату из будущего';
                    endDateValid = false;
                } else if (endDate < maxDate) {
                    fieldValidationErrors.endDate = 'У нас нет таких старых данных';
                    endDateValid = false;
                } else {
                    fieldValidationErrors.endDate = '';
                    endDateValid = true;
                }
                break;
            default:
                break;
        }
        this.setState({
            formErrors: fieldValidationErrors,
            startDateValid: startDateValid,
            endDateValid: endDateValid
        }, this.validateForm);
    }

    validateForm() {
        this.setState({
            formValid: this.state.startDateValid &&
                this.state.endDateValid
        });
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {datePeriod} = this.state;
        await fetch('/report/download', {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
            },
            body: JSON.stringify(datePeriod)
        }).then(response => {
            console.log(response);
            const filename = response.headers.get('Content-Disposition').split('filename=')[1];
            response.blob().then(blob => {
                let url = window.URL.createObjectURL(blob);
                let a = document.createElement('a');
                a.href = url;
                a.download = filename;
                a.click();
            });
        });
    }

    render() {
        const {datePeriod} = this.state;
        return (
            <Container className="col-3">
                <Form onSubmit={this.handleSubmit}>
                    <h1>Укажите период</h1>
                    <FormGroup>
                        <Label for="startDate">С</Label>
                        <Input type="date" name="startDate" id="startDate" value={datePeriod.startDate || ''}
                               onChange={this.handleChange} autoComplete="startDate"/>
                        <p className={'error-message'}>{(this.state.formErrors.startDate === '') ? ''
                            : this.state.formErrors.startDate}</p>
                    </FormGroup>
                    <FormGroup>
                        <Label for="endDate">По</Label>
                        <Input type="date" name="endDate" id="endDate" value={datePeriod.endDate || ''}
                               onChange={this.handleChange} autoComplete="endDate"/>
                        <p className={'error-message'}>{(this.state.formErrors.endDate === '') ? ''
                            : this.state.formErrors.endDate}</p>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary"
                                type="submit"
                                disabled={!this.state.formValid}>Загрузить отчет</Button>{' '}
                    </FormGroup>
                </Form>
            </Container>
        );
    }
}