import React, { useEffect, useRef, useState } from 'react';
import MessageBubble from './MessageBubble';
import styles from '../../styles/components/message/Window.module.css';
import { endpoints, useAuthAPI } from '../../configs/API';
import { useNotification } from '../../utils/toast';

const Window = ({ chat }) => {
    const [messages, setMessages] = useState([]);
    const [newMessage, setNewMessage] = useState('');
    const chatRef = useRef(null);
    const authAPI = useAuthAPI();
    const sendNotification = useNotification();

    useEffect(() => {
        if (chat) {
            authAPI().get(endpoints.messages).then((res) => {
                setMessages(res.data);
            }).catch((err) => {
                console.error(err);
                sendNotification({message: "Lỗi khi tải tin nhắn"}, 'error')
            });
        }
    }, [chat, authAPI, sendNotification]);

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
        const res = await authAPI().post(endpoints.messages, message);
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
