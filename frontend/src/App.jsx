import { Routes, Route } from 'react-router-dom';
import Dashboard from './pages/Dashboard';
import SystemDetail from './pages/SystemDetail';
import Login from './pages/Login';
import MapPage from './pages/MapPage';
import PrivateRoute from './components/PrivateRoute';
import './index.css';

function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
      <Route path="/system/:id" element={<PrivateRoute><SystemDetail /></PrivateRoute>} />
      <Route path="/map" element={<PrivateRoute><MapPage /></PrivateRoute>} />
    </Routes>
  );
}

export default App;