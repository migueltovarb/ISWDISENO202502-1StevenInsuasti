import React, { useState, useEffect } from 'react';
import { api } from '../services/api';

const Disponibilidad = ({ usuario }) => {
  const [laboratorios, setLaboratorios] = useState([]);
  const [filtros, setFiltros] = useState({
    laboratorioId: '',
    fecha: new Date().toISOString().split('T')[0],
    franja: 'FRANJA_07_09'
  });
  const [equiposDisponibles, setEquiposDisponibles] = useState([]);
  const [loading, setLoading] = useState(false);
  const [reservando, setReservando] = useState(false);

  useEffect(() => {
    cargarLaboratorios();
  }, []);

  const cargarLaboratorios = async () => {
    try {
      const response = await api.get('/disponibilidad/laboratorios');
      setLaboratorios(response.data);
      if (response.data.length > 0) {
        setFiltros({...filtros, laboratorioId: response.data[0].id});
      }
    } catch (error) {
      console.error('Error al cargar laboratorios:', error);
    }
  };

  const consultarDisponibilidad = async () => {
    setLoading(true);
    try {
      const response = await api.post('/disponibilidad/consultar', filtros);
      if (response.data.success) {
        setEquiposDisponibles(response.data.data.equiposDisponibles);
      }
    } catch (error) {
      alert('Error al consultar disponibilidad');
    } finally {
      setLoading(false);
    }
  };

  const reservarEquipo = async (equipoCodigo, laboratorioId) => {
    if (!window.confirm('¿Confirmar reserva?')) return;

    setReservando(true);
    try {
      const reservaData = {
        laboratorioId: laboratorioId,
        equipoCodigo: equipoCodigo,
        fecha: filtros.fecha,
        franja: filtros.franja
      };

      const response = await api.post('/reservas', reservaData);
      if (response.data.success) {
        alert('✅ Reserva creada exitosamente');
        consultarDisponibilidad();
      }
    } catch (error) {
      alert(error.response?.data?.message || 'Error al crear reserva');
    } finally {
      setReservando(false);
    }
  };

  const franjas = [
    { value: 'FRANJA_07_09', label: '07:00 - 09:00', icon: '🌅' },
    { value: 'FRANJA_09_11', label: '09:00 - 11:00', icon: '☀️' },
    { value: 'FRANJA_11_13', label: '11:00 - 13:00', icon: '🌤️' },
    { value: 'FRANJA_13_15', label: '13:00 - 15:00', icon: '🌞' },
    { value: 'FRANJA_15_17', label: '15:00 - 17:00', icon: '🌤️' },
    { value: 'FRANJA_17_19', label: '17:00 - 19:00', icon: '🌆' },
  ];

  return (
    <div className="animate-slideInRight">
      {/* Header */}
      <div className="mb-8">
        <h1 className="text-4xl font-black text-white mb-2">Buscar Equipos</h1>
        <p className="text-slate-400">Encuentra y reserva equipos disponibles</p>
      </div>

      {/* Filtros en Grid */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-8">
        {/* Laboratorio */}
        <div className="card p-6">
          <label className="block text-sm font-bold text-cyan-400 mb-3">
            Laboratorio
          </label>
          <select
            value={filtros.laboratorioId}
            onChange={(e) => setFiltros({...filtros, laboratorioId: e.target.value})}
            className="input-field"
          >
            <option value="">Todos</option>
            {laboratorios.map(lab => (
              <option key={lab.id} value={lab.id}>{lab.nombre}</option>
            ))}
          </select>
        </div>

        {/* Fecha */}
        <div className="card p-6">
          <label className="block text-sm font-bold text-cyan-400 mb-3">
            Fecha
          </label>
          <input
            type="date"
            value={filtros.fecha}
            onChange={(e) => setFiltros({...filtros, fecha: e.target.value})}
            className="input-field"
            min={new Date().toISOString().split('T')[0]}
          />
        </div>

        {/* Botón Buscar */}
        <div className="card p-6 flex items-end">
          <button
            onClick={consultarDisponibilidad}
            disabled={loading}
            className="w-full btn-primary"
          >
            {loading ? '⏳ Buscando...' : '🔍 Buscar'}
          </button>
        </div>
      </div>

      {/* Franjas Horarias */}
      <div className="card p-6 mb-8">
        <label className="block text-sm font-bold text-cyan-400 mb-4">
          Franja Horaria (2 horas)
        </label>
        <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-3">
          {franjas.map((franja) => (
            <button
              key={franja.value}
              onClick={() => setFiltros({...filtros, franja: franja.value})}
              className={`p-4 rounded-lg font-semibold transition-all duration-200 ${
                filtros.franja === franja.value
                  ? 'bg-cyan-500/20 text-cyan-400 border-2 border-cyan-500 shadow-neon'
                  : 'bg-slate-800 text-slate-400 border border-slate-700 hover:border-slate-600'
              }`}
            >
              <div className="text-2xl mb-1">{franja.icon}</div>
              <div className="text-xs">{franja.label}</div>
            </button>
          ))}
        </div>
      </div>

      {/* Resultados */}
      {equiposDisponibles.length > 0 ? (
        <div>
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-2xl font-bold text-white">
              {equiposDisponibles.length} Equipos Disponibles
            </h2>
            <div className="px-4 py-2 bg-emerald-500/20 text-emerald-400 rounded-lg border border-emerald-500/50 font-bold">
              ✓ Disponible
            </div>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {equiposDisponibles.map(equipo => (
              <div key={equipo.id} className="card p-6 hover-glow">
                <div className="flex items-start justify-between mb-4">
                  <div>
                    <h3 className="text-2xl font-black text-white mb-1">
                      {equipo.codigo}
                    </h3>
                    <p className="text-sm text-cyan-400 font-semibold">{equipo.tipo}</p>
                  </div>
                  <div className="w-12 h-12 bg-emerald-500/20 rounded-lg flex items-center justify-center text-2xl">
                    💻
                  </div>
                </div>

                <p className="text-sm text-slate-400 mb-6 leading-relaxed">
                  {equipo.especificaciones}
                </p>

                <button
                  onClick={() => reservarEquipo(equipo.codigo, equipo.laboratorioId)}
                  disabled={reservando}
                  className="w-full btn-success"
                >
                  Reservar
                </button>
              </div>
            ))}
          </div>
        </div>
      ) : (
        <div className="card p-16 text-center">
          <div className="text-6xl mb-4">🔍</div>
          <p className="text-xl text-slate-400 font-medium">
            {loading ? 'Buscando equipos...' : 'No hay equipos disponibles'}
          </p>
          <p className="text-sm text-slate-500 mt-2">
            Intenta con otra fecha o franja horaria
          </p>
        </div>
      )}
    </div>
  );
};

export default Disponibilidad;
