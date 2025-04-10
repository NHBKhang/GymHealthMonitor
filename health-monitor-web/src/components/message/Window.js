import React, { useEffect, useRef, useState } from 'react';
import MessageBubble from './MessageBubble';
import styles from '../../styles/components/message/Window.module.css';
import API from '../../configs/API';

const Window = ({ chat }) => {
    const [messages, setMessages] = useState([]);
    const [newMessage, setNewMessage] = useState('');
    const chatRef = useRef(null);

    useEffect(() => {
        if (chat) {
            API.get(`/chat/messages/${chat.id}`).then((res) => {
                setMessages(res.data);
            });
        }
    }, [chat]);

    useEffect(() => {
        if (chatRef.current) {
            chatRef.current.scrollTop = chatRef.current.scrollHeight;
        }
    }, [messages]);

    const sendMessage = async () => {
        if (newMessage.trim() === '') return;
        const message = {
            chatId: chat.id,
            content: newMessage,
        };
        const res = await API.post('/chat/send', message);
        setMessages([...messages, res.data]);
        setNewMessage('');
    };

    if (!chat) return <div className={styles.empty}>Chọn cuộc trò chuyện</div>;

    return (
        <div className={styles.chatWindow}>
            <div className={styles.header}>{chat.name}</div>
            <div className={styles.messages} ref={chatRef}>
                {messages.map((msg, index) => (
                    <MessageBubble key={index} message={msg} />
                ))}
            </div>
            <div className={styles.inputArea}>
                <input
                    type="text"
                    placeholder="Nhập tin nhắn..."
                    value={newMessage}
                    onChange={(e) => setNewMessage(e.target.value)}
                    onKeyDown={(e) => e.key === 'Enter' && sendMessage()}
                />
                <button onClick={sendMessage}>Gửi</button>
            </div>
        </div>
    );
};

export default Window;
