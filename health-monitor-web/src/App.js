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
import PrivateRoute from './components/base/PrivateRoute';
import { GlobalProvider } from './configs/GlobalContext';

function App() {
  return (
    <CookiesProvider defaultSetOptions={{ path: '/' }}>
      <GlobalProvider>
        <UserProvider>
          <Router>
            <Routes>
              <Route path="/" element={<Layout />}>
                <Route index element={<Home />} />
                <Route path="about" element={<About />} />
                <Route path="contact" element={<Contact />} />
                <Route path="profile" element={
                  <PrivateRoute>
                    <Profile />
                  </PrivateRoute>
                } />
                <Route path="packages" element={<Packages />} />
                <Route path="packages/:id" element={<Package />} />
                <Route path="packages/:id/register" element={
                  <PrivateRoute>
                    <PackageRegister />
                  </PrivateRoute>
                } />
                <Route path="my-schedule" element={
                  <PrivateRoute>
                    <MySchedule />
                  </PrivateRoute>
                } />
                <Route path="booking-schedule" element={
                  <PrivateRoute>
                    <Booking />
                  </PrivateRoute>
                } />
                <Route path="my-subscription" element={
                  <PrivateRoute>
                    <MySubscription />
                  </PrivateRoute>
                } />
                <Route path="settings" element={
                  <PrivateRoute>
                    <Settings />
                  </PrivateRoute>
                } />
                <Route path="message" element={
                  <PrivateRoute>
                    <Message />
                  </PrivateRoute>
                } />
              </Route>
              <Route path="*" element={<NotFound />} />
              <Route path='login' element={<Login />} />
              <Route path='signup' element={<Signup />} />
            </Routes>
          </Router>
          <ToastContainer />
        </UserProvider>
      </GlobalProvider>
    </CookiesProvider>
  );
}

export default App;
