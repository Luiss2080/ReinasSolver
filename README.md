# 👑 Solucionador de N-Reinas

🎯 Aplicación interactiva del clásico problema de N-Reinas desarrollada en Java con Swing 💻, implementando algoritmos heurísticos avanzados (Backtracking y Min-Conflicts) 🧠🔄 con interfaz gráfica intuitiva 🎮, efectos visuales y animaciones 🎨, optimización automática según tamaño del tablero ⚡ y estadísticas en tiempo real 📊. Solución eficiente y educativa 🚀.

> Una aplicación interactiva que resuelve el clásico problema de las N-Reinas utilizando algoritmos heurísticos avanzados.

## 🎯 ¿Qué es el problema de N-Reinas?

El problema de las N-Reinas consiste en colocar N reinas en un tablero de ajedrez de N×N casillas de tal manera que ninguna reina pueda atacar a otra. Esto significa que no puede haber dos reinas en la misma fila, columna o diagonal.

## ✨ Características

- 🎮 **Interfaz gráfica intuitiva** con Swing
- 🧠 **Algoritmos inteligentes**: Backtracking y Min-Conflicts
- ⚡ **Resolución automática** rápida y eficiente  
- 📊 **Estadísticas en tiempo real** del proceso
- 🎨 **Efectos visuales** y animaciones de éxito
- 🔧 **Optimización automática** según el tamaño del tablero

## 🚀 Empezar Rápidamente

### Requisitos
- ☕ Java 11 o superior
- 💻 Windows, Linux, o macOS

### 📥 Instalación y Ejecución

#### Con NetBeans
1. Abrir NetBeans IDE
2. **File → Open Project**
3. Seleccionar la carpeta `N-Reinas`
4. Click derecho → **Run**

#### Con Eclipse  
1. Abrir Eclipse IDE
2. **File → Import → General → Existing Projects into Workspace**
3. Seleccionar la carpeta `N-Reinas`
4. Click derecho → **Run As → Java Application**
5. Seleccionar `main.Main`

#### Desde línea de comandos
```bash
cd N-Reinas/src
javac main/*.java logica/*.java presentacion/*.java
java main.Main
```

## 🎮 Cómo usar la aplicación

1. **🎯 Resolver**: Click en "Resolver" para que el algoritmo encuentre una solución automáticamente
2. **🔄 Reiniciar**: Limpia el tablero para empezar de nuevo  
3. **❌ Salir**: Cierra la aplicación

## 🏗️ Arquitectura del proyecto

```
📁 N-Reinas/
├── 📁 src/
│   ├── 📁 main/
│   │   └── 📄 Main.java              # Punto de entrada
│   ├── 📁 logica/
│   │   ├── 📄 Tablero.java           # Lógica del tablero
│   │   └── 📄 HeuristicaIA.java      # Algoritmos de resolución
│   └── 📁 presentacion/
│       ├── 📄 VentanaPrincipal.java  # Ventana principal
│       ├── 📄 PanelTablero.java      # Visualización del tablero
│       ├── 📄 PanelControles.java    # Controles de usuario
│       └── 📄 EfectosVisuales.java   # Animaciones y efectos
└── 📄 README.md
```

## 🧮 Algoritmos implementados

### 🔄 Backtracking Optimizado
- **Uso**: Ideal para tableros pequeños (≤8×8)
- **Funcionamiento**: Explora sistemáticamente las posibilidades, retrocediendo cuando encuentra conflictos
- **Garantía**: Siempre encuentra una solución si existe

### ⚡ Min-Conflicts Heurístico  
- **Uso**: Eficiente para tableros grandes (>8×8)
- **Funcionamiento**: Comienza con una configuración aleatoria y reduce conflictos iterativamente
- **Ventaja**: Muy rápido en la mayoría de casos

### 🎯 Selección Inteligente
La aplicación automáticamente elige el algoritmo más apropiado según el tamaño del tablero para garantizar el mejor rendimiento.

## 🎨 Capturas de pantalla

*La aplicación presenta una interfaz moderna con:*
- Tablero de ajedrez visual con casillas alternadas
- Reinas representadas con símbolos elegantes  
- Información en tiempo real del progreso
- Animaciones de celebración al encontrar solución

## 🤝 Contribuir

¡Las contribuciones son bienvenidas! Si tienes ideas para mejorar:

1. Fork del proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📝 Licencia

Este proyecto es de uso educativo y está disponible bajo licencia libre para aprendizaje y modificación.

## 👨‍💻 Equipo de desarrollo

**Proyecto N-Reinas** - *Versión 2.0*

---

⭐ ¡No olvides darle una estrella al proyecto si te resultó útil!