import React from 'react';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
// import "../../styles.css";
import axios from 'axios';

class CarComponent extends React.Component {

    state = {
        car: {
            id: '',
            carType: 'TILT',
            name: '',
            consumption: '1',
            status: 'AVAILABLE',
        },
        formErrors: {
            name: '',
            consumption: ''
        },
        nameValid: false,
        consumptionValid: true,
        formValid: false
    };

    handleChange = event => {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let car = {...this.state.car};
        car[name] = value;
        this.setState({car},
            () => {
                this.validateField(name, value)
            });
    };

    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let nameValid = this.state.nameValid;
        let consumptionValid = this.state.consumptionValid;
        switch (fieldName) {
            case 'name':
                nameValid = value.length >= 1 && value.length <= 150;
                fieldValidationErrors.name = nameValid ? '' : ' введено неверно';
                break;
            case 'consumption':
                consumptionValid = !!value.match(/^[1-9]([0-9]*?)$/i);
                fieldValidationErrors.consumption = consumptionValid ? '' : ' введен неверно';
                break;
            default:
                break;
        }
        this.setState({
            formErrors: fieldValidationErrors,
            consumptionValid: consumptionValid,
            nameValid: nameValid
        }, this.validateForm);
    }

    validateForm() {
        this.setState({
            formValid: this.state.nameValid &&
                this.state.consumptionValid
        });
    }

    handleSubmit = event => {
        event.preventDefault();
        const {car} = this.state;
        axios.post(`/car/`, car)
            .then(resp => {
                if (resp.status === 400) {
                    return resp.json();
                } else {
                    this.props.history.push('/cars');
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
            console.log("qwe");
            axios.get(`/car/${this.props.match.params.id}`)
                .then(response => {
                    this.setState({car: response.data, nameValid: true, consumptionValid: true, formValid: true});
                }, error => {
                    const {status, statusText} = error.response;
                    const data = {status, statusText}
                    this.props.history.push('/error', {error: data});
                });
        }
    }

    render() {
        const {car} = this.state;
        return (
            <Container className="col-3">
                <h1>Авто</h1>
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Название</Label>
                        <Input type="text" name="name" id="name" value={car.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                        <p className={'error-message'}>{(this.state.formErrors.name === '') ? ''
                            : 'Название' + this.state.formErrors.name}</p>
                    </FormGroup>
                    <FormGroup>
                        <Label for="consumption">Расход топлива</Label>
                        <Input type="number" name="consumption" id="consumption" value={car.consumption || ''}
                               onChange={this.handleChange} autoComplete="consumption" min={1}/>
                        <p className={'error-message'}>{(this.state.formErrors.consumption === '') ? ''
                            : 'Расход топлива' + this.state.formErrors.consumption}</p>
                    </FormGroup>
                    <FormGroup>
                        <Label for="carType">Тип</Label>
                        <Input type="select" name="carType" id="carType" value={car.carType || ''}
                               onChange={this.handleChange} autoComplete="carType">
                            <option value="TILT">Крытый кузов</option>
                            <option value="FRIDGE">Рефрижератор</option>
                            <option value="TANKER">Автоцистерна</option>
                        </Input>
                    </FormGroup>
                    {/*<FormGroup>*/}
                        {/*<Label for="status">Статус</Label>*/}
                        {/*<Input type="select" name="status" id="status" value={car.status || ''}*/}
                               {/*onChange={this.handleChange} autoComplete="status">*/}
                            {/*<option value="AVAILABLE">Доступно</option>*/}
                            {/*<option value="UNAVAILABLE">Недоступно</option>*/}
                        {/*</Input>*/}
                    {/*</FormGroup>*/}
                    <FormGroup>
                        <Button color="primary" type="submit"
                                disabled={!this.state.formValid}>Сохранить</Button>{' '}
                    </FormGroup>
                </Form>
            </Container>
        );
    }
}

export default CarComponent;