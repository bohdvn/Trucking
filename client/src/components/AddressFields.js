import React from 'react';
import "../styles.css"

class  AddressFields extends React.Component{

    constructor(props){
        super(props);
        console.log(props);
        this.state={
            address:props.address,
        };
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let address = {...this.state.address};
        address[name] = value;
        this.setState({address});
        this.props.changeState(event);
    }

    render() {
        const {address} =this.state;
        console.log(address);
        return(
            <div>
                <table>
                    <tbody>
                    <tr>
                        <td>
                            <label htmlFor="cityInput">Город</label>
                        </td>
                        <td>
                            <input className="form-table-input" name="city" id="cityInput"
                                    value={address.city||''}
                                   onChange={this.handleChange} type="text"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label htmlFor="streetInput">Улица</label>
                        </td>
                        <td>
                            <input className="form-table-input" name="street"  id="streetInput"
                                   value={address.street||''} onChange={this.handleChange} type="text"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label htmlFor="houseInput">Дом</label>
                        </td>
                        <td>
                            <input className="form-table-input" name="building" id="buildingInput"
                                   value={address.building||''} onChange={this.handleChange} type="number" min="1"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label htmlFor="flatInput">Квартира</label>
                        </td>
                        <td>
                            <input className="form-table-input" name="flat" id="flatInput"
                                   value={address.flat||''} onChange={this.handleChange} type="number" min="1"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label htmlFor="longitudeInput">Долгота</label>
                        </td>
                        <td>
                            <input className="form-table-input" name="longitude" id="longitudeInput"
                                   value={address.longitude||''} onChange={this.handleChange} type="text"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label htmlFor="longitudeInput">Широта</label>
                        </td>
                        <td>
                            <input className="form-table-input" name="latitude" id="latitudeInput"
                                   value={address.latitude||''} onChange={this.handleChange} type="text"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        );
    }
}

export default AddressFields;
