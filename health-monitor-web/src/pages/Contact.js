import React, { useState } from "react";
import styles from "../styles/ContactPage.module.css";

const Contact = () => {
    const [formData, setFormData] = useState({
        name: "",
        email: "",
        message: "",
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log("Submitted:", formData);
        // TODO: Gửi dữ liệu đến backend (nếu cần)
    };

    return (
        <section className={styles.contactPage}>
            <h2 className={styles.title}>Liên hệ với chúng tôi</h2>

            <div className={styles.contentWrapper}>
                {/* Thông tin liên hệ và bản đồ */}
                <article className={styles.contactDetails}>
                    <h3>Thông tin liên hệ</h3>
                    <p><strong>📍 Địa chỉ:</strong> 123 Main Street, Hanoi, Vietnam</p>
                    <p><strong>📞 Điện thoại:</strong> +84 123 456 789</p>
                    <p><strong>📧 Email:</strong> contact@example.com</p>
                    <p><strong>🕒 Giờ làm việc:</strong> Thứ 2 - Thứ 6: 9:00 - 17:00</p>

                    <div className={styles.mapContainer}>
                        <iframe
                            title="Bản đồ Google"
                            src="https://maps.google.com/maps?q=Hanoi&t=&z=13&ie=UTF8&iwloc=&output=embed"
                            loading="lazy"
                            allowFullScreen
                        ></iframe>
                    </div>
                </article>

                {/* Form liên hệ */}
                <article className={styles.contactForm}>
                    <h3>Gửi tin nhắn</h3>
                    <form onSubmit={handleSubmit}>
                        <div className={styles.formGroup}>
                            <label htmlFor="name">Tên của bạn</label>
                            <input
                                type="text"
                                id="name"
                                name="name"
                                value={formData.name}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        <div className={styles.formGroup}>
                            <label htmlFor="email">Email</label>
                            <input
                                type="email"
                                id="email"
                                name="email"
                                value={formData.email}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        <div className={styles.formGroup}>
                            <label htmlFor="message">Tin nhắn</label>
                            <textarea
                                id="message"
                                name="message"
                                value={formData.message}
                                onChange={handleChange}
                                rows="5"
                                required
                            />
                        </div>

                        <button type="submit" className={styles.button}>Gửi</button>
                    </form>
                </article>
            </div>

            <div className={styles.socialSection}>
                <h4>Theo dõi chúng tôi</h4>
                <div className={styles.socialLinks}>
                    <a href="https://facebook.com" target="_blank" rel="noopener noreferrer">🌐 Facebook</a>
                    <a href="https://twitter.com" target="_blank" rel="noopener noreferrer">🐦 Twitter</a>
                    <a href="https://instagram.com" target="_blank" rel="noopener noreferrer">📸 Instagram</a>
                </div>
            </div>
        </section>
    );
};

export default Contact;
