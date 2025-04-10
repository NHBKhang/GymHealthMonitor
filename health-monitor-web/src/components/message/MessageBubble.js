import React from 'react';
import styles from '../../styles/components/message/MessageBubble.module.css';

const MessageBubble = ({ message }) => {
    const isMe = message.sender === 'me';

    return (
        <div className={`${styles.bubble} ${isMe ? styles.me : styles.them}`}>
            <div className={styles.content}>{message.content}</div>
        </div>
    );
};

export default MessageBubble;
