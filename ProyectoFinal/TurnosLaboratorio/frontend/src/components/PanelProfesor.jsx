import React, { useState, useEffect } from 'react';
import { api } from '../services/api';

const PanelProfesor = () => {
  const [bloqueos, setBloqueos] = useState([]);
  const [laboratorios, setLaboratorios] = useState([]);
  const [equipos, setEquipos] = useState([]);
  const [reservas, setReservas] = useState([]);
  const [formVisible, setFormVisible] = useState(false);
  const [verReservas, setVerReservas] = useState(false);
  const [labSeleccionado, setLabSeleccionado] = useState('');
  const [formData, setFormData] = useState({
    laboratorioId: '',
    equipoCodigo: '',
    inicio: '',
    fin: '',
    motivo: ''
  });

  useEffect(() => {
    cargarDatos();
  }, []);

  useEffect(() => {
    if (formData.laboratorioId) {
      cargarEquipos(formData.laboratorioId);
    }
  }, [formData.laboratorioId]);

  useEffect(() => {
    if (labSeleccionado) {
      cargarReservas(labSeleccionado);
    }
  }, [labSeleccionado]);

  const cargarDatos = async () => {
    try {
      const [labsRes, bloqRes] = await Promise.all([
        api.get('/disponibilidad/laboratorios'),
        api.get('/bloqueos/mis-bloqueos')
      ]);

      setLaboratorios(labsRes.data);
      if (bloqRes.data.success) {
        setBloqueos(bloqRes.data.data);
      }
    } catch (error) {
      console.error('Error al cargar datos:', error);
    }
  };

  const cargarEquipos = async (laboratorioId) => {
    try {
      const response = await api.get(`/disponibilidad/equipos/${laboratorioId}`);
      if (response.data.success) {
        setEquipos(response.data.data || []);
      } else {
        setEquipos([]);
      }
    } catch (error) {
      console.error('Error al cargar equipos:', error);
      setEquipos([]);
    }
  };

  const cargarReservas = async (laboratorioId) => {
    try {
      const response = await api.get(`/reservas/laboratorio/${laboratorioId}`);
      if (response.data.success) {
        setReservas(response.data.data);
      }
    } catch (error) {
      console.error('Error al cargar reservas:', error);
      setReservas([]);
    }
  };

  const crearBloqueo = async (e) => {
    e.preventDefault();

    try {
      const response = await api.post('/bloqueos', formData);
      if (response.data.success) {
        alert('✅ Bloqueo creado exitosamente');
        setFormVisible(false);
        setFormData({
          laboratorioId: '',
          equipoCodigo: '',
          inicio: '',
          fin: '',
          motivo: ''
        });
        setEquipos([]);
        cargarDatos();
      }
    } catch (error) {
      alert(error.response?.data?.message || 'Error al crear bloqueo');
    }
  };

  const desbloquear = async (id) => {
    if (!window.confirm('¿Desbloquear?')) return;

    try {
      const response = await api.put(`/bloqueos/${id}/desbloquear`);
      if (response.data.success) {
        alert('✅ Desbloqueado exitosamente');
        cargarDatos();
      }
    } catch (error) {
      alert('Error al desbloquear');
    }
  };

  return (
    <div className="animate-slideInRight">
      {/* Header */}
      <div className="mb-8">
        <h1 className="text-4xl font-black text-white mb-2">Panel de Control</h1>
        <p className="text-slate-400">Gestiona bloqueos y supervisa reservas</p>
      </div>

      {/* Botones de Acción */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-8">
        <button
          onClick={() => {
            setVerReservas(!verReservas);
            setFormVisible(false);
          }}
          className={`p-6 rounded-xl font-bold transition-all duration-200 ${
            verReservas
              ? 'bg-rose-500/20 text-rose-400 border-2 border-rose-500'
              : 'bg-emerald-500/20 text-emerald-400 border-2 border-emerald-500 hover:bg-emerald-500/30'
          }`}
        >
          <div className="flex items-center justify-center space-x-3">
            <span className="text-3xl">{verReservas ? '✗' : '📋'}</span>
            <span className="text-xl">{verReservas ? 'Cerrar' : 'Ver Reservas'}</span>
          </div>
        </button>

        <button
          onClick={() => {
            setFormVisible(!formVisible);
            setVerReservas(false);
          }}
          className={`p-6 rounded-xl font-bold transition-all duration-200 ${
            formVisible
              ? 'bg-rose-500/20 text-rose-400 border-2 border-rose-500'
              : 'bg-amber-500/20 text-amber-400 border-2 border-amber-500 hover:bg-amber-500/30'
          }`}
        >
          <div className="flex items-center justify-center space-x-3">
            <span className="text-3xl">{formVisible ? '✗' : '🔒'}</span>
            <span className="text-xl">{formVisible ? 'Cancelar' : 'Nuevo Bloqueo'}</span>
          </div>
        </button>
      </div>

      {/* Formulario de Bloqueo */}
      {formVisible && (
        <div className="card p-8 mb-8 animate-fadeIn">
          <h2 className="text-2xl font-black text-white mb-6">Crear Bloqueo</h2>

          <form onSubmit={crearBloqueo} className="space-y-6">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <label className="block text-sm font-bold text-amber-400 mb-2">
                  Laboratorio *
                </label>
                <select
                  value={formData.laboratorioId}
                  onChange={(e) => setFormData({...formData, laboratorioId: e.target.value, equipoCodigo: ''})}
                  className="input-field"
                  required
                >
                  <option value="">Seleccionar</option>
                  {laboratorios.map(lab => (
                    <option key={lab.id} value={lab.id}>{lab.nombre}</option>
                  ))}
                </select>
              </div>

              {formData.laboratorioId && (
                <div>
                  <label className="block text-sm font-bold text-amber-400 mb-2">
                    Equipo (opcional)
                  </label>
                  <select
                    value={formData.equipoCodigo}
                    onChange={(e) => setFormData({...formData, equipoCodigo: e.target.value})}
                    className="input-field"
                  >
                    <option value="">🔒 Todo el laboratorio</option>
                    {equipos.map(equipo => (
                      <option key={equipo.id} value={equipo.codigo}>
                        {equipo.codigo} ({equipo.tipo})
                      </option>
                    ))}
                  </select>
                </div>
              )}
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <label className="block text-sm font-bold text-amber-400 mb-2">
                  Inicio *
                </label>
                <input
                  type="datetime-local"
                  value={formData.inicio}
                  onChange={(e) => setFormData({...formData, inicio: e.target.value})}
                  className="input-field"
                  min={new Date().toISOString().slice(0, 16)}
                  required
                />
              </div>

              <div>
                <label className="block text-sm font-bold text-amber-400 mb-2">
                  Fin *
                </label>
                <input
                  type="datetime-local"
                  value={formData.fin}
                  onChange={(e) => setFormData({...formData, fin: e.target.value})}
                  className="input-field"
                  min={formData.inicio || new Date().toISOString().slice(0, 16)}
                  required
                />
              </div>
            </div>

            <div>
              <label className="block text-sm font-bold text-amber-400 mb-2">
                Motivo *
              </label>
              <textarea
                value={formData.motivo}
                onChange={(e) => setFormData({...formData, motivo: e.target.value})}
                className="input-field"
                rows="3"
                placeholder="Ej: Mantenimiento preventivo"
                required
              />
            </div>

            <button type="submit" className="w-full btn-warning">
              🔒 Crear Bloqueo
            </button>
          </form>
        </div>
      )}

      {/* Ver Reservas */}
      {verReservas && (
        <div className="card p-8 mb-8 animate-fadeIn">
          <h2 className="text-2xl font-black text-white mb-6">Reservas por Laboratorio</h2>

          <div className="mb-6">
            <label className="block text-sm font-bold text-emerald-400 mb-2">
              Seleccionar Laboratorio
            </label>
            <select
              value={labSeleccionado}
              onChange={(e) => setLabSeleccionado(e.target.value)}
              className="input-field"
            >
              <option value="">Seleccionar</option>
              {laboratorios.map(lab => (
                <option key={lab.id} value={lab.id}>{lab.nombre}</option>
              ))}
            </select>
          </div>

          {labSeleccionado && (
            <div className="space-y-4">
              <h3 className="font-bold text-white text-lg">
                Reservas Activas ({reservas.length})
              </h3>
              {reservas.length > 0 ? (
                reservas.map(reserva => (
                  <div key={reserva.id} className="bg-slate-800/50 border border-slate-700 rounded-lg p-4">
                    <div className="flex items-center justify-between">
                      <div>
                        <p className="font-bold text-white">{reserva.equipoCodigo}</p>
                        <p className="text-sm text-slate-400">👤 {reserva.estudianteNombre}</p>
                        <p className="text-sm text-slate-400">
                          📅 {new Date(reserva.franja.inicio).toLocaleString('es-CO')}
                        </p>
                      </div>
                      <span className="px-3 py-1 bg-cyan-500/20 text-cyan-400 rounded-md text-xs font-bold border border-cyan-500/50">
                        {reserva.estado}
                      </span>
                    </div>
                  </div>
                ))
              ) : (
                <p className="text-center text-slate-500 py-8">No hay reservas activas</p>
              )}
            </div>
          )}
        </div>
      )}

      {/* Lista de Bloqueos */}
      <div>
        <h2 className="text-2xl font-black text-white mb-6">Mis Bloqueos</h2>

        {bloqueos.length > 0 ? (
          <div className="space-y-4">
            {bloqueos.map(bloqueo => (
              <div key={bloqueo.id} className="card p-6 hover-glow">
                <div className="flex items-start justify-between">
                  <div className="flex-1">
                    <div className="flex items-center space-x-3 mb-4">
                      <div className="w-12 h-12 bg-amber-500/20 rounded-lg flex items-center justify-center text-2xl">
                        🔒
                      </div>
                      <div>
                        <h3 className="text-xl font-black text-white">{bloqueo.motivo}</h3>
                        <span className={`inline-block px-3 py-1 text-xs font-bold rounded-md ${
                          bloqueo.activo
                            ? 'bg-rose-500/20 text-rose-400 border border-rose-500/50'
                            : 'bg-slate-500/20 text-slate-400 border border-slate-500/50'
                        }`}>
                          {bloqueo.activo ? '🔒 Activo' : '🔓 Inactivo'}
                        </span>
                      </div>
                    </div>

                    <div className="text-sm text-slate-400 space-y-1">
                      <p>📅 Inicio: {new Date(bloqueo.franja.inicio).toLocaleString('es-CO')}</p>
                      <p>🏁 Fin: {new Date(bloqueo.franja.fin).toLocaleString('es-CO')}</p>
                    </div>
                  </div>

                  {bloqueo.activo && (
                    <button
                      onClick={() => desbloquear(bloqueo.id)}
                      className="btn-success text-sm px-4 py-2"
                    >
                      🔓 Desbloquear
                    </button>
                  )}
                </div>
              </div>
            ))}
          </div>
        ) : (
          <div className="card p-16 text-center">
            <div className="text-6xl mb-4">🔐</div>
            <p className="text-xl text-slate-400 font-medium">No hay bloqueos</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default PanelProfesor;
