import React, { useEffect, useState } from 'react';
import styles from '../../styles/components/message/Sidebar.module.css';
import API from '../../configs/API';

const ChatSidebar = ({ onSelectChat }) => {
    const [conversations, setConversations] = useState([]);

    useEffect(() => {
        const fetchConversations = async () => {
            const res = await API.get('/chat/conversations');
            setConversations(res.data);
        };
        fetchConversations();
    }, []);

    return (
        <div className={styles.sidebar}>
            <h3>Tin nhắn</h3>
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
