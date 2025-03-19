import styles from "../../styles/Header.module.css";
import { useState } from "react";
import { FaBars, FaTimes } from "react-icons/fa";

const Header = () => {
    const [isDrawerOpen, setIsDrawerOpen] = useState(false);

    return (
        <>
            <header className={styles.header}>
                <button className={styles.menuButton} onClick={() => setIsDrawerOpen(true)}>
                    <FaBars />
                </button>
                <div className={styles.logo}>
                    <img src="/logo.png" alt="Gym Health Monitor Logo" />
                    <h1>Gym Health Monitor</h1>
                </div>
                <nav className={styles.nav}>
                    <ul>
                        <li><a href="/">Home</a></li>
                        <li><a href="/">Workouts</a></li>
                        <li><a href="/">Nutrition</a></li>
                        <li><a href="/">Progress</a></li>
                        <li><a href="/">Contact</a></li>
                    </ul>
                </nav>
            </header>

            <div className={`${styles.drawer} ${isDrawerOpen ? styles.open : ""}`}>
                <button className={styles.closeButton} onClick={() => setIsDrawerOpen(false)}>
                    <FaTimes />
                </button>
                <ul>
                    <li><a href="/">Home</a></li>
                    <li><a href="/">Workouts</a></li>
                    <li><a href="/">Nutrition</a></li>
                    <li><a href="/">Progress</a></li>
                    <li><a href="/">Contact</a></li>
                </ul>
            </div>

            {isDrawerOpen && <div className={styles.overlay} onClick={() => setIsDrawerOpen(false)}></div>}
        </>
    );
};

export default Header;
