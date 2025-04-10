import styles from "../../styles/components/Header.module.css";
import { useState } from "react";
import { FaBars, FaTimes, FaChevronDown } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import { useUserContext } from "../../configs/UserContext";

const Header = () => {
    const [isDrawerOpen, setIsDrawerOpen] = useState(false);
    const [dropdown, setDropdown] = useState(null);
    const { state, logout } = useUserContext();
    const navigate = useNavigate();

    const toggleDropdown = (menu) => {
        setDropdown(dropdown === menu ? null : menu);
    };

    return (
        <>
            <header className={styles.header}>
                {/* Menu Button for Drawer */}
                <button className={styles.menuButton} onClick={() => setIsDrawerOpen(true)}>
                    <FaBars />
                </button>

                {/* Logo */}
                <div className={styles.logo} onClick={() => navigate('/')}>
                    <img src="/logo.png" alt="Gym Health Monitor Logo" />
                    <h1>Health Monitor</h1>
                </div>

                {/* Navigation */}
                <nav className={styles.nav}>
                    <ul>
                        <li><a href="/">Trang chủ</a></li>

                        {/* Dropdown - Workouts */}
                        <li className={styles.dropdown}>
                            <a href="/" onClick={(e) => { e.preventDefault(); toggleDropdown("packages"); }}>
                                Gói tập <FaChevronDown />
                            </a>
                            {dropdown === "packages" && (
                                <ul className={styles.dropdownMenu}>
                                    <li><a href="/packages">Tất cả gói tập</a></li>
                                    {state.currentUser &&
                                        <li><a href="/my-subscription">Gói tập đã đăng ký</a></li>
                                    }
                                </ul>
                            )}
                        </li>

                        {/* Dropdown - Schedule */}
                        {state.currentUser && <li className={styles.dropdown}>
                            <a href="/" onClick={(e) => { e.preventDefault(); toggleDropdown("schedule"); }}>
                                Lịch tập <FaChevronDown />
                            </a>
                            {dropdown === "schedule" && (
                                <ul className={styles.dropdownMenu}>
                                    <li><a href="/my-schedule">Lịch tập của bạn</a></li>
                                    <li><a href="/booking-schedule">Đặt lịch</a></li>
                                </ul>
                            )}
                        </li>}

                        {/* Dropdown - Nutrition */}
                        <li className={styles.dropdown}>
                            <a href="/" onClick={(e) => { e.preventDefault(); toggleDropdown("support"); }}>
                                Hỗ trợ <FaChevronDown />
                            </a>
                            {dropdown === "support" && (
                                <ul className={styles.dropdownMenu}>
                                    <li><a href="/about">Về chúng tôi</a></li>
                                    <li><a href="/contact">Liên hệ</a></li>
                                    <li><a href="/support">Hỗ trợ</a></li>
                                </ul>
                            )}
                        </li>

                        {/* Dropdown - Nutrition */}
                        <li className={styles.dropdown}>
                            {state.currentUser ? (
                                <a href="/" onClick={(e) => { e.preventDefault(); toggleDropdown("account"); }}>
                                    <img
                                        src={state.currentUser.avatar || "/default-avatar.png"}
                                        alt="User Avatar"
                                        className={styles.avatar}
                                    />
                                </a>
                            ) : (
                                <a href="/" onClick={(e) => { e.preventDefault(); toggleDropdown("account"); }}>
                                    Tài khoản <FaChevronDown />
                                </a>
                            )}
                            {dropdown === "account" && (
                                <ul className={`${styles.dropdownMenu} ${styles.profile}`}>
                                    {state.currentUser ? (
                                        <>
                                            <li><a href="/profile">Trang cá nhân</a></li>
                                            <li><a href="/message">Nhắn tin</a></li>
                                            <li><a href="/settings">Cài đặt</a></li>
                                            <li onClick={(e) => {
                                                e.preventDefault();
                                                logout();
                                            }}><a href="/">Đăng xuất</a></li>
                                        </>
                                    ) : (
                                        <>
                                            <li><a href="/login">Đăng nhập</a></li>
                                            <li><a href="/signup">Đăng ký</a></li>
                                        </>
                                    )}
                                </ul>
                            )}
                        </li>
                    </ul>
                </nav>
            </header>

            {/* Drawer Navigation */}
            <div className={`${styles.drawer} ${isDrawerOpen ? styles.open : ""}`}>
                <button className={styles.closeButton} onClick={() => setIsDrawerOpen(false)}>
                    <FaTimes />
                </button>
                <ul>
                    <li><a href="/">Trang chủ</a></li>

                    {/* Group Menu - Packages */}
                    <li>
                        <strong>Gói tập</strong>
                        <ul className={styles.groupMenu}>
                            <li><a href="/packages">Tất cả gói tập</a></li>
                            {state.currentUser &&
                                <li><a href="/my-subscription">Gói tập đã đăng ký</a></li>
                            }
                        </ul>
                    </li>

                    {/* Group Menu - Schedule */}
                    {state.currentUser && <li>
                        <strong>Lịch tập</strong>
                        <ul className={styles.groupMenu}>
                            <li><a href="/my-schedule">Lịch tập của bạn</a></li>
                            <li><a href="/book-schedule">Đặt lịch</a></li>
                        </ul>
                    </li>}

                    {/* Group Menu - Support */}
                    <li>
                        <strong>Hỗ trợ</strong>
                        <ul className={styles.groupMenu}>
                            <li><a href="/about">Về chúng tôi</a></li>
                            <li><a href="/contact">Liên hệ</a></li>
                            <li><a href="/support">Hỗ trợ</a></li>
                        </ul>
                    </li>

                    {/* Group Menu - Support */}
                    <li>
                        <strong>Tài khoản</strong>
                        <ul className={styles.groupMenu}>
                            {state.currentUser ? (
                                <>
                                    <li><a href="/profile">Trang cá nhân</a></li>
                                    <li><a href="/message">Nhắn tin</a></li>
                                    <li><a href="/settings">Cài đặt</a></li>
                                    <li><a href="/logout">Đăng xuất</a></li>
                                </>
                            ) : (
                                <>
                                    <li><a href="/login">Đăng nhập</a></li>
                                    <li><a href="/signup">Đăng ký</a></li>
                                </>
                            )}
                        </ul>
                    </li>
                </ul>
            </div>

            {/* Overlay khi mở Drawer */}
            {isDrawerOpen && <div className={styles.overlay} onClick={() => setIsDrawerOpen(false)}></div>}
        </>
    );
};

export default Header;
