import React from 'react';
import "../styles.css";

class ClientComponent extends React.Component{
    constructor(props){
        super(props);
    }

    render(){
        return(
            <div className="form-register">
                <h1>Клиент</h1>
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
                    </table>
                </div>
                <input className="form-submit" type="submit"/>
            </div>
        );
    }
}

export default ClientComponent;