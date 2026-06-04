import { Routes, Route } from 'react-router-dom';
import Dashboard from './pages/Dashboard';
import SystemDetail from './pages/SystemDetail';
import Login from './pages/Login';
import MapPage from './pages/MapPage'; // <-- CORRECTION ICI : ./pages au lieu de ./components
import './index.css';

function App() {
  return (
    <Routes>
      <Route path="/" element={<Dashboard />} />
      <Route path="/system/:id" element={<SystemDetail />} />
      <Route path="/map" element={<MapPage />} />
      <Route path="/login" element={<Login />} />
    </Routes>
  );
}

export default App;