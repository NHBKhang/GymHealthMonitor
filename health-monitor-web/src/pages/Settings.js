import React, { useState } from 'react';
import styles from '../styles/pages/Settings.module.css';

const Settings = () => {
    const [darkMode, setDarkMode] = useState(false);
    const [language, setLanguage] = useState('vi');
    const [notifications, setNotifications] = useState(true);

    return (
        <div className={styles.container}>
            <h1>Cài đặt hệ thống</h1>

            <div className={styles.settingGroup}>
                <label>Chế độ tối:</label>
                <input
                    type="checkbox"
                    checked={darkMode}
                    onChange={() => setDarkMode(prev => !prev)}
                />
            </div>

            <div className={styles.settingGroup}>
                <label>Ngôn ngữ:</label>
                <select value={language} onChange={(e) => setLanguage(e.target.value)}>
                    <option value="vi">Tiếng Việt</option>
                    <option value="en">English</option>
                </select>
            </div>

            <div className={styles.settingGroup}>
                <label>Thông báo:</label>
                <input
                    type="checkbox"
                    checked={notifications}
                    onChange={() => setNotifications(prev => !prev)}
                />
            </div>

            <button className={styles.saveBtn}>Lưu thay đổi</button>
        </div>
    );
};

export default Settings;
