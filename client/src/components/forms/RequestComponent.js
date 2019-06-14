import React from 'react';
import {Button, Container, Form, FormGroup, Input, Label, Table} from 'reactstrap';
import Modal from 'react-bootstrap/Modal';
import TempProductComponent from "./TempProductComponent";
import axios from 'axios';
import {HEADERS} from '../../constants/requestConstants'
import {ACCESS_TOKEN} from "../../constants/auth";

class RequestComponent extends React.Component {

    productStatusMap = {
        'REGISTERED': 'Зарегестрирован',
        'CHECKED': 'Проверен',
        'DELIVERED': 'Доставлен',
        'LOST': 'Утерян'
    };

    emptyProduct = {
        name: '',
        type: '',
        amount: '1',
        price: '1',
        status: 'REGISTERED'
    };

    emptyRequest = {
        id: '',
        status: 'NOT_VIEWED',
        car: '',
        driver: '',
        products: []
    };

    constructor(props) {
        super(props);
        this.state = {
            request: this.emptyRequest,
            cars: [],
            drivers: [],
            formValid: true,
            productValid: false,
            show: false,
            product: this.emptyProduct
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);

    }


    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        if (name === 'carInput') {
            this.getCar(value);
        } else if (name === 'driverInput') {
            this.getDriver(value);
        } else {
            let request = {...this.state.request};
            request[name] = value;
            this.setState({request});
        }


    }

    handleClose() {
        this.setState({show: false});
    }


    handleShow() {
        this.setState({show: true});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {request} = this.state;
        console.log(request);
        await fetch('/request/', {
            method: request.id === '' ? 'POST' : 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
            },
            body: JSON.stringify(request)
        });

        this.props.history.push('/requests');
    }

    saveProduct = () => {
        this.setState({show: false});
        let product = this.state.product;
        let products = this.state.request.products;
        products.push(product);
        let request = {...this.state.request};
        request['products'] = products;
        this.setState({request: request, product: this.emptyProduct});
    };

    async getCar(id) {
        let newCar = {};
        await axios.get(`/car/${id}`)
            .then(response => {
                newCar = response.data;
            });
        let request = {...this.state.request};
        request['car'] = newCar;
        this.setState({request});
        console.log(this.state);
    }

    async getDriver(id) {
        let newDriver = {};
        await axios.get(`/user/${id}`)
            .then(response => {
                newDriver = response.data;
            });
        let request = {...this.state.request};
        request['driver'] = newDriver;
        this.setState({request});
        console.log(this.state);
    }

    validationHandlerProduct = (formValid) => {
        this.setState({productValid: formValid});
    };

    changeFieldHandler = (product) => {
        console.log(product);
        this.setState({product: product});
    };

    populateRowsWithData = () => {
        return this.state.request.products.map(product => {
            return <tr key={product.id}>
                <td style={{whiteSpace: 'nowrap'}}>{product.name}</td>
                <td>{product.amount}</td>
                <td>{product.type}</td>
                <td>{product.price}</td>
                <td>{this.productStatusMap[product.status]}</td>
            </tr>
        });

    };

    fillCarSelector() {
        if (this.state.cars.length === 0) {
            return <option>Нет доступных машин</option>
        }
        return this.state.cars.map(car => {
            return <option value={car.id}>{car.name} {car.consumption}</option>
        });
    };

    fillDriverSelector() {
        if (this.state.drivers.length === 0) {
            return <option>Нет доступных водителей</option>
        }
        return this.state.drivers.map(driver => {
            return <option value={driver.id}>{driver.name} {driver.surname}</option>
        });
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'create') {
            let newRequest = {};
            axios.get(`/request/${this.props.match.params.id}`)
                .then(response => {
                    newRequest = response.data;
                });
            this.setState({request: newRequest});
        }

        let cars = [];
        await axios.get(`/car/all`)
            .then(response => {
                console.log(response.data);
                cars = response.data;
                console.log(cars);
            });

        let drivers = [];
        await axios.get(`/user/drivers`)
            .then(response => {
                drivers = response.data;
            });

        this.setState({cars: cars, drivers: drivers});
        if (cars.length === 0 || drivers.length === 0) {
            this.setState({formValid: false});
        }
    }


    async createProduct(id) {
        this.props.history.push('/product/create/' + id);
    }

    render() {
        const {request} = this.state;

        return (
            <Container className="col-3">
                <h1>Заявка</h1>
                <Form onSubmit={this.handleSubmit}>

                    <FormGroup>
                        <Label for="status">Статус</Label>
                        <Input type="select" name="status" id="status" value={request.status || ''}
                               onChange={this.handleChange} autoComplete="status">
                            <option value="NOT_VIEWED">Не просмотрена</option>
                            <option value="REJECTED">Отклонена</option>
                            <option value="ISSUED">Оформлена</option>
                        </Input>
                    </FormGroup>
                    <FormGroup>
                        <Label for="carInput">Машина</Label>
                        <Input type="select" name="carInput" id="carInput" value={request.car.id || ''}
                               onChange={this.handleChange}>
                            {this.fillCarSelector()}
                        </Input>
                    </FormGroup>
                    <FormGroup>
                        <Label for="driverInput">Водитель</Label>
                        <Input type="select" name="driverInput" id="driverInput" value={request.driver.id || ''}
                               onChange={this.handleChange}>
                            {this.fillDriverSelector()}
                        </Input>
                    </FormGroup>
                    <FormGroup>
                        <Label for="productTable">Продукты</Label>
                        <Table name="productTable" id="productTable" className="mt-4">
                            <thead>
                            <tr>
                                <th width="20%">Наименование</th>
                                <th width="20%">Количество</th>
                                <th width="20%">Тип</th>
                                <th>Стоимость</th>
                                <th>Статус</th>
                            </tr>
                            </thead>
                            <tbody>
                            {this.populateRowsWithData()}
                            </tbody>
                        </Table>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" onClick={this.handleShow}>Добавить продукт</Button>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" disabled={!this.state.formValid}
                                type="submit">Сохранить</Button>{' '}
                    </FormGroup>

                </Form>

                <Modal size="lg" show={this.state.show} onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>Добавление продукта</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <FormGroup>
                            <TempProductComponent
                                name="ProductComponent"
                                id="ProductComponent"
                                product={this.emptyProduct}
                                validationHandlerProduct={this.validationHandlerProduct}
                                changeFieldHandler={this.changeFieldHandler}
                            />
                        </FormGroup>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button color="primary" disabled={!this.state.productValid} onClick={this.saveProduct}>
                            Добавить
                        </Button>
                    </Modal.Footer>
                </Modal>
            </Container>


        );
    }
}

export default RequestComponent;