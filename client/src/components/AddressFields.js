import React from 'react';
import "../styles.css"

class  AddressFields extends React.Component{
    constructor(props){
        super(props);
    }

    render() {
        return(
            <div>
                <table>
                    <tr>
                        <td>
                            <label htmlFor="cityInput">Город</label>
                        </td>
                        <td>
                            <input className="form-table-input" id="cityInput" type="text"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label htmlFor="streetInput">Улица</label>
                        </td>
                        <td>
                            <input className="form-table-input" id="streetInput" type="text"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label htmlFor="houseInput">Дом</label>
                        </td>
                        <td>
                            <input className="form-table-input" id="houseInput" type="text"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label htmlFor="flatInput">Квартира</label>
                        </td>
                        <td>
                            <input className="form-table-input" id="flatInput" type="text"/>
                        </td>
                    </tr>
                </table>
            </div>
        );
    }
}

export default AddressFields;
