import React from 'react';
import "../styles.css"

class CarComponent extends React.Component{
    constructor(props){
        super(props);
    }

    render(){
        return(
            <div className="form-register">
                <h1>Авто</h1>
                <div className="form-data">
                    <table>
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
                                    //TODO: fulfill with car's type array
                                </select>
                            </td>
                        </tr>

                        <tr>
                            <td>
                                <label htmlFor="statusSelect">Статус</label>
                            </td>
                            <td>
                                <select className="form-table-input" id="statusSelect">
                                    //TODO: fulfill with car's status array
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

export default CarComponent;