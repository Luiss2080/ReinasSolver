package presentacion;

import logica.Tablero;
import logica.HeuristicaIA;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

/**
 * Ventana principal de la aplicación N-Reinas que integra todos los
 * componentes de la interfaz gráfica. Proporciona un entorno visual
 * intuitivo para interactuar con el problema de las N-Reinas.
 * 
 * @author Proyecto N-Reinas
 * @version 2.0
 */
public class VentanaPrincipal extends JFrame {
    private Tablero tablero;
    private HeuristicaIA ia;
    private PanelTablero panelTablero;
    private PanelControles panelControles;
    private EfectosVisuales efectos;
    
    private JLabel lblEstadisticas;
    
    /**
     * Constructor de la ventana principal
     */
    public VentanaPrincipal() {
        inicializarComponentes();
        configurarVentana();
        crearNuevoJuego();
        aplicarTemaOscuro();
    }
    
    /**
     * Inicializa todos los componentes de la interfaz
     */
    private void inicializarComponentes() {
        setLayout(new BorderLayout(15, 15));
        
        // Panel superior con título
        JPanel panelSuperior = crearPanelSuperior();
        add(panelSuperior, BorderLayout.NORTH);
        
        // Panel central con el tablero
        panelTablero = new PanelTablero(8);
        JScrollPane scrollTablero = new JScrollPane(panelTablero);
        scrollTablero.setPreferredSize(new Dimension(550, 550));
        add(scrollTablero, BorderLayout.CENTER);
        
        // Panel inferior con controles
        panelControles = new PanelControles(this);
        add(panelControles, BorderLayout.SOUTH);
        
        // Panel lateral con información
        JPanel panelLateral = crearPanelLateral();
        add(panelLateral, BorderLayout.EAST);
        
        // Inicializar efectos visuales
        efectos = new EfectosVisuales(this);
    }
    
    /**
     * Crea el panel superior con título
     * @return Panel superior configurado
     */
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(63, 81, 181));
        
        // Título con estilo moderno
        JLabel titulo = new JLabel("N-Reinas 8x8 - Algoritmo Heurístico", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        panel.add(titulo, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crea el panel lateral simplificado con estadísticas y reglas
     * @return Panel lateral configurado
     */
    private JPanel crearPanelLateral() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(300, 0));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Estadísticas
        JPanel panelStats = crearPanelSeccion("Estadisticas", new Color(33, 150, 243));
        lblEstadisticas = new JLabel("<html><div style='color: #333; font-size: 12px;'><b>Datos:</b><br/>-</div></html>");
        lblEstadisticas.setVerticalAlignment(SwingConstants.TOP);
        panelStats.add(lblEstadisticas, BorderLayout.CENTER);
        
        panel.add(panelStats);
        panel.add(Box.createVerticalStrut(20));
        
        // Reglas del juego
        JPanel panelReglas = crearPanelSeccion("Reglas del Juego", new Color(255, 152, 0));
        JLabel lblReglas = new JLabel("<html><div style='color: #333; font-size: 12px;'>" +
            "<b>Objetivo:</b> Colocar 8 reinas<br/>" +
            "<b>Restriccion:</b> No pueden atacarse<br/>" +
            "<b>Ataques:</b> Fila, columna, diagonal<br/>" +
            "<b>Algoritmo:</b> Resuelve automáticamente<br/>" +
            "</div></html>");
        lblReglas.setVerticalAlignment(SwingConstants.TOP);
        panelReglas.add(lblReglas, BorderLayout.CENTER);
        
        panel.add(panelReglas);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    /**
     * Crea un panel de sección con estilo moderno
     */
    private JPanel crearPanelSeccion(String titulo, Color colorAccento) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 224, 224), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Título con color de acento
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(colorAccento);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        return panel;
    }
    
    /**
     * Aplica un tema oscuro moderno a la interfaz
     */
    private void aplicarTemaOscuro() {
        // Configurar colores globales para el tema oscuro
        UIManager.put("Panel.background", new Color(35, 35, 35));
        UIManager.put("OptionPane.background", new Color(45, 45, 45));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", new Color(60, 60, 60));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("TextField.background", new Color(45, 45, 45));
        UIManager.put("TextField.foreground", Color.WHITE);
        UIManager.put("Label.foreground", Color.WHITE);
    }
    /**
     * Configura la ventana principal
     */
    private void configurarVentana() {
        setTitle("N-Reinas 8x8 - Algoritmo Heurístico v2.0");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setMinimumSize(new Dimension(1200, 800));
        getContentPane().setBackground(new Color(240, 240, 240));
        
        // Centrar la ventana
        setLocationRelativeTo(null);
        
        // Configurar cierre de ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salir();
            }
        });
        
        // Icono de la aplicación
        try {
            setIconImage(crearIcono());
        } catch (Exception e) {
            System.out.println("No se pudo cargar el icono: " + e.getMessage());
        }
        
        pack();
    }
    
    /**
     * Crea un icono para la aplicación
     * @return Imagen del icono
     */
    private Image crearIcono() {
        BufferedImage imagen = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imagen.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fondo con gradiente
        GradientPaint gradient = new GradientPaint(0, 0, new Color(101, 67, 33), 64, 64, new Color(139, 69, 19));
        g2d.setPaint(gradient);
        g2d.fillRoundRect(0, 0, 64, 64, 12, 12);
        
        // Corona
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(16, 16, 32, 32);
        
        // Detalles de la corona
        g2d.setColor(Color.ORANGE);
        int[] xPicos = {20, 28, 36, 44};
        int[] yPicos = {20, 16, 16, 20};
        g2d.fillPolygon(xPicos, yPicos, 4);
        
        g2d.dispose();
        return imagen;
    }
    
    /**
     * Crea un nuevo juego con tablero 8x8
     */
    public void crearNuevoJuego() {
        tablero = new Tablero(8);
        ia = new HeuristicaIA(tablero);
        
        // Actualizar panel del tablero
        remove(panelTablero.getParent());
        panelTablero = new PanelTablero(8);
        
        JScrollPane scrollTablero = new JScrollPane(panelTablero);
        scrollTablero.setPreferredSize(new Dimension(550, 550));
        add(scrollTablero, BorderLayout.CENTER);
        
        // Actualizar visualización
        actualizarTablero();
        actualizarEstadisticas();
        
        revalidate();
        repaint();
        pack();
    }
    
    /**
     * Resuelve el problema usando la IA
     */
    public void resolver() {
        actualizarEstado("Algoritmo resolviendo...");
        panelControles.setResolviendo(true);
        
        long tiempoInicio = System.currentTimeMillis();
        
        // Timer para actualizar tiempo
        Timer timerTiempo = new Timer(100, e -> {
            long transcurrido = System.currentTimeMillis() - tiempoInicio;
            // Solo actualizar en consola, no en UI
            System.out.printf("Tiempo transcurrido: %.1fs%n", transcurrido / 1000.0);
        });
        timerTiempo.start();
        
        // Ejecutar en un hilo separado para no bloquear la UI
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                Thread.sleep(200); // Pausa para mostrar animación
                return ia.resolver();
            }
            
            @Override
            protected void done() {
                try {
                    timerTiempo.stop();
                    long tiempoTotal = System.currentTimeMillis() - tiempoInicio;
                    boolean solucionEncontrada = get();
                    
                    actualizarTablero();
                    
                    if (solucionEncontrada) {
                        actualizarEstado("Solucion encontrada en " + (tiempoTotal / 1000.0) + "s!");
                        efectos.mostrarMensajeExito("¡Solución encontrada para 8 reinas en " + (tiempoTotal / 1000.0) + "s!");
                        efectos.mostrarAnimacionCelebracion();
                    } else {
                        actualizarEstado("No se encontro solucion");
                        efectos.mostrarMensajeError("No se pudo encontrar una solución para 8 reinas");
                    }
                    
                    actualizarEstadisticas();
                    panelControles.setResolviendo(false);
                } catch (Exception e) {
                    timerTiempo.stop();
                    actualizarEstado("Error al resolver: " + e.getMessage());
                    panelControles.setResolviendo(false);
                    efectos.mostrarMensajeError("Error durante la resolución");
                }
            }
        };
        
        worker.execute();
    }
    
    /**
     * Reinicia el juego
     */
    public void reiniciar() {
        tablero.limpiarTablero();
        panelTablero.limpiarResaltado();
        actualizarTablero();
        actualizarEstadisticas();
    }
    
    /**
     * Actualiza la visualización del tablero
     */
    private void actualizarTablero() {
        if (panelTablero != null) {
            panelTablero.actualizarTablero(tablero);
        }
    }
    
    /**
     * Actualiza el estado mostrado
     * @param mensaje Nuevo mensaje de estado
     */
    private void actualizarEstado(String mensaje) {
        // Estado se muestra ahora en los efectos visuales
        // Sin mensajes de consola
    }
    
    /**
     * Actualiza las estadísticas mostradas
     */
    private void actualizarEstadisticas() {
        if (lblEstadisticas != null && tablero != null) {
            String validez = tablero.esSolucionValida() ? "Valida" : "Invalida";
            
            String stats = String.format(
                "<html><div style='color: #333; font-size: 12px;'>" +
                "<b>Estadisticas:</b><br/>" +
                "Tamano: 8x8<br/>" +
                "Reinas: %d/8<br/>" +
                "Conflictos: %d<br/>" +
                "Estado: %s" +
                "</div></html>",
                tablero.contarReinas(),
                tablero.contarTotalConflictos(),
                validez
            );
            lblEstadisticas.setText(stats);
        }
    }
    
    /**
     * Maneja la salida de la aplicación
     */
    public void salir() {
        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea salir?",
            "Confirmar salida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (opcion == JOptionPane.YES_OPTION) {
            efectos.mostrarMensajeInfo("Gracias por jugar!");
            Timer exitTimer = new Timer(1000, e -> System.exit(0));
            exitTimer.setRepeats(false);
            exitTimer.start();
        }
    }
    
    /**
     * Obtiene el tablero actual
     * @return Tablero actual
     */
    public Tablero getTablero() {
        return tablero;
    }
    
    /**
     * Obtiene la IA
     * @return IA heurística
     */
    public HeuristicaIA getIA() {
        return ia;
    }
    
    /**
     * Obtiene los efectos visuales
     * @return Efectos visuales
     */
    public EfectosVisuales getEfectos() {
        return efectos;
    }
    
    /**
     * Obtiene el panel del tablero
     * @return Panel del tablero
     */
    public PanelTablero getPanelTablero() {
        return panelTablero;
    }
}