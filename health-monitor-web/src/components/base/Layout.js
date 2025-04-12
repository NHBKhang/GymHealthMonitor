import Header from "./Header";
import Footer from "./Footer";
import styles from "../../styles/components/Layout.module.css";
import { Outlet } from "react-router-dom";
import Calendar from "../boxes/Calendar";
import Notifications from "../boxes/Notifications";
import { useUserContext } from "../../configs/UserContext";
import PaymentResultPopup from "./PaymentResultPopup";

const Layout = () => {
    const { state } = useUserContext();

    return (
        <div className={styles.layout}>
            <Header />
            <main className={styles.mainContent}>
                <section className={styles.contentSection}>
                    <Outlet />
                </section>

                {state.currentUser &&
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
            <PaymentResultPopup />
        </div>
    );
};

export default Layout;
