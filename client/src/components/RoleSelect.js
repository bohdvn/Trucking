import React from 'react';
import {connect} from "react-redux";
import * as ROLE from '../constants/userConstants';
import {ROLE_MAP} from '../constants/userRoleMap';
import MultiSelect from "@kenshooui/react-multi-select";
import * as ROLE_OPTION from '../constants/userRoleOptions';

class RoleSelect extends React.Component {
    state = {
        loggedIn: this.props.loggedIn,
        roles: this.props.roles,
        client: this.props.client
    };

    handleChange = selectedItems => {
        const roles=selectedItems.map(item => item.value);
        this.props.onChange(roles);
    };

    getAvailableRoles = () => {
        const roleOptions = [];
        const loggedInRoles = this.state.loggedIn.claims.roles;
        const roles = this.props.roles;
        loggedInRoles.forEach(role => {
            switch (role) {
                case ROLE.SYSADMIN:
                    if (this.state.client) {
                        const adminOption=Object.assign({},ROLE_OPTION.ADMIN_OPTION);
                        roleOptions.push(
                            adminOption
                        );
                    } else {
                        const driverOption=Object.assign({},ROLE_OPTION.DRIVER_OPTION);
                        roleOptions.push(driverOption);
                    }
                    break;

                case ROLE.ADMIN:
                    const adminOption=Object.assign({},ROLE_OPTION.ADMIN_OPTION);
                    const managerOption=Object.assign({},ROLE_OPTION.MANAGER_OPTION);
                    const dispatcherOption=Object.assign({},ROLE_OPTION.DISPATCHER_OPTION);
                    const ownerOption=Object.assign({},ROLE_OPTION.OWNER_OPTION);

                    roleOptions.push(
                        adminOption,
                        managerOption,
                        dispatcherOption,
                        ownerOption
                    );
                    break;

                default:
                    return;
            }
        });
        const selected=roleOptions.filter(option => roles.includes(option.value));
        return {
            options:roleOptions,
            selected:selected
        };
    };

    render() {
        const roles=this.getAvailableRoles();
        return (
            <div>
                <MultiSelect
                    items={roles.options}
                    selectedItems={roles.selected}
                    onChange={this.handleChange}
                />
            </div>
        )
    }
}

export default connect(
    state => ({
        loggedIn: state.loggedIn,
    })
)(RoleSelect);