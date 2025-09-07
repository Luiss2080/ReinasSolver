package logica;

/**
 * Representa el tablero del juego de las N-Reinas con toda su lógica
 * de validación y manipulación. Gestiona el estado del tablero y
 * proporciona métodos para colocar reinas y verificar conflictos.
 * 
 * @author Proyecto N-Reinas
 * @version 2.0
 */
public class Tablero {
    private int n;
    private int[][] tablero;
    private boolean solucionEncontrada;
    
    /**
     * Constructor del tablero
     * @param n Tamaño del tablero (N x N)
     */
    public Tablero(int n) {
        this.n = n;
        this.tablero = new int[n][n];
        this.solucionEncontrada = false;
        inicializarTablero();
    }
    
    /**
     * Inicializa el tablero con valores vacíos (0)
     */
    private void inicializarTablero() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tablero[i][j] = 0;
            }
        }
    }
    
    /**
     * Limpia el tablero
     */
    public void limpiarTablero() {
        inicializarTablero();
        solucionEncontrada = false;
    }
    
    /**
     * Coloca una reina en la posición especificada
     * @param fila Fila donde colocar la reina
     * @param columna Columna donde colocar la reina
     */
    public void colocarReina(int fila, int columna) {
        if (fila >= 0 && fila < n && columna >= 0 && columna < n) {
            tablero[fila][columna] = 1;
        }
    }
    
    /**
     * Quita una reina de la posición especificada
     * @param fila Fila de donde quitar la reina
     * @param columna Columna de donde quitar la reina
     */
    public void quitarReina(int fila, int columna) {
        if (fila >= 0 && fila < n && columna >= 0 && columna < n) {
            tablero[fila][columna] = 0;
        }
    }
    
    /**
     * Verifica si hay una reina en la posición especificada
     * @param fila Fila a verificar
     * @param columna Columna a verificar
     * @return true si hay una reina, false en caso contrario
     */
    public boolean hayReina(int fila, int columna) {
        if (fila >= 0 && fila < n && columna >= 0 && columna < n) {
            return tablero[fila][columna] == 1;
        }
        return false;
    }
    
    /**
     * Verifica si es seguro colocar una reina en la posición especificada
     * @param fila Fila donde se quiere colocar la reina
     * @param columna Columna donde se quiere colocar la reina
     * @return true si es seguro, false en caso contrario
     */
    public boolean esSeguro(int fila, int columna) {
        // Verificar la fila hacia la izquierda
        for (int j = 0; j < columna; j++) {
            if (tablero[fila][j] == 1) {
                return false;
            }
        }
        
        // Verificar diagonal superior izquierda
        for (int i = fila, j = columna; i >= 0 && j >= 0; i--, j--) {
            if (tablero[i][j] == 1) {
                return false;
            }
        }
        
        // Verificar diagonal inferior izquierda
        for (int i = fila, j = columna; j >= 0 && i < n; i++, j--) {
            if (tablero[i][j] == 1) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Cuenta el número de conflictos para una posición dada
     * @param fila Fila de la posición
     * @param columna Columna de la posición
     * @return Número de reinas que atacan esta posición
     */
    public int contarConflictos(int fila, int columna) {
        int conflictos = 0;
        
        // Verificar fila
        for (int j = 0; j < n; j++) {
            if (j != columna && tablero[fila][j] == 1) {
                conflictos++;
            }
        }
        
        // Verificar columna
        for (int i = 0; i < n; i++) {
            if (i != fila && tablero[i][columna] == 1) {
                conflictos++;
            }
        }
        
        // Verificar diagonal principal
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != fila && j != columna && 
                    Math.abs(i - fila) == Math.abs(j - columna) && 
                    tablero[i][j] == 1) {
                    conflictos++;
                }
            }
        }
        
        return conflictos;
    }
    
    /**
     * Cuenta el total de conflictos en el tablero
     * @return Número total de conflictos
     */
    public int contarTotalConflictos() {
        int totalConflictos = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tablero[i][j] == 1) {
                    totalConflictos += contarConflictos(i, j);
                }
            }
        }
        return totalConflictos / 2; // Dividir por 2 porque cada conflicto se cuenta dos veces
    }
    
    /**
     * Verifica si la solución actual es válida (sin conflictos)
     * @return true si es una solución válida, false en caso contrario
     */
    public boolean esSolucionValida() {
        return contarTotalConflictos() == 0 && contarReinas() == n;
    }
    
    /**
     * Cuenta el número de reinas en el tablero
     * @return Número de reinas
     */
    public int contarReinas() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tablero[i][j] == 1) {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     * Obtiene una copia del tablero
     * @return Matriz representando el tablero
     */
    public int[][] obtenerTablero() {
        int[][] copia = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copia[i][j] = tablero[i][j];
            }
        }
        return copia;
    }
    
    /**
     * Establece el estado del tablero
     * @param nuevoTablero Nuevo estado del tablero
     */
    public void establecerTablero(int[][] nuevoTablero) {
        if (nuevoTablero.length == n && nuevoTablero[0].length == n) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tablero[i][j] = nuevoTablero[i][j];
                }
            }
        }
    }
    
    /**
     * Obtiene el tamaño del tablero
     * @return Tamaño N del tablero
     */
    public int getTamano() {
        return n;
    }
    
    /**
     * Establece si se encontró una solución
     * @param encontrada true si se encontró solución
     */
    public void setSolucionEncontrada(boolean encontrada) {
        this.solucionEncontrada = encontrada;
    }
    
    /**
     * Verifica si se encontró una solución
     * @return true si se encontró solución
     */
    public boolean isSolucionEncontrada() {
        return solucionEncontrada;
    }
    
    /**
     * Representación en cadena del tablero
     * @return String representando el tablero
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(tablero[i][j] == 1 ? "♛ " : "□ ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}