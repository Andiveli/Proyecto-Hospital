# 🏥 Proyecto-Hospital

Sistema de gestión hospitalaria desarrollado en **Java**, como parte de un proyecto académico de **Programación Orientada a Objetos (POO)**.  
Simula un entorno hospitalario donde se pueden registrar médicos, pacientes, citas, terapias, cirugías y facturas, utilizando persistencia en archivos de texto.

---

## 🛠️ Tecnologías utilizadas

- **Java**
- **Programación Orientada a Objetos**
- **Maven**
- **Persistencia con archivos `.txt`**
- **Interfaces:** `Atendible`, `Pagable`

---


---

## 🎯 Funcionalidades principales

- Registro de **médicos** y **pacientes** y su respectivo listado por filtros
- Agendamiento de **citas** verificando que cada Médico esté disponible, exista y no hayan horarios repetidos
- Registro de **cirugías**, **terapias** y **medicaciones** para verificar los reportes generados por diferentes filtros
- Generación de **facturas** por servicios médicos
- **Persistencia de datos** en archivos `.txt`
- Uso de **interfaces** para definir comportamientos comunes (`Atendible`, `Pagable`)

---

## 🚀 Cómo ejecutar el proyecto

1. Clona el repositorio:
   ```bash
   git clone https://github.com/Andiveli/Proyecto-Hospital.git
   cd Proyecto-Hospital
   mvn clean install
   mvn exec:java -Dexec.mainClass="proyecto.Main"

---
## 🧪 Pruebas y validaciones

Actualmente no hay pruebas automatizadas (JUnit), pero puedes verificar manualmente que:
 - Los registros se guardan correctamente en los archivos .txt.
 - Las operaciones de carga y guardado funcionan sin errores.
 - Las clases implementan correctamente las interfaces definidas.
---

## 🧱 Estructura de clases (resumen funcional)

- `Main.java` — Punto de entrada del sistema. Coordina la carga de datos, inicializa `Hospital` y gestiona el flujo principal del programa validando cada petición con `Validaciones.java`.
- `Hospital.java` Se encarga de interaturar con todos los modelos. Es la clase encargada de toda la lógica del programa.
- `Paciente.java` — Representa a un paciente. Contiene atributos personales y métodos para validación y visualización.
- `Medico.java` — Define a un médico con especialidad, horario y validaciones de disponibilidad mediante `HorarioAtencion`. Implementa la interfaz `Atendible`.
- `Cita.java` — Gestiona la asignación de citas entre pacientes y médicos. 
- `Cirugia.java` — Registra procedimientos quirúrgicos asociados a pacientes y médicos.
- `Terapia.java` — Administra terapias asignadas a pacientes.
- `Medicacion.java` — Controla medicamentos prescritos, su dosis y frecuencia.
- `Factura.java` — Genera facturas por servicios médicos. Se usa también de apoyo para reconstruir la relación de datos entre Pacientes y Tratamientos usando `factura.txt`.
- `Interfaz Atendible` — Define el contrato para clases que ofrecen servicios médicos (como `Medico`).
- `Interfaz Pagable` — Define el contrato para clases que generan costos o facturación (`Medicacion`, `Cirugia` y `Terapia`) implementada por `Tratamiento.java`.
- `HorarioAtencion.java` Es el módelo para los horarios de cada Médico, esta se encarga de validar que el médico esté disponible y los otros métodos para limpiar el horario.

- Hay métodos *guardar*, y *cargarTodos* que se encargan de almacenar la información del modelo haciendo uso de *toString* o un método similar que devuelvan los datos del objeto como string, para guardarlos y reconstruirlos al inicializar el programa. 

