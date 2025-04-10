import React, { useEffect, useState } from 'react';
import { endpoints, useAuthAPI } from '../configs/API';
import styles from '../styles/pages/MySchedule.module.css';
import { useNotification } from '../utils/toast';

const MySchedule = () => {
    const [schedules, setSchedules] = useState([]);
    const authAPI = useAuthAPI();
    const sendNotification = useNotification();

    useEffect(() => {
        const fetchSchedule = async () => {
            try {
                const res = await authAPI().get(endpoints['my-schedules']);
                setSchedules(res.data);
            } catch (err) {
                sendNotification({
                    message: err.response?.data ?? err.message
                }, 'error');
            }
        };

        fetchSchedule();
    }, [authAPI, sendNotification]);

    return (
        <div className={styles.container}>
            <h2>Lịch tập của tôi</h2>
            {schedules.length === 0 ? (
                <p>Bạn chưa có buổi tập nào.</p>
            ) : (
                <ul className={styles.scheduleList}>
                    {schedules.map((item, idx) => (
                        <li key={idx} className={styles.scheduleItem}>
                            <strong>Ngày:</strong> {item.date} - <strong>Giờ:</strong> {item.time}<br />
                            <strong>Huấn luyện viên:</strong> {item.trainerName}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default MySchedule;
