# 📦 Sistema de Vacaciones (Spring Boot + Vue 3 + PostgreSQL en Docker)

Este proyecto es un prototipo de **gestión de vacaciones** con dos perfiles:

- **Empleado**: crea solicitudes y ve sus solicitudes.
- **Talento Humano (TH)**: ve todas las solicitudes y las aprueba o rechaza.

Incluye:
- Backend en **Spring Boot** (API REST).
- Frontend en **Vue 3 + Vite** usando el diseño en Tailwind.
- Base de datos **PostgreSQL en Docker**.
- Usuarios de prueba y registro de nuevos usuarios.

---

## 📁 Estructura del proyecto

```text
/ (raíz)
├── backend/          # Proyecto Spring Boot (carpeta src, pom.xml, etc.)
├── frontend/         # Proyecto Vue 3 (vite, src, App.vue, etc.)
└── README.md
```

*(si lo tienes en una sola carpeta, ajusta las rutas en este README)*

---

## 1. ✅ Requerimientos

- Java 17 (o 21) instalado
- Maven (`mvn -v`)
- Node.js 18+ y npm / pnpm / yarn
- Docker instalado y corriendo
- DBeaver (opcional, para ver la BD)

---

## 2. 🐘 Levantar PostgreSQL con Docker

Ejecuta en la terminal:

```bash
docker rm -f pg-vacaciones 2>/dev/null || true

docker run --name pg-vacaciones   -e POSTGRES_PASSWORD=postgres   -e POSTGRES_DB=vacaciones   -p 5434:5432   -d postgres:15
```

- **Host**: `localhost`
- **Puerto**: `5434` (porque `5432` puede estar ocupado en la Mac)
- **Usuario**: `postgres`
- **Password**: `postgres`
- **BD**: `vacaciones`

Puedes comprobar que está arriba con:

```bash
docker ps
```

y conectarte con DBeaver a:

- host: `localhost`
- port: `5434`
- database: `vacaciones`
- user: `postgres`
- pass: `postgres`

---

## 3. 🧠 Configurar Spring Boot

En `backend/src/main/resources/application.properties` (o donde lo tengas) pon:

```properties
server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5434/vacaciones
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

> **Nota:** con `ddl-auto=update` las tablas se crean solitas a partir de las entidades.

---

## 4. 🧪 Datos de prueba (seed)

Hay una clase `DataLoader` (o similar) que al arrancar el backend crea usuarios de prueba si la tabla está vacía:

```text
usuario: empleado1 / 1234 → rol EMPLOYEE
usuario: th1       / 1234 → rol TH
```

Si ya tienes datos, no los vuelve a insertar.

---

## 5. 🚀 Levantar el backend

Desde la carpeta del backend:

```bash
mvn spring-boot:run
```

o desde tu IDE (IntelliJ / STS / VSCode) → botón Run.

Si todo sale bien verás en consola algo como:

```text
Tomcat started on port(s): 8080
```

Prueba el login con curl o Postman:

```bash
curl -X POST http://localhost:8080/auth/login   -H "Content-Type: application/json"   -d '{"username":"empleado1","password":"1234"}'
```

Debe responder un JSON con el usuario y el rol.

---

## 6. 🖥️ Frontend (Vue 3 + Vite)

Ve a la carpeta del frontend:

```bash
cd frontend
npm install
```

Asegúrate de que el archivo `vite.config.js` tenga el **proxy** para hablar con el backend:

```js
import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5173,
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
      "/auth": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
    },
  },
  resolve: {
    alias: {
      "@": "/src",
    },
  },
});
```

Luego levanta el frontend:

```bash
npm run dev
```

Te mostrará algo como:

```text
VITE v5.x  ready in ...
➜  Local:   http://localhost:5173/
```

Abre esa URL en el navegador.

---

## 7. 🔐 Flujo de autenticación

1. Entras a `http://localhost:5173/`
2. Te aparece la pantalla de **Iniciar sesión**
3. Puedes:
   - entrar con un usuario ya creado (`empleado1 / 1234`, `th1 / 1234`)
   - o crear una cuenta nueva con el botón **“Crear nueva”**
4. Si te registras:
   - se llama a `POST /auth/register`
   - el backend crea el usuario en **Postgres**
   - el frontend te deja logueado de una
5. Según tu rol:
   - **EMPLOYEE** → ve “Solicitud” y “Mis solicitudes”
   - **TH** → ve “Lista (Talento Humano)” y “Aprobación”

---

## 8. 📡 Endpoints principales (backend)

- `POST /auth/login` → login
- `POST /auth/register` → registro (empleado o TH)
- `POST /api/vacaciones/empleado` → crear solicitud
- `GET /api/vacaciones/empleado/{idEmpleado}` → listó mis solicitudes
- `GET /api/vacaciones/th` → lista global (solo TH)
- `PUT /api/vacaciones/th/{id}/decision` → aprobar / negar

*(los paths pueden variar si cambiaste el `@RequestMapping`, pero esta es la idea)*

---

## 9. 🐞 Problemas comunes

### ❌ 1. `Bind for 0.0.0.0:5432 failed: port is already allocated`
Tienes otro Postgres usando el 5432. Usa otro puerto:

```bash
docker run --name pg-vacaciones   -e POSTGRES_PASSWORD=postgres   -e POSTGRES_DB=vacaciones   -p 5434:5432 -d postgres:15
```

y cambia la URL en Spring a `jdbc:postgresql://localhost:5434/vacaciones`.

---

### ❌ 2. Desde Vue sale “usuario incorrecto”
- Verifica que el backend esté en `http://localhost:8080`
- Verifica que `vite.config.js` tenga `/auth` en el proxy
- Reinicia `npm run dev`
- Prueba el endpoint directo con Postman

---

### ❌ 3. No se crean las tablas en Postgres
- revisa que en `application.properties` tengas:
  ```properties
  spring.jpa.hibernate.ddl-auto=update
  ```
- revisa que te estás conectando al puerto correcto (5433, 5434…)
- revisa logs de Spring

---

## 10. 🗃️ Docker Compose (opcional)

Si prefieres no escribir el `docker run` cada vez, crea un archivo `docker-compose.yml` en la raíz:

```yaml
version: "3.9"
services:
  postgres:
    image: postgres:15
    container_name: pg-vacaciones
    environment:
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: vacaciones
      POSTGRES_USER: postgres
    ports:
      - "5434:5432"
    volumes:
      - pgdata_vacaciones:/var/lib/postgresql/data
    restart: unless-stopped

volumes:
  pgdata_vacaciones:
```

Y luego solo haces:

```bash
docker compose up -d
```

---

## 11. ✅ Usuarios de prueba

- **Empleado**
  - usuario: `empleado1`
  - password: `1234`
  - rol: `EMPLOYEE`

- **Talento Humano**
  - usuario: `th1`
  - password: `1234`
  - rol: `TH`

También puedes registrarte desde el frontend.

