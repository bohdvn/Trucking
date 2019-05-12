import React from 'react';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';

class CarComponent extends React.Component {
    emptyCar={
        id:'',
        carType: 'TILT',
        name: '',
        consumption: '',
        status: 'AVAILABLE',
    };

    constructor(props){
        super(props);
        this.state = {
            car: this.emptyCar
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let car = {...this.state.car};
        car[name] = value;
        this.setState({car});
        console.log(this.state);
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {car} = this.state;
        await fetch( '/car/', {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(car),
        });
        this.props.history.push('/cars');
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'create') {
            const newCar = await (await fetch(`/car/${this.props.match.params.id}`)).json();
            this.setState({car: newCar});
        }
    }

    render() {
        const {car}=this.state;
        return (
            <Container className="col-3">
                <h1>Авто</h1>
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Название</Label>
                        <Input type="text" name="name" id="name" value={car.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="consumption">Расход топлива</Label>
                        <Input type="number" name="consumption" id="consumption" value={car.consumption || ''}
                               onChange={this.handleChange} autoComplete="consumption"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="carType">Тип</Label>
                        <Input type="select" name="carType" id="carType" value={car.carType||''}
                               onChange={this.handleChange} autoComplete="carType">
                            <option value="TILT">Крытый кузов</option>
                            <option value="FRIDGE">Рефрежератор</option>
                            <option value="TANKER">Автоцистерна</option>
                        </Input>
                    </FormGroup>
                    <FormGroup>
                        <Label for="status">Статус</Label>
                        <Input type="select" name="status" id="status" value={car.status||''}
                               onChange={this.handleChange} autoComplete="status">
                            <option value="AVAILABLE">Доступно</option>
                            <option value="UNAVAILABLE">Недоступно</option>
                        </Input>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Сохранить</Button>{' '}
                    </FormGroup>
                </Form>
            </Container>
        );
    }
}

export default CarComponent;