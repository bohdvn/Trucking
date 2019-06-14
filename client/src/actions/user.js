export const CHANGE_LOGGED_IN = 'CHANGE_LOGGED_IN';


export const changeLoggedIn = (loggedIn) => ({
    type: CHANGE_LOGGED_IN,
    loggedIn: loggedIn,
});