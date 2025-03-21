import Header from "./Header";
import Footer from "./Footer";
import styles from "../../styles/Layout.module.css";
import { Outlet } from "react-router-dom";

const Layout = () => {
    return (
        <div className={styles.layout}>
            <Header />
            <main className={styles.mainContent}>
                <Outlet />
            </main>
            <Footer />
        </div>
    );
};

export default Layout;
