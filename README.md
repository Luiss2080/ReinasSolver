# 👑 ReinasSolver

> Resuelve el problema de las N-Reinas para cualquier tamaño de tablero
> que elijas (1 a 100), combinando dos algoritmos clásicos de IA —
> Backtracking exhaustivo para tableros pequeños y Min-Conflicts con
> reinicio aleatorio para tableros grandes — con una interfaz de
> escritorio en Java Swing. Pensado para quien quiera ver, de verdad
> funcionando, la diferencia entre búsqueda exhaustiva y búsqueda
> heurística en el mismo problema.

## Características

- **Backtracking exhaustivo** (N ≤ 8): coloca una reina por columna
  comprobando fila y ambas diagonales contra las reinas ya colocadas;
  siempre encuentra una solución si existe, y reporta correctamente que
  no existe solución para N=2 y N=3 (los dos únicos casos sin solución).
- **Min-Conflicts con reinicio aleatorio** (N > 8): parte de una
  colocación aleatoria y mueve iterativamente la reina más conflictiva a
  su posición de menor conflicto; si una ejecución queda atascada en una
  meseta, se reinicia desde una nueva colocación aleatoria (hasta 30
  veces) en lugar de recurrir a un backtracking exhaustivo que nunca
  terminaría en un tablero grande.
- **Selector de tamaño real** (N = 1 a 100): un control en la parte
  superior de la ventana permite elegir el tamaño del tablero y ver
  ambos algoritmos en acción, incluyendo los casos límite N=1, N=2 y
  N=3.
- **Estadísticas en tiempo real**: número de reinas colocadas,
  conflictos totales y validez de la solución actual.
- **Interfaz sin bloqueos**: la resolución corre en un hilo de fondo
  (`SwingWorker`) mientras la ventana permanece responsiva.
- **Efectos visuales**: animaciones de celebración al encontrar
  solución, resaltado de reinas en conflicto y temas de color modernos.

## Cómo usar

1. Elige el tamaño del tablero (N) con el control numérico de la parte
   superior.
2. Pulsa **Resolver** para que la IA encuentre una solución: usa
   Backtracking si N ≤ 8, o Min-Conflicts si N > 8.
3. Pulsa **Reiniciar** para limpiar el tablero, o F1 para ver la ayuda
   en pantalla.

## Instalación y uso local

Requiere JDK 17 o superior.

**Con Maven** (recomendado — compila, corre las pruebas y empaqueta):

```bash
mvn package
java -jar target/reinassolver-1.0.0.jar
```

**Sin Maven, con javac directamente:**

```bash
javac -d bin src/logica/*.java src/main/*.java src/presentacion/*.java
java -cp bin main.Main
```

También puede importarse como proyecto existente en NetBeans, Eclipse o
IntelliJ IDEA (incluye metadatos de proyecto para NetBeans y Eclipse).

## Tecnologías

- **Java 17+**, interfaz gráfica con **Swing** (sin dependencias de UI
  externas).
- **Maven** para la gestión de dependencias, pruebas y empaquetado.
- **JUnit 5** para las pruebas automatizadas.
- **GitHub Actions** para integración continua.

## Algoritmos

- **Backtracking**: para cada columna, prueba cada fila y comprueba
  `esSeguro()` (misma fila, diagonal superior izquierda y diagonal
  inferior izquierda contra las columnas ya colocadas — las únicas que
  pueden tener una reina, dado que se coloca columna por columna); si
  ninguna fila funciona, retrocede. Completo y correcto para cualquier
  N, pero con costo exponencial en el peor caso, por lo que solo se usa
  hasta N=8.
- **Min-Conflicts**: coloca una reina por columna al azar y, mientras
  existan reinas en conflicto, mueve una de ellas (elegida al azar entre
  las conflictivas) a la fila de su columna con menos conflictos.
  Es una búsqueda de ascenso de colinas y puede quedar atascada en una
  meseta; el reinicio aleatorio acotado (hasta 30 intentos) es la
  técnica estándar para resolver esto y hace que prácticamente siempre
  converja, incluso para tableros de cientos de casillas.

## Tests

64 pruebas con JUnit 5: la lógica de `Tablero` (colocar/quitar reinas,
`esSeguro()` en fila y ambas diagonales, conteo de conflictos, validez
de una solución conocida), y `HeuristicaIA` de principio a fin —
revalidando cada solución reportada de forma independiente (una reina
por fila, sin columnas ni diagonales repetidas) en vez de confiar en el
propio código de producción: el caso trivial N=1, los casos sin
solución N=2 y N=3, Backtracking para N=4 a 8, Min-Conflicts para N=40
y N=100 bajo límites de tiempo (`@Timeout`) que detectan una regresión
del bloqueo por meseta, y repeticiones para detectar fallos
intermitentes.

```bash
mvn test
```

## Licencia

MIT — ver [LICENSE](LICENSE).
