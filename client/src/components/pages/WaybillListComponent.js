import React from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import {Link} from 'react-router-dom';
import axios from 'axios';
import Pagination from "react-js-pagination";


class WaybillListComponent extends React.Component {

    waybillStatusMap = {
        'STARTED': 'Начата',
        'FINISHED': 'Завершена'
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
        this.remove = this.remove.bind(this);
    }

    fetchURL(page) {
        axios.get(`/waybill/list?page=${page}&size=5`)
            .then(response => {
                    const totalPages = response.data.totalPages;
                    const itemsCountPerPage = response.data.size;
                    const totalItemsCount = response.data.totalElements;
                    console.log(response);
                    this.setState({
                        totalPages: totalPages,
                        totalItemsCount: totalItemsCount,
                        itemsCountPerPage: itemsCountPerPage,
                        waybills: response.data.content || []
                    });
                }, error => {
                    const {status, statusText} = error.response;
                    const data = {status, statusText}
                    this.props.history.push('/error', {error: data});
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
                <td>{waybill.invoice.number}</td>
                <td>{`${waybill.invoice.request.clientCompanyFrom.address.city}
                    ${waybill.invoice.request.clientCompanyFrom.address.street}
                    ${waybill.invoice.request.clientCompanyFrom.address.building}
                    ${waybill.invoice.request.clientCompanyFrom.address.flat}`}
                </td>
                <td>{`${waybill.invoice.request.address.city}
                    ${waybill.invoice.request.address.street}
                    ${waybill.invoice.request.address.building}
                    ${waybill.invoice.request.address.flat}`}
                </td>
                <td>{waybill.dateFrom}</td>
                <td>{this.waybillStatusMap[waybill.status]}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link}
                                to={"/waybill/" + waybill.id}>Открыть</Button>
                        {/*<Button size="sm" color="danger" onClick={() => this.remove(waybill.id)}>Удалить</Button>*/}
                    </ButtonGroup>
                </td>
            </tr>
        });

        return waybills;
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
        });
    }


    render() {
        const check = !this.state.waybills.length;
        return (
            <Container className="text-center" fluid>
                {check ? <h3>Список пуст</h3> :
                        <div>
                            <Table className="mt-4">
                                <thead>
                                <tr>
                                    <th width="20%">Номер ТТН</th>
                                    <th width="20%">Пункт отправления</th>
                                    <th width="20%">Пункт назначения</th>
                                    <th width="20%">Дата отправления</th>
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
                        </div>
                }
            </Container>
        );
    }
}

export default WaybillListComponent;