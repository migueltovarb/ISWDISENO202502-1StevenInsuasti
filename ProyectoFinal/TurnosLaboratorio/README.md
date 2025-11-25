# 🎓 Sistema de Gestión de Turnos - Laboratorio

Sistema completo de gestión de turnos para laboratorios de computación, desarrollado con Spring Boot y React.

## ✅ SISTEMA FUNCIONANDO

### 🌐 Acceso Rápido
- **Frontend**: http://localhost:3000
- **Backend**: http://localhost:8080

### 📊 Estado Actual
- ✅ Backend corriendo (Puerto 8080)
- ✅ Frontend corriendo (Puerto 3000)
- ✅ Base de datos MongoDB Atlas conectada
- ✅ 2 Laboratorios y 18 Equipos disponibles

---

## 🚀 Inicio Rápido

### 1. Accede al Sistema
Abre tu navegador en:
```
http://localhost:3000
```

### 2. Regístrate
- Click en "Registrarse"
- Completa el formulario
- Selecciona rol: ESTUDIANTE o PROFESOR

### 3. Usa el Sistema
- **Estudiantes**: Consulta disponibilidad, crea reservas, haz check-in/out
- **Profesores**: Bloquea/desbloquea equipos para mantenimiento

---

## 📚 Documentación

| Documento | Descripción |
|-----------|-------------|
| [README-INICIO-RAPIDO.md](README-INICIO-RAPIDO.md) | Guía rápida de uso |
| [RESUMEN-EJECUTIVO.md](RESUMEN-EJECUTIVO.md) | Resumen completo del sistema |
| [SISTEMA-COMPLETO-FUNCIONANDO.md](SISTEMA-COMPLETO-FUNCIONANDO.md) | Documentación técnica completa |
| [INSTRUCCIONES-POSTMAN.md](INSTRUCCIONES-POSTMAN.md) | Guía de pruebas con Postman |
| [COMO-REINICIAR.md](COMO-REINICIAR.md) | Cómo reiniciar el sistema |

---

## 🎯 Funcionalidades

### Para Estudiantes
- ✅ Registro y autenticación
- ✅ Consultar disponibilidad de equipos
- ✅ Crear y gestionar reservas
- ✅ Check-in y check-out
- ✅ Cancelar reservas

### Para Profesores
- ✅ Bloquear equipos para mantenimiento
- ✅ Desbloquear equipos
- ✅ Ver bloqueos activos

---

## 🛠️ Tecnologías

### Backend
- Java 17
- Spring Boot 3.2.0
- MongoDB Atlas
- Maven

### Frontend
- React 18.2.0
- Axios
- Tailwind CSS 3.4.0

---

## 📦 Estructura del Proyecto

```
LAB-TURNOS/
├── backend/              # API REST Spring Boot
│   ├── src/
│   └── pom.xml
├── frontend/             # Aplicación React
│   ├── src/
│   └── package.json
├── postman/              # Colecciones de prueba
└── documentacion/        # Documentación adicional
```

---

## 🔄 Reiniciar el Sistema

### Backend
```bash
cd backend
mvn spring-boot:run
```

### Frontend
```bash
cd frontend
npm start
```

Ver guía completa: [COMO-REINICIAR.md](COMO-REINICIAR.md)

---

## 🧪 Pruebas con Postman

1. Importa colecciones desde `postman/`
2. Selecciona entorno "LAB-TURNOS Environment"
3. Ejecuta peticiones de prueba

Ver guía: [INSTRUCCIONES-POSTMAN.md](INSTRUCCIONES-POSTMAN.md)

---

## 📊 Datos Disponibles

### Laboratorios
- **LAB-A**: 10 equipos (PC-A-01 a PC-A-10)
- **LAB-B**: 8 equipos (PC-B-01 a PC-B-08)

### Franjas Horarias
- **MANANA**: 7:00 - 12:00
- **TARDE**: 12:00 - 18:00
- **NOCHE**: 18:00 - 22:00

---

## 🎓 Historias de Usuario

| ID | Historia | Estado |
|----|----------|--------|
| HU001 | Registro de Usuario | ✅ |
| HU002 | Login | ✅ |
| HU003 | Consultar Disponibilidad | ✅ |
| HU004 | Crear Reserva | ✅ |
| HU005 | Cancelar Reserva | ✅ |
| HU006 | Check-in y Check-out | ✅ |
| HU007 | Ver Mis Reservas | ✅ |
| HU008 | Bloquear Equipos | ✅ |
| HU009 | Desbloquear Equipos | ✅ |
| HU010 | Listar Reservas | ✅ |

---

## 📞 Endpoints API

### Usuarios
- `POST /api/usuarios/registro` - Registrar
- `POST /api/usuarios/login` - Login
- `GET /api/usuarios/session` - Verificar sesión

### Disponibilidad
- `GET /api/disponibilidad/laboratorios` - Listar laboratorios
- `POST /api/disponibilidad/consultar` - Consultar disponibilidad

### Reservas
- `POST /api/reservas` - Crear reserva
- `GET /api/reservas/mis-reservas` - Mis reservas
- `PUT /api/reservas/{id}/checkin` - Check-in
- `PUT /api/reservas/{id}/checkout` - Check-out
- `PUT /api/reservas/{id}/cancelar` - Cancelar

### Bloqueos (Profesor)
- `POST /api/bloqueos` - Crear bloqueo
- `PUT /api/bloqueos/{id}/desbloquear` - Desbloquear
- `GET /api/bloqueos/mis-bloqueos` - Mis bloqueos

---

## 🐛 Troubleshooting

Ver guía completa: [COMO-REINICIAR.md](COMO-REINICIAR.md)

### Puerto en uso
```bash
# Ver proceso en puerto 8080
netstat -ano | findstr :8080

# Matar proceso
taskkill /F /PID [PID]
```

### Frontend no conecta
Verifica que el backend esté corriendo en puerto 8080

### No aparecen equipos
Usa `LAB-A` o `LAB-B` como laboratorioId

---

## ✨ ¡Listo para Usar!

El sistema está completamente funcional. Abre tu navegador en:

**http://localhost:3000**

---

## 📝 Licencia

Proyecto académico - Universidad

## 👥 Autores

Diseño de Software - Cuarto Semestre

---

**Fecha**: Noviembre 2025
**Versión**: 1.0.0
**Estado**: ✅ OPERATIVO
