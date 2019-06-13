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
//         .then(response =>
//             response.json().then(json => {
//                 console.log(response.status);
//                 if (!response.ok) {
//                     return Promise.reject(json);
//                 }
//                 return json;
//             })
//         );
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

// export default function setAuthToken(token) {
//     if (token) {
//         axios.defaults.headers.common['Authorization'] = `JWT ${token}`;
//     } else {
//         delete axios.defaults.headers.common['Authorization'];
//     }
// }