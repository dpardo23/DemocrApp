# DemocrApp
# Proyecto de Sistemas de Información II

Bienvenido a la documentación del proyecto de **Sistemas de Información II**. Este README contiene todo lo necesario para instalar, configurar y utilizar el proyecto.

---
![image](https://github.com/user-attachments/assets/26fde24a-6562-4099-b887-602c5e764b60)

## **Requisitos Previos**
Asegúrate de contar con los siguientes componentes antes de comenzar:

### **1. Base de Datos**
- **SQL Server Management Studio (SSMS):**
  - **Versión:** 20.2  
  - **Compilación:** 20.2.30.0  
  - [Descarga desde el sitio oficial de Microsoft](https://www.microsoft.com/en-us/sql-server/sql-server-downloads).
- **Infraestructura en la nube (opcional):**
  - **Microsoft Azure SQL Database**
    - Tener una cuenta Microsoft activa.
    - Configurar una instancia de base de datos SQL en la nube desde [Azure](https://azure.microsoft.com).

### **2. Herramientas de Diagramación**
- [DBDiagram.io](https://dbdiagram.io): Diseñar y documentar la estructura de la base de datos.  
- [Visual Paradigm](https://www.visual-paradigm.com): Crear diagramas complementarios del sistema.

### **3. Control de Versiones**
- **Git:**
  - **Versión:** 2.49.0  
  - [Descarga desde el sitio oficial de Git](https://git-scm.com/downloads).  
- **GitHub:**
  - Crear una cuenta en [GitHub](https://github.com).  
  - Configuración:
    - Generar un token de acceso personal y usarlo como contraseña al autenticarse desde NetBeans.  
    - Configurar el acceso remoto con SSH siguiendo [la documentación oficial de GitHub](https://docs.github.com/en/authentication/connecting-to-github-with-ssh).

### **4. Lenguaje de Programación**
- **Java:**
  - Java Development Kit (JDK):
    - **Versión:** 24  
    - [Descarga desde el sitio oficial de Oracle](https://www.oracle.com/java/technologies/javase-downloads.html).

### **5. Entorno de Desarrollo Integrado (IDE)**
- **Apache NetBeans IDE:**
  - **Versión:** 25  
  - [Descarga desde el sitio oficial de Apache NetBeans](https://netbeans.apache.org/).  
  - Requisitos previos:
    - Tener instalado JDK 24 previamente.  
  - Configuración:
    - Integrar repositorios de GitHub directamente desde el IDE.

### **6. Librerías**
- **Microsoft JDBC Driver:**
  - **Versión:** 12.8.1  
  - Compatible con Java 8, 11, 17, 21 y 22.  
- **JUnit:**
  - **Versión:** 5.12.1  
  - Compatible con Java 8 y superior.

### **7. Estándares de Documentación**
- **Javadoc:** Para documentar el código Java explicando clases, métodos y atributos.
- **IEEE 830-1998:** Para estructurar la especificación completa del proyecto.
- **Markdown:** Para archivos README.md y documentación legible.

### **8. Estándares de Programación**
- **Java Code Conventions.**

---

## **Instalación**

### **Base de Datos**
1. Descarga SQL Server Management Studio (SSMS) desde [aquí](https://www.microsoft.com/en-us/sql-server/sql-server-downloads).
2. Crea un usuario con el nombre "Ciudadano" y una contraseña para ese usuario "UMSS2025".
3. Habilitar la autenticación `SQL Server and Windows Authentication mode`.
4. Habilitar Protocols for SQLEXPRESS > TCP/IP
5. Habilitar el puerto 1433 en las configuraciones de red desde SQL Server Configuration Manager.
6. Asigna la base de datos al usuario Ciudadano.
7. Importa los scripts SQL de inicialización del proyecto disponibles en el repositorio (carpeta `database-scripts`).

### **Entorno de Desarrollo**
1. Instala el JDK desde [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html).
2. Descarga e instala Apache NetBeans IDE desde [NetBeans](https://netbeans.apache.org/).
3. Configura el IDE:
   - Enlaza tu cuenta de GitHub para control de versiones.
   - Asegúrate de instalar las dependencias necesarias como JDBC y JUnit.

### **Configuración de Versionado**
1. Instala Git desde [Git](https://git-scm.com/downloads).
2. Clona este repositorio:
   ```bash
   git clone <repositorio-url>
![image](https://github.com/user-attachments/assets/d0616421-9cc9-4ba7-b870-248dca789d29)
