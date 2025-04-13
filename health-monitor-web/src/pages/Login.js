import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from '../styles/pages/Login.module.css';
import API, { useAuthAPI, endpoints } from '../configs/API';
import { useUserContext } from '../configs/UserContext';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const { state, dispatch, saveToken } = useUserContext();
    const authAPI = useAuthAPI();
    const navigate = useNavigate();

    useEffect(() => {
        if (state.currentUser) {
            navigate('/');
        }
    }, [state.currentUser, navigate]);

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            let res = await API.post(endpoints.login, { username, password }, {
                headers: {
                    "Content-Type": "application/json"
                }
            });
            await saveToken(res.data);

            if (res.status === 200) {
                let res = await authAPI().get(endpoints['current-user']);
                dispatch({ type: 'SET_USER', payload: res.data.user });

                navigate('/');
            }
        } catch (err) {
            console.info(err);
            setError(err.message);
        }
    };

    return (
        <div className={styles.loginContainer}>
            <div className={styles.logoContainer} onClick={() => navigate('/')}>
                <img src='./logo.png' alt='Gym Logo' />
                <h1>Gym Health Monitor</h1>
            </div>

            <form onSubmit={handleSubmit}>
                <h1 className={styles.loginTitle}>Đăng nhập</h1>
                <div className={styles.formGroup}>
                    <label htmlFor="username">Tên tài khoản:</label>
                    <input
                        type="text"
                        id="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div className={styles.formGroup}>
                    <label htmlFor="password">Mật khẩu:</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                {error && <p className={styles.errorMessage}>{error}</p>}
                <button type="submit" className={styles.loginButton}>Đăng nhập</button>

                <p className={styles.loginLink}>
                    Chưa có tài khoản? <a href="/signup">Đăng ký</a>
                </p>
            </form>
        </div>
    );
};

export default Login;
