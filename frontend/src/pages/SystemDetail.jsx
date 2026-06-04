import { useEffect, useMemo, useState } from "react";
import { Link, useParams } from "react-router-dom";
import {
  CartesianGrid,
  Line,
  LineChart,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";
import Navbar from "../components/Navbar";
import { getSystemProduction } from "../api/axiosConfig"; // Import corrigé

const DEFAULT_START_DATE = "2024-06-01";
const DEFAULT_END_DATE = "2024-06-07";

function formatHour(value) {
  if (!value) return "";
  const date = new Date(value);
  return date.toLocaleString("fr-FR", {
    day: "2-digit",
    month: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
  });
}

function ChartCard({ title, unit, data, dataKey }) {
  return (
    <section className="chart-card">
      <div className="chart-header">
        <h2>{title}</h2>
        <span>{unit}</span>
      </div>

      <ResponsiveContainer width="100%" height={290}>
        <LineChart data={data}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis
            dataKey="timestamp"
            tickFormatter={formatHour}
            minTickGap={32}
          />
          <YAxis />
          <Tooltip
            labelFormatter={formatHour}
            formatter={(value) => [`${value} ${unit}`, title]}
          />
          <Line
            type="monotone"
            dataKey={dataKey}
            stroke="#137a4b"
            strokeWidth={2}
            dot={false}
            activeDot={{ r: 5 }}
          />
        </LineChart>
      </ResponsiveContainer>
    </section>
  );
}

function SystemDetail() {
  const { id } = useParams(); // Utilise id pour correspondre à ton App.jsx

  const [startDate, setStartDate] = useState(DEFAULT_START_DATE);
  const [endDate, setEndDate] = useState(DEFAULT_END_DATE);
  const [system, setSystem] = useState(null);
  const [production, setProduction] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const loadProduction = async () => {
    setLoading(true);
    setError("");

    try {
      const response = await getSystemProduction(id, startDate, endDate);
      // NOTE: Ajuste ces lignes si Spring Boot renvoie directement le tableau
      setSystem(response.data.system || { systemName: id, systemId: id });
      setProduction(response.data.data || response.data || []);
    } catch (err) {
      setError("Impossible de charger les données de production.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadProduction();
  }, [id, startDate, endDate]);

  // Passage en camelCase
  const summary = useMemo(() => {
    if (!production.length) {
      return { maxAc: 0, maxDc: 0, totalEnergy: 0, maxIrradiance: 0 };
    }

    return {
      maxAc: Math.max(...production.map((row) => row.acPowerKw || 0)),
      maxDc: Math.max(...production.map((row) => row.dcPowerKw || 0)),
      totalEnergy: production.reduce((sum, row) => sum + Number(row.acEnergyKwh || 0), 0),
      maxIrradiance: Math.max(...production.map((row) => row.irradianceWm2 || 0)),
    };
  }, [production]);

  return (
    <div className="page-shell">
      <Navbar />

      <main className="content">
        <div className="detail-top">
          <div>
            <Link to="/" className="back-link">
              ← Retour au dashboard
            </Link>
            <p>Analyse de production</p>
            <h1>{system ? system.systemName : id}</h1>
          </div>

          <div className="date-filter">
            <label>Début
              <input type="date" value={startDate} onChange={(e) => setStartDate(e.target.value)} />
            </label>
            <label>Fin
              <input type="date" value={endDate} onChange={(e) => setEndDate(e.target.value)} />
            </label>
          </div>
        </div>

        {system && system.totalCapacityKwc && (
          <section className="detail-summary">
            <div>
              <span>Système</span>
              <strong>{system.systemId}</strong>
            </div>
            <div>
              <span>Capacité installée</span>
              <strong>{system.totalCapacityKwc} kWc</strong>
            </div>
            <div>
              <span>Orientation</span>
              <strong>{system.orientation} / {system.tiltAngle}°</strong>
            </div>
            <div>
              <span>Points affichés</span>
              <strong>{production.length}</strong>
            </div>
          </section>
        )}

        <section className="kpi-grid">
          <div><span>Puissance AC max</span><strong>{summary.maxAc.toFixed(2)} kW</strong></div>
          <div><span>Puissance DC max</span><strong>{summary.maxDc.toFixed(2)} kW</strong></div>
          <div><span>Énergie AC cumulée</span><strong>{summary.totalEnergy.toFixed(2)} kWh</strong></div>
          <div><span>Irradiance max</span><strong>{summary.maxIrradiance.toFixed(0)} W/m²</strong></div>
        </section>

        {loading && <div className="state-box">Chargement des graphiques...</div>}
        {error && <div className="state-box error-box">{error}</div>}

        {!loading && !error && (
          <section className="charts-grid">
            {/* dataKey mis à jour en camelCase */}
            <ChartCard title="Puissance AC" unit="kW" data={production} dataKey="acPowerKw" />
            <ChartCard title="Énergie AC" unit="kWh" data={production} dataKey="acEnergyKwh" />
            <ChartCard title="Puissance DC" unit="kW" data={production} dataKey="dcPowerKw" />
            <ChartCard title="Tension DC" unit="V" data={production} dataKey="dcVoltageV" />
            <ChartCard title="Courant DC par string" unit="A" data={production} dataKey="dcCurrentPerStringA" />
            <ChartCard title="Irradiance solaire" unit="W/m²" data={production} dataKey="irradianceWm2" />
            <ChartCard title="Température ambiante" unit="°C" data={production} dataKey="ambientTemperatureC" />
          </section>
        )}
      </main>
    </div>
  );
}

export default SystemDetail;