import React from 'react';
import Container from "reactstrap/es/Container";
import FormGroup from "reactstrap/es/FormGroup";
import {Input, Label} from "reactstrap";

class  AddressFields extends React.Component{

    constructor(props){
        super(props);
        console.log(this.props.address);
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
        const state =this.state;
        this.props.changeState(state);
    }

    render() {
        const {address} =this.state;
        return(
            <Container>
                <FormGroup>
                    <Label for="city">Город</Label>
                    <Input type="text" name="city" id="city" value={address.city || ''}
                       onChange={this.handleChange} autoComplete="city"/>
                </FormGroup>

                <FormGroup>
                    <Label for="street">Улица</Label>
                    <Input type="text" name="street" id="street" value={address.street || ''}
                           onChange={this.handleChange} autoComplete="street"/>
                </FormGroup>

                <FormGroup>
                    <Label for="building">Дом</Label>
                    <Input type="number" name="building" id="building" value={address.building || ''}
                           onChange={this.handleChange} autoComplete="building" min="1"/>
                </FormGroup>

                <FormGroup>
                    <Label for="flat">Квартира</Label>
                    <Input type="number" name="flat" id="flat" value={address.flat || ''}
                           onChange={this.handleChange} autoComplete="flat" min="1"/>
                </FormGroup>

                <FormGroup>
                    <Label for="longitude">Долгота</Label>
                    <Input type="text" name="longitude" id="longitude" value={address.longitude || ''}
                           onChange={this.handleChange} autoComplete="longitude"/>
                </FormGroup>

                <FormGroup>
                    <Label for="latitude">Широта</Label>
                    <Input type="text" name="latitude" id="latitude" value={address.latitude || ''}
                           onChange={this.handleChange} autoComplete="latitude"/>
                </FormGroup>
            </Container>
        );
    }
}

export default AddressFields;