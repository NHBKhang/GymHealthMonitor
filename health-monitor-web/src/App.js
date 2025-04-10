import './App.css';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { UserProvider } from './configs/UserContext';
import { CookiesProvider } from 'react-cookie';
import { ToastContainer } from 'react-toastify';
import Layout from './components/base/Layout';
import Home from "./pages/Home";
import About from "./pages/About";
import Contact from "./pages/Contact";
import NotFound from "./pages/NotFound";
import Login from "./pages/Login";
import Signup from './pages/Signup';
import Profile from './pages/Profile';
import Packages from './pages/Packages';
import Package from './pages/Package';
import PackageRegister from './pages/PackageRegister';
import MySchedule from './pages/MySchedule';
import Booking from './pages/Booking';
import MySubscription from './pages/MySubscription';
import Settings from './pages/Settings';
import Message from './pages/Message';

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
              <Route path="packages/:id" element={<Package />} />
              <Route path="packages/:id/register" element={<PackageRegister />} />
              <Route path="my-schedule" element={<MySchedule />} />
              <Route path="booking-schedule" element={<Booking />} />
              <Route path="my-subscription" element={<MySubscription />} />
              <Route path="settings" element={<Settings />} />
              <Route path="message" element={<Message />} />
            </Route>
            <Route path="*" element={<NotFound />} />
            <Route path='login' element={<Login />} />
            <Route path='signup' element={<Signup />} />
          </Routes>
        </Router>
        <ToastContainer />
      </UserProvider>
    </CookiesProvider>
  );
}

export default App;
