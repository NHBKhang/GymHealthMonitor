import axios from "axios";

const API_URL = process.env.REACT_APP_API_URL;

export const endpoints = {
    'login': 'login',
    'users': 'users',
    'user': (userId) => `users/${userId}`,
}

export const authApi = (accessToken) => axios.create({
    baseURL: API_URL,
    headers: {
        "Authorization": `bearer ${accessToken}`
    }
});

export default axios.create({
    baseURL: API_URL
});