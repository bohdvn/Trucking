import React from 'react';
import {Button, ButtonGroup, Container, Input, Table} from 'reactstrap';
import {Link} from 'react-router-dom';
import axios from 'axios';
import Pagination from "react-js-pagination";


class CarListComponent extends React.Component {

    carTypeMap = {
        'TILT': 'Крытый кузов',
        'TANKER': 'Автоцистерна',
        'FRIDGE': 'Рефрижератор'
    };

    carStatusMap = {
        'AVAILABLE': 'Доступен',
        'UNAVAILABLE': 'Недоступен'
    };

    constructor(props) {
        super(props);
        this.state = {
            cars: [],
            activePage:0,
            totalPages: null,
            itemsCountPerPage: null,
            totalItemsCount: null
        };
        this.handlePageChange = this.handlePageChange.bind(this);
        this.fetchURL = this.fetchURL.bind(this);
        this.removeChecked = this.removeChecked.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    fetchURL(page) {
        axios.get(`/car/list?page=${page}&size=5`, {
            proxy: {
                host: 'http://localhost',
                port: 8080
            }
        })
            .then(response => {
                    console.log(response);
                    const totalPages = response.data.totalPages;
                    const itemsCountPerPage = response.data.size;
                    const totalItemsCount = response.data.totalElements;

                    this.setState({totalPages: totalPages});
                    this.setState({totalItemsCount: totalItemsCount});
                    this.setState({itemsCountPerPage: itemsCountPerPage});

                    const results = response.data.content;
                    console.log(this.state);

                    if (results != null){
                    this.setState({cars: results});
                    console.log(results);
                    }

                    console.log(this.state.activePage);
                    console.log(this.state.itemsCountPerPage);
                }
            );
    }

    componentDidMount() {
        this.fetchURL(this.state.activePage)
    }

    handlePageChange(pageNumber) {
        console.log(`active page is ${pageNumber}`);
        this.setState({activePage: pageNumber});
        this.fetchURL(pageNumber-1)
    }

    handleChange = e => {
        const id = e.target.id;
        this.setState(prevState => {
            return {
                cars: prevState.cars.map(
                    car => (car.id === +id ? {
                        ...car, value: !car.value
                    } : car)
                )
            }
        })
    };

    populateRowsWithData = () => {
        const cars = this.state.cars.map(car => {
            return <tr key={car.id}>
                <td><Input
                    type="checkbox"
                    id={car.id || ''}
                    name="selected_cars"
                    value={car.id || ''}
                    checked={car.value || ''}
                    onChange={this.handleChange}/></td>
                <td style={{whiteSpace: 'nowrap'}}><Link to={"/car/" + car.id}>{car.name}</Link></td>
                <td style={{whiteSpace: 'nowrap'}}>{car.name}</td>
                <td>{this.carTypeMap[car.carType]}</td>
                <td>{car.consumption}</td>
                <td>{this.carStatusMap[car.status]}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/car/" + car.id}>Редактировать</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(car.id)}>Удалить</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return cars
    };

    async remove(id) {
        await fetch(`/car/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updateCars = [...this.state.cars].filter(i => i.id !== id);
            this.setState({cars: updateCars});
            this.handlePageChange(0);
        });
    }

    async removeChecked() {
        const selectedCars = Array.apply(null,
            document.cars.selected_cars).filter(function (el) {
            return el.checked === true
        }).map(function (el) {
            return el.value
        });
        console.log(selectedCars);
        await fetch(`/car/${selectedCars}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updateCars = [...this.state.cars].filter(car => !car.value);
            this.setState({cars: updateCars});
            this.handlePageChange(0);
        });
    }


    render() {
        return (
            <form name="cars">
                <div>
                    <Container fluid>
                        <div className="float-right">
                            <ButtonGroup>
                                <Button color="success" tag={Link} to="/car/create">Добавить</Button>
                                <Button color="danger" onClick={() => this.removeChecked()}>Удалить выбранные</Button>
                            </ButtonGroup>
                        </div>
                        <Table className="mt-4">
                            <thead>
                            <tr>
                                <th></th>
                                <th width="20%">Название</th>
                                <th width="20%">Тип</th>
                                <th width="20%">Расход топлива</th>
                                <th>Статус</th>
                                <th width="10%"></th>
                            </tr>
                            </thead>
                            <tbody>
                            {this.populateRowsWithData()}
                            </tbody>
                        </Table>

                        <div className="d-flex justify-content-center">
                            <Pagination
                                activePage={this.state.activePage}
                                itemsCountPerPage={this.state.itemsCountPerPage}
                                totalItemsCount={this.state.totalItemsCount}
                                itemClass='page-item'
                                linkClass='btn btn-light'
                                onChange={this.handlePageChange}

                            />
                        </div>
                    </Container>
                </div>

            </form>
        );
    }
}

export default CarListComponent;