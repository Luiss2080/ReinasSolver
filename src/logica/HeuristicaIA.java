package logica;

import java.util.*;

/**
 * Implementa algoritmos heurísticos para resolver el problema de las N-Reinas.
 * Utiliza estrategias como Min-Conflicts y Backtracking optimizado para
 * encontrar soluciones eficientes según el tamaño del tablero.
 * 
 * @author Proyecto N-Reinas
 * @version 2.0
 */
public class HeuristicaIA {
    private Tablero tablero;
    private Random random;
    private static final int MAX_INTENTOS = 1000;
    
    /**
     * Constructor que inicializa la clase con el tablero de trabajo.
     * @param tablero Instancia del tablero sobre el cual trabajar
     */
    public HeuristicaIA(Tablero tablero) {
        this.tablero = tablero;
        this.random = new Random();
    }
    
    /**
     * Resuelve el problema de las N-Reinas usando múltiples estrategias
     * @return true si encuentra una solución, false en caso contrario
     */
    public boolean resolver() {
        int n = tablero.getTamano();
        
        // Para tableros pequeños, usar backtracking
        if (n <= 8) {
            return resolverConBacktracking();
        }
        // Para tableros más grandes, usar min-conflicts
        else {
            return resolverConMinConflicts();
        }
    }
    
    /**
     * Resuelve usando algoritmo de backtracking optimizado
     * @return true si encuentra solución, false en caso contrario
     */
    public boolean resolverConBacktracking() {
        tablero.limpiarTablero();
        boolean resultado = backtrackingRecursivo(0);
        tablero.setSolucionEncontrada(resultado);
        return resultado;
    }
    
    /**
     * Función recursiva para backtracking
     * @param columna Columna actual a procesar
     * @return true si se puede completar la solución desde esta columna
     */
    private boolean backtrackingRecursivo(int columna) {
        int n = tablero.getTamano();
        
        // Caso base: todas las reinas han sido colocadas
        if (columna >= n) {
            return true;
        }
        
        // Intentar colocar reina en cada fila de esta columna
        for (int fila = 0; fila < n; fila++) {
            if (tablero.esSeguro(fila, columna)) {
                tablero.colocarReina(fila, columna);
                
                // Recursivamente colocar el resto de reinas
                if (backtrackingRecursivo(columna + 1)) {
                    return true;
                }
                
                // Si no funciona, quitar la reina (backtrack)
                tablero.quitarReina(fila, columna);
            }
        }
        
        return false;
    }
    
    /**
     * Resuelve usando algoritmo de Min-Conflicts
     * @return true si encuentra solución, false en caso contrario
     */
    public boolean resolverConMinConflicts() {
        // Colocación inicial aleatoria (una reina por columna)
        colocacionInicialAleatoria();
        
        // Intentar resolver con min-conflicts
        for (int intento = 0; intento < MAX_INTENTOS; intento++) {
            if (tablero.esSolucionValida()) {
                tablero.setSolucionEncontrada(true);
                return true;
            }
            
            // Encontrar reina en conflicto
            int[] reinaConflicto = encontrarReinaEnConflicto();
            if (reinaConflicto == null) {
                break;
            }
            
            // Mover la reina a la posición con menos conflictos
            moverReinaMinConflictos(reinaConflicto[0], reinaConflicto[1]);
        }
        
        // Si min-conflicts no funciona, intentar con backtracking
        return resolverConBacktracking();
    }
    
    /**
     * Coloca reinas aleatoriamente, una por columna
     */
    private void colocacionInicialAleatoria() {
        tablero.limpiarTablero();
        int tamano = tablero.getTamano();
        
        for (int col = 0; col < tamano; col++) {
            int fila = random.nextInt(tamano);
            tablero.colocarReina(fila, col);
        }
    }
    
    /**
     * Encuentra una reina que esté en conflicto
     * @return Array con [fila, columna] de la reina en conflicto, null si no hay conflictos
     */
    private int[] encontrarReinaEnConflicto() {
        int n = tablero.getTamano();
        List<int[]> reinasEnConflicto = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tablero.hayReina(i, j) && tablero.contarConflictos(i, j) > 0) {
                    reinasEnConflicto.add(new int[]{i, j});
                }
            }
        }
        
        if (reinasEnConflicto.isEmpty()) {
            return null;
        }
        
        // Seleccionar aleatoriamente una reina en conflicto
        return reinasEnConflicto.get(random.nextInt(reinasEnConflicto.size()));
    }
    
    /**
     * Mueve una reina a la posición con menos conflictos en su columna
     * @param filaActual Fila actual de la reina
     * @param columna Columna de la reina (no cambia)
     */
    private void moverReinaMinConflictos(int filaActual, int columna) {
        int n = tablero.getTamano();
        int mejorFila = filaActual;
        int menorConflictos = Integer.MAX_VALUE;
        
        // Quitar temporalmente la reina
        tablero.quitarReina(filaActual, columna);
        
        // Buscar la mejor posición en esta columna
        for (int fila = 0; fila < n; fila++) {
            tablero.colocarReina(fila, columna);
            int conflictos = tablero.contarConflictos(fila, columna);
            
            if (conflictos < menorConflictos) {
                menorConflictos = conflictos;
                mejorFila = fila;
            } else if (conflictos == menorConflictos && random.nextBoolean()) {
                // En caso de empate, elegir aleatoriamente
                mejorFila = fila;
            }
            
            tablero.quitarReina(fila, columna);
        }
        
        // Colocar la reina en la mejor posición encontrada
        tablero.colocarReina(mejorFila, columna);
    }
}