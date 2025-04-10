import React, { useEffect, useState } from 'react';
import { endpoints, useAuthAPI } from '../configs/API';
import styles from '../styles/pages/MySubscription.module.css';
import { useNotification } from '../utils/toast';

const MySubscription = () => {
    const [subscriptions, setSubscriptions] = useState([]);
    const authAPI = useAuthAPI();
    const sendNotification = useNotification();

    useEffect(() => {
        const fetchMySubscriptions = async () => {
            try {
                const res = await authAPI().get(endpoints['my-subscriptions']);
                setSubscriptions(res.data);
            } catch (err) {
                sendNotification({
                    message: err.response?.data ?? err.message
                }, 'error');
            }
        };

        fetchMySubscriptions();
    }, [authAPI, sendNotification]);

    const formatDate = (timestamp) => {
        return new Date(timestamp).toLocaleDateString('vi-VN');
    };

    return (
        <div className={styles.container}>
            <h2>Gói tập đã đăng ký</h2>
            {subscriptions.length === 0 ? (
                <p>Bạn chưa đăng ký gói tập nào.</p>
            ) : (
                <ul className={styles.packageList}>
                    {subscriptions.map((subscription) => (
                        <li key={subscription.id} className={styles.packageItem}>
                            <h3>{subscription.package.name}</h3>
                            <p><strong>Mã gói:</strong> {subscription.package.code}</p>
                            <p><strong>Thời gian:</strong> {subscription.package.duration}</p>
                            <p><strong>Buổi tập với PT:</strong> {subscription.package.pt_sessions}</p>
                            <p><strong>Giá:</strong> {Number(subscription.package.price).toLocaleString()} VNĐ</p>
                            <p><strong>Ngày bắt đầu:</strong> {formatDate(subscription.start_date)}</p>
                            <p><strong>Ngày kết thúc:</strong> {formatDate(subscription.end_date)}</p>
                            <p><strong>Trạng thái:</strong> {subscription.status}</p>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default MySubscription;
