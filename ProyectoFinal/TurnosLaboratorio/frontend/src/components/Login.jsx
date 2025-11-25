import React, { useState } from 'react';
import { api } from '../services/api';

const Login = ({ onLogin, onSwitchToRegister }) => {
  const [formData, setFormData] = useState({
    correo: '',
    password: ''
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const response = await api.post('/usuarios/login', formData);
      if (response.data.success) {
        onLogin(response.data.data);
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Error al iniciar sesión');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center px-4 bg-slate-950 relative overflow-hidden">
      {/* Efectos de fondo */}
      <div className="absolute inset-0 bg-gradient-to-br from-cyan-500/10 via-transparent to-blue-500/10"></div>
      <div className="absolute top-20 left-20 w-72 h-72 bg-cyan-500/20 rounded-full blur-3xl"></div>
      <div className="absolute bottom-20 right-20 w-96 h-96 bg-blue-500/20 rounded-full blur-3xl"></div>

      <div className="max-w-md w-full relative z-10 animate-fadeIn">
        <div className="text-center mb-8">
          <div className="inline-block p-4 bg-gradient-to-br from-cyan-500 to-blue-600 rounded-2xl shadow-neon-lg mb-6">
            <span className="text-5xl">💻</span>
          </div>
          <h2 className="text-4xl font-black text-white mb-2">
            LabTurnos
          </h2>
          <p className="text-slate-400 text-lg">
            Sistema de Gestión de Laboratorios
          </p>
        </div>

        <div className="card p-8">
          <form onSubmit={handleSubmit} className="space-y-6">
            {error && (
              <div className="bg-rose-500/10 border border-rose-500/50 rounded-lg p-4 text-rose-400 text-sm font-medium animate-fadeIn">
                <span className="text-lg mr-2">⚠️</span>
                {error}
              </div>
            )}

            <div>
              <label className="block text-sm font-bold text-slate-300 mb-2">
                Correo Electrónico
              </label>
              <input
                type="email"
                value={formData.correo}
                onChange={(e) => setFormData({...formData, correo: e.target.value})}
                className="input-field"
                placeholder="tu@email.com"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-bold text-slate-300 mb-2">
                Contraseña
              </label>
              <input
                type="password"
                value={formData.password}
                onChange={(e) => setFormData({...formData, password: e.target.value})}
                className="input-field"
                placeholder="••••••••"
                required
              />
            </div>

            <button
              type="submit"
              disabled={loading}
              className="w-full btn-primary text-lg py-4 font-bold"
            >
              {loading ? (
                <span className="flex items-center justify-center">
                  <span className="animate-spin mr-2">⏳</span>
                  Iniciando...
                </span>
              ) : (
                <span>Iniciar Sesión</span>
              )}
            </button>

            <div className="text-center pt-4">
              <button
                type="button"
                onClick={onSwitchToRegister}
                className="text-cyan-400 hover:text-cyan-300 text-sm font-semibold transition-colors duration-200"
              >
                ¿No tienes cuenta? <span className="underline">Regístrate</span>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Login;