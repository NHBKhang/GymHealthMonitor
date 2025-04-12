import React, { useEffect, useState } from 'react';
import styles from '../../styles/components/message/Sidebar.module.css';
import { endpoints, useAuthAPI } from '../../configs/API';

const ChatSidebar = ({ onSelectChat }) => {
    const [conversations, setConversations] = useState([]);
    const [error, setError] = useState("");
    const authAPI = useAuthAPI();

    useEffect(() => {
        const fetchConversations = async () => {
            try {
                let res = await authAPI().get(endpoints.messages);
                setConversations(res.data);
            } catch(err) {
                setError(err.response?.detail ?? err.message);
            }
        };
        fetchConversations();
    }, [authAPI]);

    return (
        <div className={styles.sidebar}>
            <h3>Tin nhắn</h3>
            {error && <p>{error}</p>}
            {conversations.map((conv) => (
                <div
                    key={conv.id}
                    className={styles.conversation}
                    onClick={() => onSelectChat(conv)}
                >
                    <img src={conv.avatar} alt="avatar" />
                    <div>
                        <p className={styles.name}>{conv.name}</p>
                        <p className={styles.lastMessage}>{conv.lastMessage}</p>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default ChatSidebar;
