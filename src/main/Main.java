package main;

import presentacion.VentanaPrincipal;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Clase principal que inicia la aplicación N-Reinas.
 * Configura el entorno y lanza la interfaz gráfica.
 * 
 * @author Proyecto N-Reinas
 * @version 2.0
 */
public class Main {
    
    public static void main(String[] args) {
        // Configurar Look and Feel nativo del sistema (compatible con todas las versiones de Java)
        configurarLookAndFeel();
        
        // Iniciar la interfaz gráfica en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            try {
                VentanaPrincipal ventana = new VentanaPrincipal();
                ventana.setVisible(true);
            } catch (Exception e) {
                System.err.println("Error al inicializar la ventana principal: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
    
    /**
     * Configura el Look and Feel de manera compatible con todas las versiones de Java
     */
    private static void configurarLookAndFeel() {
        try {
            // Intentar con el Look and Feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            try {
                // Si falla, intentar con Nimbus (moderno y elegante)
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception ex) {
                try {
                    // Como última opción, usar Metal (siempre disponible)
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                } catch (Exception exc) {
                    // Si todo falla, usar el Look and Feel por defecto
                    System.err.println("No se pudo establecer ningún Look and Feel personalizado, usando el por defecto.");
                }
            }
        }
    }
}