import React from 'react';
import {Container, Form, FormGroup, Input, Label, Table} from 'reactstrap';


class InvoiceComponent extends React.Component {

    requestStatusMap = {
        'NOT_VIEWED': 'Не просмотрена',
        'REJECTED': 'Отклонена',
        'ISSUED': 'Оформлена'
    };

    constructor(props) {
        super(props);
        console.log(props);
        this.state = {
            invoice: props.invoice
        };
    }

    populateRowsWithData = (products) => {
        return products.map(product => {
            return <tr key={product.id}>
                <td style={{whiteSpace: 'nowrap'}}>{product.name}</td>
                <td>{product.amount}</td>
                <td>{product.type}</td>
                <td>{product.price}</td>
            </tr>
        });
    };

    handleChange = event => {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let invoice = {...this.state.invoice};
        invoice[name] = value;
        this.setState({invoice}, () => {
            this.props.handleChange(value);
        });
        console.log(this.state);
    };

    render() {
        const {request} = this.state.invoice;
        const {invoice} = this.state;
        console.log(request);
        return (
            <Container className="col-3">
                <h1>ТТН</h1>
                <Form>
                    <FormGroup>
                        <Label for="number">Номер</Label>
                        <Input readOnly={invoice.id} type="text" name="number" id="number"
                               value={invoice.number || ''}
                               onChange={this.handleChange}
                               autoComplete="number"/>
                    </FormGroup>

                    <FormGroup>
                        <Label for="status">Статус</Label>
                        <Input readOnly type="text" name="status" id="status"
                               value={this.requestStatusMap[request.status]}
                               autoComplete="status"/>
                    </FormGroup>

                    <FormGroup>
                        <Label for="car">Машина</Label>
                        <Input readOnly type="text" name="car" id="car" value={request.car.name}
                               autoComplete="car"/>
                    </FormGroup>

                    <FormGroup>
                        <Label for="driver">Водитель</Label>
                        <Input readOnly type="text" name="driver" id="driver"
                               value={`${request.driver.surname} ${request.driver.name}`}/>
                    </FormGroup>

                    {invoice.dateOfIssue ?
                        <FormGroup>
                            <Label for="dateOfIssue">Дата оформления</Label>
                            <Input readOnly type="date" name="dateOfIssue" id="dateOfIssue"
                                   value={invoice.dateOfIssue}/>
                        </FormGroup> : null}

                    {invoice.dispatcherFrom ?
                        <FormGroup>
                            <Label for="dispatcherFrom">Оформил(а)</Label>
                            <Input readOnly type="text" name="dispatcherFrom" id="dispatcherFrom"
                                   value={`${invoice.dispatcherFrom.surname} ${invoice.dispatcherFrom.name}`}/>
                        </FormGroup> : null}

                    <FormGroup>
                        < Label for="productTable"> Продукты </Label>
                        <Table name="productTable" id="productTable" className="mt-4">
                            <thead>
                            <tr>
                                <th width="20%">Наименование</th>
                                <th width="20%">Количество</th>
                                <th width="20%">Тип</th>
                                <th>Стоимость</th>
                            </tr>
                            </thead>
                            <tbody>
                            {this.populateRowsWithData(request.products)}
                            </tbody>
                        </Table>
                    </FormGroup>
                </Form>
            </Container>
        );
    }
}

export default InvoiceComponent;