# Guía de Pruebas con Postman

## Archivos Disponibles

1. **LAB-TURNOS-7ECs.postman_collection.json** - Colección con las 7 historias de usuario principales
2. **LAB-TURNOS-10HUs.postman_collection.json** - Colección completa con las 10 historias de usuario
3. **LAB-TURNOS.postman_environment.json** - Variables de entorno

## Importar en Postman

1. Abre Postman
2. Click en "Import" (esquina superior izquierda)
3. Arrastra los 3 archivos JSON o selecciónalos manualmente
4. Click en "Import"

## Configurar el Entorno

1. En Postman, selecciona el entorno "LAB-TURNOS Environment" en el dropdown superior derecho
2. Verifica que `base_url` esté configurada como `http://localhost:8080`

## Orden de Ejecución de Pruebas

### Flujo Básico (Estudiante)

1. **HU001 - Registro**: Registrar un estudiante
2. **HU002 - Login**: Iniciar sesión con el estudiante
3. **HU003 - Consultar Disponibilidad**: Ver laboratorios y disponibilidad
4. **HU004 - Crear Reserva**: Hacer una reserva (guarda el `reserva_id`)
5. **HU007 - Ver Mis Reservas**: Consultar tus reservas
6. **HU006 - Check-in**: Hacer check-in en la reserva
7. **HU006 - Check-out**: Hacer check-out
8. **HU005 - Cancelar Reserva**: Cancelar una reserva

### Flujo Profesor

1. **HU001 - Registro**: Registrar un profesor (rol: PROFESOR)
2. **HU002 - Login**: Iniciar sesión como profesor
3. **HU008 - Bloquear Equipos**: Crear un bloqueo
4. **HU009 - Listar Mis Bloqueos**: Ver bloqueos creados
5. **HU009 - Desbloquear**: Desactivar un bloqueo

## Endpoints Disponibles

### Usuarios
- `POST /api/usuarios/registro` - Registrar usuario
- `POST /api/usuarios/login` - Iniciar sesión
- `POST /api/usuarios/logout` - Cerrar sesión
- `GET /api/usuarios/session` - Verificar sesión activa

### Disponibilidad
- `GET /api/disponibilidad/laboratorios` - Listar laboratorios
- `POST /api/disponibilidad/consultar` - Consultar disponibilidad

### Reservas
- `POST /api/reservas` - Crear reserva
- `GET /api/reservas/mis-reservas` - Ver mis reservas
- `GET /api/reservas/{id}` - Ver reserva por ID
- `GET /api/reservas` - Ver todas (admin)
- `PUT /api/reservas/{id}/cancelar` - Cancelar reserva
- `PUT /api/reservas/{id}/checkin` - Check-in
- `PUT /api/reservas/{id}/checkout` - Check-out

### Bloqueos (Solo Profesores)
- `POST /api/bloqueos` - Crear bloqueo
- `PUT /api/bloqueos/{id}/desbloquear` - Desbloquear
- `GET /api/bloqueos/mis-bloqueos` - Mis bloqueos
- `GET /api/bloqueos/activos` - Bloqueos activos

## Datos de Ejemplo

### Registro Estudiante
```json
{
    "nombre": "Juan Pérez",
    "correo": "juan.perez@estudiante.edu",
    "password": "password123",
    "rol": "ESTUDIANTE"
}
```

### Registro Profesor
```json
{
    "nombre": "Dr. Carlos Gómez",
    "correo": "carlos.gomez@profesor.edu",
    "password": "profesor123",
    "rol": "PROFESOR"
}
```

### Login
```json
{
    "correo": "juan.perez@estudiante.edu",
    "password": "password123"
}
```

### Consultar Disponibilidad
```json
{
    "laboratorioId": "LAB-A",
    "fecha": "2025-11-25",
    "franja": "FRANJA_07_09"
}
```

Franjas disponibles (2 horas cada una):
- `FRANJA_07_09` (7:00 - 9:00)
- `FRANJA_09_11` (9:00 - 11:00)
- `FRANJA_11_13` (11:00 - 13:00)
- `FRANJA_13_15` (13:00 - 15:00)
- `FRANJA_15_17` (15:00 - 17:00)
- `FRANJA_17_19` (17:00 - 19:00)

### Crear Reserva
```json
{
    "laboratorioId": "LAB-A",
    "equipoCodigo": "PC-A-01",
    "fecha": "2025-11-25",
    "franja": "FRANJA_07_09"
}
```

### Crear Bloqueo (Profesor)
```json
{
    "laboratorioId": "LAB-A",
    "equipoCodigo": "PC-A-05",
    "fecha": "2025-11-26",
    "franja": "FRANJA_15_17",
    "motivo": "Mantenimiento programado"
}
```

## Notas Importantes

1. **Sesiones**: El backend usa sesiones HTTP. Postman mantiene las cookies automáticamente.
2. **Variables**: Algunas peticiones guardan IDs en variables de entorno (`reserva_id`, `bloqueo_id`)
3. **Autenticación**: Debes hacer login antes de crear reservas o bloqueos
4. **Roles**: Los bloqueos solo funcionan con usuarios PROFESOR
5. **Fechas**: Usa formato ISO `YYYY-MM-DD`

## Troubleshooting

- Si obtienes error 401: Haz login primero
- Si obtienes error 403: Verifica que tu rol sea correcto (PROFESOR para bloqueos)
- Si obtienes error 409: El equipo ya está reservado o bloqueado
- Si el servidor no responde: Verifica que esté corriendo en `http://localhost:8080`
