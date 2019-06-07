import React from 'react';
import {Button, Container, Form, FormGroup, Input, Label, Table} from 'reactstrap';
import Modal from 'react-bootstrap/Modal';
import TempCheckpointComponent from "./TempCheckpointComponent";


class WaybillComponent extends React.Component {

    checkpointStatusMap = {
        'PASSED': 'Пройдена',
        'NOT_PASED': 'Не пройдена',
    };

    emptyCheckpoint = {
        name: '',
        date: '',
        latitude: '',
        longitude: '',
        status: 'NOT_PASSED'
    };

    emptyWaybill = {
        id: '',
        status: 'STARTED',
        dateFrom: '',
        dateTo: '',
        invoice: '',
        checkpoints: []
    };

    constructor(props) {
        super(props);
        this.state = {
            waybill: this.emptyWaybill,
            invoice: [],

            show: false,
            checkpoint: this.emptyCheckpoint
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);

    }


    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        if (name === 'invoiceInput') {
            this.getInvoice(value);
        } else {
            let waybill = {...this.state.waybill};
            waybill[name] = value;
            this.setState({waybill});
        }


    }

    handleClose() {
        this.setState({show: false});
    }

    saveCheckpoint = () => {
        this.setState({show: false});
        let checkpoint = this.state.checkpoint;
        let checkpoints = this.state.waybill.checkpoints;
        checkpoints.push(checkpoint);
        let waybill = {...this.state.waybill};
        waybill['checkpoints'] = checkpoints;
        this.setState({waybill: waybill, checkpoint: this.emptyCheckpoint});
    }

    handleShow() {
        this.setState({show: true});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {waybill} = this.state;
        await fetch('/waybill/', {
            method: waybill.id ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(waybill)
        });

        this.props.history.push('/waybills');
    }

    async getInvoice(id) {
        const newInvoice = await (await fetch(`/invoice/${id}`)).json();
        let waybill = {...this.state.waybill};
        waybill['invoice'] = newInvoice;
        this.setState({waybill});

    }

    validationHandlerCheckpoint = (formValid) => {
    };

    changeFieldHandler = (checkpoint) => {
        console.log(checkpoint);
        this.setState({checkpoint: checkpoint});
    }

    populateRowsWithData = () => {
        return this.state.waybill.checkpoints.map(checkpoint => {
            return <tr key={checkpoint.id}>
                <td style={{whiteSpace: 'nowrap'}}>{checkpoint.name}</td>
                <td>{checkpoint.latitude}</td>
                <td>{checkpoint.longitude}</td>
                <td>{checkpoint.date}</td>
                <td>{this.checkpointStatusMap[checkpoint.status]}</td>
            </tr>
        });

    };

    fillInvoiceSelector() {
        return this.state.invoices.map(invoice => {
            return <option value={invoice.id}>{invoice.name}</option>
        });
    };

    async componentDidMount() {
        if (this.props.match.params.id !== 'create') {
            const newWaybill = await (await fetch(`/waybill/${this.props.match.params.id}`)).json();
            this.setState({waybill: newWaybill});
        }
        const invoices = await (await fetch(`/invoice/all`)).json();
        console.log(invoices);
        this.setState({invoices: invoices});

    }

    async createCheckpoint(id) {
        this.props.history.push('/checkpoint/create/' + id);
    }

    render() {
        const {waybill} = this.state;

        return (
            <Container className="col-3">
                <h1>Путевой лист</h1>
                <Form onSubmit={this.handleSubmit}>

                    <FormGroup>
                        <Label for="status">Статус</Label>
                        <Input type="select" name="status" id="status" value={waybill.status || ''}
                               onChange={this.handleChange} autoComplete="status">
                            <option value="STARTED">Перевозка начата</option>
                            <option value="FINISHED">Перевозка завершена</option>
                        </Input>
                    </FormGroup>
                    {/*<FormGroup>*/}
                    {/*<Label for="invoiceInput">ТТН</Label>*/}
                    {/*<Input type="select" name="invoiceInput" id="invoiceInput" value={waybill.invoice.id || ''}*/}
                    {/*onChange={this.handleChange}>*/}
                    {/*{this.fillInvoiceSelector()}*/}
                    {/*</Input>*/}
                    {/*</FormGroup>*/}
                    <FormGroup>
                        <Label for="dateFrom">Дата начала</Label>
                        <Input type="date" name="dateFrom" id="dateFrom" value={waybill.dateFrom || ''}
                               onChange={this.handleChange} autoComplete="dateFrom"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="dateTo">Дата окончания</Label>
                        <Input type="date" name="dateTo" id="dateTo" value={waybill.dateTo || ''}
                               onChange={this.handleChange} autoComplete="dateTo"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="checkpointTable">Контрольные точки</Label>
                        <Table name="checkpointTable" id="checkpointTable" className="mt-4">
                            <thead>
                            <tr>
                                <th width="20%">Наименование</th>
                                <th width="20%">Широта</th>
                                <th width="20%">Долгота</th>
                                <th>Дата прохождения</th>
                                <th>Статус</th>
                            </tr>
                            </thead>
                            <tbody>
                            {this.populateRowsWithData()}
                            </tbody>
                        </Table>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" onClick={this.handleShow}>Добавить контрольную точку</Button>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Сохранить</Button>{' '}
                    </FormGroup>

                </Form>

                <Modal size="lg" show={this.state.show} onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>Добавление контрольной точки</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <FormGroup>
                            <TempCheckpointComponent
                                name="CheckpointComponent"
                                id="CheckpointComponent"
                                checkpoint={this.emptyCheckpoint}
                                validationHandlerCheckpoint={this.validationHandlerCheckpoint}
                                changeFieldHandler={this.changeFieldHandler}
                            />
                        </FormGroup>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button color="primary" onClick={this.saveCheckpoint}>
                            Добавить
                        </Button>
                    </Modal.Footer>
                </Modal>
            </Container>


        );
    }
}

export default WaybillComponent;