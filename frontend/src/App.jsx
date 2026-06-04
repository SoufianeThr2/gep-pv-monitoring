import { useEffect, useState } from 'react';
import api from './api/axiosConfig';

function App() {
  const [systems, setSystems] = useState([]);

  useEffect(() => {
    api.get('/systems')
      .then(response => {
        setSystems(response.data);
        console.log("Données reçues :", response.data);
      })
      .catch(error => console.error("Erreur API :", error));
  }, []);

  return (
    <div>
      <h1>Liste des systèmes PV</h1>
      <ul>
        {systems.map(sys => (
          <li key={sys.systemId}>{sys.systemName} - {sys.totalCapacityKwc} kWc</li>
        ))}
      </ul>
    </div>
  );
}

export default App;