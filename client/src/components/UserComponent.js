import React from 'react';
import AddressFields from "./AddressFields";
import '../styles.css';

class UserComponent extends React.Component{
    emptyItem = {
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
        addressDto:'',
    };

    constructor(props){
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'create') {
            const user = await (await fetch(`/user/${this.props.match.params.id}`)).json();
            this.setState({item: user});
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;

        await fetch('/user', {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/users');
    }

    render(){
        const {item} = this.state;
        return(
            <div className="form-register">
                <h1>Пользователь</h1>
                <div className="form-data">
                    <table>
                        <tbody>
                            <tr>
                                <td>
                                    <label htmlFor="surnameInput">Фамилия<sup>*</sup></label>
                                </td>
                                <td>
                                    <input className="form-table-input" name="surname" id="surnameInput"
                                           type="text" onChange={this.handleChange} value={item.surname||''} required/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label htmlFor="nameInput">Имя</label>
                                </td>
                                <td>
                                    <input className="form-table-input" name="name" id="nameInput"
                                           value={item.name||''} type="text" onChange={this.handleChange}/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label htmlFor="patronymicInput">Отчество</label>
                                </td>
                                <td>
                                    <input className="form-table-input" name="patronymic" value={item.patronymic||''}
                                           id="patronymicInput" type="text" onChange={this.handleChange}/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label htmlFor="passportNumberInput">Номер паспорта</label>
                                </td>
                                <td>
                                    <input className="form-table-input" name="passportNumber" value={item.passportNumber||''}
                                           id="passportNumberInput" type="text" onChange={this.handleChange}/>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <label htmlFor="passportIssuedInput">Паспорт выдан</label>
                                </td>
                                <td>
                                    <input className="form-table-input" name="passportIssued" value={item.passportIssued||''}
                                           id="passportIssuedInput" type="text" onChange={this.handleChange}/>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <label htmlFor="dateInput">Дата рождения</label>
                                </td>
                                <td>
                                    <input className="form-table-input" id="dateInput" name="birthDate"
                                           defaultValue={item.birthDate||''} type="date" onChange={this.handleChange}/>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <label htmlFor="emailInput">Email</label>
                                </td>
                                <td>
                                    <input className="form-table-input" name="email" id="emailInput"
                                           value={item.email||''} type="email" onChange={this.handleChange}/>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <label htmlFor="addressField">Адрес</label>
                                </td>
                                <td>
                                    <AddressFields id="addressFields" address={item.addressDto||''}/>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <label htmlFor="roleSelect">Роль</label>
                                </td>
                                <td>
                                    <select className="form-table-input" name="role" id="roleSelect"
                                            value={item.role||''} onChange={this.handleChange}>
                                        <option value="0">Системный администратор</option>
                                        <option value="1">Администратор</option>
                                        <option value="2">Менеджер</option>
                                        <option value="3">Диспетчер</option>
                                        <option value="4">Водитель</option>
                                        <option value="5">Владелец</option>
                                    </select>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <label htmlFor="loginInput">Логин</label>
                                </td>
                                <td>
                                    <input className="form-table-input" name="login" id="loginInput"
                                           value={item.login||''} type="text" onChange={this.handleChange}/>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <label htmlFor="passInput">Пароль</label>
                                </td>
                                <td>
                                    <input className="form-table-input" name="password" id="passInput"
                                           value={item.password||''} type="password" onChange={this.handleChange}/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <input className="form-submit" type="submit" value="Сохранить"/>
            </div>
        );
    }
}

export default UserComponent;