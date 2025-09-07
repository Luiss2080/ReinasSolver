package presentacion;

import logica.Tablero;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel que renderiza visualmente el tablero de N-Reinas con una interfaz
 * interactiva y atractiva. Gestiona la visualización del tablero, las reinas
 * y los efectos visuales asociados con la resolución del problema.
 * 
 * @author Proyecto N-Reinas
 * @version 2.0
 */
public class PanelTablero extends JPanel {
    private int n;
    private Tablero tablero;
    private CeldaTablero[][] celdas;
    private CeldaTablero celdaSeleccionada;
    private List<CeldaTablero> celdasResaltadas;
    private Timer animacionTimer;
    
    // Colores mejorados
    private static final Color COLOR_CLARO = new Color(240, 217, 181);
    private static final Color COLOR_OSCURO = new Color(181, 136, 99);
    private static final Color COLOR_REINA = new Color(255, 215, 0);
    private static final Color COLOR_CONFLICTO = new Color(255, 100, 100);
    private static final Color COLOR_SELECCIONADA = new Color(100, 149, 237);
    private static final Color COLOR_HOVER = new Color(135, 206, 235, 100);
    private static final Color COLOR_RESALTADA = new Color(144, 238, 144, 150);
    
    /**
     * Constructor del panel del tablero
     * @param n Tamaño del tablero
     */
    public PanelTablero(int n) {
        this.n = n;
        this.celdasResaltadas = new ArrayList<>();
        inicializarPanel();
        crearTablero();
        inicializarAnimaciones();
    }
    
    /**
     * Inicializa la configuración del panel
     */
    private void inicializarPanel() {
        setLayout(new GridLayout(n, n, 2, 2));
        setPreferredSize(new Dimension(500, 500));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        setBackground(new Color(139, 69, 19));
    }
    
    /**
     * Inicializa las animaciones
     */
    private void inicializarAnimaciones() {
        animacionTimer = new Timer(50, e -> {
            for (CeldaTablero[] fila : celdas) {
                for (CeldaTablero celda : fila) {
                    if (celda != null) {
                        celda.actualizarAnimacion();
                    }
                }
            }
            repaint();
        });
    }
    
    /**
     * Crea las celdas del tablero
     */
    private void crearTablero() {
        celdas = new CeldaTablero[n][n];
        removeAll();
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                CeldaTablero celda = new CeldaTablero(i, j);
                celdas[i][j] = celda;
                add(celda);
            }
        }
        
        revalidate();
        repaint();
    }
    
    /**
     * Actualiza la visualización del tablero con el estado actual
     * @param tablero Tablero con el estado actual
     */
    public void actualizarTablero(Tablero tablero) {
        this.tablero = tablero;
        
        // Si el tamaño cambió, recrear el tablero
        if (tablero.getTamano() != n) {
            n = tablero.getTamano();
            setLayout(new GridLayout(n, n, 2, 2));
            crearTablero();
        }
        
        // Actualizar cada celda con animación
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (celdas[i][j] != null) {
                        celdas[i][j].actualizarEstado();
                    }
                }
            }
            iniciarAnimacionActualizacion();
        });
    }
    
    /**
     * Inicia animación de actualización
     */
    private void iniciarAnimacionActualizacion() {
        if (!animacionTimer.isRunning()) {
            animacionTimer.start();
        }
        
        Timer stopTimer = new Timer(1000, e -> {
            animacionTimer.stop();
        });
        stopTimer.setRepeats(false);
        stopTimer.start();
    }
    
    /**
     * Resalta las posiciones que una reina puede atacar
     * @param fila Fila de la reina
     * @param columna Columna de la reina
     */
    public void resaltarAtaques(int fila, int columna) {
        limpiarResaltado();
        
        if (tablero == null) return;
        
        // Resaltar fila
        for (int j = 0; j < n; j++) {
            if (j != columna) {
                celdasResaltadas.add(celdas[fila][j]);
            }
        }
        
        // Resaltar columna
        for (int i = 0; i < n; i++) {
            if (i != fila) {
                celdasResaltadas.add(celdas[i][columna]);
            }
        }
        
        // Resaltar diagonales
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != fila && j != columna && 
                    Math.abs(i - fila) == Math.abs(j - columna)) {
                    celdasResaltadas.add(celdas[i][j]);
                }
            }
        }
        
        // Actualizar visualización
        for (CeldaTablero celda : celdasResaltadas) {
            celda.setResaltada(true);
        }
        
        repaint();
    }
    
    /**
     * Limpia el resaltado de las celdas
     */
    public void limpiarResaltado() {
        for (CeldaTablero celda : celdasResaltadas) {
            celda.setResaltada(false);
        }
        celdasResaltadas.clear();
        repaint();
    }
    
    /**
     * Clase interna que representa una celda del tablero con interactividad mejorada
     */
    private class CeldaTablero extends JPanel {
        private int fila, columna;
        private boolean tieneReina = false;
        private boolean enConflicto = false;
        private boolean seleccionada = false;
        private boolean hover = false;
        private boolean resaltada = false;
        private float alphaAnimacion = 1.0f;
        private float escalaAnimacion = 1.0f;
        
        /**
         * Constructor de la celda
         * @param fila Fila de la celda
         * @param columna Columna de la celda
         */
        public CeldaTablero(int fila, int columna) {
            this.fila = fila;
            this.columna = columna;
            configurarCelda();
        }
        
        /**
         * Configura la apariencia y comportamiento de la celda
         */
        private void configurarCelda() {
            setPreferredSize(new Dimension(60, 60));
            setBorder(BorderFactory.createRaisedBevelBorder());
            setOpaque(false);
            
            // Tooltip dinámico
            actualizarTooltip();
            
            // Eventos del mouse mejorados
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mostrarInformacionCelda();
                }
                
                @Override
                public void mousePressed(MouseEvent e) {
                    seleccionada = true;
                    if (celdaSeleccionada != null && celdaSeleccionada != CeldaTablero.this) {
                        celdaSeleccionada.seleccionada = false;
                        celdaSeleccionada.repaint();
                    }
                    celdaSeleccionada = CeldaTablero.this;
                    
                    if (tieneReina) {
                        resaltarAtaques(fila, columna);
                    }
                    
                    repaint();
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    seleccionada = false;
                    repaint();
                }
                
                @Override
                public void mouseEntered(MouseEvent e) {
                    hover = true;
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    actualizarTooltip();
                    repaint();
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    hover = false;
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    repaint();
                }
            });
        }
        
        /**
         * Actualiza el tooltip con información dinámica
         */
        private void actualizarTooltip() {
            if (tablero != null) {
                String info = String.format(
                    "<html><b>Posición:</b> (%d, %d)<br/>" +
                    "<b>Reina:</b> %s<br/>" +
                    "<b>Conflictos:</b> %d<br/>" +
                    "<b>Segura:</b> %s</html>",
                    fila + 1, columna + 1,
                    tieneReina ? "Sí" : "No",
                    tieneReina ? tablero.contarConflictos(fila, columna) : 0,
                    tablero.esSeguro(fila, columna) ? "Sí" : "No"
                );
                setToolTipText(info);
            } else {
                setToolTipText(String.format("Posición: (%d, %d)", fila + 1, columna + 1));
            }
        }
        
        /**
         * Muestra información detallada de la celda
         */
        private void mostrarInformacionCelda() {
            if (tablero != null) {
                String info = String.format(
                    "🏰 INFORMACIÓN DE LA CELDA\\n\\n" +
                    "📍 Posición: Fila %d, Columna %d\\n" +
                    "👑 Reina: %s\\n" +
                    "⚔️ Conflictos: %d\\n" +
                    "🛡️ Posición segura: %s\\n" +
                    "🎯 Puede colocar reina: %s",
                    fila + 1, columna + 1,
                    tieneReina ? "Sí" : "No",
                    tieneReina ? tablero.contarConflictos(fila, columna) : 0,
                    tablero.esSeguro(fila, columna) ? "Sí" : "No",
                    (!tieneReina && tablero.esSeguro(fila, columna)) ? "Sí" : "No"
                );
                
                JOptionPane.showMessageDialog(
                    PanelTablero.this.getParent(),
                    info,
                    "Información de la Celda",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
        
        /**
         * Actualiza el estado de la celda basado en el tablero
         */
        public void actualizarEstado() {
            if (tablero != null) {
                boolean nuevaReina = tablero.hayReina(fila, columna);
                boolean nuevoConflicto = nuevaReina && tablero.contarConflictos(fila, columna) > 0;
                
                // Animación cuando cambia el estado
                if (nuevaReina != tieneReina) {
                    iniciarAnimacionCambio();
                }
                
                tieneReina = nuevaReina;
                enConflicto = nuevoConflicto;
            } else {
                tieneReina = false;
                enConflicto = false;
            }
            
            actualizarTooltip();
            repaint();
        }
        
        /**
         * Inicia animación de cambio de estado
         */
        private void iniciarAnimacionCambio() {
            Timer animTimer = new Timer(30, null);
            animTimer.addActionListener(e -> {
                escalaAnimacion += 0.1f;
                if (escalaAnimacion >= 1.3f) {
                    escalaAnimacion = 1.3f;
                    Timer reverseTimer = new Timer(30, null);
                    reverseTimer.addActionListener(e2 -> {
                        escalaAnimacion -= 0.1f;
                        if (escalaAnimacion <= 1.0f) {
                            escalaAnimacion = 1.0f;
                            reverseTimer.stop();
                        }
                        repaint();
                    });
                    reverseTimer.start();
                    animTimer.stop();
                }
                repaint();
            });
            animTimer.start();
        }
        
        /**
         * Actualiza animaciones
         */
        public void actualizarAnimacion() {
            if (tieneReina && !enConflicto) {
                // Animación sutil de brillo para reinas válidas
                long time = System.currentTimeMillis();
                alphaAnimacion = 0.8f + 0.2f * (float) Math.sin(time * 0.003);
            } else {
                alphaAnimacion = 1.0f;
            }
        }
        
        /**
         * Establece si la celda debe mostrarse resaltada
         */
        public void setResaltada(boolean resaltada) {
            this.resaltada = resaltada;
        }
        
        /**
         * Dibuja la celda y su contenido con efectos mejorados
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            
            int width = getWidth();
            int height = getHeight();
            
            // Aplicar escala de animación
            if (escalaAnimacion != 1.0f) {
                g2d.translate(width / 2, height / 2);
                g2d.scale(escalaAnimacion, escalaAnimacion);
                g2d.translate(-width / 2, -height / 2);
            }
            
            // Color de fondo con gradiente
            dibujarFondo(g2d, width, height);
            
            // Efectos de estado
            dibujarEfectosEstado(g2d, width, height);
            
            // Contenido (reina)
            if (tieneReina) {
                dibujarReina(g2d, width, height);
            }
            
            g2d.dispose();
        }
        
        /**
         * Dibuja el fondo de la celda con gradientes
         */
        private void dibujarFondo(Graphics2D g2d, int width, int height) {
            Color colorBase = ((fila + columna) % 2 == 0) ? COLOR_CLARO : COLOR_OSCURO;
            
            if (enConflicto) {
                colorBase = COLOR_CONFLICTO;
            }
            
            // Gradiente suave
            GradientPaint gradient = new GradientPaint(
                0, 0, colorBase.brighter(),
                width, height, colorBase.darker()
            );
            g2d.setPaint(gradient);
            g2d.fill(new RoundRectangle2D.Float(2, 2, width - 4, height - 4, 8, 8));
        }
        
        /**
         * Dibuja efectos de estado (hover, selección, etc.)
         */
        private void dibujarEfectosEstado(Graphics2D g2d, int width, int height) {
            // Efecto hover
            if (hover) {
                g2d.setColor(COLOR_HOVER);
                g2d.fill(new RoundRectangle2D.Float(2, 2, width - 4, height - 4, 8, 8));
            }
            
            // Efecto seleccionado
            if (seleccionada) {
                g2d.setColor(COLOR_SELECCIONADA);
                g2d.setStroke(new BasicStroke(3));
                g2d.draw(new RoundRectangle2D.Float(1, 1, width - 2, height - 2, 8, 8));
            }
            
            // Efecto resaltado
            if (resaltada) {
                g2d.setColor(COLOR_RESALTADA);
                g2d.fill(new RoundRectangle2D.Float(2, 2, width - 4, height - 4, 8, 8));
            }
        }
        
        /**
         * Dibuja una reina mejorada con efectos
         */
        private void dibujarReina(Graphics2D g2d, int width, int height) {
            // Aplicar transparencia animada
            Composite originalComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaAnimacion));
            
            int margen = 8;
            Color colorReina = enConflicto ? Color.RED.darker() : COLOR_REINA;
            
            // Sombra de la reina
            g2d.setColor(new Color(0, 0, 0, 100));
            dibujarFormaReina(g2d, width + 2, height + 2, margen, colorReina.darker());
            
            // Reina principal
            g2d.setColor(colorReina);
            dibujarFormaReina(g2d, width, height, margen, colorReina);
            
            // Brillo
            if (!enConflicto) {
                g2d.setColor(Color.WHITE);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                int brilloSize = Math.max(4, (width - margen * 2) / 4);
                g2d.fillOval(width / 2 - brilloSize / 2, height / 3, brilloSize, brilloSize);
            }
            
            g2d.setComposite(originalComposite);
        }
        
        /**
         * Dibuja la forma de la reina
         */
        private void dibujarFormaReina(Graphics2D g2d, int width, int height, int margen, Color color) {
            g2d.setColor(color);
            
            // Base de la corona
            int baseY = height - margen - 6;
            int baseX = margen;
            int baseAncho = width - 2 * margen;
            int baseAlto = 10;
            g2d.fillRoundRect(baseX, baseY, baseAncho, baseAlto, 4, 4);
            
            // Cuerpo de la corona
            int cuerpoY = baseY - 18;
            int cuerpoAncho = baseAncho - 6;
            int cuerpoAlto = 18;
            g2d.fillRoundRect(baseX + 3, cuerpoY, cuerpoAncho, cuerpoAlto, 4, 4);
            
            // Picos de la corona mejorados
            int numPicos = 5;
            int[] xPicos = new int[numPicos];
            int[] yPicos = new int[numPicos];
            
            for (int i = 0; i < numPicos; i++) {
                xPicos[i] = baseX + 3 + (i * cuerpoAncho / (numPicos - 1));
                yPicos[i] = cuerpoY - (i % 2 == 1 ? 8 : 4);
            }
            
            g2d.fillPolygon(xPicos, yPicos, numPicos);
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
     * Obtiene una celda específica
     * @param fila Fila de la celda
     * @param columna Columna de la celda
     * @return CeldaTablero en la posición especificada
     */
    public CeldaTablero getCelda(int fila, int columna) {
        if (fila >= 0 && fila < n && columna >= 0 && columna < n) {
            return celdas[fila][columna];
        }
        return null;
    }
}