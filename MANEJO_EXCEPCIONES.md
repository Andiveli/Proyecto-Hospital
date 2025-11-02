# 📚 GUÍA DE MANEJO DE EXCEPCIONES - SISTEMA HOSPITALARIO

## 🎯 OBJETIVO

Este documento explica el manejo de excepciones implementado en el sistema hospitalario, siguiendo las mejores prácticas de Java y proporcionando una experiencia de usuario robusta.

---

## 🔍 DIFERENCIAS FUNDAMENTALES

### ❌ ERROR vs ✅ EXCEPCIÓN

#### **ERROR**

- **Definición**: Problemas graves que normalmente no deberían ser capturados por la aplicación.
- **Características**:
  - Indican problemas graves a nivel de JVM
  - Generalmente irrecuperables
  - Ejemplos: `OutOfMemoryError`, `StackOverflowError`
- **En nuestro sistema**: No manejamos Errors directamente, dejamos que la JVM los gestione.

#### **EXCEPCIÓN**

- **Definición**: Condiciones anormales que un programa bien construido debe capturar y manejar.
- **Características**:
  - Recuperables
  - Predecibles en el flujo del programa
  - Pueden ser manejadas por el programa
- **En nuestro sistema**: Implementamos un manejo completo de excepciones.

---

## 🏷️ TIPOS DE EXCEPCIONES

### ✅ CHECKED (Verificadas)

- **Definición**: Excepciones que el compilador obliga a manejar.
- **Características**:
  - Deben ser declaradas con `throws`
  - Deben ser capturadas con `try-catch`
  - Representan condiciones recuperables
- **Ejemplos en nuestro sistema**:

  ```java
  // Excepciones personalizadas CHECKED
  public class PacienteNoEncontradoException extends Exception
  public class MedicoNoEncontradoException extends Exception
  public class CitaNoDisponibleException extends Exception
  public class ArchivoException extends Exception
  ```

### ⚡ UNCHECKED (No Verificadas)

- **Definición**: Excepciones que no requieren manejo obligatorio.
- **Características**:
  - Heredan de `RuntimeException`
  - No requieren `throws` ni `try-catch` obligatorio
  - Generalmente indican errores de programación
- **Ejemplos en nuestro sistema**:

  ```java
  // Excepción personalizada UNCHECKED
  public class DatoInvalidoException extends RuntimeException
  ```

---

## 🏥 EXCEPCIONES DEL SISTEMA vs PERSONALIZADAS

### 📦 EXCEPCIONES DEL SISTEMA (Java)

- **Definición**: Excepciones proporcionadas por el JDK.
- **Ejemplos utilizados**:

  ```java
  IOException          // Problemas de entrada/salida
  NumberFormatException // Error en conversión de números
  IllegalArgumentException // Argumento inválido
  NullPointerException  // Referencia nula
  ```

### 🎯 EXCEPCIONES PERSONALIZADAS

- **Definición**: Excepciones creadas específicamente para nuestro dominio.
- **Ventajas**:
  - Código más legible y específico
  - Manejo más preciso de errores
  - Mejor experiencia de usuario

#### **Nuestras Excepciones Personalizadas**

1. **PacienteNoEncontradoException** (CHECKED)

   ```java
   // Uso: Cuando se busca un paciente que no existe
   throw new PacienteNoEncontradoException("Paciente con correo " + correo + " no encontrado.");
   ```

2. **MedicoNoEncontradoException** (CHECKED)

   ```java
   // Uso: Cuando se busca un médico que no existe
   throw new MedicoNoEncontradoException("Médico con correo " + correo + " no encontrado.");
   ```

3. **CitaNoDisponibleException** (CHECKED)

   ```java
   // Uso: Cuando no se puede crear una cita por disponibilidad
   throw new CitaNoDisponibleException("El médico no está disponible en el horario solicitado.");
   ```

4. **ArchivoException** (CHECKED)

   ```java
   // Uso: Problemas al leer/escribir archivos
   throw new ArchivoException("No se pudieron guardar los datos en el archivo", e);
   ```

5. **DatoInvalidoException** (UNCHECKED)

   ```java
   // Uso: Validación de datos de entrada
   throw new DatoInvalidoException("El formato del correo es inválido.");
   ```

---

## 🛠️ PATRONES DE MANEJO IMPLEMENTADOS

### 1. **VALIDACIÓN CON INTENTOS LIMITADOS**

```java
public static int validarEntero(String mensaje) throws DatoInvalidoException {
    int intentos = 0;
    final int MAX_INTENTOS = 3;

    while(intentos < MAX_INTENTOS) {
        try {
            // Lógica de validación
            return valorValidado;
        } catch (DatoInvalidoException e) {
            intentos++;
            if (intentos < MAX_INTENTOS) {
                // Dar oportunidad de corregir
            }
        }
    }
    throw new DatoInvalidoException("Máximo de intentos alcanzado");
}
```

### 2. **ENCADENAMIENTO DE EXCEPCIONES**

```java
try {
    // Operación que puede fallar
} catch (IOException e) {
    throw new ArchivoException("Error específico del dominio", e);
}
```

### 3. **MANEJO EN CAPAS**

```java
// Capa de presentación (Main.java)
try {
    hospital.crearCitaMedica(datos);
} catch (CitaNoDisponibleException e) {
    DialogoError.manejarExcepcion(e);
}

// Capa de negocio (Hospital.java)
public boolean crearCitaMedica(...) throws CitaNoDisponibleException {
    // Lógica de negocio
    if (!disponible) {
        throw new CitaNoDisponibleException("Médico no disponible");
    }
}
```

---

## 🖥️ SISTEMA DE MENSAJES EN CONSOLA

### **Manejo de Excepciones en Consola**
El sistema muestra mensajes claros y específicos directamente en la consola:

```java
// Ejemplo de manejo en Main.java
catch (PacienteNoEncontradoException e) {
    System.out.println("ERROR: " + e.getMessage());
    System.out.println("Solución: Verifique el correo del paciente o regístrelo si es nuevo.");
}
```

### **Características**:
- ✅ Mensajes claros y específicos en consola
- ✅ Soluciones concretas para el usuario
- ✅ Diferenciación por tipo de error
- ✅ Formato consistente y fácil de leer

---

## 📋 BUENAS PRÁCTICAS IMPLEMENTADAS

### ✅ **MANEJO ADECUADO**

1. **try-catch-finally** donde corresponde
2. **throws** en métodos que propagan excepciones
3. **Mensajes claros** al usuario
4. **Programa no termina abruptamente**

### ✅ **DISEÑO DE EXCEPCIONES**

1. **Nombres descriptivos** del dominio
2. **Jerarquía lógica** (checked vs unchecked)
3. **Información contextual** en los mensajes
4. **Encadenamiento** para preservar causa raíz

### ✅ **EXPERIENCIA DE USUARIO**
1. **Mensajes en consola** claros e informativos
2. **Opciones de recuperación** claras
3. **Intentos limitados** para evitar bucles
4. **Cancelación** permitida en cualquier momento

---

## 🔄 FLUJO DE MANEJO DE EXCEPCIONES

```
Entrada de Usuario
       ↓
Validación (Validaciones.java)
       ↓
¿Excepción DatoInvalidoException?
       ↓ SÍ
Mostrar Error en Consola → Reintentar o Cancelar
       ↓ NO
Lógica de Negocio (Hospital.java)
       ↓
¿Excepción Personalizada?
       ↓ SÍ
Mensaje Específico en Consola
       ↓ NO
¿Excepción de Sistema?
       ↓ SÍ
Mensaje Genérico en Consola
       ↓ NO
Operación Exitosa
```

---

## 🎯 CRITERIOS MÍNIMOS CUMPLIDOS

### ✅ **REQUISITOS OBLIGATORIOS**

1. ✅ **1+ excepción personalizada**: 5 implementadas
2. ✅ **Diferencias claras**: Documentación completa
3. ✅ **Checked vs Unchecked**: Ambos tipos implementados
4. ✅ **Sistema vs Personalizadas**: Ambos tipos utilizados

### ✅ **MANEJO ADECUADO**

1. ✅ **try-catch-finally**: Implementado correctamente
2. ✅ **throws**: Usado donde corresponde
3. ✅ **Mensajes claros**: Con soluciones específicas
4. ✅ **Sin terminación abrupta**: Programa siempre controlado

### ✅ **PUNTOS CRÍTICOS IDENTIFICADOS**

1. ✅ **Entrada de datos**: Validaciones robustas
2. ✅ **Operaciones de archivo**: ArchivoException
3. ✅ **Lógica de negocio**: Excepciones específicas
4. ✅ **NullPointerException**: Manejo preventivo

---

## 🚀 CONCLUSIONES

Este sistema implementa un manejo de excepciones **profesional y completo** que:

- 🎯 **Protege la integridad** de los datos
- 👥 **Mejora la experiencia** del usuario en consola  
- 🔧 **Facilita el mantenimiento** del código
- 📈 **Aumenta la robustez** del sistema
- 💻 **Proporciona feedback** claro y útil en consola

El manejo de excepciones ya no es un "mal necesario", sino una **característica fundamental** que hace que el sistema sea confiable y fácil de usar.

---

_📝 Documentación creada por: Sistema de Gestión Hospitalaria_  
_📅 Fecha: Diciembre 2025_  
_🏥 Versión: 1.0 con Manejo Profesional de Excepciones_

