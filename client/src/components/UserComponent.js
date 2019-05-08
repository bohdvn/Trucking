import React from 'react';
import AddressFields from "./AddressFields";
import '../styles.css';

class UserComponent extends React.Component{
    constructor(props){
        super(props);
    }

    render(){
        return(
            <div className="form-register">
                <h1>Пользователь</h1>
                <div className="form-data">
                    <table>
                        <tr>
                            <td>
                                <label htmlFor="surnameInput">Фамилия<sup>*</sup></label>
                            </td>
                            <td>
                                <input className="form-table-input" id="surnameInput" type="text" required/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label htmlFor="nameInput">Имя</label>
                            </td>
                            <td>
                                <input className="form-table-input" id="nameInput" type="text"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label htmlFor="patronymicInput">Отчество</label>
                            </td>
                            <td>
                                <input className="form-table-input" id="patronymicInput" type="text"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label htmlFor="dateInput">Дата рождения</label>
                            </td>
                            <td>
                                <input className="form-table-input" id="dateInput" type="date"/>
                            </td>
                        </tr>

                        <tr>
                            <td>
                                <label htmlFor="emailInput">Email</label>
                            </td>
                            <td>
                                <input className="form-table-input" id="emailInput" type="email"/>
                            </td>
                        </tr>

                        <tr>
                            <td>
                                <label htmlFor="addressField">Адрес</label>
                            </td>
                            <td>
                                <AddressFields id="addressFields"/>
                            </td>
                        </tr>

                        <tr>
                            <td>
                                <label htmlFor="roleSelect">Роль</label>
                            </td>
                            <td>
                                <select className="form-table-input" id="roleSelect">

                                </select>
                            </td>
                        </tr>

                        <tr>
                            <td>
                                <label htmlFor="loginInput">Логин</label>
                            </td>
                            <td>
                                <input className="form-table-input" id="loginInput" type="text"/>
                            </td>
                        </tr>

                        <tr>
                            <td>
                                <label htmlFor="passInput">Пароль</label>
                            </td>
                            <td>
                                <input className="form-table-input" id="passInput" type="password"/>
                            </td>
                        </tr>
                    </table>
                </div>
                <input className="form-submit" type="submit" value="Сохранить"/>
            </div>
        );
    }
}

export default UserComponent;