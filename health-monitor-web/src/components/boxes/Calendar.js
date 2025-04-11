import React, { useState } from "react";
import { Calendar as ReactCalendar } from "react-calendar";
import "react-calendar/dist/Calendar.css";
import styles from "../../styles/components/Calendar.module.css";

const Calendar = () => {
    const [date, setDate] = useState(new Date());

    return (
        <div className={styles.calendarBox}>
            <div className={styles.header}>
                <h4>Lịch</h4>
            </div>
            <ReactCalendar onChange={setDate} value={date} />
        </div>
    );
}

export default Calendar;
