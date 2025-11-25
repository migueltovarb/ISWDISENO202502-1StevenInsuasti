import React from 'react';

const Sidebar = ({ currentView, setCurrentView, usuario, onLogout }) => {
  const menuItems = usuario.rol === 'ESTUDIANTE' 
    ? [
        { id: 'disponibilidad', icon: '🔍', label: 'Buscar Equipos', color: 'cyan' },
        { id: 'mis-reservas', icon: '📋', label: 'Mis Reservas', color: 'emerald' },
      ]
    : [
        { id: 'profesor', icon: '⚙️', label: 'Panel Control', color: 'amber' },
      ];

  return (
    <div className="fixed left-0 top-0 h-screen w-72 bg-slate-900 border-r border-slate-700 flex flex-col animate-slideInLeft">
      {/* Logo y Header */}
      <div className="p-6 border-b border-slate-700">
        <div className="flex items-center space-x-3 mb-4">
          <div className="w-12 h-12 bg-gradient-to-br from-cyan-500 to-blue-600 rounded-xl flex items-center justify-center text-2xl shadow-neon">
            💻
          </div>
          <div>
            <h1 className="text-xl font-bold text-white">LabTurnos</h1>
            <p className="text-xs text-slate-400">Sistema de Gestión</p>
          </div>
        </div>
      </div>

      {/* User Info */}
      <div className="p-6 border-b border-slate-700">
        <div className="flex items-center space-x-3">
          <div className="w-10 h-10 bg-gradient-to-br from-purple-500 to-pink-500 rounded-full flex items-center justify-center text-lg">
            👤
          </div>
          <div className="flex-1 min-w-0">
            <p className="text-sm font-semibold text-white truncate">{usuario.nombre}</p>
            <span className={`inline-block px-2 py-0.5 text-xs font-bold rounded ${
              usuario.rol === 'PROFESOR' 
                ? 'bg-amber-500/20 text-amber-400 border border-amber-500/50' 
                : 'bg-cyan-500/20 text-cyan-400 border border-cyan-500/50'
            }`}>
              {usuario.rol}
            </span>
          </div>
        </div>
      </div>

      {/* Menu Items */}
      <nav className="flex-1 p-4 space-y-2">
        {menuItems.map((item) => (
          <button
            key={item.id}
            onClick={() => setCurrentView(item.id)}
            className={`w-full flex items-center space-x-3 px-4 py-3 rounded-lg font-semibold transition-all duration-200 ${
              currentView === item.id
                ? `bg-${item.color}-500/20 text-${item.color}-400 border-2 border-${item.color}-500/50 shadow-${item.color}`
                : 'text-slate-400 hover:bg-slate-800 hover:text-white'
            }`}
          >
            <span className="text-2xl">{item.icon}</span>
            <span>{item.label}</span>
          </button>
        ))}
      </nav>

      {/* Logout Button */}
      <div className="p-4 border-t border-slate-700">
        <button
          onClick={onLogout}
          className="w-full flex items-center justify-center space-x-2 px-4 py-3 bg-rose-500/10 hover:bg-rose-500/20 text-rose-400 rounded-lg font-semibold transition-all duration-200 border border-rose-500/30"
        >
          <span className="text-xl">🚪</span>
          <span>Cerrar Sesión</span>
        </button>
      </div>
    </div>
  );
};

export default Sidebar;
