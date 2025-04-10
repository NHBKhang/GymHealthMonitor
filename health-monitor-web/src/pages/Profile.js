import React from 'react';
import { Navigate } from 'react-router-dom';
import styles from '../styles/pages/Profile.module.css';
import { useUserContext } from '../configs/UserContext';

const Profile = () => {
    const { state } = useUserContext();
    const user = state.currentUser;

    if (!user) return <Navigate to="/login" />;

    return (
        <div className={styles.container}>
            <h2>Trang cá nhân</h2>
            <div className={styles.card}>
                <img
                    src={user.avatar || "/default-avatar.png"}
                    alt="Avatar"
                    className={styles.avatar}
                />
                <div className={styles.info}>
                    <p><strong>Họ tên:</strong> {user.full_name}</p>
                    <p><strong>Email:</strong> {user.email}</p>
                    <p><strong>Số điện thoại:</strong> {user.phone}</p>
                    <p><strong>Vai trò:</strong> {user.role}</p>
                </div>
                <button className={styles.editBtn}>Chỉnh sửa</button>
            </div>
        </div>
    );
};

export default Profile;
