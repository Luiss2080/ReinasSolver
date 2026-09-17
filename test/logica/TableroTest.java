package logica;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de la lógica pura de Tablero: colocación de reinas, detección
 * de seguridad (esSeguro) y conteo de conflictos, que son la base sobre
 * la que se apoyan tanto el backtracking como Min-Conflicts.
 */
class TableroTest {

    @Test
    void unTableroNuevoNoTieneReinas() {
        Tablero t = new Tablero(8);
        assertEquals(0, t.contarReinas());
        assertFalse(t.hayReina(0, 0));
    }

    @Test
    void colocarYQuitarReinaFuncionaCorrectamente() {
        Tablero t = new Tablero(8);
        t.colocarReina(3, 4);
        assertTrue(t.hayReina(3, 4));
        assertEquals(1, t.contarReinas());

        t.quitarReina(3, 4);
        assertFalse(t.hayReina(3, 4));
        assertEquals(0, t.contarReinas());
    }

    @Test
    void esSeguroEsFalsoSiHayOtraReinaEnLaMismaFila() {
        Tablero t = new Tablero(8);
        t.colocarReina(2, 0);
        assertFalse(t.esSeguro(2, 5));
    }

    @Test
    void esSeguroEsFalsoEnLaDiagonalSuperiorIzquierda() {
        Tablero t = new Tablero(8);
        t.colocarReina(2, 2);
        // (4,4) está en la misma diagonal descendente que (2,2)
        assertFalse(t.esSeguro(4, 4));
    }

    @Test
    void esSeguroEsFalsoEnLaDiagonalInferiorIzquierda() {
        // esSeguro() solo considera columnas ANTERIORES a la columna
        // objetivo (ver esSeguroSoloConsideraColumnasAnterioresALaActual),
        // así que para ejercitar el bucle de la diagonal
        // "inferior izquierda" (i++, j--) la reina existente debe estar en
        // una columna menor y una fila mayor que la posición objetivo.
        Tablero t = new Tablero(8);
        t.colocarReina(6, 2); // columna 2, anterior a la columna objetivo 4
        // |6-4| == |2-4| == 2: misma diagonal
        assertFalse(t.esSeguro(4, 4));
    }

    @Test
    void esSeguroEsVerdaderoParaUnaPosicionSinAtaques() {
        Tablero t = new Tablero(8);
        t.colocarReina(0, 0);
        // (1,2) no comparte fila, columna ni diagonal con (0,0)
        assertTrue(t.esSeguro(1, 2));
    }

    @Test
    void esSeguroSoloConsideraColumnasAnterioresALaActual() {
        // esSeguro() se usa en backtracking columna por columna: una
        // reina colocada en una columna POSTERIOR no debería impedir
        // colocar en la columna actual (aunque en la práctica el
        // backtracking nunca coloca reinas fuera de orden).
        Tablero t = new Tablero(8);
        t.colocarReina(0, 5); // columna 5, posterior a la columna 2
        assertTrue(t.esSeguro(0, 2));
    }

    @Test
    void contarConflictosDetectaAtaquesDeFilaColumnaYDiagonal() {
        Tablero t = new Tablero(4);
        t.colocarReina(0, 0);
        t.colocarReina(0, 1); // ataca por fila a (0,0)... y a sí misma no
        t.colocarReina(1, 1); // ataca por columna a (0,1) y diagonal a (0,0)

        // Conflictos de la reina en (1,1): comparte columna con (0,1) y
        // diagonal con (0,0) -> 2.
        assertEquals(2, t.contarConflictos(1, 1));
    }

    @Test
    void contarTotalConflictosEsCeroEnUnTableroVacio() {
        Tablero t = new Tablero(8);
        assertEquals(0, t.contarTotalConflictos());
    }

    @Test
    void esSolucionValidaRequiereNReinasYCeroConflictos() {
        Tablero t = new Tablero(4);
        // Solución conocida para 4-reinas: (0,1),(1,3),(2,0),(3,2)
        t.colocarReina(0, 1);
        t.colocarReina(1, 3);
        t.colocarReina(2, 0);
        t.colocarReina(3, 2);

        assertTrue(t.esSolucionValida());
    }

    @Test
    void esSolucionValidaEsFalsaConMenosDeNReinas() {
        Tablero t = new Tablero(4);
        t.colocarReina(0, 1);
        assertFalse(t.esSolucionValida());
    }

    @Test
    void limpiarTableroDejaElTableroVacioYSinSolucion() {
        Tablero t = new Tablero(4);
        t.colocarReina(0, 1);
        t.setSolucionEncontrada(true);

        t.limpiarTablero();

        assertEquals(0, t.contarReinas());
        assertFalse(t.isSolucionEncontrada());
    }
}
