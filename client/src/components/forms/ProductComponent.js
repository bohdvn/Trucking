import React from 'react';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';


class ProductComponent extends React.Component {
    emptyProduct = {
        name: '',
        type: '',
        amount: '1',
        price: '1',
        status: 'REGISTERED',
        lostAmount: '0'
    };

    constructor(props) {
        super(props);
        this.state = {
            product: this.emptyProduct,
            formErrors: {name: '', type: '', price: '', amount: ''},
            nameValid: false,
            typeValid: false,
            priceValid: true,
            amountValid: true,
            formValid: false
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        console.log(this.props.match.params.id);
        if (this.props.match.params.id !== 'create') {
            const newProduct = await (await fetch(`/product/${this.props.match.params.id}`)).json();
            this.setState({
                product: newProduct, nameValid: true, typeValid: true, priceValid: true,
                amountValid: true, formValid: true
            });
        }
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
    }

    changeAddressFields(value) {
        console.log(value);
        let user = {...this.state.user};
        user['address'] = value.address;
        this.setState({user: user});
    };

    async handleSubmit(event) {
        event.preventDefault();
        const {product} = this.state;
        await fetch('/product/', {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(product)
        }).then(resp => {
            if (resp.status === 400) {
                return resp.json();
            }
            else {
                this.props.history.push('/request/' + this.state.product.request_id);
                return null;
            }
        }).then(data => {
            if (data) {
                let s = '';
                for (const k in data) {
                    s += data[k] + '\n';
                }
                alert(s);
            }
        });

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
        this.setState({
            formValid: this.state.nameValid && this.state.typeValid &&
            this.state.amountValid && this.state.priceValid
        });
    }

    render() {
        const {product} = this.state;
        return (
            <Container className="col-3">
                <h1>Продукт</h1>
                <Form onSubmit={this.handleSubmit}>
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
                    <FormGroup>
                        <Button color="primary" type="submit"
                                disabled={!this.state.formValid}>Сохранить</Button>{' '}
                    </FormGroup>
                </Form>
            </Container>
        );
    }
}

export default ProductComponent;