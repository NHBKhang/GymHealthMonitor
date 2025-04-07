import React, { useEffect, useState } from 'react';
import styles from '../styles/Packages.module.css';
import API, { endpoints } from '../configs/API';
import { useNotification } from '../utils/toast';

const Packages = () => {
    const [packages, setPackages] = useState([]);
    const [loading, setLoading] = useState(true);
    const sendNotification = useNotification();

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

    if (loading) return <div className={styles.loading}>Đang tải...</div>;

    return (
        <div className={styles.container}>
            <h2 className={styles.title}>Tất cả gói tập</h2>
            <div className={styles.grid}>
                {packages.map(pkg => (
                    <div key={pkg.id} className={styles.card}>
                        <img src={pkg.image || '/images/default_package.jpg'} alt={pkg.name} className={styles.image} />
                        <h3 className={styles.name}>{pkg.name}</h3>
                        <p className={styles.description}>{pkg.description}</p>
                        <p className={styles.price}>{pkg.price} VNĐ</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Packages;
