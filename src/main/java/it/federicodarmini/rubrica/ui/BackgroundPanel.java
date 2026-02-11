package it.federicodarmini.rubrica.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BackgroundPanel extends JPanel {

    private BufferedImage bg;

    public BackgroundPanel(String resourcePath) {
        setLayout(new BorderLayout());
        setOpaque(true);

        try {
            bg = ImageIO.read(BackgroundPanel.class.getResource(resourcePath));
        } catch (IOException | IllegalArgumentException e) {
            // Se manca l'immagine, non crashiamo: sfondo semplice
            bg = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bg == null) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int w = getWidth();
        int h = getHeight();

        // Copertura tipo “cover” (riempie tutto mantenendo aspect ratio)
        double panelRatio = (double) w / h;
        double imgRatio = (double) bg.getWidth() / bg.getHeight();

        int drawW, drawH;
        if (imgRatio > panelRatio) {
            drawH = h;
            drawW = (int) (h * imgRatio);
        } else {
            drawW = w;
            drawH = (int) (w / imgRatio);
        }

        int x = (w - drawW) / 2;
        int y = (h - drawH) / 2;

        g2.drawImage(bg, x, y, drawW, drawH, null);

        // Overlay leggero per leggibilità (effetto “glass”)
        g2.setComposite(AlphaComposite.SrcOver.derive(0.55f));
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, w, h);

        g2.dispose();
    }
}
