import React from 'react';
import '../styles.css';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';


class ProductComponent extends React.Component{
    emptyProduct={
        id:'',
        name:'',
        amount:'',
        type:'',
        price:'',
        status:'REGISTERED',
        actOfLossId:'',
        invoiceId:''
    };
    constructor(props){
        super(props);
        this.state = {
            product: this.emptyProduct
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'create') {
            const newProduct = await (await fetch(`/product/${this.props.match.params.id}`)).json();
            this.setState({product: newProduct});
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let product = {...this.state.product};
        product[name] = value;
        this.setState({product});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {product} = this.state;
        await fetch( '/product/', {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(product),
        });
        this.props.history.push('/products');
    }

    render(){
        const {product}=this.state;
        return(
            <Container className="col-3">
                <h1>Продукт</h1>
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Название</Label>
                        <Input type="text" name="name" id="name" value={product.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>

                    <FormGroup>
                        <Label for="amount">Количество</Label>
                        <Input type="number" name="amount" id="amount" value={product.amount || ''}
                               onChange={this.handleChange} autoComplete="amount" min="1"/>
                    </FormGroup>

                    <FormGroup>
                        <Label for="type">Тип</Label>
                        <Input type="text" name="type" id="type" value={product.type || ''}
                               onChange={this.handleChange} autoComplete="type"/>
                    </FormGroup>

                    <FormGroup>
                        <Label for="price">Стоимость</Label>
                        <Input type="number" name="price" id="price" value={product.price || ''}
                               onChange={this.handleChange} autoComplete="price" min="0"/>
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
                        <Button color="primary" type="submit">Сохранить</Button>{' '}
                    </FormGroup>
                </Form>
            </Container>
        );
    }
}

export default ProductComponent;