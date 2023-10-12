
package practica.pkg2;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Entrega extends Thread {
    JLabel motorista;
    public int distancia;
    boolean regreso;
    public boolean enviado;
    final int posDestino = Usu2.destino.getLocation().x;
    public Entrega(JLabel motorista, int distancia) {
        this.motorista = motorista;
        this.distancia = distancia;
        this.regreso = false;
        this.enviado = false;
    }
    
    public void run() {
        enviado = true; // cambiar estado de motorista a enviando u ocupado
        while (true) {
            try {
                sleep(distancia);
                int vehiculoPosX = motorista.getLocation().x; // posición actual del motirista
                if (!regreso) {
                    if (vehiculoPosX < posDestino - 125) {
                        motorista.setLocation(vehiculoPosX + 1, motorista.getLocation().y); // actualizar posicion del motorista
                    } else {
                        regreso = true; // final de recorrido para entrega
                        motorista.setIcon(cambio_Direccion((ImageIcon) motorista.getIcon())); // Voltear imagen
                        motorista.repaint(); // actualizar label de motorista
                    }
                } else {
                    if (vehiculoPosX > 125) {
                        motorista.setLocation(vehiculoPosX - 1, motorista.getLocation().y); // actualizar posición del motorista para el regreso
                    } else {
                        break; // terminar entrega
                    }
                }
            } catch (Exception e) {}
        }
        enviado = false; // cambiar estado de motorista a disponible
    }
    public ImageIcon cambio_Direccion(ImageIcon icon) {
        Image img = icon.getImage();
        int width = img.getWidth(null);
        int height = img.getHeight(null);

        BufferedImage flippedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = flippedImage.createGraphics();

        // Aplicar transformación de inversión horizontal
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-width, 0);
        g.drawImage(img, tx, null);
        g.dispose();

        return new ImageIcon(flippedImage);
    }
}
