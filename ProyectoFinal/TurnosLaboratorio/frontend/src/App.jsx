import React, { useState, useEffect } from 'react';
import { api } from './services/api';
import Sidebar from './components/Sidebar';
import Login from './components/Login';
import Register from './components/Register';
import Disponibilidad from './components/Disponibilidad';
import MisReservas from './components/MisReservas';
import PanelProfesor from './components/PanelProfesor';

function App() {
  const [currentView, setCurrentView] = useState('login');
  const [usuario, setUsuario] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const verificarSesion = async () => {
      try {
        const response = await api.get('/usuarios/session');
        if (response.data.success) {
          const userData = response.data.data;
          setUsuario(userData);
          setCurrentView(userData.rol === 'PROFESOR' ? 'profesor' : 'disponibilidad');
        }
      } catch (error) {
        console.log('No hay sesión activa');
      } finally {
        setLoading(false);
      }
    };

    verificarSesion();
  }, []);

  const handleLogin = (usuarioData) => {
    setUsuario(usuarioData);
    setCurrentView(usuarioData.rol === 'PROFESOR' ? 'profesor' : 'disponibilidad');
  };

  const handleLogout = async () => {
    try {
      await api.post('/usuarios/logout');
      setUsuario(null);
      setCurrentView('login');
    } catch (error) {
      console.error('Error al cerrar sesión:', error);
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-slate-950">
        <div className="text-center animate-fadeIn">
          <div className="w-16 h-16 border-4 border-cyan-500 border-t-transparent rounded-full animate-spin mx-auto"></div>
          <p className="mt-6 text-slate-300 text-xl font-bold">Cargando sistema...</p>
        </div>
      </div>
    );
  }

  if (!usuario) {
    return (
      <div className="min-h-screen">
        {currentView === 'login' ? (
          <Login 
            onLogin={handleLogin}
            onSwitchToRegister={() => setCurrentView('register')}
          />
        ) : (
          <Register 
            onRegister={() => setCurrentView('login')}
            onSwitchToLogin={() => setCurrentView('login')}
          />
        )}
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-slate-950 flex">
      <Sidebar 
        currentView={currentView}
        setCurrentView={setCurrentView}
        usuario={usuario}
        onLogout={handleLogout}
      />

      <main className="flex-1 ml-72 p-8 overflow-y-auto">
        <div className="max-w-7xl mx-auto">
          {currentView === 'disponibilidad' && (
            <Disponibilidad usuario={usuario} />
          )}

          {currentView === 'mis-reservas' && (
            <MisReservas usuario={usuario} />
          )}

          {currentView === 'profesor' && usuario.rol === 'PROFESOR' && (
            <PanelProfesor usuario={usuario} />
          )}
        </div>

        {/* Footer integrado */}
        <footer className="mt-16 pt-8 border-t border-slate-800">
          <div className="text-center text-slate-500 text-sm">
            <p className="font-semibold">© 2025 LabTurnos - Sistema de Gestión de Laboratorios</p>
            <p className="mt-1">Universidad Cooperativa de Colombia</p>
          </div>
        </footer>
      </main>
    </div>
  );
}

export default App;