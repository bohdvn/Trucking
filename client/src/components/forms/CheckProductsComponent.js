import React from 'react';
import {Container, FormGroup, Input, Label, Table} from 'reactstrap';


class CheckProductsComponent extends React.Component {

    productStatusMap = {
        'REGISTERED': 'Зарегестрирован',
        'CHECKED': 'Проверен',
        'DELIVERED': 'Доставлен',
        'LOST': 'Утерян'
    };

    constructor(props) {
        super(props);
        this.state = {
            waybill: props.waybill,
            formValid: false,
            inputsValid: this.fillInputsValid(props.waybill.invoice.request.products),
            formErrors: this.fillFormErrors(props.waybill.invoice.request.products)
        };
        this.handleChange = this.handleChange.bind(this);
    }

    fillFormErrors(array) {
        let newArray = [];
        array.forEach(element => {
            newArray.push({id: element.id, value: ''})
        });
        return newArray;
    }

    fillInputsValid(array) {
        let newArray = [];
        array.forEach(element => {
            newArray.push({id: element.id, value: true})
        });
        return newArray;
    }

    handleChange(event) {
        console.log(event);
        let productId = event.target.id;
        let value = (event.target.value === '') ? '0' : event.target.value;
        let waybill = this.state.waybill;
        let array = waybill.invoice.request.products;
        let product = array.find(x => x.id === parseInt(productId));
        product.lostAmount = product.amount - value;
        waybill.invoice.request.products = array;

        this.setState({waybill},
            () => {
                this.validateField(productId, value, product.amount);
            });
    }

    validateField(productId, value, maxValue) {
        let fieldValidationErrors = this.state.formErrors;
        let arrayValid = this.state.inputsValid;
        let inputValid = !!value.match(/^0$|^[1-9]([0-9]*?)$/i) && parseInt(value) <= parseInt(maxValue);
        fieldValidationErrors.find(x => x.id === parseInt(productId)).value = inputValid ? '' : ' введено неверно';
        arrayValid.find(x => x.id === parseInt(productId)).value = inputValid;

        this.setState({
            formErrors: fieldValidationErrors,
            inputsValid: arrayValid
        }, this.validateForm);
    }

    validateForm() {
        const array = this.state.inputsValid.filter(el => el.value === false);
        this.setState({
            formValid: array.length === 0
        });
        this.props.validationHandler(array.length === 0);

    }

    populateRowsWithData = () => {
        return this.state.waybill.invoice.request.products.map(product => {
            return <tr key={product.id}>
                <td style={{whiteSpace: 'nowrap'}}>{product.name}</td>
                <td>{product.amount}</td>
                <td>{product.type}</td>
                <td>{product.price}</td>
                <td>{this.productStatusMap[product.status]}</td>
                <td>
                    <Input type="number" name="lostAmount" id={product.id} value={product.amount - product.lostAmount}
                           onChange={this.handleChange} autoComplete="building" min="0"/>
                    <p className={'error-message'}>{(this.state.formErrors.find(x => x.id === parseInt(product.id)).value === '') ? ''
                        : 'Количество' + this.state.formErrors.find(x => x.id === parseInt(product.id)).value}</p>
                </td>
            </tr>
        });

    };

    render() {
        const {waybill} = this.state;
        return (
            <Container>
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
                            <th>Пришло</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.populateRowsWithData()}
                        </tbody>
                    </Table>
                </FormGroup>

            </Container>
        );
    }
}

export default CheckProductsComponent;