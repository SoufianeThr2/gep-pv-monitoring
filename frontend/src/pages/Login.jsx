import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../api/axiosConfig";

function Login() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError("");
    setLoading(true);

    try {
      // Appel API d'authentification
      const response = await login(email, password);
      
      // Stockage du token
      localStorage.setItem("access_token", response.data.access_token);
      navigate("/");
      
    } catch (err) {
      if (err.response && err.response.status === 401) {
        setError("Email ou mot de passe incorrect.");
      } else {
        setError("Une erreur est survenue lors de la connexion.");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="login-page">
      <section className="login-card">
        <div className="login-header">
          <span className="logo-mark">GEP</span>
          <h1>PV Monitoring Platform</h1>
          <p>Green Energy Park — Benguerir</p>
        </div>

        <form onSubmit={handleSubmit} className="login-form">
          {error && <div className="form-error">{error}</div>}

          <label htmlFor="email">Email</label>
          <input
            id="email"
            type="email"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
            required
          />

          <label htmlFor="password">Mot de passe</label>
          <input
            id="password"
            type="password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
            required
          />

          <button type="submit" disabled={loading}>
            {loading ? "Connexion..." : "Se connecter"}
          </button>
        </form>
      </section>
    </main>
  );
}

export default Login;