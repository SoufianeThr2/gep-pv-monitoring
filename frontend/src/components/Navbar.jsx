import { Link, useNavigate } from "react-router-dom";

function Navbar() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token");
    navigate("/login");
  };

  return (
    <header className="navbar">
      <div className="navbar-brand">
        <Link to="/">GEP Monitoring</Link>
        <span>PV Plant Dashboard</span>
      </div>

      <nav className="navbar-links">
        <Link to="/">Dashboard</Link>
        <Link to="/map">Carte</Link>
        <button onClick={handleLogout}>Déconnexion</button>
      </nav>
    </header>
  );
}

export default Navbar;