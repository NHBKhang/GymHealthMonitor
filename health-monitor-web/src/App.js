import './App.css';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { UserProvider } from './configs/UserContext';
import { CookiesProvider } from 'react-cookie';
import Layout from './components/base/Layout';
import Home from "./pages/Home";
import About from "./pages/About";
import Contact from "./pages/Contact";
import NotFound from "./pages/NotFound";
import Login from "./pages/Login";
import Signup from './pages/Signup';
import Profile from './pages/Profile';
import Packages from './pages/Packages';

function App() {
  return (
    <CookiesProvider defaultSetOptions={{ path: '/' }}>
      <UserProvider>
        <Router>
          <Routes>
            <Route path="/" element={<Layout />}>
              <Route index element={<Home />} />
              <Route path="about" element={<About />} />
              <Route path="contact" element={<Contact />} />
              <Route path="profile" element={<Profile />} />
              <Route path="packages" element={<Packages />} />
            </Route>
            <Route path="*" element={<NotFound />} />
            <Route path='login' element={<Login />} />
            <Route path='signup' element={<Signup />} />
          </Routes>
        </Router>
      </UserProvider>
    </CookiesProvider>
  );
}

export default App;
