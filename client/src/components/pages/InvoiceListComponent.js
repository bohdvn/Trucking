import React from 'react';
import {Container, Table} from 'reactstrap';
import axios from 'axios';
import Pagination from "react-js-pagination";


class InvoiceListComponent extends React.Component {

    carStatusMap = {
        'AVAILABLE': 'Доступен',
        'UNAVAILABLE': 'Недоступен'
    };
    carTypeMap = {
        'TILT': 'Крытый кузов',
        'TANKER': 'Автоцистерна',
        'FRIDGE': 'Рефрижератор'
    };
    invoiceStatusMap = {
        'COMPLETED': 'Оформлена',
        'CHECKED': 'Проверена',
        'CHECKED_BY_DRIVER': 'Проверена водителем',
        'DELIVERED': 'Доставлена'
    };

    constructor(props) {
        super(props);
        this.state = {
            invoices: [],
            activePage: 0,
            totalPages: null,
            itemsCountPerPage: null,
            totalItemsCount: null
        };
        this.handlePageChange = this.handlePageChange.bind(this);
        this.fetchURL = this.fetchURL.bind(this);
        this.remove = this.remove.bind(this);
    }

    fetchURL(page) {
        axios.get(`/invoice/list?page=${page}&size=5`, {
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
                        this.setState({invoices: results});
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
        return this.state.invoices.map(invoice => {
            return <tr key={invoice.id}>
                <td style={{whiteSpace: 'nowrap'}}>{invoice.request.car.name}</td>
                <td>{this.carTypeMap[invoice.request.car.carType]}</td>
                <td>{this.carStatusMap[invoice.request.car.status]}</td>
                <td>{this.invoiceStatusMap[invoice.status]}</td>
                <td>{invoice.request.driver.name}</td>

            </tr>
        });

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
        });
    }


    render() {
        return (
            <div>
                <Container fluid>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Машина</th>
                            <th width="20%">Тип мфшины</th>
                            <th width="20%">Статус машины</th>
                            <th>Статус ТТН</th>
                            <th width="20%">Водитель</th>
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
        );
    }
}

export default InvoiceListComponent;