import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import SystemCard from "../components/SystemCard";
import { getSystems } from "../api/axiosConfig"; // Import corrigé

function Dashboard() {
  const [systems, setSystems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadSystems = async () => {
      try {
        const response = await getSystems();
        setSystems(response.data);
      } catch (err) {
        setError("Impossible de charger les systèmes PV.");
      } finally {
        setLoading(false);
      }
    };

    loadSystems();
  }, []);

  return (
    <div className="page-shell">
      <Navbar />

      <main className="content">
        <div className="page-title">
          <div>
            <p>Green Energy Park — Benguerir</p>
            <h1>Dashboard des systèmes photovoltaïques</h1>
          </div>
          <span>{systems.length} systèmes</span>
        </div>

        {loading && <div className="state-box">Chargement des données...</div>}

        {error && <div className="state-box error-box">{error}</div>}

        {!loading && !error && (
          <section className="systems-grid">
            {systems.map((system) => (
              // Correction : systemId au lieu de system_id
              <SystemCard key={system.systemId} system={system} />
            ))}
          </section>
        )}
      </main>
    </div>
  );
}

export default Dashboard;