package logica;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de HeuristicaIA: backtracking, Min-Conflicts, y los casos
 * límite explícitamente pedidos en la auditoría (N=1 trivial, N=2 y N=3
 * sin solución, N grande para las características de rendimiento).
 *
 * Cada solución que se reporta como encontrada se valida de forma
 * independiente (validarSolucion), sin confiar en tablero.esSolucionValida()
 * para no dar por buena una solución solo porque el propio código de
 * producción diga que lo es.
 */
class HeuristicaIATest {

    /**
     * Verifica de forma independiente que hay exactamente una reina por
     * fila, ninguna columna repetida y ninguna diagonal compartida.
     */
    private static boolean validarSolucion(Tablero tablero, int n) {
        int[] columnaDeFila = new int[n];
        java.util.Arrays.fill(columnaDeFila, -1);

        for (int fila = 0; fila < n; fila++) {
            int reinasEnFila = 0;
            for (int columna = 0; columna < n; columna++) {
                if (tablero.hayReina(fila, columna)) {
                    reinasEnFila++;
                    columnaDeFila[fila] = columna;
                }
            }
            if (reinasEnFila != 1) {
                return false;
            }
        }

        boolean[] columnaUsada = new boolean[n];
        for (int fila = 0; fila < n; fila++) {
            int columna = columnaDeFila[fila];
            if (columnaUsada[columna]) {
                return false;
            }
            columnaUsada[columna] = true;
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(columnaDeFila[i] - columnaDeFila[j]) == (j - i)) {
                    return false;
                }
            }
        }

        return true;
    }

    // -----------------------------------------------------------------
    // Backtracking
    // -----------------------------------------------------------------

    @Test
    void backtrackingResuelveElCasoTrivialDeUnaReina() {
        Tablero tablero = new Tablero(1);
        HeuristicaIA ia = new HeuristicaIA(tablero);

        assertTrue(ia.resolverConBacktracking());
        assertEquals(1, tablero.contarReinas());
        assertTrue(validarSolucion(tablero, 1));
    }

    @Test
    void backtrackingReportaCorrectamenteQueNoHaySolucionParaDosReinas() {
        Tablero tablero = new Tablero(2);
        HeuristicaIA ia = new HeuristicaIA(tablero);

        assertFalse(ia.resolverConBacktracking());
    }

    @Test
    void backtrackingReportaCorrectamenteQueNoHaySolucionParaTresReinas() {
        Tablero tablero = new Tablero(3);
        HeuristicaIA ia = new HeuristicaIA(tablero);

        assertFalse(ia.resolverConBacktracking());
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 5, 6, 7, 8})
    void backtrackingEncuentraUnaSolucionValidaParaNSinSolucionesConocidasFaltantes(int n) {
        Tablero tablero = new Tablero(n);
        HeuristicaIA ia = new HeuristicaIA(tablero);

        assertTrue(ia.resolverConBacktracking());
        assertTrue(validarSolucion(tablero, n), "la solución reportada para N=" + n + " no es válida");
    }

    // -----------------------------------------------------------------
    // Min-Conflicts
    // -----------------------------------------------------------------

    @RepeatedTest(10)
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void minConflictsEncuentraUnaSolucionValidaParaNGrande() {
        int n = 40;
        Tablero tablero = new Tablero(n);
        HeuristicaIA ia = new HeuristicaIA(tablero);

        assertTrue(ia.resolverConMinConflicts());
        assertTrue(validarSolucion(tablero, n));
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void minConflictsEncuentraUnaSolucionValidaParaNMuyGrande() {
        int n = 100;
        Tablero tablero = new Tablero(n);
        HeuristicaIA ia = new HeuristicaIA(tablero);

        assertTrue(ia.resolverConMinConflicts());
        assertTrue(validarSolucion(tablero, n));
    }

    // -----------------------------------------------------------------
    // resolver(): selección automática de estrategia según N
    // -----------------------------------------------------------------

    @Test
    void resolverUsaBacktrackingParaTablerosDeHastaOchoYEncuentraSolucion() {
        Tablero tablero = new Tablero(8);
        HeuristicaIA ia = new HeuristicaIA(tablero);

        assertTrue(ia.resolver());
        assertTrue(validarSolucion(tablero, 8));
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void resolverUsaMinConflictsParaTablerosMayoresAOchoYEncuentraSolucion() {
        Tablero tablero = new Tablero(20);
        HeuristicaIA ia = new HeuristicaIA(tablero);

        assertTrue(ia.resolver());
        assertTrue(validarSolucion(tablero, 20));
    }

    @Test
    void resolverReportaCorrectamenteQueNoHaySolucionParaDosYTresReinas() {
        assertFalse(new HeuristicaIA(new Tablero(2)).resolver());
        assertFalse(new HeuristicaIA(new Tablero(3)).resolver());
    }

    @RepeatedTest(30)
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void resolverEsConsistenteEnRepeticionesParaUnNModerado() {
        // Min-Conflicts arranca desde una colocación aleatoria, así que se
        // repite para reducir la posibilidad de que una ejecución con
        // suerte oculte un problema de fiabilidad.
        int n = 12;
        Tablero tablero = new Tablero(n);
        HeuristicaIA ia = new HeuristicaIA(tablero);

        assertTrue(ia.resolver());
        assertTrue(validarSolucion(tablero, n));
    }
}
