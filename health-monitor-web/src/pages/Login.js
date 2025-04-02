import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from '../styles/Login.module.css';
import { style } from 'framer-motion/client';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();

        if (username === 'user@example.com' && password === 'password123') {
            navigate('/dashboard');
        } else {
            setError('Invalid username or password');
        }
    };
    return (
        <div className={styles['login-container']}>
            <h1 className={styles['login-title']}>Login</h1>
            <form onSubmit={handleSubmit}>
                <div className={styles["form-group"]}>
                    <label htmlFor="username">Username:</label>
                    <input
                        type="username"
                        id="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div className={styles["form-group"]}>
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                {error && <p className={styles["error-message"]}>{error}</p>}
                <button type="submit" className={styles["login-button"]}>Login</button>
            </form>
        </div>
    );
};

export default Login;