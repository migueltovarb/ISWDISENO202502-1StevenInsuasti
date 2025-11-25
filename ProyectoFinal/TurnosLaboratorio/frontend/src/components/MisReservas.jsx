import React, { useState, useEffect } from 'react';
import { api } from '../services/api';

const MisReservas = ({ usuario }) => {
  const [reservas, setReservas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [mostrarFinalizadas, setMostrarFinalizadas] = useState(true);

  useEffect(() => {
    cargarReservas();
  }, []);

  const cargarReservas = async () => {
    try {
      const response = await api.get('/reservas/mis-reservas');
      if (response.data.success) {
        setReservas(response.data.data);
      }
    } catch (error) {
      console.error('Error al cargar reservas:', error);
    } finally {
      setLoading(false);
    }
  };

  const cancelarReserva = async (id) => {
    if (!window.confirm('¿Estás seguro de cancelar esta reserva?')) return;

    try {
      const response = await api.put(`/reservas/${id}/cancelar`);
      if (response.data.success) {
        alert('✅ Reserva cancelada exitosamente');
        cargarReservas();
      }
    } catch (error) {
      alert(error.response?.data?.message || 'Error al cancelar');
    }
  };

  const checkIn = async (id) => {
    if (!window.confirm('¿Confirmar check-in?')) return;

    try {
      const response = await api.put(`/reservas/${id}/checkin`);
      if (response.data.success) {
        alert('✅ Check-in exitoso');
        cargarReservas();
      }
    } catch (error) {
      alert(error.response?.data?.message || 'Error al hacer check-in');
    }
  };

  const checkOut = async (id) => {
    if (!window.confirm('¿Confirmar check-out?')) return;

    try {
      const response = await api.put(`/reservas/${id}/checkout`);
      if (response.data.success) {
        alert('✅ Check-out exitoso');
        cargarReservas();
      }
    } catch (error) {
      alert(error.response?.data?.message || 'Error al hacer check-out');
    }
  };

  const limpiarCanceladas = async () => {
    const reservasCanceladas = reservas.filter(
      r => r.estado === 'CANCELADA' || r.estado === 'CANCELADA_POR_MANTENIMIENTO'
    );

    if (reservasCanceladas.length === 0) {
      alert('No hay reservas canceladas para limpiar');
      return;
    }

    if (!window.confirm(`¿Eliminar ${reservasCanceladas.length} reserva(s) cancelada(s)?`)) return;

    try {
      const response = await api.delete('/reservas/limpiar-canceladas');
      if (response.data.success) {
        alert(`✅ ${response.data.data.eliminadas} reserva(s) eliminada(s)`);
        cargarReservas();
      }
    } catch (error) {
      alert(error.response?.data?.message || 'Error al limpiar reservas');
    }
  };

  const getEstadoConfig = (estado) => {
    const configs = {
      PROGRAMADA: { color: 'cyan', icon: '📅', label: 'Programada' },
      EN_CURSO: { color: 'emerald', icon: '▶️', label: 'En Curso' },
      COMPLETADA: { color: 'blue', icon: '✓', label: 'Completada' },
      CANCELADA: { color: 'rose', icon: '✗', label: 'Cancelada' },
      EXPIRADA: { color: 'gray', icon: '⏱️', label: 'Expirada' },
      CANCELADA_POR_MANTENIMIENTO: { color: 'amber', icon: '⚠️', label: 'Cancelada' }
    };
    return configs[estado] || configs.PROGRAMADA;
  };

  if (loading) {
    return (
      <div className="text-center py-20 animate-fadeIn">
        <div className="w-16 h-16 border-4 border-cyan-500 border-t-transparent rounded-full animate-spin mx-auto"></div>
        <p className="mt-6 text-slate-300 text-xl font-bold">Cargando reservas...</p>
      </div>
    );
  }

  const reservasFiltradas = mostrarFinalizadas 
    ? reservas 
    : reservas.filter(r => r.estado !== 'CANCELADA' && r.estado !== 'COMPLETADA' && r.estado !== 'CANCELADA_POR_MANTENIMIENTO');

  const reservasActivas = reservas.filter(r => r.estado === 'PROGRAMADA' || r.estado === 'EN_CURSO').length;
  const reservasFinalizadas = reservas.filter(r => r.estado === 'CANCELADA' || r.estado === 'COMPLETADA' || r.estado === 'CANCELADA_POR_MANTENIMIENTO').length;
  const reservasCanceladas = reservas.filter(r => r.estado === 'CANCELADA' || r.estado === 'CANCELADA_POR_MANTENIMIENTO').length;

  return (
    <div className="animate-slideInRight">
      {/* Header */}
      <div className="mb-8">
        <h1 className="text-4xl font-black text-white mb-2">Mis Reservas</h1>
        <p className="text-slate-400">Gestiona tus reservas activas y pasadas</p>
      </div>

      {/* Stats y Controles */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-8">
        {/* Stat Card - Activas */}
        <div className="card p-6">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm text-slate-400 font-semibold mb-1">Activas</p>
              <p className="text-4xl font-black text-emerald-400">{reservasActivas}</p>
            </div>
            <div className="w-16 h-16 bg-emerald-500/20 rounded-xl flex items-center justify-center text-3xl">
              ✓
            </div>
          </div>
        </div>

        {/* Stat Card - Finalizadas */}
        <div className="card p-6">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm text-slate-400 font-semibold mb-1">Finalizadas</p>
              <p className="text-4xl font-black text-cyan-400">{reservasFinalizadas}</p>
            </div>
            <div className="w-16 h-16 bg-cyan-500/20 rounded-xl flex items-center justify-center text-3xl">
              📋
            </div>
          </div>
        </div>

        {/* Controles */}
        <div className="card p-6 flex flex-col justify-center space-y-3">
          {reservasFinalizadas > 0 && (
            <button
              onClick={() => setMostrarFinalizadas(!mostrarFinalizadas)}
              className="btn-secondary text-sm py-2"
            >
              {mostrarFinalizadas ? '👁️ Ocultar finalizadas' : '👁️ Mostrar todas'}
            </button>
          )}
          {reservasCanceladas > 0 && (
            <button
              onClick={limpiarCanceladas}
              className="btn-danger text-sm py-2"
            >
              🗑️ Limpiar ({reservasCanceladas})
            </button>
          )}
        </div>
      </div>

      {/* Lista de Reservas */}
      {reservasFiltradas.length > 0 ? (
        <div className="space-y-4">
          {reservasFiltradas.map(reserva => {
            const estadoConfig = getEstadoConfig(reserva.estado);
            return (
              <div key={reserva.id} className="card p-6 hover-glow">
                <div className="flex items-start justify-between">
                  <div className="flex-1">
                    <div className="flex items-center space-x-4 mb-4">
                      <div className="w-14 h-14 bg-gradient-to-br from-cyan-500 to-blue-600 rounded-xl flex items-center justify-center text-2xl shadow-neon">
                        💻
                      </div>
                      <div>
                        <h3 className="text-2xl font-black text-white">
                          {reserva.equipoCodigo}
                        </h3>
                        <span className={`inline-block px-3 py-1 text-xs font-bold rounded-md bg-${estadoConfig.color}-500/20 text-${estadoConfig.color}-400 border border-${estadoConfig.color}-500/50`}>
                          {estadoConfig.icon} {estadoConfig.label}
                        </span>
                      </div>
                    </div>

                    <div className="grid grid-cols-1 md:grid-cols-3 gap-4 text-sm">
                      <div className="flex items-center space-x-2 text-slate-400">
                        <span className="text-lg">🏢</span>
                        <span>{reserva.laboratorioNombre}</span>
                      </div>
                      <div className="flex items-center space-x-2 text-slate-400">
                        <span className="text-lg">📅</span>
                        <span>{new Date(reserva.franja.inicio).toLocaleDateString('es-CO')}</span>
                      </div>
                      <div className="flex items-center space-x-2 text-slate-400">
                        <span className="text-lg">⏰</span>
                        <span>
                          {new Date(reserva.franja.inicio).toLocaleTimeString('es-CO', {hour: '2-digit', minute: '2-digit'})}
                          {' - '}
                          {new Date(reserva.franja.fin).toLocaleTimeString('es-CO', {hour: '2-digit', minute: '2-digit'})}
                        </span>
                      </div>
                    </div>
                  </div>

                  <div className="flex flex-col space-y-2 ml-4">
                    {reserva.estado === 'PROGRAMADA' && (
                      <>
                        <button
                          onClick={() => checkIn(reserva.id)}
                          className="btn-success text-sm px-4 py-2"
                        >
                          ✓ Check-in
                        </button>
                        <button
                          onClick={() => cancelarReserva(reserva.id)}
                          className="btn-danger text-sm px-4 py-2"
                        >
                          ✗ Cancelar
                        </button>
                      </>
                    )}
                    
                    {reserva.estado === 'EN_CURSO' && (
                      <>
                        <button
                          onClick={() => checkOut(reserva.id)}
                          className="btn-primary text-sm px-4 py-2"
                        >
                          🚪 Check-out
                        </button>
                        <button
                          onClick={() => cancelarReserva(reserva.id)}
                          className="btn-danger text-sm px-4 py-2"
                        >
                          ✗ Cancelar
                        </button>
                      </>
                    )}
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      ) : (
        <div className="card p-16 text-center">
          <div className="text-6xl mb-4">📋</div>
          <p className="text-xl text-slate-400 font-medium">
            {reservas.length === 0 ? 'No tienes reservas' : 'No hay reservas activas'}
          </p>
        </div>
      )}
    </div>
  );
};

export default MisReservas;
