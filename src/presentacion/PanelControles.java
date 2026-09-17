package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Panel de control que proporciona la interfaz de usuario para interactuar
 * con el solucionador de N-Reinas. Incluye botones para resolver, reiniciar
 * y salir, además de mostrar información de tiempo de ejecución.
 * 
 * @author Proyecto N-Reinas
 * @version 2.0
 */
public class PanelControles extends JPanel {
    private VentanaPrincipal ventanaPrincipal;
    private JButton btnResolver;
    private JButton btnReiniciar;
    private JButton btnSalir;
    private JLabel lblTiempo;
    
    private Timer timerProgreso;
    private long tiempoInicio;
    private boolean resolviendo = false;
    
    /**
     * Constructor del panel de controles
     * @param ventanaPrincipal Referencia a la ventana principal
     */
    public PanelControles(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        inicializarComponentes();
        configurarPanel();
        configurarEventos();
        configurarTeclasRapidas();
    }
    
    /**
     * Inicializa todos los componentes del panel
     */
    private void inicializarComponentes() {
        // Botón Resolver (Verde)
        btnResolver = new JButton("Resolver");
        configurarBotonModerno(btnResolver, new Color(76, 175, 80), new Color(67, 160, 71));
        btnResolver.setToolTipText("Ejecuta la IA para resolver el problema (Ctrl+R)");
        
        // Botón Reiniciar (Naranja)
        btnReiniciar = new JButton("Reiniciar");
        configurarBotonModerno(btnReiniciar, new Color(255, 152, 0), new Color(245, 124, 0));
        btnReiniciar.setToolTipText("Limpia el tablero para empezar de nuevo (Ctrl+N)");
        
        // Botón Salir (Rojo)
        btnSalir = new JButton("Salir");
        configurarBotonModerno(btnSalir, new Color(244, 67, 54), new Color(229, 57, 53));
        btnSalir.setToolTipText("Cierra la aplicación (Ctrl+Q)");
        
        // Label para tiempo transcurrido
        lblTiempo = new JLabel("Tiempo: 0s");
        lblTiempo.setFont(new Font("Arial", Font.PLAIN, 10));
        lblTiempo.setVisible(false);
        
        // Timer para actualizar progreso
        timerProgreso = new Timer(100, e -> actualizarTiempo());
    }
    
    /**
     * Configura un botón con estilo moderno
     */
    private void configurarBotonModerno(JButton boton, Color colorNormal, Color colorHover) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(colorNormal);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setPreferredSize(new Dimension(140, 45));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Agregar bordes redondeados y efectos hover
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorNormal.darker(), 1),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        
        // Efectos de hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorHover);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorNormal);
            }
        });
    }
    
    /**
     * Configura el layout y apariencia del panel
     */
    private void configurarPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(245, 245, 245));
        
        // Panel de botones con mejor espaciado
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(new Color(240, 240, 240));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panelBotones.add(btnResolver);
        panelBotones.add(btnReiniciar);
        panelBotones.add(btnSalir);
        
        // Panel de estado
        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelEstado.setBackground(new Color(240, 240, 240));
        panelEstado.add(lblTiempo);
        
        add(panelBotones, BorderLayout.CENTER);
        add(panelEstado, BorderLayout.SOUTH);
    }
    
    /**
     * Configura los eventos de los botones
     */
    private void configurarEventos() {
        // Evento del botón Resolver
        btnResolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!resolviendo) {
                    ventanaPrincipal.resolver();
                }
            }
        });
        
        // Evento del botón Reiniciar
        btnReiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!resolviendo) {
                    int opcion = JOptionPane.showConfirmDialog(
                        ventanaPrincipal,
                        "¿Está seguro que desea reiniciar el tablero?",
                        "Confirmar reinicio",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                    );
                    
                    if (opcion == JOptionPane.YES_OPTION) {
                        ventanaPrincipal.reiniciar();
                    }
                }
            }
        });
        
        // Evento del botón Salir
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaPrincipal.salir();
            }
        });
        
        // Efectos hover para los botones
        configurarEfectosHover();
    }
    
    /**
     * Configura efectos visuales cuando el mouse está sobre los botones
     */
    private void configurarEfectosHover() {
        // Efecto hover para btnResolver
        btnResolver.addMouseListener(new java.awt.event.MouseAdapter() {
            Color colorOriginal = btnResolver.getBackground();
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btnResolver.isEnabled()) {
                    btnResolver.setBackground(colorOriginal.darker());
                }
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnResolver.setBackground(colorOriginal);
            }
        });
        
        // Efecto hover para btnReiniciar
        btnReiniciar.addMouseListener(new java.awt.event.MouseAdapter() {
            Color colorOriginal = btnReiniciar.getBackground();
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btnReiniciar.isEnabled()) {
                    btnReiniciar.setBackground(colorOriginal.darker());
                }
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnReiniciar.setBackground(colorOriginal);
            }
        });
        
        // Efecto hover para btnSalir
        btnSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            Color colorOriginal = btnSalir.getBackground();
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btnSalir.isEnabled()) {
                    btnSalir.setBackground(colorOriginal.darker());
                }
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnSalir.setBackground(colorOriginal);
            }
        });
    }
    
    /**
     * Configura las teclas rápidas (shortcuts) para los botones
     */
    private void configurarTeclasRapidas() {
        // Mapa de acciones
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();
        
        // Ctrl+R para resolver
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK), "resolver");
        actionMap.put("resolver", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnResolver.isEnabled()) {
                    btnResolver.doClick();
                }
            }
        });
        
        // Ctrl+N para reiniciar (nuevo juego)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK), "reiniciar");
        actionMap.put("reiniciar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnReiniciar.isEnabled()) {
                    btnReiniciar.doClick();
                }
            }
        });
        
        // Ctrl+Q para salir
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK), "salir");
        actionMap.put("salir", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSalir.doClick();
            }
        });
        
        // F1 para ayuda
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "ayuda");
        actionMap.put("ayuda", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarAyuda();
            }
        });
    }
    
    /**
     * Establece el estado de resolución (activa/desactiva controles)
     * @param resolviendo true si está resolviendo, false en caso contrario
     */
    public void setResolviendo(boolean resolviendo) {
        this.resolviendo = resolviendo;
        
        if (resolviendo) {
            // Deshabilitar botones y mostrar progreso
            btnResolver.setEnabled(false);
            btnResolver.setText("Resolviendo...");
            btnReiniciar.setEnabled(false);
            
            lblTiempo.setVisible(true);
            tiempoInicio = System.currentTimeMillis();
            timerProgreso.start();
            
        } else {
            // Habilitar botones y ocultar progreso
            btnResolver.setEnabled(true);
            btnResolver.setText("Resolver");
            btnReiniciar.setEnabled(true);
            
            lblTiempo.setVisible(false);
            timerProgreso.stop();
        }
        
        revalidate();
        repaint();
    }
    
    /**
     * Actualiza el tiempo transcurrido
     */
    private void actualizarTiempo() {
        if (resolviendo) {
            long tiempoTranscurrido = System.currentTimeMillis() - tiempoInicio;
            double segundos = tiempoTranscurrido / 1000.0;
            lblTiempo.setText(String.format("Tiempo: %.1fs", segundos));
        }
    }
    
    /**
     * Muestra la ventana de ayuda
     */
    private void mostrarAyuda() {
        String ayuda =
            "🏆 JUEGO DE LAS N-REINAS CON IA HEURÍSTICA\n\n" +
            "📋 OBJETIVO:\n" +
            "Colocar N reinas en un tablero NxN de tal forma\n" +
            "que ninguna reina pueda atacar a otra.\n\n" +
            "🎮 CONTROLES:\n" +
            "• 🤖 Resolver (Ctrl+R): Ejecuta la IA para encontrar la solución\n" +
            "• 🔄 Reiniciar (Ctrl+N): Limpia el tablero\n" +
            "• ❌ Salir (Ctrl+Q): Cierra la aplicación\n" +
            "• F1: Muestra esta ayuda\n\n" +
            "🧠 ALGORITMOS UTILIZADOS:\n" +
            "• Backtracking optimizado (tableros pequeños)\n" +
            "• Min-Conflicts heurístico (tableros grandes)\n\n" +
            "🎯 REGLAS:\n" +
            "• Las reinas no pueden estar en la misma fila\n" +
            "• Las reinas no pueden estar en la misma columna\n" +
            "• Las reinas no pueden estar en la misma diagonal\n\n" +
            "🔍 INFORMACIÓN ADICIONAL:\n" +
            "• Haz clic en una celda para ver información detallada\n" +
            "• Las reinas en conflicto se muestran en rojo\n" +
            "• Cambia el tamaño del tablero con el control superior";
        
        JOptionPane.showMessageDialog(
            ventanaPrincipal,
            ayuda,
            "Ayuda - N-Reinas con IA",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Verifica si está en proceso de resolución
     * @return true si está resolviendo
     */
    public boolean isResolviendo() {
        return resolviendo;
    }
    
    /**
     * Obtiene el botón resolver
     * @return Botón resolver
     */
    public JButton getBtnResolver() {
        return btnResolver;
    }
    
    /**
     * Obtiene el botón reiniciar
     * @return Botón reiniciar
     */
    public JButton getBtnReiniciar() {
        return btnReiniciar;
    }
    
    /**
     * Obtiene el botón salir
     * @return Botón salir
     */
    public JButton getBtnSalir() {
        return btnSalir;
    }
}