import React from 'react';
import {connect} from "react-redux";
import * as ROLE from '../constants/userConstants';
import MultiSelect from "@kenshooui/react-multi-select";
import * as ROLE_OPTION from '../constants/userRoleOptions';

class RoleSelect extends React.Component {
    state = {
        loggedIn: this.props.loggedIn,
        roles: this.props.roles,
        client: this.props.client
    };

    handleChange = selectedItems => {
        const roles = selectedItems.map(item => item.value);
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
                        roleOptions.push(
                            ROLE_OPTION.ADMIN_OPTION
                        );
                    } else {
                        roleOptions.push(
                            ROLE_OPTION.DRIVER_OPTION
                        );
                    }
                    break;

                case ROLE.ADMIN:
                    roleOptions.push(
                        ROLE_OPTION.ADMIN_OPTION,
                        ROLE_OPTION.MANAGER_OPTION,
                        ROLE_OPTION.DISPATCHER_OPTION,
                        ROLE_OPTION.OWNER_OPTION
                    );
                    break;

                default:
                    return;
            }
        });
        const selected = roleOptions.filter(option => roles.includes(option.value));
        return {
            options: roleOptions,
            selected: selected
        };
    };

    render() {
        const roles = this.getAvailableRoles();
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