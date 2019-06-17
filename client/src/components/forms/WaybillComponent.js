import React from 'react';
import {Button, ButtonGroup, Container, Form, FormGroup, Input, Label, Table} from 'reactstrap';
import Modal from 'react-bootstrap/Modal';
import TempCheckpointComponent from "./TempCheckpointComponent";
import {currentTime} from "../../utils/currentTime";
import {connect} from "react-redux";
import * as ROLE from "../../constants/userConstants";
import axios from 'axios';

class WaybillComponent extends React.Component {

    checkpointStatusMap = {
        'PASSED': 'Пройдена',
        'NOT_PASSED': 'Не пройдена',
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
        dateFrom: currentTime(),
        dateTo: '',
        invoice: '',
        checkpoints: []
    };

    constructor(props) {
        super(props);
        this.state = {
            roles: props.loggedIn.claims.roles,
            waybill: this.emptyWaybill,
            show: false,
            checkpoint: this.emptyCheckpoint
        };
        const locationState = props.location.state;
        this.state.waybill.invoice = locationState ? locationState.invoice : '';
        console.log(this.state);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
    }


    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let waybill = {...this.state.waybill};
        waybill[name] = value;
        this.setState({waybill});
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
    };

    handleShow = checkpoint => {
        console.log(checkpoint);
        this.setState({checkpoint: checkpoint}, () => {
            this.setState({show: true});
        });
    };

    async handleSubmit(event) {
        event.preventDefault();
        const {waybill, roles} = this.state;
        if (!waybill.id) {
            waybill.invoice.status = 'CHECKED';
            waybill.invoice.dateOfCheck = currentTime();
        }
        console.log(waybill);
        await axios({
            method: waybill.id ? 'PUT' : 'POST',
            url: '/waybill/',
            data: waybill
        });
        if (waybill.id && roles.includes(ROLE.DRIVER)) {
            this.props.history.push('/waybills');
        } else {
            this.props.history.push('/invoices');
        }
    }

    validationHandlerCheckpoint = (formValid) => {
    };

    changeFieldHandler = (checkpoint) => {
        this.setState({checkpoint: checkpoint});
        console.log(checkpoint);
    };

    populateRowsWithData = () => {
        return this.state.waybill.checkpoints.map(checkpoint => {
            return <tr key={checkpoint.id}>
                <td style={{whiteSpace: 'nowrap'}}>{checkpoint.name}</td>
                <td>{checkpoint.latitude}</td>
                <td>{checkpoint.longitude}</td>
                <td>{checkpoint.date}</td>
                <td>{this.checkpointStatusMap[checkpoint.status]}</td>
                {checkpoint.id ? <td>
                    <Button size="sm" color="primary" onClick={() => this.handleShow(checkpoint)}>Посмотреть</Button>
                </td> : null}
            </tr>
        });

    };

    async componentDidMount() {
        if (this.props.match.params.id !== 'create') {
            const newWaybill = await (await fetch(`/waybill/${this.props.match.params.id}`)).json();
            this.setState({waybill: newWaybill});
        }
    }

    render() {
        const {waybill, roles} = this.state;
        return (
            <Container className="col-3">
                <h1>Путевой лист</h1>
                <Form onSubmit={this.handleSubmit}>

                    <FormGroup>
                        <Label for="status">Статус</Label>
                        <Input disabled={!roles.includes(ROLE.DRIVER) && !waybill.id} type="select" name="status" id="status"
                               value={waybill.status || ''}
                               onChange={this.handleChange} autoComplete="status">
                            <option value="STARTED">Перевозка начата</option>
                            <option value="FINISHED">Перевозка завершена</option>
                        </Input>
                    </FormGroup>

                    <FormGroup>
                        <Label for="dateFrom">Дата начала</Label>
                        <Input readOnly type="date" name="dateFrom" id="dateFrom" value={waybill.dateFrom || ''}
                               onChange={this.handleChange} autoComplete="dateFrom"/>
                    </FormGroup>

                    <FormGroup>
                        <Label for="dateTo">Дата окончания</Label>
                        <Input readOnly={!roles.includes(ROLE.DRIVER) && !waybill.id} type="date" name="dateTo" id="dateTo"
                               value={waybill.dateTo || ''}
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
                        <Button color="primary" onClick={() => this.handleShow(this.emptyCheckpoint)}>
                            Добавить контрольную точку
                        </Button>
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
                                checkpoint={this.state.checkpoint}
                                validationHandlerCheckpoint={this.validationHandlerCheckpoint}
                                changeFieldHandler={this.changeFieldHandler}
                            />
                        </FormGroup>
                    </Modal.Body>
                    <Modal.Footer>
                        {!this.state.checkpoint.id ?
                            <Button color="primary" onClick={this.saveCheckpoint}>
                                Добавить
                            </Button>
                            : null}
                    </Modal.Footer>
                </Modal>
            </Container>


        );
    }
}

export default connect(
    state => ({
        loggedIn: state.loggedIn,
    }),
)(WaybillComponent);