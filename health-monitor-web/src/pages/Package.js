import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import styles from '../styles/pages/Package.module.css';
import API, { endpoints, useAuthAPI } from '../configs/API';
import { useNotification } from '../utils/toast';
import PaymentResultPopup from '../components/cards/PaymentResultPopup';

const Package = () => {
    const { id } = useParams();
    const [pkg, setPkg] = useState(null);
    const [loading, setLoading] = useState(true);
    const [popupVisible, setPopupVisible] = useState(false);
    const [transactionId, setTransactionId] = useState(null);
    const sendNotification = useNotification();
    const navigate = useNavigate();
    const authAPI = useAuthAPI();
    const location = useLocation();

    useEffect(() => {
        const loadPackage = async () => {
            try {
                const res = await API.get(endpoints.package(id));
                setPkg(res.data);
            } catch (err) {
                sendNotification({ message: "Lỗi khi tải chi tiết gói tập" }, 'error');
            } finally {
                setLoading(false);
            }
        };

        loadPackage();
    }, [id, sendNotification]);

    useEffect(() => {
        const checkVnpayReturn = async () => {
            const query = location.search;
            const params = new URLSearchParams(query);
            if (query.includes("vnp_")) {
                try {
                    const res = await authAPI().get(endpoints["vnpay-return"] + query);
                    if (res.data.code === 1) {
                        setTransactionId(params.get("vnp_TransactionNo"));
                        setPopupVisible(true);
                    }
                } catch (err) {
                    console.error("Lỗi kiểm tra thanh toán:", err);
                }
            }
        };

        checkVnpayReturn();
    }, [location.search, authAPI]);

    useEffect(() => {
        if (popupVisible) {
            navigate(`/packages/${id}`, { replace: true });
        }
    }, [popupVisible, id, navigate]);

    if (loading) return <div className={styles.loading}>Đang tải...</div>;
    if (!pkg) return <div className={styles.error}>Không tìm thấy gói tập</div>;

    const handleRegister = () => navigate(`/packages/${pkg.id}/register`);

    return (
        <>
            <div className={styles.container}>
                <h1 className={styles.title}>{pkg.name}</h1>
                <div className={styles.card}>
                    <div className={styles.code}>
                        <p>{pkg.code}</p>
                    </div>

                    <div className={styles.section}>
                        <h3>Chi tiết gói tập</h3>
                        <ul>
                            <li><strong>Thời gian:</strong> {pkg.duration}</li>
                            <li><strong>Số buổi PT:</strong> {pkg.pt_sessions}</li>
                            <li><strong>Giá:</strong> {Number(pkg.price).toLocaleString('vi-VN')} VNĐ</li>
                        </ul>
                    </div>

                    <div className={styles.section}>
                        <h3>Mô tả</h3>
                        <p><div dangerouslySetInnerHTML={{ __html: pkg?.description }} /></p>
                    </div>

                    <div className={styles.actions}>
                        <button className={`${styles.button}`}
                            onClick={() => navigate("/packages")}>← Quay lại</button>
                        <button className={`${styles.button} ${styles.subscribe}`}
                            onClick={handleRegister}>📝 Đăng ký</button>
                    </div>
                </div>
            </div>
            {popupVisible && (
                <PaymentResultPopup
                    transactionId={transactionId}
                    onClose={() => setPopupVisible(false)}
                />
            )}
        </>
    );
};

export default Package;
