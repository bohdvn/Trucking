import React from 'react';
import {Container, FormGroup, Table} from 'reactstrap';


class ActOfLossComponent extends React.Component {

    productStatusMap = {
        'REGISTERED': 'Зарегестрирован',
        'CHECKED': 'Проверен',
        'DELIVERED': 'Доставлен',
        'LOST': 'Утерян'
    };

    constructor(props) {
        super(props);
        this.state = {
            waybill: props.waybill
        };
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        console.log(event);
        let productId = event.target.id;
        let value = event.target.value;
        let waybill = this.state.waybill;
        let array = waybill.invoice.request.products;
        let product = array.find(x => x.id === parseInt(productId));
        product.lostAmount = product.amount - value;
        waybill.invoice.request.products = array;
        this.setState({waybill: waybill});
    }


    populateRowsWithData = (array) => {
        console.log(array);
        return array.map(product => {
            return <tr key={product.id}>
                <td style={{whiteSpace: 'nowrap'}}>{product.name}</td>
                <td>{product.amount}</td>
                <td>{product.type}</td>
                <td>{product.price}</td>
                <td>{this.productStatusMap[product.status]}</td>
                <td>{product.lostAmount}</td>
            </tr>
        });

    };

    render() {
        const {waybill} = this.state;
        console.log(waybill.invoice.request.products);
        const array = waybill.invoice.request.products.filter(product => product.lostAmount !== 0);
        return (
            <Container>
                <FormGroup>
                    {
                        array.length === 0 ?
                            <p>Все продукты доставлены</p> :
                            <Table name="productTable" id="productTable" className="mt-4">
                                <thead>
                                <tr>
                                    <th width="20%">Наименование</th>
                                    <th width="20%">Количество</th>
                                    <th width="20%">Тип</th>
                                    <th>Стоимость</th>
                                    <th>Статус</th>
                                    <th>Утеряно</th>
                                </tr>
                                </thead>
                                <tbody>
                                {this.populateRowsWithData(array)}
                                </tbody>
                            </Table>

                    }
                </FormGroup>

            </Container>
        );
    }
}

export default ActOfLossComponent;