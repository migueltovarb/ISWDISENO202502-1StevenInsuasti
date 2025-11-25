module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      colors: {
        'lab-blue': '#3b82f6',
        'lab-dark': '#1e3a8a',
        'lab-green': '#10b981',
        'lab-purple': '#8b5cf6',
        'lab-orange': '#f97316',
        'lab-cyan': '#06b6d4',
      },
      backgroundImage: {
        'gradient-primary': 'linear-gradient(135deg, #06b6d4 0%, #3b82f6 100%)',
        'gradient-success': 'linear-gradient(135deg, #10b981 0%, #059669 100%)',
        'gradient-warning': 'linear-gradient(135deg, #f59e0b 0%, #d97706 100%)',
        'gradient-danger': 'linear-gradient(135deg, #ef4444 0%, #dc2626 100%)',
        'gradient-info': 'linear-gradient(135deg, #06b6d4 0%, #0891b2 100%)',
      },
      boxShadow: {
        'soft': '0 2px 15px -3px rgba(0, 0, 0, 0.07), 0 10px 20px -2px rgba(0, 0, 0, 0.04)',
        'glow': '0 0 20px rgba(6, 182, 212, 0.4)',
        'neon': '0 0 20px rgba(6, 182, 212, 0.3)',
        'neon-lg': '0 0 30px rgba(6, 182, 212, 0.5)',
        'emerald': '0 0 20px rgba(16, 185, 129, 0.3)',
        'rose': '0 0 20px rgba(244, 63, 94, 0.3)',
        'amber': '0 0 20px rgba(245, 158, 11, 0.3)',
      },
    },
  },
  plugins: [],
  safelist: [
    {
      pattern: /(bg|text|border)-(cyan|emerald|rose|amber|blue|slate|gray)-(400|500)/,
    },
  ],
}