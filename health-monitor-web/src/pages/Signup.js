import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from '../styles/pages/Signup.module.css';
import API, { endpoints } from '../configs/API';
import { useUserContext } from '../configs/UserContext';

const Signup = () => {
    const [formData, setFormData] = useState({
        username: '',
        password: '',
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        file: null
    });
    const [previewAvatar, setPreviewAvatar] = useState(null);
    const [errors, setErrors] = useState({});
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [showPassword, setShowPassword] = useState(false);
    const { state } = useUserContext();
    const navigate = useNavigate();

    useEffect(() => {
        if (state.currentUser) {
            navigate('/');
        }
    }, [state.currentUser, navigate]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleFileChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            setFormData({
                ...formData,
                file
            });
            setPreviewAvatar(URL.createObjectURL(file));
        }
    };

    const validateForm = () => {
        const newErrors = {};

        if (!formData.username.trim()) newErrors.username = 'Tên người dùng là bắt buộc';
        if (!formData.password) {
            newErrors.password = 'Mật khẩu là bắt buộc';
        } else if (formData.password.length < 8) {
            newErrors.password = 'Mật khẩu phải có ít nhất 8 ký tự';
        }
        if (!formData.firstName.trim()) newErrors.firstName = 'Họ là bắt buộc';
        if (!formData.lastName.trim()) newErrors.lastName = 'Tên là bắt buộc';

        if (!formData.email.trim()) {
            newErrors.email = 'Email là bắt buộc';
        } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
            newErrors.email = 'Email không hợp lệ';
        }

        if (!formData.phone.trim()) {
            newErrors.phone = 'Số điện thoại là bắt buộc';
        } else if (!/^[+]?[(]?[0-9]{3}[)]?[-\s.]?[0-9]{3}[-\s.]?[0-9]{4,6}$/.test(formData.phone)) {
            newErrors.phone = 'Số điện thoại không hợp lệ';
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = async (e) => {
        setErrors('')
        e.preventDefault();
        setIsSubmitting(true);

        if (validateForm()) {
            const formPayload = new FormData();
            for (const key in formData) {
                let value = formData[key]
                if (value && value.toString().trim() !== '')
                    formPayload.append(key, value);
            }

            try {
                let res = await API.post(endpoints.users, formPayload,
                    {
                        headers: { "Content-Type": "mulitpart/form-data" }
                    }
                );
                console.info(res);
                if (res.status === 201)
                    navigate('/login');
                else
                    setErrors({ submit: "Yêu cầu không hợp lệ!" })
            } catch (error) {
                console.info(error);
                setErrors({ submit: error.message })
            } finally {
                setIsSubmitting(false);
            }
        } else {
            setIsSubmitting(false);
        }
    };

    return (
        <div className={styles.signupContainer}>
            <div className={styles.logoContainer} onClick={() => navigate('/')}>
                <img src='./logo.png' alt='Gym Logo' />
                <h1>Gym Health Monitor</h1>
            </div>

            <div className={styles.signupCard}>
                <h1>Tạo tài khoản</h1>

                <form onSubmit={handleSubmit} noValidate>
                    <div className={styles.formRow}>
                        <div className={styles.formGroup}>
                            <label htmlFor="firstName">
                                Họ <span style={{ color: 'red' }}>*</span>
                            </label>
                            <input
                                type="text"
                                id="firstName"
                                name="firstName"
                                value={formData.firstName}
                                onChange={handleChange}
                                className={errors.firstName ? styles.errorInput : ''}
                            />
                            {errors.firstName && <span className={styles.errorText}>{errors.firstName}</span>}
                        </div>

                        <div className={styles.formGroup}>
                            <label htmlFor="lastName">
                                Tên <span style={{ color: 'red' }}>*</span>
                            </label>
                            <input
                                type="text"
                                id="lastName"
                                name="lastName"
                                value={formData.lastName}
                                onChange={handleChange}
                                className={errors.lastName ? styles.errorInput : ''}
                            />
                            {errors.lastName && <span className={styles.errorText}>{errors.lastName}</span>}
                        </div>
                    </div>

                    <div className={styles.formGroup}>
                        <label htmlFor="username">
                            Tên tài khoản <span style={{ color: 'red' }}>*</span>
                        </label>
                        <input
                            type="text"
                            id="username"
                            name="username"
                            value={formData.username}
                            onChange={handleChange}
                            className={errors.username ? styles.errorInput : ''}
                        />
                        {errors.username && <span className={styles.errorText}>{errors.username}</span>}
                    </div>

                    <div className={styles.formGroup}>
                        <label htmlFor="email">
                            Email <span style={{ color: 'red' }}>*</span>
                        </label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            className={errors.email ? styles.errorInput : ''}
                        />
                        {errors.email && <span className={styles.errorText}>{errors.email}</span>}
                    </div>

                    <div className={styles.formGroup}>
                        <label htmlFor="phone">
                            Số điện thoại <span style={{ color: 'red' }}>*</span>
                        </label>
                        <input
                            type="tel"
                            id="phone"
                            name="phone"
                            value={formData.phone}
                            onChange={handleChange}
                            placeholder="+1234567890"
                            className={errors.phone ? styles.errorInput : ''}
                        />
                        {errors.phone && <span className={styles.errorText}>{errors.phone}</span>}
                    </div>

                    <div className={styles.formGroup}>
                        <label htmlFor="password">
                            Mật khẩu <span style={{ color: 'red' }}>*</span>
                        </label>
                        <div className={styles.passwordInput}>
                            <input
                                type={showPassword ? "text" : "password"}
                                id="password"
                                name="password"
                                value={formData.password}
                                onChange={handleChange}
                                className={errors.password ? styles.errorInput : ''}
                            />
                            <button
                                type="button"
                                className={styles.togglePassword}
                                onClick={() => setShowPassword(!showPassword)}
                            >
                                {showPassword ? (
                                    <svg viewBox="0 0 24 24">
                                        <path d="M12,9A3,3 0 0,1 15,12A3,3 0 0,1 12,15A3,3 0 0,1 9,12A3,3 0 0,1 12,9M12,4.5C17,4.5 21.27,7.61 23,12C21.27,16.39 17,19.5 12,19.5C7,19.5 2.73,16.39 1,12C2.73,7.61 7,4.5 12,4.5M3.18,12C4.83,15.36 8.24,17.5 12,17.5C15.76,17.5 19.17,15.36 20.82,12C19.17,8.64 15.76,6.5 12,6.5C8.24,6.5 4.83,8.64 3.18,12Z" />
                                    </svg>
                                ) : (
                                    <svg viewBox="0 0 24 24">
                                        <path d="M11.83,9L15,12.16C15,12.11 15,12.05 15,12A3,3 0 0,0 12,9C11.94,9 11.89,9 11.83,9M7.53,9.8L9.08,11.35C9.03,11.56 9,11.77 9,12A3,3 0 0,0 12,15C12.22,15 12.44,14.97 12.65,14.92L14.2,16.47C13.53,16.8 12.79,17 12,17A5,5 0 0,1 7,12C7,11.21 7.2,10.47 7.53,9.8M2,4.27L4.28,6.55L4.73,7C3.08,8.3 1.78,10 1,12C2.73,16.39 7,19.5 12,19.5C13.55,19.5 15.03,19.2 16.38,18.66L16.8,19.08L19.73,22L21,20.73L3.27,3M12,7A5,5 0 0,1 17,12C17,12.64 16.87,13.26 16.64,13.82L19.57,16.75C21.07,15.5 22.27,13.86 23,12C21.27,7.61 17,4.5 12,4.5C10.6,4.5 9.26,4.75 8,5.2L10.17,7.35C10.74,7.13 11.35,7 12,7Z" />
                                    </svg>
                                )}
                            </button>
                        </div>
                        {errors.password && <span className={styles.errorText}>{errors.password}</span>}
                    </div>

                    <div className={styles.formGroup} style={{ marginBottom: '1rem' }}>
                        <label htmlFor="avatar">Ảnh đại diện</label>
                        <div className={styles.avatarUpload}>
                            <div className={styles.avatarPreview}>
                                {previewAvatar ? (
                                    <img src={previewAvatar} alt="Avatar preview" />
                                ) : (
                                    <div className={styles.avatarPlaceholder}>
                                        <svg viewBox="0 0 24 24">
                                            <path d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,6A2,2 0 0,0 10,8A2,2 0 0,0 12,10A2,2 0 0,0 14,8A2,2 0 0,0 12,6M12,13C14.67,13 20,14.33 20,17V20H4V17C4,14.33 9.33,13 12,13Z" />
                                        </svg>
                                    </div>
                                )}
                            </div>
                            <input
                                type="file"
                                id="avatar"
                                name="avatar"
                                accept="image/*"
                                onChange={handleFileChange}
                                className={styles.fileInput}
                            />
                            <label htmlFor="avatar" className={styles.uploadButton}>
                                {previewAvatar ? 'Thay đổi hình ảnh' : 'Tải ảnh lên'}
                            </label>
                        </div>
                    </div>

                    {errors.submit && <span className={`${styles.errorText} ${styles.submitError}`}>{errors.submit}</span>}

                    <button
                        type="submit"
                        className={styles.submitButton}
                        disabled={isSubmitting}
                    >
                        {isSubmitting ? (
                            <>
                                <span className={styles.spinner}></span>
                                Đang tạo tài khoản...
                            </>
                        ) : (
                            'Đăng ký'
                        )}
                    </button>
                </form>

                <p className={styles.loginLink}>
                    Đã có tài khoản? <a href="/login">Đăng nhập</a>
                </p>
            </div>
        </div>
    );
};

export default Signup;
