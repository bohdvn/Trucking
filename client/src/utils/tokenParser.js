import axios from 'axios';
import {changeLoggedIn} from "../actions/user";

export function parseToken() {
    const token = localStorage.getItem('accessToken');
    if (token) {
        const user = decryptToken(token);
        axios.defaults.headers['Authorization'] = `Bearer ${token}`;
        return user;
    }
    return {
        claims: {
            roles: [{authority: ''}]
        }
    }
}

export function setToken(token) {
    localStorage.setItem('accessToken', token);
    localStorage.setItem('loggedIn', decryptToken(token));
    return parseToken();
}

function decryptToken(token) {
    const authDataEncrypted = token.split('.')[1];
    const user = JSON.parse(window.atob(authDataEncrypted));
    const roles = [];
    user.claims.roles.forEach(roleObject=>{
       roles.push(roleObject.authority);
    });
    user.claims.roles=roles;
    changeLoggedIn(user);
    return user;
}