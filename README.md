Link al repositorio: https://github.com/Oyupa/Examen2_PDE.git
Trabajo realizado por Adrián Puyo Olías
Link al video: https://youtu.be/1mUVSoXq23w

# Examen 2 - Programación Dirigida por Eventos
## Tarea 1 - Horarios

### Descripción
Esta aplicación Android maneja horarios y farmacias. A continuación se describen los componentes principales:

### Componentes

- **HorarioActivity**: Es una actividad que maneja la visualización y gestión de horarios en la aplicación.

- **DailyScheduleActivity**: Es una actividad que muestra el horario de un día específico. Obtiene la fecha seleccionada de un `Intent`, consulta la base de datos de Firestore para obtener los horarios correspondientes a esa fecha, y los muestra en un `RecyclerView`.

- **ScheduleAdapter**: Es un adaptador para un `RecyclerView` que maneja la visualización de los elementos del horario. Toma una lista de `ScheduleItem` y los vincula a las vistas en el `RecyclerView`.

- **ScheduleItem**: Es una clase de datos que representa un elemento del horario, con propiedades como `asignatura` (nombre de la asignatura) y `hora` (hora de la asignatura).

## Tarea 2 - Eventos

### Descripción

Esta aplicación Android maneja eventos y lugares. A continuación se describen los componentes principales:

### Componentes

- **Evento**: Es una clase de datos que representa un evento, con propiedades como `nombre`, `fecha`, `ubicacion`, etc.

- **EventoAdapter**: Es un adaptador para un `RecyclerView` que maneja la visualización de los elementos de eventos. Toma una lista de `Evento` y los vincula a las vistas en el `RecyclerView`.

- **EventoActivity**: Es una actividad que muestra una lista de eventos. Consulta la base de datos de Firestore para obtener los eventos y los muestra en un `RecyclerView` utilizando `EventoAdapter`.

## Tarea 3 - Farmacias

### Descripción

Esta aplicación Android muestre las farmacias. A continuación se describen los componentes principales:

### Componentes
- **FarmaciasActivity**: Es una actividad que muestra una lista de farmacias. Consulta la base de datos de Firestore para obtener las farmacias y las muestra en un `RecyclerView` utilizando `FarmaciasAdapter`.

- **FarmaciasAdapter**: Es un adaptador para un `RecyclerView` que maneja la visualización de los elementos de farmacias. Toma una lista de `Farmacia` y los vincula a las vistas en el `RecyclerView`.

- **Farmacia**: Es una clase de datos que representa una farmacia, con propiedades como `title`, `description`, `link`, `coordinates`, etc.
 
- **FarmaciaDetalleActivity**: Es una actividad que muestra los detalles de una farmacia seleccionada. Obtiene los datos de un `Intent` y los muestra en la interfaz de usuario.

- **fun loadFarmaciasToFirestore()**: Es una función que carga las farmacias del fichero `res/raw/farmacias_equipamiento.json` en el Firestore. Se ejecuta una sola vez para cargar los datos en Firestore.

