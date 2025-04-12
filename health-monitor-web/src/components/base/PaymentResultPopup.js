import React, { useEffect, useState } from 'react';
import styles from '../../styles/components/PaymentResultPopup.module.css';
import { CheckCircle } from 'lucide-react';
import { endpoints, useAuthAPI } from '../../configs/API';
import { useLocation, useNavigate } from 'react-router-dom';
import { useGlobalContext } from '../../configs/GlobalContext';

const PaymentResultPopup = () => {
    const { popupVisible, setPopupVisible, popupData } = useGlobalContext();
    const [transactionId, setTransactionId] = useState(popupData?.transactionId);
    const [paymentCode, setPaymentCode] = useState(popupData?.paymentCode);
    const [paymentText,] = useState(popupData?.paymentText || "Gói tập đã được thanh toán thành công!");
    const authAPI = useAuthAPI();
    const location = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        const checkVnpayReturn = async () => {
            const query = location.search;
            const params = new URLSearchParams(query);
            if (query.includes("vnp_")) {
                try {
                    const res = await authAPI().get(endpoints["vnpay-return"] + query);
                    if (res.data.code === 1) {
                        setTransactionId(params.get("vnp_TransactionNo"));
                        setPaymentCode(params.get("vnp_PaymentCode"));
                        setPopupVisible(true);
                    }
                } catch (err) {
                    console.error("Lỗi kiểm tra thanh toán:", err);
                }
            }
        };

        checkVnpayReturn();
    }, [location.search, authAPI, setPopupVisible]);

    useEffect(() => {
        if (popupVisible) {
            navigate(location.pathname, { replace: true });
        }
    }, [popupVisible, location.pathname, navigate]);

    if (!popupVisible) return <></>

    return (
        <div className={styles.overlay}>
            <div className={styles.popup}>
                <CheckCircle className={styles.icon} />
                <h2>Thanh toán thành công!</h2>
                {paymentCode && <p>Mã thanh toán: <strong>{paymentCode}</strong></p>}
                {transactionId && <p>Mã giao dịch: <strong>{transactionId}</strong></p>}
                <p>{paymentText}</p>
                <button onClick={() => setPopupVisible(false)}>OK</button>
            </div>
        </div>
    );
};

export default PaymentResultPopup;
