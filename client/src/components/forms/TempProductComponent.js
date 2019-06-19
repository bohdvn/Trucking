import React from 'react';
import {Container, Form, FormGroup, Input, Label} from 'reactstrap';


class TempProductComponent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            product: props.product,
            formErrors: {name: '', type: '', price: '', amount: ''},
            nameValid: false,
            typeValid: false,
            priceValid: true,
            amountValid: true,
            formValid: false
        };
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let product = {...this.state.product};
        product[name] = value;
        this.setState({product},
            () => {
                this.validateField(name, value)
            });
        this.props.changeFieldHandler(product);
    }

    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let nameValid = this.state.nameValid;
        let typeValid = this.state.typeValid;
        let amountValid = this.state.amountValid;
        let priceValid = this.state.priceValid;
        switch (fieldName) {
            case 'name':
                nameValid = value.length >= 2 && value.length <= 150;
                fieldValidationErrors.name = nameValid ? '' : ' введено неверно';
                break;
            case 'type':
                typeValid = value.length >= 2 && value.length <= 150;
                fieldValidationErrors.type = typeValid ? '' : ' введен неверно';
                break;
            case 'amount':
                amountValid = !!value.match(/^[1-9]([0-9]*?)$/i);
                fieldValidationErrors.amount = amountValid ? '' : ' введено неверно';
                break;
            case 'price':
                priceValid = !!value.match(/^[1-9]([0-9]*?)$/i);
                fieldValidationErrors.price = priceValid ? '' : ' введена неверно';
                break;
            default:
                break;
        }
        this.setState({
            formErrors: fieldValidationErrors,
            typeValid: typeValid,
            nameValid: nameValid,
            amountValid: amountValid,
            priceValid: priceValid
        }, this.validateForm);
    }

    validateForm() {
        let valid = this.state.nameValid && this.state.typeValid &&
            this.state.amountValid && this.state.priceValid;
        this.setState({
            formValid: valid
        });
        this.props.validationHandlerProduct(valid);
    }

    render() {
        const {product} = this.state;
        return (
            <Container className="col-3">
                <h1>Продукт</h1>
                <Form>
                    <FormGroup>
                        <Label for="name">Название</Label>
                        <Input type="text" name="name" id="name" value={product.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                        <p className={'error-message'}>{(this.state.formErrors.name === '') ? ''
                            : 'Название' + this.state.formErrors.name}</p>

                    </FormGroup>

                    <FormGroup>
                        <Label for="amount">Количество</Label>
                        <Input type="number" name="amount" id="amount" value={product.amount || ''}
                               onChange={this.handleChange} autoComplete="amount" min="1"/>
                        <p className={'error-message'}>{(this.state.formErrors.amount === '') ? ''
                            : 'Количество' + this.state.formErrors.amount}</p>
                    </FormGroup>

                    <FormGroup>
                        <Label for="type">Тип</Label>
                        <Input type="text" name="type" id="type" value={product.type || ''}
                               onChange={this.handleChange} autoComplete="type"/>
                        <p className={'error-message'}>{(this.state.formErrors.type === '') ? ''
                            : 'Тип' + this.state.formErrors.type}</p>
                    </FormGroup>

                    <FormGroup>
                        <Label for="price">Стоимость</Label>
                        <Input type="number" name="price" id="price" value={product.price || ''}
                               onChange={this.handleChange} autoComplete="price" min="0"/>
                        <p className={'error-message'}>{(this.state.formErrors.price === '') ? ''
                            : 'Цена' + this.state.formErrors.price}</p>
                    </FormGroup>

                    <FormGroup>
                        <Label for="status">Статус</Label>
                        <Input type="select" name="status" id="status" value={product.status || ''}
                               onChange={this.handleChange} autoComplete="status">
                            <option value="REGISTERED">Зарегестрирован</option>
                            <option value="CHECKED">Проверен</option>
                            <option value="DELIVERED">Доставлен</option>
                            <option value="LOST">Утерян</option>
                        </Input>
                    </FormGroup>
                </Form>
            </Container>
        );
    }
}

export default TempProductComponent;