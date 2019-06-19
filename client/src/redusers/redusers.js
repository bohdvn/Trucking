import {combineReducers} from 'redux';

import {CHANGE_LOGGED_IN} from '../actions/user';

import {parseToken} from "../utils/tokenParser";

const loggedInReducer = (state = parseToken(), action) => {
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