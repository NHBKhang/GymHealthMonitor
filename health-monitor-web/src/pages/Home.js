import { Link } from "react-router-dom";

const Home = () => {
    return (
        <div>
            <h1>Home Page</h1>
            <p>Chào mừng đến với trang chủ!</p>
            <Link to="/about">Go to About</Link>
        </div>
    );
}

export default Home;
