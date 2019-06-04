//Dmitry Gorlach
import React from 'react';
import {Button, ButtonGroup, Container, Input, Table} from 'reactstrap';
import {Link} from 'react-router-dom';
import axios from 'axios';
import Pagination from "react-js-pagination";

const address = <th width="30%">Адрес</th>;
const name = <th width="30%">Название</th>;

class WarehouseListComponent extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            warehouses: [],
            activePage: 0,
            totalPages: null,
            itemsCountPerPage: null,
            totalItemsCount: null,
            warehouse: {
                companyType: 'WAREHOUSE'
            }
        };
        this.handlePageChange = this.handlePageChange.bind(this);
        this.fetchURL = this.fetchURL.bind(this);
        this.removeChecked = this.removeChecked.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange = e => {
        const id = e.target.id;
        this.setState(prevState => {
            return {
                warehouses: prevState.warehouses.map(
                    warehouse => (warehouse.id === +id ? {
                        ...warehouse, value: !warehouse.value
                    } : warehouse)
                )
            }
        })
    };

    fetchURL(page) {
        axios.get(`/client/list?page=${page}&size=5&companyType=${this.state.warehouse.companyType}`, {
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

                    if (results != null) {
                        this.setState({warehouses: results});
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
        this.fetchURL(pageNumber - 1)
    }

    populateRowsWithData = () => {
        const warehouses = this.state.warehouses.map(warehouse => {
            return <tr key={warehouse.id}>
                <td><Input
                    type="checkbox"
                    id={warehouse.id || ''}
                    name="selected_warehouses"
                    value={warehouse.id || ''}
                    checked={warehouse.value || ''}
                    onChange={this.handleChange}/></td>
                <td style={{whiteSpace: 'nowrap'}}><Link to={"/warehouse/" + warehouse.id}>{warehouse.name}</Link></td>
                <td>{warehouse.address.city}, {warehouse.address.street}, {warehouse.address.building}-{warehouse.address.flat}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link}
                                to={"/warehouse/" + warehouse.id}>Редактировать</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(warehouse.id)}>Удалить</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });
        return warehouses
    };

    async remove(id) {
        await fetch(`/client/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updateWarehouses = [...this.state.warehouses].filter(i => i.id !== id);
            this.setState({warehouses: updateWarehouses});
            this.handlePageChange(0);
        });
    }

    async removeChecked() {
        const selectedWarehouses = Array.apply(null,
            document.warehouses.selected_warehouses).filter(function (el) {
            return el.checked === true
        }).map(function (el) {
            return el.value
        });
        console.log(selectedWarehouses);
        await fetch(`/client/${selectedWarehouses}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updateWarehouses = [...this.state.warehouses].filter(warehouse => !warehouse.value);
            this.setState({warehouses: updateWarehouses});
            this.handlePageChange(0);
        });
    }

    render() {
        return (
            <form name="warehouses">
                <div>
                    <Container fluid>
                        <div className="float-right">
                            <ButtonGroup>
                                <Button color="success" tag={Link} to="/warehouse/create">Добавить</Button>
                                <Button color="danger" onClick={() => this.removeChecked()}>Удалить выбранные</Button>
                            </ButtonGroup>
                        </div>
                        <Table className="mt-4">
                            <thead>
                            <tr>
                                <th></th>
                                {name}
                                {address}
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

export default WarehouseListComponent;