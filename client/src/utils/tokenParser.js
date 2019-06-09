import axios from 'axios';
import {changeLoggedIn} from "../actions/user";

export function parseToken() {
    console.log('parse token');
    const token = localStorage.getItem('accessToken');
    if(token){
        const user=decryptToken(token);
        axios.defaults.headers['Authorization']=`Bearer ${token}`;
        return user;
    }
    return {roles:[{authority:''}]};
}

export function setToken(token){
    console.log('set token');
    localStorage.setItem('accessToken',token);
    localStorage.setItem('loggedIn',decryptToken(token));
    parseToken();
}

function decryptToken(token){
    console.log('decrypt token');
    const authDataEncrypted = token.split('.')[1];
    const user = JSON.parse(window.atob(authDataEncrypted));
    changeLoggedIn(user);
    console.log(user);
    return user;
}