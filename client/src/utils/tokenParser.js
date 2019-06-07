export function parseToken() {
    const token = localStorage.getItem('accessToken');
    if (token) {
        const user = decryptToken(token);
        return user;
    }
    return {roles: [{authority: ''}]};
}

export function setToken(token) {
    localStorage.setItem('accessToken', token);
    localStorage.setItem('loggedIn', decryptToken(token));
}

function decryptToken(token) {
    const authDataEncrypted = token.split('.')[1];
    const user = JSON.parse(window.atob(authDataEncrypted));
    console.log(user);
    return user;
}