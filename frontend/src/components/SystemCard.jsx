import { useNavigate } from "react-router-dom";

function SystemCard({ system }) {
  const navigate = useNavigate();

  // Passage en camelCase
  const moduleCount =
    system.module?.nbPerString && system.nbStrings
      ? system.module.nbPerString * system.nbStrings
      : null;

  return (
    <article
      className="system-card"
      onClick={() => navigate(`/system/${system.systemId}`)} // Corrigé : /system/ au lieu de /systems/
      style={{ cursor: 'pointer', overflow: 'hidden' }}
    >
      {/* --- ZONE D'IMAGE --- */}
      <div className="system-image" style={{ position: 'relative', height: '150px' }}>
        <img 
          src="/pv-image.jpg" 
          alt={`Système photovoltaïque ${system.systemName}`} 
          style={{ width: '100%', height: '100%', objectFit: 'cover', display: 'block' }} 
        />
        <span 
          style={{ 
            position: 'absolute', top: '12px', left: '12px', 
            backgroundColor: 'rgba(21, 101, 59, 0.9)', color: 'white', 
            padding: '4px 12px', borderRadius: '20px', fontWeight: 'bold', fontSize: '0.85rem' 
          }}
        >
          {system.systemId}
        </span>
      </div>
      {/* --------------------------------- */}

      <div className="system-card-body">
        <div className="system-card-title">
          <div>
            <h2>{system.systemName}</h2>
            <p>{system.systemId}</p>
          </div>
          <strong>{system.totalCapacityKwc} kWc</strong>
        </div>

        <div className="info-grid">
          <div>
            <span>Mise en service</span>
            <strong>{system.commissioningDate}</strong>
          </div>
          <div>
            <span>Orientation</span>
            <strong>{system.orientation}</strong>
          </div>
          <div>
            <span>Inclinaison</span>
            <strong>{system.tiltAngle}°</strong>
          </div>
          <div>
            <span>Strings</span>
            <strong>{system.nbStrings}</strong>
          </div>
        </div>

        <section className="card-section">
          <h3>Modules</h3>
          <p>
            {system.module?.brand} — {system.module?.model}
          </p>
          <p>
            {system.module?.technology}, {system.module?.powerWc} Wc
            {moduleCount ? `, ${moduleCount} modules` : ""}
          </p>
        </section>

        <section className="card-section">
          <h3>Onduleur</h3>
          <p>
            {system.inverter?.brand} — {system.inverter?.model}
          </p>
          <p>
            {system.inverter?.powerKwAc} kW AC, {system.inverter?.nbMppt} MPPT
          </p>
          <p>S/N : {system.inverter?.serialNumber}</p>
        </section>

        <div className="live-grid">
          <div>
            <span>Puissance AC</span>
            <strong>{system.lastAcPowerKw || 0} kW</strong>
          </div>
          <div>
            <span>Énergie journalière</span>
            <strong>{system.dailyEnergyKwh || 0} kWh</strong>
          </div>
        </div>
      </div>
    </article>
  );
}

export default SystemCard;