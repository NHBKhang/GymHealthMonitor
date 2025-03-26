import { createContext, useContext, useState, useEffect } from "react";

const UserContext = createContext();

export const UserProvider = ({ children }) => {
    const [currentUser, setCurrentUser] = useState(() => {
        const storedUser = localStorage.getItem("currentUser");
        return storedUser ? JSON.parse(storedUser) : null;
    });

    useEffect(() => {
        if (currentUser) {
            localStorage.setItem("currentUser", JSON.stringify(currentUser));
        } else {
            localStorage.removeItem("currentUser");
        }
    }, [currentUser]);

    return (
        <UserContext.Provider value={{ currentUser, setCurrentUser }}>
            {children}
        </UserContext.Provider>
    );
};

export const useUserContext = () => useContext(UserContext);
