import React from 'react';
import {Button, ButtonGroup, Container, Form, FormGroup, Input, Label, Table} from 'reactstrap';
import Modal from 'react-bootstrap/Modal';
import TempProductComponent from "./TempProductComponent";
import axios from 'axios';
import {ACCESS_TOKEN} from "../../constants/auth";
import {Link} from "react-router-dom";
import AddressFields from "./AddressFields";
import {connect} from "react-redux";
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
        products: [],
        address: ''
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
            roles:props.loggedIn.claims.roles,
            request: this.emptyRequest,
            cars: [],
            drivers: [],
            formValid: true,
            productValid: false,
            show: false,
            product: this.emptyProduct,
            productToEdit: this.emptyProduct
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
        this.setState({show: true, product: this.emptyProduct, productToEdit: this.emptyProduct});
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
        this.setState({showExecuteWaybill: false});
        let product = this.state.product;
        let productToEdit = this.state.productToEdit;
        let products = this.state.request.products;
        if (productToEdit !== this.emptyProduct) {
            for (let i = 0; i < products.length; i++) {
                if (products[i] === productToEdit) {
                    products[i] = product;
                }
            }
        } else products.push(product);
        let request = {...this.state.request};
        request['products'] = products;
        this.setState({request: request, product: this.emptyProduct, productToEdit: this.emptyProduct});
    };

    removeProduct(prod) {
        let product = prod;
        let products = this.state.request.products;
        for (let i = 0; i < products.length; i++) {
            if (products[i] === product) {
                products.splice(i, 1);
                i--;
            }
        }
        let request = {...this.state.request};
        request['products'] = products;
        this.setState({request: request, product: this.emptyProduct});
    }

    editProduct(prod) {
        this.setState({show: true, product: prod, productToEdit: prod,});
    }

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
                <td style={{whiteSpace: 'nowrap'}}><Link onClick={() => this.editProduct(product)}>{product.name}</Link>
                </td>
                <td>{product.amount}</td>
                <td>{product.type}</td>
                <td>{product.price}</td>
                <td>{this.productStatusMap[product.status]}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" onClick={() => this.editProduct(product)}
                        >Редактировать</Button>
                        <Button size="sm" color="danger"
                                onClick={() => this.removeProduct(product)}
                        >Удалить</Button>
                    </ButtonGroup>
                </td>
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
            await axios.get(`/request/${this.props.match.params.id}`)
                .then(response => {
                    const newRequest = response.data;
                    newRequest.status='ISSUED';
                    this.setState({request: response.data});
                    console.log(this.state);
                });
        }
        else{
            const request = this.state.request;
            request['address'] = this.address;
            this.setState({request});
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
            return;
        }
        let request = {...this.state.request};
        request['car'] = cars[0];
        request['driver'] = drivers[0];
        this.setState({request: request});
    }

    changeAddress(value) {
        let request = {...this.state.request};
        request['address'] = value.address;
        this.setState({request: request});
    };

    validateForm = () => {
        this.setState({
            addressValid: this.state.addressValid,
        });
    };


    render() {
        const {request} = this.state;
        console.log(request);
        return (
            <Container className="col-3">
                <h1>Заявка</h1>
                <Form onSubmit={this.handleSubmit}>

                    <FormGroup>
                        <Label for="status">Статус</Label>
                        <Input disabled type="select" name="status" id="status" value={request.id?'ISSUED':'NOT_VIEWED' || ''}
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
                        {request.address ? <AddressFields
                            name="address"
                            id="addressFields"
                            validationHandler={this.validateForm}
                            changeState={this.changeAddress.bind(this)}
                            address={request.address}/> : null}
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
                                <th width="10%"></th>
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

                <Modal size="lg" show={this.state.showExecuteWaybill} onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>Добавление продукта</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <FormGroup>
                            <TempProductComponent
                                name="ProductComponent"
                                id="ProductComponent"
                                product={this.state.product}
                                validationHandlerProduct={this.validationHandlerProduct}
                                changeFieldHandler={this.changeFieldHandler}
                            />
                        </FormGroup>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button color="primary"
                                disabled={!this.state.productValid}
                                onClick={this.saveProduct}>
                            Сохранить
                        </Button>
                    </Modal.Footer>
                </Modal>
            </Container>


        );
    }
}

export default connect(
    state => ({
        loggedIn: state.loggedIn,
    }),
)(RequestComponent);