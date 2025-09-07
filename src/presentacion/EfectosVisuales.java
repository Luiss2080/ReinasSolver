package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

/**
 * Maneja efectos visuales y animaciones para enriquecer la experiencia
 * del usuario. Incluye mensajes emergentes, animaciones de éxito y
 * notificaciones visuales durante la resolución del problema.
 * 
 * @author Proyecto N-Reinas
 * @version 2.0
 */
public class EfectosVisuales {
    private VentanaPrincipal ventanaPrincipal;
    private JDialog dialogoMensaje;
    
    /**
     * Constructor de efectos visuales
     * @param ventanaPrincipal Referencia a la ventana principal
     */
    public EfectosVisuales(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
    }
    
    /**
     * Muestra un mensaje de éxito con animación
     * @param mensaje Mensaje a mostrar
     */
    public void mostrarMensajeExito(String mensaje) {
        mostrarMensajeAnimado(mensaje, TipoMensaje.EXITO);
    }
    
    /**
     * Muestra un mensaje de error con animación
     * @param mensaje Mensaje a mostrar
     */
    public void mostrarMensajeError(String mensaje) {
        mostrarMensajeAnimado(mensaje, TipoMensaje.ERROR);
    }
    
    /**
     * Muestra un mensaje de información con animación
     * @param mensaje Mensaje a mostrar
     */
    public void mostrarMensajeInfo(String mensaje) {
        mostrarMensajeAnimado(mensaje, TipoMensaje.INFO);
    }
    
    /**
     * Muestra un mensaje de advertencia con animación
     * @param mensaje Mensaje a mostrar
     */
    public void mostrarMensajeAdvertencia(String mensaje) {
        mostrarMensajeAnimado(mensaje, TipoMensaje.ADVERTENCIA);
    }
    
    /**
     * Muestra un mensaje animado personalizable
     * @param mensaje Mensaje a mostrar
     * @param tipo Tipo de mensaje
     */
    private void mostrarMensajeAnimado(String mensaje, TipoMensaje tipo) {
        // Cerrar mensaje anterior si existe
        if (dialogoMensaje != null) {
            dialogoMensaje.dispose();
        }
        
        SwingUtilities.invokeLater(() -> {
            crearDialogoAnimado(mensaje, tipo);
        });
    }
    
    /**
     * Crea un diálogo animado personalizado
     * @param mensaje Mensaje a mostrar
     * @param tipo Tipo de mensaje
     */
    private void crearDialogoAnimado(String mensaje, TipoMensaje tipo) {
        dialogoMensaje = new JDialog(ventanaPrincipal, "Notificación", false);
        dialogoMensaje.setUndecorated(true);
        
        // Panel personalizado
        MensajePanel panel = new MensajePanel(mensaje, tipo);
        dialogoMensaje.add(panel);
        dialogoMensaje.pack();
        
        // Posicionar en la esquina superior derecha
        Point ubicacionVentana = ventanaPrincipal.getLocationOnScreen();
        Dimension tamanoVentana = ventanaPrincipal.getSize();
        Dimension tamanoDialogo = dialogoMensaje.getSize();
        
        int x = ubicacionVentana.x + tamanoVentana.width - tamanoDialogo.width - 20;
        int y = ubicacionVentana.y + 60;
        
        dialogoMensaje.setLocation(x, y);
        
        // Hacer visible con efecto fade-in
        dialogoMensaje.setOpacity(0.0f);
        dialogoMensaje.setVisible(true);
        
        // Animación fade-in
        Timer fadeInTimer = new Timer(50, null);
        fadeInTimer.addActionListener(new ActionListener() {
            float opacity = 0.0f;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += 0.1f;
                if (opacity >= 1.0f) {
                    opacity = 1.0f;
                    fadeInTimer.stop();
                    
                    // Programar cierre automático después de 3 segundos
                    Timer closeTimer = new Timer(3000, evt -> {
                        cerrarMensajeConAnimacion();
                    });
                    closeTimer.setRepeats(false);
                    closeTimer.start();
                }
                dialogoMensaje.setOpacity(opacity);
            }
        });
        fadeInTimer.start();
    }
    
    /**
     * Cierra el mensaje con animación fade-out
     */
    private void cerrarMensajeConAnimacion() {
        if (dialogoMensaje == null || !dialogoMensaje.isVisible()) {
            return;
        }
        
        Timer fadeOutTimer = new Timer(50, null);
        fadeOutTimer.addActionListener(new ActionListener() {
            float opacity = 1.0f;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.1f;
                if (opacity <= 0.0f) {
                    opacity = 0.0f;
                    fadeOutTimer.stop();
                    dialogoMensaje.dispose();
                    dialogoMensaje = null;
                } else {
                    dialogoMensaje.setOpacity(opacity);
                }
            }
        });
        fadeOutTimer.start();
    }
    
    /**
     * Muestra una animación de celebración cuando se resuelve el problema
     */
    public void mostrarAnimacionCelebracion() {
        // Crear ventana de celebración temporal
        JWindow ventanaCelebracion = new JWindow(ventanaPrincipal);
        CelebracionPanel panelCelebracion = new CelebracionPanel();
        ventanaCelebracion.add(panelCelebracion);
        ventanaCelebracion.setSize(400, 200);
        ventanaCelebracion.setLocationRelativeTo(ventanaPrincipal);
        
        // Mostrar con transparencia
        ventanaCelebracion.setOpacity(0.9f);
        ventanaCelebracion.setVisible(true);
        
        // Cerrar después de la animación
        Timer closeTimer = new Timer(4000, e -> {
            ventanaCelebracion.dispose();
        });
        closeTimer.setRepeats(false);
        closeTimer.start();
    }
    
    /**
     * Resalta una celda del tablero temporalmente
     * @param fila Fila a resaltar
     * @param columna Columna a resaltar
     */
    public void resaltarCelda(int fila, int columna) {
        // Esta funcionalidad se puede implementar en el futuro
        // para mostrar el proceso de resolución paso a paso
    }
    
    /**
     * Enumeration para tipos de mensaje
     */
    private enum TipoMensaje {
        EXITO(new Color(46, 204, 113), "✅", Color.WHITE),
        ERROR(new Color(231, 76, 60), "❌", Color.WHITE),
        INFO(new Color(52, 152, 219), "ℹ️", Color.WHITE),
        ADVERTENCIA(new Color(241, 196, 15), "⚠️", Color.BLACK);
        
        private final Color colorFondo;
        private final String icono;
        private final Color colorTexto;
        
        TipoMensaje(Color colorFondo, String icono, Color colorTexto) {
            this.colorFondo = colorFondo;
            this.icono = icono;
            this.colorTexto = colorTexto;
        }
    }
    
    /**
     * Panel personalizado para mostrar mensajes
     */
    private class MensajePanel extends JPanel {
        private String mensaje;
        private TipoMensaje tipo;
        
        public MensajePanel(String mensaje, TipoMensaje tipo) {
            this.mensaje = mensaje;
            this.tipo = tipo;
            setPreferredSize(new Dimension(300, 80));
            setOpaque(false);
            
            // Agregar listener para cerrar al hacer clic
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    cerrarMensajeConAnimacion();
                }
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int width = getWidth();
            int height = getHeight();
            
            // Fondo redondeado con sombra
            g2d.setColor(new Color(0, 0, 0, 50));
            g2d.fill(new RoundRectangle2D.Float(3, 3, width - 6, height - 6, 15, 15));
            
            // Fondo del mensaje
            g2d.setColor(tipo.colorFondo);
            g2d.fill(new RoundRectangle2D.Float(0, 0, width - 3, height - 3, 15, 15));
            
            // Borde
            g2d.setColor(tipo.colorFondo.darker());
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(new RoundRectangle2D.Float(1, 1, width - 5, height - 5, 15, 15));
            
            // Icono
            g2d.setColor(tipo.colorTexto);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            FontMetrics fmIcono = g2d.getFontMetrics();
            int iconoX = 15;
            int iconoY = height / 2 + fmIcono.getAscent() / 2;
            g2d.drawString(tipo.icono, iconoX, iconoY);
            
            // Texto del mensaje
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            FontMetrics fmTexto = g2d.getFontMetrics();
            
            // Dividir el mensaje en líneas si es muy largo
            String[] lineas = dividirTexto(mensaje, fmTexto, width - 60);
            int lineaY = height / 2 - (lineas.length * fmTexto.getHeight()) / 2 + fmTexto.getAscent();
            
            for (String linea : lineas) {
                int textoX = 50;
                g2d.drawString(linea, textoX, lineaY);
                lineaY += fmTexto.getHeight();
            }
            
            // Indicador de clic para cerrar
            g2d.setFont(new Font("Arial", Font.PLAIN, 8));
            g2d.setColor(tipo.colorTexto.darker());
            g2d.drawString("Clic para cerrar", width - 80, height - 5);
            
            g2d.dispose();
        }
        
        /**
         * Divide el texto en líneas para ajustarse al ancho disponible
         */
        private String[] dividirTexto(String texto, FontMetrics fm, int anchoMaximo) {
            String[] palabras = texto.split(" ");
            java.util.List<String> lineas = new java.util.ArrayList<>();
            StringBuilder lineaActual = new StringBuilder();
            
            for (String palabra : palabras) {
                String pruebaLinea = lineaActual.length() > 0 ? 
                    lineaActual + " " + palabra : palabra;
                
                if (fm.stringWidth(pruebaLinea) <= anchoMaximo) {
                    lineaActual = new StringBuilder(pruebaLinea);
                } else {
                    if (lineaActual.length() > 0) {
                        lineas.add(lineaActual.toString());
                        lineaActual = new StringBuilder(palabra);
                    } else {
                        lineas.add(palabra);
                    }
                }
            }
            
            if (lineaActual.length() > 0) {
                lineas.add(lineaActual.toString());
            }
            
            return lineas.toArray(new String[0]);
        }
    }
    
    /**
     * Panel para mostrar animación de celebración
     */
    private class CelebracionPanel extends JPanel {
        private Timer animTimer;
        private int frame = 0;
        private final int maxFrames = 60;
        
        public CelebracionPanel() {
            setBackground(new Color(46, 204, 113, 200));
            setOpaque(false);
            
            animTimer = new Timer(66, e -> {
                frame++;
                if (frame >= maxFrames) {
                    frame = 0;
                }
                repaint();
            });
            animTimer.start();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int width = getWidth();
            int height = getHeight();
            
            // Fondo semi-transparente
            g2d.setColor(new Color(46, 204, 113, 180));
            g2d.fill(new RoundRectangle2D.Float(20, 20, width - 40, height - 40, 20, 20));
            
            // Texto principal
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            FontMetrics fm = g2d.getFontMetrics();
            String texto = "🎉 ¡SOLUCIÓN ENCONTRADA! 🎉";
            int textX = (width - fm.stringWidth(texto)) / 2;
            int textY = height / 2 - 10;
            g2d.drawString(texto, textX, textY);
            
            // Subtexto
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            fm = g2d.getFontMetrics();
            String subtexto = "El algoritmo ha resuelto exitosamente el problema";
            int subtextX = (width - fm.stringWidth(subtexto)) / 2;
            int subtextY = height / 2 + 20;
            g2d.drawString(subtexto, subtextX, subtextY);
            
            // Efectos de partículas animadas
            dibujarParticulas(g2d, width, height);
            
            g2d.dispose();
        }
        
        /**
         * Dibuja partículas animadas para el efecto de celebración
         */
        private void dibujarParticulas(Graphics2D g2d, int width, int height) {
            g2d.setColor(Color.YELLOW);
            
            for (int i = 0; i < 20; i++) {
                int x = (int) (Math.sin(frame * 0.1 + i) * 50 + width / 2);
                int y = (int) (Math.cos(frame * 0.08 + i * 2) * 30 + height / 2);
                int size = (int) (Math.sin(frame * 0.2 + i) * 3 + 5);
                
                g2d.fillOval(x - size / 2, y - size / 2, size, size);
            }
        }
    }
}