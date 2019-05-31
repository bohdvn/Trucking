export function parseToken() {
    const token = localStorage.getItem('accessToken');
    const authDataEncrypted = token.split('.')[1];
    const user = JSON.parse(window.atob(authDataEncrypted))
    console.log(user);
    return user;
}