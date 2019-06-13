import {ACCESS_TOKEN} from '../constants/auth';
import axios from 'axios';


const request = (url,options) => {
    const headers = new Headers({
        'Accept': 'application/json',
        'Content-Type': 'application/json',
    });

    if (localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);

    return fetch(url, options);
};

export function login(url,loginRequest) {
    return request(url,{
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function getUserById(url){
    return request(url,{
        method: 'GET'
    });
}

export function saveUser(user){
    return request('/user/',{
        method: user.id?'PUT':'POST',
        body: JSON.stringify(user),
    })
}

export function getSelected() {
    const selectedUsers = Array.apply(null,
        document.users.selected_users)
        .filter(function (el) {
            return el.checked === true
        }).map(function (el) {
            return el.value
        });
    return selectedUsers;
}