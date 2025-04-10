import styles from "../styles/pages/Home.module.css";
import { HeartPulse, Flame, Footprints } from "lucide-react";
import { motion } from "framer-motion";
import { HealthCard, PageHead } from "../components";

const Home = () => {
    return (
        <>
            <PageHead title="Trang chủ"/>
            <div className={`container ${styles.pageContainer}`}>
                <motion.h1
                    className="text-center mb-4"
                    initial={{ opacity: 0, y: -20 }}
                    animate={{ opacity: 1, y: 0 }}
                >
                    Theo Dõi Sức Khỏe
                </motion.h1>
                <div className="row">
                    <div className="col-md-4">
                        <HealthCard
                            title="Nhịp tim"
                            value="75 bpm"
                            icon={<HeartPulse className={styles.heartIcon} />}
                            progress={75}
                        />
                    </div>
                    <div className="col-md-4">
                        <HealthCard
                            title="Calo tiêu thụ"
                            value="1200 kcal"
                            icon={<Flame className={styles.flameIcon} />}
                            progress={60}
                        />
                    </div>
                    <div className="col-md-4">
                        <HealthCard
                            title="Bước đi"
                            value="8500"
                            icon={<Footprints className={styles.footprintsIcon} />}
                            progress={85}
                        />
                    </div>
                </div>
            </div>
        </>
    );
}

export default Home;
