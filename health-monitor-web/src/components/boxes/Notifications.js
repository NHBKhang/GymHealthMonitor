import { useState, useEffect, useRef } from "react";
import styles from "../../styles/components/Notifications.module.css";
import SockJS from "sockjs-client";

const Notifications = () => {
    const [isOpen, setIsOpen] = useState(true);
    const [notifications, setNotifications] = useState([
        "Bạn có cuộc hẹn lúc 10:00 AM",
        "Nhắc nhở: Uống nước đủ mỗi ngày!",
        "Lịch tập gym: Hôm nay là ngày tập chân",
    ]);

    const socketRef = useRef(null);

    useEffect(() => {
        const socket = new SockJS("http://localhost:8080/HealthMonitorApp/ws/notifications");
        socketRef.current = socket;

        socket.onopen = () => {
            console.log("✅ WebSocket connected");
        };

        socket.onmessage = (event) => {
            const message = event.data;
            setNotifications((prev) => [message, ...prev]);
        };

        socket.onerror = (err) => {
            console.error("❌ WebSocket error", err);
        };

        socket.onclose = () => {
            console.log("❎ WebSocket closed");
        };

        return () => {
            socket.close();
        };
    }, []);

    return (
        <div className={`${styles.notificationsBox} ${isOpen ? styles.open : styles.closed}`}>
            <div className={styles.header}>
                <h4>Thông báo</h4>
                <button onClick={() => setIsOpen(!isOpen)}>
                    {isOpen ? "Ẩn" : "Hiện"}
                </button>
            </div>
            {isOpen && (
                <ul className={styles.list}>
                    {notifications.map((note, index) => (
                        <li key={index}>{note}</li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default Notifications;
