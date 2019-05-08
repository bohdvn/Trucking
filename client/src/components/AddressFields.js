import React from 'react';
import "../styles.css"

class  AddressFields extends React.Component{
    address={
        id: '',
        city: '',
        street: '',
        house: '',
        flat: '',
    };

    constructor(props){
        super(props);
        console.log(this.props);
        // this.state=props.address;

        this.handleChange = this.handleChange.bind(this);
        // this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState({ [event.target.name]: event.target.value });
    }

    render() {
        const {address}=this.state;
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
                                   value={address.city||''} onChange={this.handleChange} type="text"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label htmlFor="streetInput">Улица</label>
                        </td>
                        <td>
                            <input className="form-table-input" name="street"  id="streetInput" onChange={this.handleChange} type="text"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label htmlFor="houseInput">Дом</label>
                        </td>
                        <td>
                            <input className="form-table-input" name="house" id="houseInput" onChange={this.handleChange} type="text"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label htmlFor="flatInput">Квартира</label>
                        </td>
                        <td>
                            <input className="form-table-input" name="flat" id="flatInput" onChange={this.handleChange} type="text"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        );
    }
}

export default AddressFields;
