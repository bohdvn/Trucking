import React from 'react';
import AddressFields from "./AddressFields";
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';


class UserComponent extends React.Component{
    emptyUser = {
        id:'',
        surname: '',
        name: '',
        patronymic: '',
        passportNumber:'',
        passportIssued:'',
        birthDate: '',
        email: '',
        role:'',
        login:'',
        password:'',
        address:'{}',
    };

    constructor(props){
        super(props);
        this.state = {
            user: this.emptyUser
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'create') {
            const newUser = await (await fetch(`/user/${this.props.match.params.id}`)).json();
            this.setState({user: newUser});
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let user = {...this.state.user};
        user[name] = value;
        this.setState({user});
    }

    changeAddressFields(value){
        let user = {...this.state.user};
        user['address'] = value.address;
        this.setState({user});
    };

    async handleSubmit(event) {
        event.preventDefault();
        const {user} = this.state;

        await fetch('/user/', {
            method:'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user),
        });

        await fetch('/address/', {
            method:'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user.address),
        });
        this.props.history.push('/user');
    }

    render(){
        const {user} = this.state;
        return(
            <Container className="col-3">
                <h1>Пользователь</h1>
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="surname">Фамилия</Label>
                        <Input type="text" name="surname" id="surname" value={user.surname || ''}
                               onChange={this.handleChange} autoComplete="surname"/>
                    </FormGroup>

                    <FormGroup>
                        <Label for="name">Имя</Label>
                        <Input type="text" name="name" id="name" value={user.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>

                    <FormGroup>
                        <Label for="patronymic">Отчество</Label>
                        <Input type="text" name="patronymic" id="patronymic" value={user.patronymic || ''}
                               onChange={this.handleChange} autoComplete="patronymic"/>
                    </FormGroup>

                    <FormGroup>
                        <Label for="passportNumber">Номер паспорта</Label>
                        <Input type="text" name="passportNumber" id="passportNumber" value={user.passportNumber || ''}
                               onChange={this.handleChange} autoComplete="passportNumber"/>
                    </FormGroup>

                    <FormGroup>
                        <Label for="passportIssued">Паспорт выдан</Label>
                        <Input type="text" name="passportIssued" id="passportIssued" value={user.passportIssued || ''}
                               onChange={this.handleChange} autoComplete="passportIssued"/>
                    </FormGroup>

                    <FormGroup>
                        <Label for="birthDate">Дата рождения</Label>
                        <Input type="date" name="birthDate" id="birthDate" value={user.birthDate || ''}
                               onChange={this.handleChange} autoComplete="birthDate"/>
                    </FormGroup>

                    <FormGroup>
                        <Label for="email">Email</Label>
                        <Input type="email" name="email" id="email" value={user.email || ''}
                               onChange={this.handleChange} autoComplete="email"/>
                    </FormGroup>

                    <FormGroup>
                        {user.address?<AddressFields
                            name="address"
                            id="addressFields"
                            changeState={this.changeAddressFields.bind(this)}
                            address={user.address}/>:null}
                    </FormGroup>

                    <FormGroup>
                        <Label for="role">Роль</Label>
                        <Input type="select" name="role" id="role" value={user.role || ''}
                               onChange={this.handleChange} autoComplete="role">
                            <option value="0">Системный администратор</option>
                            <option value="1">Администратор</option>
                            <option value="2">Менеджер</option>
                            <option value="3">Диспетчер</option>
                            <option value="4">Водитель</option>
                            <option value="5">Владелец</option>
                        </Input>
                    </FormGroup>

                    <FormGroup>
                        <Label for="login">Логин</Label>
                        <Input type="text" name="login" id="login" value={user.login || ''}
                               onChange={this.handleChange} autoComplete="login"/>
                    </FormGroup>

                    <FormGroup>
                        <Label for="password">Пароль</Label>
                        <Input type="password" name="password" id="password" value={user.password || ''}
                               onChange={this.handleChange} autoComplete="password"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Сохранить</Button>{' '}
                    </FormGroup>
                </Form>
            </Container>
        );
    }
}

export default UserComponent;