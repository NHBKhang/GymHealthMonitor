import React, { useEffect, useState } from 'react';
import styles from '../styles/PackagesPage.module.css';
import API, { endpoints } from '../configs/API';
import { useNotification } from '../utils/toast';
import { useNavigate } from 'react-router-dom';

const COLORS = ['#e57373', '#64b5f6', '#81c784', '#ffd54f', '#ba68c8'];

const formatCurrency = (price) => {
    return new Intl.NumberFormat('vi-VN').format(price) + ' VNĐ';
};

const Packages = () => {
    const [packages, setPackages] = useState([]);
    const [loading, setLoading] = useState(true);
    const sendNotification = useNotification();
    const navigate = useNavigate();

    useEffect(() => {
        const loadPackages = async () => {
            try {
                const res = await API.get(endpoints.packages);
                setPackages(res.data.results);
            } catch (err) {
                sendNotification({
                    message: "Lỗi khi tải danh sách gói tập"
                }, 'error');
            } finally {
                setLoading(false);
            }
        };

        loadPackages();
    }, [sendNotification]);

    const goToDetail = (packageId) => navigate(`/packages/${packageId}`);

    if (loading) return <div className={styles.loading}>Đang tải...</div>;

    return (
        <div className={styles.container}>
            <h2 className={styles.title}>Tất cả gói tập</h2>
            <div className={styles.grid}>
                {packages.map((pkg, index) => (
                    <div key={pkg.id} className={styles.card} onClick={() => goToDetail(pkg.id)}>
                        <div
                            className={styles.colorBox}
                            style={{ backgroundColor: COLORS[index % COLORS.length] }}
                        >
                            <span className={styles.initial}>{pkg.code.toUpperCase()}</span>
                        </div>
                        <h3 className={styles.name}>{pkg.name}</h3>
                        <p className={styles.duration}>⏳ {pkg.duration}</p>
                        <p className={styles.ptSessions}>💪 {pkg.ptSessions} buổi PT</p>
                        <p className={styles.price}>{formatCurrency(pkg.price)}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Packages;
