import React from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import styles from '../styles/PackagePage.module.css';

const PackageConfirm = () => {
    const { id } = useParams();
    const navigate = useNavigate();

    const handleConfirm = () => {
        alert("Đăng ký thành công!");
        navigate("/transactions");
    };

    const handleCancel = () => {
        navigate(`/packages/${id}`);
    };

    return (
        <div className={styles.container}>
            <h2 className={styles.title}>Xác nhận đăng ký</h2>
            <div className={styles.card}>
                <p>Bạn có chắc chắn muốn đăng ký gói tập này không?</p>
                <div className={styles.actions}>
                    <button className={styles.button} onClick={handleCancel}>❌ Hủy</button>
                    <button className={`${styles.button} ${styles.subscribe}`} onClick={handleConfirm}>✅ Xác nhận</button>
                </div>
            </div>
        </div>
    );
};

export default PackageConfirm;
