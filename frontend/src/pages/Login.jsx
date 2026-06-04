import { useState } from "react";
import { useNavigate } from "react-router-dom";
// import { login } from "../api/axiosConfig"; // Désactivé tant que le backend n'a pas AuthController

function Login() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("admin@gep.ma");
  const [password, setPassword] = useState("admin123");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError("");
    setLoading(true);

    try {
      // Simulation d'un temps de réponse serveur (0.8 seconde)
      await new Promise(resolve => setTimeout(resolve, 800));

      // --- VÉRIFICATION STRICTE DE L'ADMIN ---
      if (email === "admin@gep.ma" && password === "admin123") {
        // L'accès est accordé uniquement si la condition exacte est remplie
        localStorage.setItem("access_token", "gep-admin-token-valide");
        navigate("/");
      } else {
        // On déclenche manuellement une erreur pour bloquer l'accès
        throw new Error("Accès refusé");
      }

    } catch (err) {
      setError("Email ou mot de passe incorrect.");
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