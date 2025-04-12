import { createContext, useContext, useState } from "react";

const GlobalContext = createContext();

export const GlobalProvider = ({ children }) => {
    const [popupVisible, setPopupVisible] = useState(false);
    const [popupData, setPopupData] = useState({});

    const updatePopupData = (field, value) =>
        setPopupData(prev => ({ ...prev, [field]: value }))

    return (
        <GlobalContext.Provider value={{
            popupVisible, setPopupVisible,
            popupData, updatePopupData
        }}>
            {children}
        </GlobalContext.Provider>
    );
}

export const useGlobalContext = () => {
    return useContext(GlobalContext);
};