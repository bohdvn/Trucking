import React from 'react';
import '../styles.css';

class ProductComponent extends React.Component{
    constructor(props){
        super(props);
    }

    render(){
        return(
            <div className="form-register">
                <h1>Продукт</h1>
                <div className="form-data">
                    <table>
                        <tr>
                            <td>
                                <label htmlFor="nameInput">Наименование</label>
                            </td>
                            <td>
                                <input className="form-table-input" id="nameInput" type="text"/>
                            </td>
                        </tr>

                        <tr>
                            <td>
                                <label htmlFor="amountInput">Количество</label>
                            </td>
                            <td>
                                <input className="form-table-input" id="amountInput" type="number" min="1"/>
                            </td>
                        </tr>

                        <tr>
                            <td>
                                <label htmlFor="packageInput">Упаковка</label>
                            </td>
                            <td>
                                <input className="form-table-input" id="packageInput" type="text"/>
                            </td>
                        </tr>

                        <tr>
                            <td>
                                <label htmlFor="statusSelect">Статус</label>
                            </td>
                            <td>
                                <select className="form-table-input" id="statusSelect">
                                    //TODO: fulfill with status options
                                </select>
                            </td>
                        </tr>
                    </table>
                </div>
                <input className="form-submit" type="submit" value="Сохранить"/>
            </div>
        );
    }
}

export default ProductComponent;