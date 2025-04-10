import { useState } from "react";
import styles from "../../styles/components/Notifications.module.css";

const Notifications = () => {
    const [isOpen, setIsOpen] = useState(true);
    const notifications = [
        "Bạn có cuộc hẹn lúc 10:00 AM",
        "Nhắc nhở: Uống nước đủ mỗi ngày!",
        "Lịch tập gym: Hôm nay là ngày tập chân",
    ];

    return (
        <div className={`${styles.notificationsBox} ${isOpen ? styles.open : styles.closed}`}>
            <div className={styles.header}>
                <h3>Thông báo</h3>
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
