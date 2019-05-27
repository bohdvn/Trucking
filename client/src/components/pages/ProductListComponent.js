import React from 'react';
import {Button, ButtonGroup, Container, Input, Table} from 'reactstrap';
import {Link} from 'react-router-dom';
import axios from 'axios';
import Pagination from "react-js-pagination";


class ProductListComponent extends React.Component {

    productStatusMap = {
        'REGISTERED': 'Зарегестрирован',
        'CHECKED': 'Проверен',
        'DELIVERED': 'Доставлен',
        'LOST': 'Утерян'
    };

    constructor(props) {
        super(props);
        this.state = {
            products: [],
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

    fetchURL(page) {
        axios.get(`/product/list?page=${page}&size=5`, {
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

                    this.setState({products: results});
                    console.log(results);
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
                products: prevState.products.map(
                    product => (product.id === +id ? {
                        ...product, value: !product.value
                    } : product)
                )
            }
        })
    };

    populateRowsWithData = () => {
        const products = this.state.products.map(product => {
            return <tr key={product.id}>
                <td><Input
                    type="checkbox"
                    id={product.id || ''}
                    name="selected_products"
                    value={product.id || ''}
                    checked={product.value || ''}
                    onChange={this.handleChange}/></td>
                <td style={{whiteSpace: 'nowrap'}}><Link to={"/product/" + product.id}>{product.name}</Link></td>
                <td>{product.amount}</td>
                <td>{product.type}</td>
                <td>{product.price}</td>
                <td>{this.productStatusMap[product.status]}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/product/" + product.id}>Редактировать</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(product.id)}>Удалить</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return products;
    };

    async remove(id) {
        await fetch(`/product/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updateProducts = [...this.state.products].filter(i => i.id !== id);
            this.setState({products: updateProducts});
            this.handlePageChange(0);
        });
    }

    async removeChecked() {
        const selectedProducts = Array.apply(null,
            document.products.selected_products).filter(function (el) {
            return el.checked === true
        }).map(function (el) {
            return el.value
        });
        console.log(selectedProducts);
        await fetch(`/product/${selectedProducts}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updateProducts = [...this.state.products].filter(product => !product.value);
            this.setState({products: updateProducts});
            this.handlePageChange(0);
        });
    }

    render() {
        return (
            <form name="products">
            <div>
                <Container fluid>
                    <div className="float-right">
                        <ButtonGroup>
                        <Button color="success" tag={Link} to="/product/create">Добавить</Button>
                        <Button color="danger" onClick={() => this.removeChecked()}>Удалить выбранные</Button>
                        </ButtonGroup>
                    </div>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th></th>
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

export default ProductListComponent;