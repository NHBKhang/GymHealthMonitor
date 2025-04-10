import React, { useState } from 'react';
import ChatSidebar from '../components/message/Sidebar';
import ChatWindow from '../components/message/Window';
import styles from '../styles/pages/Message.module.css';

const Message = () => {
    const [selectedChat, setSelectedChat] = useState(null);

    return (
        <div className={styles.chatContainer}>
            <ChatSidebar onSelectChat={setSelectedChat} />
            <ChatWindow chat={selectedChat} />
        </div>
    );
};

export default Message;