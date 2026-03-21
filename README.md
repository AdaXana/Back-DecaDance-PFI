## 🎵 Carga Inicial de Datos (Seed)
El sistema incluye un `MusicImportService` que conecta con la API de Deezer para obtener metadatos oficiales (año, género, portadas).

**Para realizar una carga masiva:**
1. Coloca tu archivo `pills.csv` en `src/main/resources/data/`.
2. En `BackDecaDancePfiApplication.java`, activa temporalmente el `CommandLineRunner`.
3. Ejecuta la aplicación. El servicio procesará el CSV y enriquecerá los datos mediante una doble llamada a la API (Search + Album Detail).
4. Una vez finalizado, se recomienda exportar la tabla a un archivo `data.sql` para persistencia offline.