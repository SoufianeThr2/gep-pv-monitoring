import { useCallback, useEffect, useMemo, useState } from "react";
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

import { getSystem, getSystemProduction } from "../api/axiosConfig";
import Navbar from "../components/Navbar";

const DEFAULT_START_DATE = "2024-06-01";
const DEFAULT_END_DATE = "2024-06-07";

function formatHour(value) {
  if (!value) return "";
  return new Date(value).toLocaleString("fr-FR", {
    day: "2-digit",
    month: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
  });
}

function CustomTooltip({ active, payload, label, unit, title }) {
  if (!active || !payload || !payload.length) return null;
  const value = payload[0]?.value;
  return (
    <div style={{
      background: "white",
      border: "1px solid #e4eaf2",
      borderRadius: 10,
      padding: "10px 14px",
      boxShadow: "0 4px 12px rgba(16,35,66,0.10)",
      fontSize: 13,
    }}>
      <p style={{ margin: "0 0 4px", color: "#6b7280", fontWeight: 600 }}>
        {formatHour(label)}
      </p>
      <p style={{ margin: 0, color: "#137a4b", fontWeight: 700 }}>
        {title} : {value !== undefined ? `${value} ${unit}` : "—"}
      </p>
    </div>
  );
}

function ChartCard({ title, unit, data, dataKey }) {
  return (
    <div className="chart-card" style={{ width: "100%", height: 320 }}>
      <div className="chart-header">
        <h2>{title}</h2>
        <span>{unit}</span>
      </div>
      <ResponsiveContainer width="100%" height="85%">
        <LineChart data={data}>
          <CartesianGrid strokeDasharray="3 3" stroke="#e4eaf2" />
          <XAxis
            dataKey="timestamp"
            tickFormatter={formatHour}
            minTickGap={30}
            tick={{ fontSize: 12, fill: "#6b7280" }}
            axisLine={false}
            tickLine={false}
          />
          <YAxis
            domain={[0, 'auto']}
            tick={{ fontSize: 12, fill: "#6b7280" }}
            axisLine={false}
            tickLine={false}
          />
          <Tooltip
            content={<CustomTooltip unit={unit} title={title} />}
            cursor={{ stroke: "#137a4b", strokeWidth: 1, strokeDasharray: "4 2" }}
          />
          <Line
            type="monotone"
            dataKey={dataKey}
            stroke="#137a4b"
            strokeWidth={2}
            dot={false}
            activeDot={{ r: 5, fill: "#137a4b", stroke: "white", strokeWidth: 2 }}
          />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
}

export default function SystemDetail() {
  const { id } = useParams();

  const [startDate, setStartDate] = useState(DEFAULT_START_DATE);
  const [endDate, setEndDate] = useState(DEFAULT_END_DATE);
  const [system, setSystem] = useState(null);
  const [production, setProduction] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const loadProduction = useCallback(async () => {
    setLoading(true);
    setError("");

    try {
      // Appels parallèles : données de production + infos du système
      const [prodResponse, sysResponse] = await Promise.all([
        getSystemProduction(id, startDate, endDate),
        getSystem(id),
      ]);

      // --- Normalisation des données de production ---
      const raw = prodResponse.data.data || prodResponse.data || [];
      const normalized = raw.map((r) => ({
        timestamp: r.timestamp,
        ac_power_kw: r.acPower,
        dc_power_kw: r.dcPower,
        ac_energy_kwh: r.acEnergy,
        dc_voltage_v: r.dcVoltage,
        dc_current_per_string_a: r.dcCurrent,
        irradiance_wm2: r.irradiance,
        ambient_temperature_c: r.ambientTemperature,
      }));

      // --- Normalisation de l'objet système (/api/systems/{id} retourne PvSystem) ---
      const s = sysResponse.data || {};
      setSystem({
        system_id:          s.systemId          || s.system_id          || id,
        system_name:        s.systemName        || s.system_name        || id,
        total_capacity_kwc: s.totalCapacityKwc  ?? s.total_capacity_kwc ?? null,
        orientation:        s.orientation       || null,
        tilt_angle:         s.tiltAngle         ?? s.tilt_angle         ?? null,
      });

      setProduction(normalized);
    } catch (err) {
      console.error(err);
      setError("Impossible de charger les données.");
    } finally {
      setLoading(false);
    }
  }, [id, startDate, endDate]);

  useEffect(() => {
    loadProduction();
  }, [loadProduction]);

  const summary = useMemo(() => {
    if (!production.length) {
      return {
        maxAc: 0,
        maxDc: 0,
        totalEnergy: 0,
        maxIrradiance: 0,
      };
    }

    return {
      maxAc: Math.max(...production.map((r) => r.ac_power_kw || 0)),
      maxDc: Math.max(...production.map((r) => r.dc_power_kw || 0)),
      totalEnergy: production.reduce(
        (sum, r) => sum + (r.ac_energy_kwh || 0),
        0
      ),
      maxIrradiance: Math.max(...production.map((r) => r.irradiance_wm2 || 0)),
    };
  }, [production]);

  return (
    <div className="page-shell">
      <Navbar />
      <main className="content">
      {/* HEADER */}
      <div className="detail-top">
        <div>
          <Link to="/" className="back-link">
            ← Retour au dashboard
          </Link>

          <p>Analyse de production</p>
          <h1>{system?.system_name || id}</h1>
        </div>

        <div className="date-filter">
          <label>
            Début
            <input
              type="date"
              value={startDate}
              onChange={(e) => setStartDate(e.target.value)}
            />
          </label>

          <label>
            Fin
            <input
              type="date"
              value={endDate}
              onChange={(e) => setEndDate(e.target.value)}
            />
          </label>
        </div>
      </div>

      {/* SYSTEM INFO */}
      {system && (
        <section className="detail-summary">
          <div>
            <span>Système</span>
            <strong>{system.system_id}</strong>
          </div>
          <div>
            <span>Capacité installée</span>
            <strong>{system.total_capacity_kwc != null ? `${system.total_capacity_kwc} kWc` : "—"}</strong>
          </div>
          <div>
            <span>Orientation</span>
            <strong>
              {system.orientation || "—"} / {system.tilt_angle != null ? `${system.tilt_angle}°` : "—"}
            </strong>
          </div>
          <div>
            <span>Points affichés</span>
            <strong>{production.length}</strong>
          </div>
        </section>
      )}

      {/* KPI */}
      <section className="kpi-grid">
        <div>
          <span>Puissance AC max</span>
          <strong>{summary.maxAc.toFixed(2)} kW</strong>
        </div>

        <div>
          <span>Puissance DC max</span>
          <strong>{summary.maxDc.toFixed(2)} kW</strong>
        </div>

        <div>
          <span>Énergie AC cumulée</span>
          <strong>{summary.totalEnergy.toFixed(2)} kWh</strong>
        </div>

        <div>
          <span>Irradiance max</span>
          <strong>{summary.maxIrradiance.toFixed(0)} W/m²</strong>
        </div>
      </section>

      {/* STATES */}
      {loading && (
        <div className="state-box">Chargement des données...</div>
      )}

      {error && <div className="state-box error-box">{error}</div>}

      {/* CHARTS */}
      {!loading && !error && (
        <section className="charts-grid">
          <ChartCard
            title="Puissance AC"
            unit="kW"
            data={production}
            dataKey="ac_power_kw"
          />

          <ChartCard
            title="Puissance DC"
            unit="kW"
            data={production}
            dataKey="dc_power_kw"
          />

          <ChartCard
            title="Énergie AC"
            unit="kWh"
            data={production}
            dataKey="ac_energy_kwh"
          />

          <ChartCard
            title="Tension DC"
            unit="V"
            data={production}
            dataKey="dc_voltage_v"
          />

          <ChartCard
            title="Courant DC"
            unit="A"
            data={production}
            dataKey="dc_current_per_string_a"
          />

          <ChartCard
            title="Irradiance"
            unit="W/m²"
            data={production}
            dataKey="irradiance_wm2"
          />

          <ChartCard
            title="Température ambiante"
            unit="°C"
            data={production}
            dataKey="ambient_temperature_c"
          />
        </section>
      )}
    </main>
    </div>
  );
}