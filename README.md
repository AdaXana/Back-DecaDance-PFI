# 🪩 DecaDance - Backend API (MVP)

DecaDance es una aplicación web interactiva basada en un juego de cartas y líneas temporales musicales. Los jugadores deben adivinar el año de lanzamiento de diferentes canciones, retar a sus oponentes usando "púas" (vidas/intentos) y conseguir armar su línea temporal con 10 aciertos para ganar la partida.

Este repositorio contiene el **Backend (API RESTful)** desarrollado para gestionar la lógica de usuarios, la seguridad, la persistencia de las partidas y la integración de metadatos musicales.

🔗 **Repositorio del Frontend:** [Front-DecaDance-PFI](https://github.com/AdaXana/Front-DecaDance-PFI)

---

## 🛠️ Tecnologías Utilizadas

El proyecto ha sido construido siguiendo los principios de la Arquitectura Limpia (Clean Architecture), separación de responsabilidades (SRP) y código escalable (DRY).

* **Lenguaje:** Java 17+
* **Framework:** Spring Boot 3.x
* **Base de Datos:** PostgreSQL
* **ORM:** Spring Data JPA / Hibernate
* **Seguridad:** Spring Security con JSON Web Tokens (JWT)
* **Integraciones Externas:** API pública de Deezer (para carátulas y metadatos)
* **Procesamiento de Datos:** OpenCSV

---

## ⚙️ Características Principales (MVP)

1.  **Gestión de Usuarios y Seguridad:** Registro, login, encriptación de contraseñas (BCrypt) y autorización mediante tokens JWT.
2.  **Core Loop del Juego:**
    * Creación de salas de juego (Estado: `WAITING`).
    * Sistema de unión de jugadores (Soporta usuarios registrados e invitados).
    * Gestión de estados de la partida (`IN_PROGRESS`, `FINISHED`).
    * Registro de puntuaciones, gestión de "púas" (intentos) y declaración de ganadores.
3.  **Mazo de Cartas Automatizado:** Endpoints dedicados para proveer al Frontend de canciones aleatorias activas.
4.  **Carga Inicial de Datos (Data Seeder):** Poblado automático de la base de datos al arrancar mediante un archivo CSV y consumo de la API de Deezer.

---

## 🚀 Requisitos y Configuración Local

Para levantar este proyecto en tu entorno local, necesitarás tener instalado:
* Java Development Kit (JDK) 17 o superior.
* PostgreSQL (servidor corriendo localmente o en contenedor).
* Maven.

### Pasos de Instalación:

1. **Clona el repositorio:**
   ```bash
   git clone [https://github.com/AdaXana/Back-DecaDance-PFI.git]
   cd Back-DecaDance-PFI

2. **Configura la Base de Datos:**
   Crea una base de datos en PostgreSQL llamada `decadance_db` (o el nombre que prefieras). Luego, actualiza el archivo `src/main/resources/application.properties` con tus credenciales:
   
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/decadance_db
   spring.datasource.username=TU_USUARIO_POSTGRES
   spring.datasource.password=TU_CONTRASEÑA
   ```

3. **Inicia la aplicación:**
   Puedes arrancar el servidor desde tu IDE o usando la terminal:
   
   ```bash
   mvn spring-boot:run
   ```

---

## 🎵 Carga Inicial de Datos (Data Seeding)

El sistema incluye un `DataSeeder` automatizado que nutre la base de datos con el mazo inicial de canciones utilizando un archivo CSV y la API de Deezer.

**¿Cómo funciona?**
1. Asegúrate de tener tu archivo base (ej. `pills.csv`) en la ruta `src/main/resources/data/`.
2. Al arrancar la aplicación, el `DataSeeder` comprobará si la tabla de canciones está vacía.
3. Si está vacía, leerá el CSV, hará una doble llamada a la API de Deezer (Search + Album Detail) para obtener las URLs de las portadas y previsualizaciones de audio, y guardará todo en la base de datos.
4. Si la base de datos ya contiene información, el Seeder saltará este proceso automáticamente para garantizar un arranque rápido y evitar duplicados.

---

## 📡 Endpoints Principales (Cheat Sheet)

La API expone las siguientes rutas principales para el consumo desde el Frontend:

**Autenticación y Usuarios:**
* `POST /api/v1/auth/register` - Registro de usuario.
* `POST /api/v1/auth/login` - Inicio de sesión (devuelve JWT).
* `GET /api/v1/users` - Gestión de perfiles.

**Partidas (Game Core):**
* `POST /api/v1/games` - El Host crea una nueva sala.
* `POST /api/v1/games/join` - Un usuario (registrado o invitado) se une a la sala.
* `PATCH /api/v1/games/start/{idGame}` - Cierra la sala y arranca la partida.
* `PATCH /api/v1/games/{idGame}/score` - Registra un acierto o resta una púa a un jugador.
* `PATCH /api/v1/games/{idGame}/end` - Finaliza la partida y guarda al ganador.

**Canciones (Mazo):**
* `GET /api/v1/songs/active` - Devuelve el listado de canciones jugables para el Front.

---

## 🗺️ Próximas Implementaciones (Roadmap)

### Sistema Avanzado de Importación de Playlists
Actualmente, el backend cuenta con un `DataSeeder` que carga un mazo base en el primer arranque. Para escalar esta funcionalidad y permitir que la comunidad suba sus propias listas de reproducción en el futuro, se implementará:
1. **Historial de Migraciones de Datos:** Creación de una tabla (ej. `imported_files`) que registre cada CSV procesado.
2. **Importación Incremental:** El sistema escaneará el directorio de datos y solo procesará los archivos nuevos, evitando la duplicación de canciones.
3. **Integración de Herramientas Estándar:** Transición hacia herramientas como **Flyway** o **Liquibase** para un control de versiones de base de datos de grado de producción.
