import styles from "../../styles/components/HealthCard.module.css";
import { ProgressBar } from "react-bootstrap";
import { motion } from "framer-motion";

const HealthCard = ({ title, value, icon, progress }) => {
    return (
        <div className={`card ${styles.healthCard} shadow-sm p-3`}>
            <div className="card-body text-center">
                <motion.div
                    className="display-4 mb-2"
                    initial={{ scale: 0.8 }}
                    animate={{ scale: 1 }}
                >
                    {icon}
                </motion.div>
                <h5 className="card-title">{title}</h5>
                <p className="card-text fs-4 fw-bold text-muted">{value}</p>
                <ProgressBar now={progress} className="w-100 mt-2" />
            </div>
        </div>
    );
}

export default HealthCard;