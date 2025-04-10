import React, { useEffect, useState } from 'react';
import styles from '../styles/pages/Booking.module.css';
import { useAuthAPI, endpoints } from '../configs/API';
import { useNotification } from '../utils/toast';

const Booking = () => {
    const [bookings, setBookings] = useState([]);
    const [packageInfo, setPackageInfo] = useState(null);
    const [trainers, setTrainers] = useState([]);
    const [loading, setLoading] = useState(true);
    const sendNotification = useNotification();
    const authAPI = useAuthAPI();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [pkgRes, trainerRes] = await Promise.all([
                    // authAPI().get(endpoints['my-package']),
                    authAPI().get(endpoints['trainers'])
                ]);
                setPackageInfo(pkgRes.data);
                setTrainers(trainerRes.data);
            } catch (err) {
                sendNotification({ message: 'Không thể tải dữ liệu' }, 'error');
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, [authAPI, sendNotification]);

    const addBooking = () => {
        setBookings(prev => [...prev, {
            date: '',
            time: '',
            trainerId: ''
        }]);
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
            await authAPI.post(endpoints['booking'], { bookings });
            sendNotification({ message: 'Đặt lịch thành công' });
            setBookings([]);
        } catch (err) {
            sendNotification({ message: 'Đặt lịch thất bại' }, 'error');
        }
    };

    if (loading) return <div className={styles.loading}>Đang tải dữ liệu...</div>;

    return (
        <div className={styles.container}>
            <h1>Đặt lịch tập</h1>

            {packageInfo && (
                <div className={styles.packageInfo}>
                    <p><strong>Gói tập:</strong> {packageInfo.name}</p>
                    <p><strong>Số buổi còn lại:</strong> {packageInfo.remainingSessions}</p>
                </div>
            )}

            {bookings.map((b, i) => (
                <div key={i} className={styles.bookingItem}>
                    <input
                        type="date"
                        value={b.date}
                        onChange={e => updateBooking(i, 'date', e.target.value)}
                    />
                    <input
                        type="time"
                        value={b.time}
                        onChange={e => updateBooking(i, 'time', e.target.value)}
                    />
                    <select
                        value={b.trainerId}
                        onChange={e => updateBooking(i, 'trainerId', e.target.value)}
                    >
                        <option value="">-- Chọn PT --</option>
                        {trainers.map(tr => (
                            <option key={tr.id} value={tr.id}>{tr.name}</option>
                        ))}
                    </select>
                </div>
            ))}

            <div className={styles.actions}>
                <button onClick={addBooking}>+ Thêm buổi tập</button>
                <button onClick={handleSubmit}>📝 Xác nhận đặt lịch</button>
            </div>
        </div>
    );
};

export default Booking;
