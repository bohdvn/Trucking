import {combineReducers} from 'redux';

import {CHANGE_LOGGED_IN} from '../actions/user';

import {parseToken} from "../utils/tokenParser";

const loggedInReducer = (state = parseToken(), action) => {
    console.log('reducer call');
    console.log(`action ${action.loggedIn}`);
    console.log(state);
    switch (action.type) {
        case CHANGE_LOGGED_IN:
            return action.loggedIn;
        default:
            return state;
    }
};


const reducers = combineReducers({
    loggedIn: loggedInReducer,
});


export default reducers;