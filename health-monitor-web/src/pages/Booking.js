import React, { useEffect, useState } from 'react';
import styles from '../styles/pages/Booking.module.css';
import { useAuthAPI, endpoints } from '../configs/API';
import { useNotification } from '../utils/toast';

const Booking = () => {
    const [bookings, setBookings] = useState([]);
    const [subscription, setSubscription] = useState(null);
    const [trainers, setTrainers] = useState([]);
    const [loading, setLoading] = useState(true);
    const sendNotification = useNotification();
    const authAPI = useAuthAPI();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [subRes, trainerRes] = await Promise.all([
                    authAPI().get(endpoints['my-subscriptions']),
                    authAPI().get(endpoints['trainers'])
                ]);
                setSubscription(subRes.data);
                setTrainers(trainerRes.data.results);
            } catch (err) {
                sendNotification({ message: 'Không thể tải dữ liệu' }, 'error');
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, [authAPI, sendNotification]);

    const addBooking = () => {
        setBookings(prev => [...prev, { date: '', time: '', trainerId: '' }]);
    };

    const updateBooking = (index, field, value) => {
        const updated = [...bookings];
        updated[index][field] = value;
        setBookings(updated);
    };

    const handleSubmit = async () => {
        if (bookings.length === 0) {
            sendNotification({ message: 'Bạn cần chọn ít nhất một buổi tập' }, 'info');
            return;
        }

        try {
            await authAPI().post(endpoints['booking-schedule'], { bookings });
            sendNotification({ message: 'Đặt lịch thành công' });
            setBookings([]);
        } catch (err) {
            sendNotification({ message: 'Đặt lịch thất bại' }, 'error');
        }
    };

    if (loading) return <div className={styles.loading}>Đang tải dữ liệu...</div>;

    return (
        <div className={styles.container}>
            <h2 className={styles.title}>Đặt lịch tập</h2>

            {subscription && (
                <div className={styles.subscription}>
                    <p><strong>Gói tập:</strong> {subscription.name}</p>
                    <p><strong>Số buổi còn lại:</strong> {subscription.remainingSessions}</p>
                </div>
            )}

            {bookings.map((b, i) => (
                <div key={i} className={styles.bookingItem}>
                    <input
                        type="date"
                        value={b.date}
                        onChange={e => updateBooking(i, 'date', e.target.value)}
                        className={styles.input}
                    />
                    <input
                        type="time"
                        value={b.time}
                        onChange={e => updateBooking(i, 'time', e.target.value)}
                        className={styles.input}
                    />
                    <select
                        value={b.trainerId}
                        onChange={e => updateBooking(i, 'trainerId', e.target.value)}
                        className={styles.select}
                    >
                        <option value="">-- Chọn PT --</option>
                        {trainers.map(tr => (
                            <option key={tr.id} value={tr.id}>{tr.full_name}</option>
                        ))}
                    </select>
                </div>
            ))}

            <div className={styles.actions}>
                <button className={styles.button} onClick={addBooking}>+ Thêm buổi tập</button>
                <button className={`${styles.button} ${styles.submit}`} onClick={handleSubmit}>📝 Xác nhận đặt lịch</button>
            </div>
        </div>
    );
};

export default Booking;
