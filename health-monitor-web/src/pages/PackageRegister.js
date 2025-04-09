import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import styles from '../styles/PackageRegisterPage.module.css';
import { useNotification } from '../utils/toast';
import API, { endpoints, useAuthAPI } from '../configs/API';

const VNPayBody = ({ method, body, updateBody }) => {
    const banks = [
        { code: "", name: "-- Chọn ngân hàng --" },
        { code: "NCB", name: "Ngân hàng Quốc Dân (NCB)" },
        { code: "VCB", name: "Vietcombank" },
        { code: "BIDV", name: "BIDV" },
        { code: "VIB", name: "VIB" },
        { code: "TCB", name: "Techcombank" },
        { code: "VNPAY-QR", name: "VNPAY QR" },
    ];

    const locales = [
        { code: "vn", name: "Tiếng Việt" },
        { code: "en", name: "English" }
    ];

    return (
        <div className={`${styles.vnpayBodyWrapper} ${method === "vnpay" ? styles.show : ""}`}>
            <div className={styles.vnpayOptions}>
                <div className={styles.selectGroup}>
                    <label>Ngân hàng:</label>
                    <select value={body.bankCode} onChange={(e) => updateBody('bankCode', e.target.value)}>
                        {banks.map(bank => (
                            <option key={bank.code} value={bank.code}>{bank.name}</option>
                        ))}
                    </select>
                </div>

                <div className={styles.selectGroup}>
                    <label>Ngôn ngữ:</label>
                    <select value={body.locale} onChange={(e) => updateBody('locale', e.target.value)}>
                        {locales.map(l => (
                            <option key={l.code} value={l.code}>{l.name}</option>
                        ))}
                    </select>
                </div>
            </div>

            <div className={styles.selectGroup}>
                <label>Ghi chú:</label>
                <textarea value={body.notes} onChange={(e) => updateBody('notes', e.target.value)}>
                </textarea>
            </div>
        </div>
    );
};

const TransferBody = ({ method, body, updateBody }) => {
    return (
        <div className={`${styles.transferOptions} ${method === "transfer" ? styles.show : ""}`}>
            <div className={styles.transferDetails}>
                <p><strong>Ngân hàng thụ hưởng:</strong> BIDV</p>
                <p><strong>Số tài khoản:</strong> 123456789</p>
                <p><strong>Chủ tài khoản:</strong> Công ty ABC</p>
                <p><strong>Nội dung chuyển khoản:</strong> Thanh toan goi tap - [Mã gói] - [Họ tên]</p>
            </div>

            <div className={styles.uploadGroup}>
                <label>Upload biên lai thanh toán:</label>
                <input
                    type="file"
                    accept="image/*,application/pdf"
                    onChange={(e) => updateBody("receipt", e.target.files[0])}
                />
            </div>
        </div>
    );
};

const paymentOptions = [
    {
        value: "vnpay",
        name: "Thanh toán qua VNPAY",
        logo: "https://vinadesign.vn/uploads/images/2023/05/vnpay-logo-vinadesign-25-12-57-55.jpg",
        disabled: false,
        body: (paymentMethod, vnpayBody, updateVnpayBody) =>
            <VNPayBody method={paymentMethod} body={vnpayBody} updateBody={updateVnpayBody} />
    },
    {
        value: "momo",
        name: "Thanh toán qua MoMo",
        logo: "https://cdn.haitrieu.com/wp-content/uploads/2022/10/Logo-MoMo-Square.png",
        disabled: true,
    },
    {
        value: "paypal",
        name: "Thanh toán qua Paypal",
        logo: "https://cdn.pixabay.com/photo/2018/05/08/21/29/paypal-3384015_1280.png",
        disabled: true,
    },
    {
        value: "transfer",
        name: "Chuyển khoản & Upload biên lai",
        logo: "https://png.pngtree.com/png-vector/20221022/ourmid/pngtree-mobile-bank-users-transferring-money-png-image_6339062.png",
        disabled: false,
        body: (paymentMethod, transferInfo, updateTransferInfo) =>
            <TransferBody method={paymentMethod} transferInfo={transferInfo} updateBody={updateTransferInfo} />
    }
];

const PackageRegister = () => {
    const { id } = useParams();
    const [pkg, setPkg] = useState(null);
    const [paymentMethod, setPaymentMethod] = useState('');
    const [body, setBody] = useState({
        vnpay: {
            bankCode: "",
            locale: "vn",
            notes: ""
        },
        transfer: {
            receipt: null
        }
    });
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();
    const sendNotification = useNotification();
    const authAPI = useAuthAPI();

    const updateBody = (method, field, value) => {
        setBody(prev => ({
            ...prev,
            [method]: { ...prev[method], [field]: value }
        }));
    };

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

    const handleSubmit = async () => {
        if (!paymentMethod) {
            sendNotification({ message: "Vui lòng chọn phương thức thanh toán" }, 'info');
            return;
        }

        try {
            let res = null;

            if (paymentMethod === 'vnpay') {
                res = await authAPI().post(endpoints['vnpay-payment'], {
                    package: pkg.id,
                    returnUrl: `${window.location.origin}/${endpoints.package(pkg.id)}`,
                    amount: pkg.price,
                    orderType: "other",
                    bankCode: body.vnpay.bankCode,
                    locale: body.vnpay.locale,
                    notes: body.vnpay.notes
                });
            } else if (paymentMethod === 'momo') {
                return;
                // res = await authAPI().get(`/payments/momo?packageId=${pkg.id}`);
            } else if (paymentMethod === 'paypal') {
                return;
                // res = await authAPI().get(`/payments/paypal?packageId=${pkg.id}`);
            } else if (paymentMethod === 'transfer') {
                res = await authAPI().get(`/payments/paypal?packageId=${pkg.id}`);
            }

            if (res.data && res.data.payUrl) {
                window.location.href = res.data.payUrl;
            } else {
                sendNotification({ message: "Không thể lấy được link thanh toán" }, 'error');
            }
        } catch (err) {
            console.error("Lỗi khi gọi API thanh toán:", err);
            sendNotification({ message: "Có lỗi xảy ra khi xử lý thanh toán" }, 'error');
        }
    };

    if (loading) return <div className={styles.loading}>Đang tải...</div>;
    if (!pkg) return <div className={styles.error}>Không tìm thấy gói tập</div>;

    return (
        <div className={styles.container}>
            <h2>Thanh toán gói tập</h2>

            <div className={styles.packageInfo}>
                <p><strong>Mã gói tập: </strong><span>{pkg.code}</span></p>
                <p><strong>Tên gói tập: </strong><span>{pkg.name}</span></p>
                <p><strong>Thời hạn: </strong><span>{pkg.duration} tháng</span></p>
                <p><strong>Giá: </strong><span>{pkg.price.toLocaleString()} VNĐ</span></p>
            </div>

            <h3>Chọn phương thức thanh toán</h3>
            <div className={styles.paymentMethods}>
                {paymentOptions.map((method) => (<>
                    <label
                        key={method.value}
                        className={`${styles.paymentOption} ${paymentMethod === method.value ? styles.selected : ""}`}
                        onClick={() => setPaymentMethod(method.value)}
                    >
                        <input
                            type="radio"
                            name="payment"
                            value={method.value}
                            checked={paymentMethod === method.value}
                            onChange={() => setPaymentMethod(method.value)}
                            disabled={method.disabled}
                        />
                        <img src={method.logo} alt={method.name} />
                        <span>{method.name}</span>
                    </label>
                    <div className={styles.paymentBody}>{method.body && method.body(paymentMethod, body, updateBody)}</div>
                </>))}
            </div>

            <div className={styles.buttonGroup}>
                <button onClick={() => navigate(`/packages/${pkg.id}`)} className={styles.returnBtn}>
                    Quay lại
                </button>
                <button onClick={handleSubmit} className={styles.confirmBtn}>
                    Đăng ký
                </button>
            </div>
        </div>
    );
};

export default PackageRegister;
