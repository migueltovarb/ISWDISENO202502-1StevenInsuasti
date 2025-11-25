import React, { useState } from 'react';
import { api } from '../services/api';

const Register = ({ onRegister, onSwitchToLogin }) => {
  const [formData, setFormData] = useState({
    nombre: '',
    correo: '',
    password: '',
    confirmPassword: ''
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    // Validar que las contraseñas coincidan
    if (formData.password !== formData.confirmPassword) {
      setError('Las contraseñas no coinciden');
      return;
    }

    setLoading(true);

    try {
      // Enviar solo los datos necesarios con rol fijo de ESTUDIANTE
      const registroData = {
        nombre: formData.nombre,
        correo: formData.correo,
        password: formData.password,
        rol: 'ESTUDIANTE'
      };

      const response = await api.post('/usuarios/registro', registroData);
      if (response.data.success) {
        alert('✅ Registro exitoso. Por favor inicia sesión.');
        onRegister();
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Error al registrar');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center px-4 py-12 bg-slate-950 relative overflow-hidden">
      {/* Efectos de fondo */}
      <div className="absolute inset-0 bg-gradient-to-br from-emerald-500/10 via-transparent to-cyan-500/10"></div>
      <div className="absolute top-10 right-10 w-96 h-96 bg-emerald-500/20 rounded-full blur-3xl"></div>
      <div className="absolute bottom-10 left-10 w-80 h-80 bg-cyan-500/20 rounded-full blur-3xl"></div>

      <div className="max-w-2xl w-full relative z-10 animate-fadeIn">
        <div className="text-center mb-8">
          <div className="inline-block p-4 bg-gradient-to-br from-emerald-500 to-cyan-600 rounded-2xl shadow-emerald mb-6">
            <span className="text-5xl">🎓</span>
          </div>
          <h2 className="text-4xl font-black text-white mb-2">
            Crear Cuenta
          </h2>
          <p className="text-slate-400 text-lg">
            Únete al sistema de gestión de laboratorios
          </p>
        </div>

        <div className="card p-8">
          <form onSubmit={handleSubmit} className="space-y-5">
            {error && (
              <div className="bg-rose-500/10 border border-rose-500/50 rounded-lg p-4 text-rose-400 text-sm font-medium animate-fadeIn">
                <span className="text-lg mr-2">⚠️</span>
                {error}
              </div>
            )}

            <div>
              <label className="block text-sm font-bold text-slate-300 mb-2">
                Nombre Completo
              </label>
              <input
                type="text"
                value={formData.nombre}
                onChange={(e) => setFormData({...formData, nombre: e.target.value})}
                className="input-field"
                placeholder="Juan Pérez"
                required
              />
            </div>

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
                Contraseña (mínimo 8 caracteres)
              </label>
              <input
                type="password"
                value={formData.password}
                onChange={(e) => setFormData({...formData, password: e.target.value})}
                className="input-field"
                placeholder="••••••••"
                minLength="8"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-bold text-slate-300 mb-2">
                Confirmar Contraseña
              </label>
              <input
                type="password"
                value={formData.confirmPassword}
                onChange={(e) => setFormData({...formData, confirmPassword: e.target.value})}
                className="input-field"
                placeholder="••••••••"
                minLength="8"
                required
              />
              {formData.confirmPassword && formData.password !== formData.confirmPassword && (
                <p className="text-sm text-rose-400 mt-2 font-medium flex items-center animate-fadeIn">
                  <span className="mr-2">✗</span>
                  Las contraseñas no coinciden
                </p>
              )}
              {formData.confirmPassword && formData.password === formData.confirmPassword && (
                <p className="text-sm text-emerald-400 mt-2 font-medium flex items-center animate-fadeIn">
                  <span className="mr-2">✓</span>
                  Las contraseñas coinciden
                </p>
              )}
            </div>

            <button
              type="submit"
              disabled={loading}
              className="w-full btn-success text-lg py-4 font-bold"
            >
              {loading ? (
                <span className="flex items-center justify-center">
                  <span className="animate-spin mr-2">⏳</span>
                  Creando cuenta...
                </span>
              ) : (
                <span>Registrarse</span>
              )}
            </button>

            <div className="text-center pt-4">
              <button
                type="button"
                onClick={onSwitchToLogin}
                className="text-cyan-400 hover:text-cyan-300 text-sm font-semibold transition-colors duration-200"
              >
                ¿Ya tienes cuenta? <span className="underline">Inicia sesión</span>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Register;