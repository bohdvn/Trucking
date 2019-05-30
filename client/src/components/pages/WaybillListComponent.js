import React from 'react';
import {Button, ButtonGroup, Container, Input, Table} from 'reactstrap';
import {Link} from 'react-router-dom';
import axios from 'axios';
import Pagination from "react-js-pagination";


class WaybillListComponent extends React.Component {
    waybillStatusMap = {
        'STARTED': 'Перевозка начата',
        'FINISHED': 'Перевозка завершена'
    };

    constructor(props) {
        super(props);
        this.state = {
            waybills: [],
            activePage: 0,
            totalPages: null,
            itemsCountPerPage: null,
            totalItemsCount: null
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
                waybills: prevState.waybills.map(
                    waybill => (waybill.id === +id ? {
                        ...waybill, value: !waybill.value
                    } : waybill)
                )
            }
        })
    };

    fetchURL(page) {
        axios.get(`/waybill/list?page=${page}&size=5`, {
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
                        this.setState({waybills: results});
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
        const waybills = this.state.waybills.map(waybill => {
            return <tr key={waybill.id}>
                <td><Input
                    type="checkbox"
                    id={waybill.id || ''}
                    name="selected_waybills"
                    value={waybill.id || ''}
                    checked={waybill.value || ''}
                    onChange={this.handleChange}/></td>
                    <td style={{whiteSpace: 'nowrap'}}>
                        {waybill.addressFrom.city} {waybill.addressFrom.street} {waybill.addressFrom.building}
                    </td>
                    <td>
                        {waybill.addressTo.city} {waybill.addressTo.street} {waybill.addressTo.building}
                    </td>
                    <td>
                        {waybill.invoice.carName}
                    </td>
                    <td>
                        {waybill.invoice.number}
                    </td>
                    <td>{waybill.dateFrom}</td>
                    <td>{this.waybillStatusMap[waybill.status]}</td>

                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link}
                                to={"/waybill/" + waybill.id}>Редактировать</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(waybill.id)}>Удалить</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return waybills
    };

    async remove(id) {
        await fetch(`/waybill/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updateWaybills = [...this.state.waybills].filter(i => i.id !== id);
            this.setState({waybills: updateWaybills});
            this.handlePageChange(0);
        });
    }

    async removeChecked() {
        const selectedWaybills = Array.apply(null,
            document.waybills.selected_waybills).filter(function (el) {
            return el.checked === true
        }).map(function (el) {
            return el.value
        });
        console.log(selectedWaybills);
        await fetch(`/waybill/${selectedWaybills}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updateWaybills = [...this.state.waybills].filter(waybill => !waybill.value);
            this.setState({products: updateWaybills});
            this.handlePageChange(0);
        });
    }


    render() {
        return (
            <form name="waybills">
                <div>
                    <Container fluid>
                        <div className="float-right">
                            <ButtonGroup>
                                <Button color="success" tag={Link} to="/waybill/create">Добавить</Button>
                                <Button color="danger" onClick={() => this.removeChecked()}>Удалить выбранные</Button>
                            </ButtonGroup>
                        </div>
                        <Table className="mt-4">
                            <thead>
                            <tr>
                                <th></th>
                                <th width="15%">Отправитель</th>
                                <th width="15%">Получатель</th>
                                <th width="15%">Номер автомобиля</th>
                                <th width="15%">Номер ТТН</th>
                                <th width="15%">Дата оформления</th>
                                <th width="15%">Статус</th>
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

export default WaybillListComponent;