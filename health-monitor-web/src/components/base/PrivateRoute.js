import { useCookies } from "react-cookie";
import { Navigate } from "react-router-dom";

const PrivateRoute = ({ children }) => {
    const [cookies] = useCookies(['access-token']);
    const isAuthenticated = !!cookies['access-token']; 

    return isAuthenticated ? children : <Navigate to="/login" />;
};

export default PrivateRoute;
