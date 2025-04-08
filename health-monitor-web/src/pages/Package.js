import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import styles from '../styles/PackagePage.module.css';
import API, { endpoints } from '../configs/API';
import { useNotification } from '../utils/toast';

const Package = () => {
    const { id } = useParams();
    const [pkg, setPkg] = useState(null);
    const [loading, setLoading] = useState(true);
    const sendNotification = useNotification();
    const navigate = useNavigate();

    useEffect(() => {
        const fetchPackage = async () => {
            try {
                const res = await API.get(`${endpoints.packages}/${id}`);
                setPkg(res.data);
            } catch (err) {
                sendNotification({ message: "Lỗi khi tải chi tiết gói tập" }, 'error');
            } finally {
                setLoading(false);
            }
        };

        fetchPackage();
    }, [id, sendNotification]);

    if (loading) return <div className={styles.loading}>Đang tải...</div>;
    if (!pkg) return <div className={styles.error}>Không tìm thấy gói tập</div>;

    const handleRegister = () => navigate(`/packages/${pkg.id}/confirm`);

    return (
        <div className={styles.container}>
            <h1 className={styles.title}>{pkg.name}</h1>
            <div className={styles.card}>
                <div className={styles.code}>
                    <p>{pkg.code}</p>
                </div>

                <div className={styles.section}>
                    <h3>Chi tiết gói tập</h3>
                    <ul>
                        <li><strong>Thời gian:</strong> {pkg.duration}</li>
                        <li><strong>Số buổi PT:</strong> {pkg.ptSessions}</li>
                        <li><strong>Giá:</strong> {Number(pkg.price).toLocaleString('vi-VN')} VNĐ</li>
                    </ul>
                </div>

                <div className={styles.section}>
                    <h3>Mô tả</h3>
                    <p>{pkg.description}</p>
                </div>

                <div className={styles.actions}>
                    <button className={`${styles.button}`}
                        onClick={() => navigate("/packages")}>← Quay lại</button>
                    <button className={`${styles.button} ${styles.subscribe}`}
                        onClick={handleRegister}>📝 Đăng ký</button>
                </div>
            </div>
        </div>
    );
};

export default Package;
