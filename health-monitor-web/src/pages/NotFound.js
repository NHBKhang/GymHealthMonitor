import { Link } from "react-router-dom";
import { PageHead } from "../components";

const NotFound = () => {
    return (
        <>
            <PageHead title="404 - Không tìm thấy" />
            <div style={{
                width: '100vw',
                height: '100vh',
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                flexDirection: 'column'
            }}>
                <h1>404 - Page Not Found</h1>
                <p>Trang bạn tìm kiếm không tồn tại.</p>
                <Link to="/">Quay trở về trang chủ</Link>
            </div>
        </>
    );
}

export default NotFound;
