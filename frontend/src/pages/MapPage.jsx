import { useEffect, useRef, useState } from "react";
import { LayersControl, MapContainer, TileLayer, useMap, GeoJSON } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import Navbar from "../components/Navbar";
import proj4 from "proj4";
import L from "leaflet";
import { getSystems } from "../api/axiosConfig";

const MAP_CENTER = [33.9712, -5.0076];

// Définition de la projection UTM Zone 29N utilisée dans le GeoJSON
proj4.defs("EPSG:32629", "+proj=utm +zone=29 +datum=WGS84 +units=m +no_defs");

function GeoTiffOverlay({ showThermal }) {
  const map = useMap();
  const rgbLayerRef = useRef(null);
  const thermalLayerRef = useRef(null);

  useEffect(() => {
    let isMounted = true;

    async function loadRaster() {
      try {
        const georasterModule = await import("georaster");
        const GeoRasterLayerModule = await import("georaster-layer-for-leaflet");

        const parseGeoraster = georasterModule.default;
        const GeoRasterLayer = GeoRasterLayerModule.default;

        const response = await fetch("/masque.tif");
        const arrayBuffer = await response.arrayBuffer();
        const georaster = await parseGeoraster(arrayBuffer);

        if (!isMounted) return;

        const rgbLayer = new GeoRasterLayer({
          georaster,
          opacity: 0.85,
          resolution: 2048,
          pixelValuesToColorFn: (values) => {
            const r = values[0] ?? 0;
            const g = values[1] ?? 0;
            const b = values[2] ?? 0;

            if (r === 0 && g === 0 && b === 0) return null;

            return `rgb(${r}, ${g}, ${b})`;
          },
        });

        rgbLayer.addTo(map);
        rgbLayerRef.current = rgbLayer;

        const thermalLayer = new GeoRasterLayer({
          georaster,
          opacity: 0.72,
          resolution: 2048,
          pixelValuesToColorFn: (values) => {
            const r = values[0] ?? 0;
            const g = values[1] ?? 0;
            const b = values[2] ?? 0;

            if (r === 0 && g === 0 && b === 0) return null;

            const light = (0.299 * r + 0.587 * g + 0.114 * b) / 255;

            if (light < 0.2) return "rgba(68, 1, 84, 0.82)";
            if (light < 0.4) return "rgba(59, 82, 139, 0.82)";
            if (light < 0.6) return "rgba(33, 145, 140, 0.82)";
            if (light < 0.8) return "rgba(94, 201, 98, 0.82)";
            return "rgba(253, 231, 37, 0.82)";
          },
        });

        thermalLayerRef.current = thermalLayer;

        try {
          map.fitBounds(rgbLayer.getBounds());
        } catch (err) {
          map.setView(MAP_CENTER, 16);
        }
      } catch (error) {
        console.error("GeoTIFF loading error:", error);
      }
    }

    loadRaster();

    return () => {
      isMounted = false;
      if (rgbLayerRef.current) {
        try { map.removeLayer(rgbLayerRef.current); } catch {}
        rgbLayerRef.current = null;
      }
      if (thermalLayerRef.current) {
        try { map.removeLayer(thermalLayerRef.current); } catch {}
        thermalLayerRef.current = null;
      }
    };
  }, [map]);

  useEffect(() => {
    if (!thermalLayerRef.current) return;

    if (showThermal) {
      thermalLayerRef.current.addTo(map);
    } else if (map.hasLayer(thermalLayerRef.current)) {
      map.removeLayer(thermalLayerRef.current);
    }
  }, [showThermal, map]);

  return null;
}

function ThermalLegend() {
  return (
    <div className="thermal-legend">
      <h3>Température simulée</h3>

      <div className="legend-row">
        <span style={{ background: "rgba(68, 1, 84, 0.82)" }} />
        <p>20 - 25 °C</p>
      </div>

      <div className="legend-row">
        <span style={{ background: "rgba(59, 82, 139, 0.82)" }} />
        <p>25 - 30 °C</p>
      </div>

      <div className="legend-row">
        <span style={{ background: "rgba(33, 145, 140, 0.82)" }} />
        <p>30 - 35 °C</p>
      </div>

      <div className="legend-row">
        <span style={{ background: "rgba(94, 201, 98, 0.82)" }} />
        <p>35 - 40 °C</p>
      </div>

      <div className="legend-row">
        <span style={{ background: "rgba(253, 231, 37, 0.82)" }} />
        <p>40 - 45 °C</p>
      </div>
    </div>
  );
}

function PvSystemsLayer() {
  const [geoData, setGeoData] = useState(null);
  const [systemsMap, setSystemsMap] = useState({});
  const [systemsLoaded, setSystemsLoaded] = useState(false);

  useEffect(() => {
    getSystems()
      .then((res) => {
        const sysMap = {};
        res.data.forEach((sys) => {
          sysMap[sys.systemId] = sys;
        });
        setSystemsMap(sysMap);
      })
      .catch((err) => console.error("Erreur de chargement des systèmes :", err))
      .finally(() => setSystemsLoaded(true));

    fetch("/systems.geojson")
      .then((res) => res.json())
      .then((data) => {
        const reprojectedFeatures = data.features.map((feature) => {
          if (feature.geometry && feature.geometry.coordinates) {
            const newCoordinates = feature.geometry.coordinates.map(polygon => 
              polygon.map(ring => 
                ring.map(coord => {
                  const wgs84 = proj4("EPSG:32629", "EPSG:4326", [coord[0], coord[1]]);
                  return [wgs84[0], wgs84[1]];
                })
              )
            );
            return {
              ...feature,
              geometry: {
                ...feature.geometry,
                coordinates: newCoordinates
              }
            };
          }
          return feature;
        });

        setGeoData({
          ...data,
          features: reprojectedFeatures
        });
      })
      .catch((err) => console.error("Erreur de chargement du GeoJSON :", err));
  }, []);

  if (!geoData || !systemsLoaded) return null;

  return (
    <GeoJSON
      data={geoData}
      style={() => ({
        color: "#2a9df4",
        weight: 2,
        fillColor: "#2a9df4",
        fillOpacity: 0.3,
      })}
      onEachFeature={(feature, layer) => {
        const sysId = feature.properties.system;
        
        layer.on('click', () => {
          const sysData = systemsMap[sysId];
          
          let popupContent = `
            <div style="font-family: sans-serif; min-width: 200px;">
              <h3 style="margin: 0 0 8px 0; color: #2c3e50; border-bottom: 1px solid #eee; padding-bottom: 5px;">${sysId}</h3>
          `;

          if (sysData) {
            popupContent += `
              <p style="margin: 5px 0;"><strong>Nom:</strong> ${sysData.systemName || 'N/A'}</p>
              <p style="margin: 5px 0;"><strong>Capacité:</strong> ${sysData.totalCapacityKwc || 'N/A'} kWc</p>
              <p style="margin: 8px 0 5px 0;"><strong>Dernière Prod.:</strong> <span style="color: #27ae60; font-weight: bold; font-size: 1.1em;">${sysData.lastAcPowerKw !== null && sysData.lastAcPowerKw !== undefined ? sysData.lastAcPowerKw + ' kW' : 'N/A'}</span></p>
              <p style="margin: 5px 0; font-size: 0.9em; color: #7f8c8d;"><strong>Énergie du jour:</strong> ${sysData.dailyEnergyKwh !== null && sysData.dailyEnergyKwh !== undefined ? sysData.dailyEnergyKwh + ' kWh' : 'N/A'}</p>
            `;
          } else {
            popupContent += `<p style="color: #e74c3c; margin: 5px 0;">Données indisponibles</p>`;
          }

          popupContent += `</div>`;
          layer.bindPopup(popupContent).openPopup();
        });
      }}
    />
  );
}

function MapPage() {
  const [showThermal, setShowThermal] = useState(false);

  return (
    <div className="page-shell">
      <Navbar />

      <main className="map-page">
        <div className="map-panel">
          <div className="map-title">
            <div>
              <p>Vue géospatiale</p>
              <h1>Carte interactive du site PV</h1>
            </div>

            <label className="thermal-toggle">
              <input
                type="checkbox"
                checked={showThermal}
                onChange={(event) => setShowThermal(event.target.checked)}
              />
              Couche thermique
            </label>
          </div>

          <div className="map-wrapper">
            {showThermal && <ThermalLegend />}

            <MapContainer
              center={MAP_CENTER}
              zoom={16}
              scrollWheelZoom
              className="leaflet-map"
            >
              <LayersControl position="topright">
                <LayersControl.BaseLayer checked name="OpenStreetMap">
                  <TileLayer
                    attribution="&copy; OpenStreetMap contributors"
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                  />
                </LayersControl.BaseLayer>

                <LayersControl.BaseLayer name="Satellite">
                  <TileLayer
                    attribution="Tiles &copy; Esri"
                    url="https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}"
                  />
                </LayersControl.BaseLayer>

                <LayersControl.BaseLayer name="Terrain">
                  <TileLayer
                    attribution="Map data &copy; OpenTopoMap"
                    url="https://{s}.tile.opentopomap.org/{z}/{x}/{y}.png"
                    maxNativeZoom={17}
                    maxZoom={22}
                  />
                </LayersControl.BaseLayer>
              </LayersControl>

              <GeoTiffOverlay showThermal={showThermal} />
              
              <PvSystemsLayer />
            </MapContainer>
          </div>
        </div>
      </main>
    </div>
  );
}

export default MapPage;