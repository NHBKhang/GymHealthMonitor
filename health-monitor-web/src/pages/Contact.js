import React, { useState } from 'react';
import styles from '../styles/ContactPage.module.css';

const Contact = () => {
    const [formData, setFormData] = useState({ name: '', email: '', message: '' });

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(formData);
    };

    return (
        <div className={styles.container}>
            <h2 className={styles.title}>Contact Us</h2>

            {/* Thông tin liên hệ và bản đồ */}
            <div className={styles.contactInfo}>
                <div className={styles.contactDetails}>
                    <p><strong>📍 Address:</strong> 123 Main Street, City, Country</p>
                    <p><strong>📞 Phone:</strong> +123 456 7890</p>
                    <p><strong>📧 Email:</strong> contact@example.com</p>
                    <p><strong>🕒 Hours:</strong> Mon - Fri: 9AM - 5PM</p>
                </div>
                <div className={styles.map}>
                    <iframe
                        title="Google Map"
                        src="https://maps.google.com/maps?q=Hanoi&t=&z=13&ie=UTF8&iwloc=&output=embed"
                    ></iframe>
                </div>
            </div>

            {/* Form liên hệ */}
            <form onSubmit={handleSubmit}>
                <div className={styles.formGroup}>
                    <label className={styles.label}>Name:</label>
                    <input type="text" className={styles.input} name="name" value={formData.name} onChange={handleChange} required />
                </div>
                <div className={styles.formGroup}>
                    <label className={styles.label}>Email:</label>
                    <input type="email" className={styles.input} name="email" value={formData.email} onChange={handleChange} required />
                </div>
                <div className={styles.formGroup}>
                    <label className={styles.label}>Message:</label>
                    <textarea className={styles.textarea} name="message" value={formData.message} onChange={handleChange} required />
                </div>
                <button type="submit" className={styles.button}>Submit</button>
            </form>

            {/* Biểu tượng mạng xã hội */}
            <div className={styles.socialIcons}>
                <a href="https://facebook.com" target="_blank" rel="noopener noreferrer" className={styles.socialLink}>🌐 Facebook</a>
                <a href="https://twitter.com" target="_blank" rel="noopener noreferrer" className={styles.socialLink}>🐦 Twitter</a>
                <a href="https://instagram.com" target="_blank" rel="noopener noreferrer" className={styles.socialLink}>📸 Instagram</a>
            </div>
        </div>
    );
};

export default Contact;
