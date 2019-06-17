export function currentTime() {
    let date = new Date();
    return date.getFullYear() + '-' + ((date.getMonth().toString().length === 1)
        ? ('0' + (+date.getMonth() + 1)) : (+date.getMonth() + 1)) + '-' + ((date.getDate().toString().length === 1)
        ? ('0' + date.getDate()) : date.getDate());
}