import React from 'react';
import "../../styles.css";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";

class ClientComponent extends React.Component {
    emptyClient = {
        id: '',
        name: '',
        type: 'LEGAL',
        status: 'ACTIVE',
    };

    constructor(props) {
        super(props);
        this.state = {
            client: this.emptyClient
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let client = {...this.state.client};
        client[name] = value;
        this.setState({client});
        console.log(this.state);
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {client} = this.state;
        await fetch('/client/', {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(client),
        });
        this.props.history.push('/clients');
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'create') {
            const newClient = await (await fetch(`/client/${this.props.match.params.id}`)).json();
            this.setState({client: newClient});
        }
    }

    render() {
        const {client} = this.state;
        return (
            <Container className="col-3">
                <h1>Клиент</h1>
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Имя/название</Label>
                        <Input type="text" name="name" id="name" value={client.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="type">Тип</Label>
                        <Input type="select" name="type" id="type" value={client.type || ''}
                               onChange={this.handleChange} autoComplete="type">
                            <option value="INDIVIDUAL">Физическое лицо</option>
                            <option value="LEGAL">Юридическое лицо</option>
                        </Input>
                    </FormGroup>
                    <FormGroup>
                        <Label for="status">Статус</Label>
                        <Input type="select" name="status" id="status" value={client.status || ''}
                               onChange={this.handleChange} autoComplete="status">
                            <option value="ACTIVE">Активен</option>
                            <option value="BLOCKED">Заблокирован</option>
                        </Input>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Сохранить</Button>{' '}
                    </FormGroup>
                </Form>
            </Container>
        );
    }
}

export default ClientComponent;