import React from 'react';
import styles from '../../styles/PaymentResultPopup.module.css';
import { CheckCircle } from 'lucide-react';

const PaymentResultPopup = ({ transactionId, onClose }) => {
    return (
        <div className={styles.overlay}>
            <div className={styles.popup}>
                <CheckCircle className={styles.icon} />
                <h2>Thanh toán thành công!</h2>
                <p>Mã giao dịch: <strong>{transactionId}</strong></p>
                <p>Đơn hàng của bạn đã được đặt thành công!</p>
                <button onClick={onClose}>OK</button>
            </div>
        </div>
    );
};

export default PaymentResultPopup;
