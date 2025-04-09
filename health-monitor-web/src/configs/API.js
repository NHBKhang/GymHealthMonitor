import axios from "axios";
import { useCallback } from "react";
import { useCookies } from 'react-cookie';

const API_URL = process.env.REACT_APP_API_URL;

export const endpoints = {
    'login': 'login',
    'users': 'users',
    'user': (userId) => `users/${userId}`,
    'current-user': 'current-user',
    'packages': 'packages',
    'package': (packageId) => `packages/${packageId}`,
    'vnpay-payment': 'payments/vnpay',
    'vnpay-return': 'payments/vnpay-return',
}

export const useAuthAPI = () => {
    const [cookies] = useCookies(['access-token']);

    const authAPI = useCallback(() => axios.create({
        baseURL: API_URL,
        headers: {
            "Authorization": `Bearer ${cookies['access-token']}`
        }
    }), [cookies]);

    return authAPI;
};

export default axios.create({
    baseURL: API_URL
});
