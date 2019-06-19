import React from 'react';
import {Button, ButtonGroup, Container, FormGroup, Input, Table} from 'reactstrap';
import {Link} from 'react-router-dom';
import axios from 'axios';
import Pagination from "react-js-pagination";
import {ACCESS_TOKEN} from "../../constants/auth";

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
            query: '',
            cars: [],
            activePage: 0,
            totalPages: null,
            itemsCountPerPage: null,
            totalItemsCount: null
        };
        this.queryTimeout = -1;
        this.handlePageChange = this.handlePageChange.bind(this);
        this.fetchURL = this.fetchURL.bind(this);
        this.removeChecked = this.removeChecked.bind(this);
        this.handleQueryChange = this.handleQueryChange.bind(this);
        this.changeQuery = this.changeQuery.bind(this);
    }

    fetchURL(page, query) {
        axios.get(`/car/list/${query}?page=${page}&size=5`)
            .then(response => {
                    const totalPages = response.data.totalPages;
                    const itemsCountPerPage = response.data.size;
                    const totalItemsCount = response.data.totalElements;

                    this.setState({
                        totalPages: totalPages,
                        totalItemsCount: totalItemsCount,
                        itemsCountPerPage: itemsCountPerPage,
                        cars: response.data.content

                    });
                }, error => {
                    const {status, statusText} = error.response;
                    const data = {status, statusText}
                    this.props.history.push('/error', {error: data});
                }
            );
    }

    componentDidMount() {
        this.fetchURL(this.state.activePage, this.state.query)
    }

    handlePageChange(pageNumber) {
        this.setState({activePage: pageNumber});
        this.fetchURL(pageNumber - 1, this.state.query);
    }

    changeQuery() {
        this.fetchURL(0, this.state.query);
    }

    handleQueryChange(event) {
        clearTimeout(this.queryTimeout);
        const target = event.target;
        const value = target.value;
        this.setState(() => ({
            query: value
        }));
        this.queryTimeout = setTimeout(this.changeQuery, 1000);
    }

    getSelected=()=>{
        return Array.apply(this,
            document.getElementsByName("selectedСars")).filter(function (el) {
            return el.checked === true
        }).map(function (el) {
            return el.value
        });
    };

    handleChange = e => {
        const id = e.target.id;
        console.log(this.state);
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
                    name="selectedСars"
                    value={car.id || ''}
                    checked={car.value || ''}
                    onChange={this.handleChange}/></td>
                <td style={{whiteSpace: 'nowrap'}}><Link to={"/car/" + car.id}>{car.name}</Link></td>
                <td>{this.carTypeMap[car.carType]}</td>
                <td>{car.consumption}</td>
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

    remove(id) {
        axios.delete(`/car/${id}`)
            .then(() => {
                let updateCars = [...this.state.cars].filter(i => i.id !== id);
                this.setState({cars: updateCars});
                this.handlePageChange(0);
            }, error => {
                const {status, statusText} = error.response;
                const data = {status, statusText}
                this.props.history.push('/error', {error: data});
            });
    }

    async removeChecked() {
        const selectedCars = this.getSelected();
        console.log(selectedCars);
        await fetch(`/car/${selectedCars}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
            }
        }).then(() => {
            let updateCars = [...this.state.cars].filter(car => !car.value);
            this.setState({cars: updateCars});
            this.handlePageChange(0);
        });
    }


    render() {
        const check = !this.state.cars.length;
        return (
            <Container className="text-center" fluid>
                <FormGroup>
                    <Input type="text" name="searchQuery" id="searchQuery" value={this.state.query}
                           onChange={this.handleQueryChange} autoComplete="searchQuery"/>
                </FormGroup>
                <div className="float-right">
                    <ButtonGroup>
                        <Button color="success" tag={Link} to="/car/create">Добавить</Button>
                        <Button color="danger" onClick={() => this.removeChecked()}>Удалить выбранные</Button>
                    </ButtonGroup>
                </div>
                {check ? <h3>Список пуст</h3> :
                    <div>
                        <Table className="mt-4">
                            <thead>
                            <tr>
                                <th width="5%"></th>
                                <th width="20%">Название</th>
                                <th width="20%">Тип</th>
                                <th width="20%">Расход топлива</th>
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
                    </div>}
            </Container>
        );
    }
}

export default CarListComponent;