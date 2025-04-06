import Header from "./Header";
import Footer from "./Footer";
import styles from "../../styles/Layout.module.css";
import { Outlet } from "react-router-dom";
import Calendar from "../boxes/Calendar";
import Notifications from "../boxes/Notifications";
import { useUserContext } from "../../configs/UserContext";

const Layout = () => {
    const { currentUser } = useUserContext();

    return (
        <div className={styles.layout}>
            <Header />
            <main className={styles.mainContent}>
                <section className={styles.contentSection}>
                    <Outlet />
                </section>

                {currentUser &&
                    <aside className={styles.sidebar}>
                        <section>
                            <Notifications />
                        </section>
                        <section>
                            <Calendar />
                        </section>
                    </aside>
                }
            </main>
            <Footer />
        </div>
    );
};

export default Layout;
