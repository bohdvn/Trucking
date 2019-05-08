import React from 'react';
import "../styles.css"

class CarComponent extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="form-register">
                <h1>Авто</h1>
                <div className="form-data">
                    <table>
                        <tbody>
                            <tr>
                                <td>
                                    <label htmlFor="nameInput">Название</label>
                                </td>
                                <td>
                                    <input className="form-table-input" id="nameInput" type="text"/>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <label htmlFor="consumptionInput">Расход топлива</label>
                                </td>
                                <td>
                                    <input className="form-table-input" id="consumptionInput" type="number" min="0"/>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <label htmlFor="typeSelect">Тип</label>
                                </td>
                                <td>
                                    <select className="form-table-input" id="typeSelect">
                                        <option value="0">Крытый кузов</option>
                                        <option value="1">Рефрежератор</option>
                                        <option value="2">Автоцистерна</option>
                                    </select>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <label htmlFor="statusSelect">Статус</label>
                                </td>
                                <td>
                                    <select className="form-table-input" id="statusSelect">
                                        <option value="0">Доступно</option>
                                        <option value="1">Недоступно</option>
                                    </select>
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

export default CarComponent;