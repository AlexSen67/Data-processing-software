package Presentation;

import javax.swing.*;
import java.awt.*;
import java.util.Map;


public class Diagramme extends JPanel {
    private Map<String, Integer> data;

    public Diagramme(Map<String, Integer> data) {
        this.data = data;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int spacing = 40; // Espacement entre les barres et les libellés
        int startY = getHeight() - 30;
        int startX = 30;

        int maxValue = 0;
        int totalWidth = getWidth() - 2 * startX; // Largeur totale disponible pour les barres
        int numBars = data.size();
        int barWidth = (totalWidth - (numBars - 1) * spacing) / numBars; // Largeur de chaque barre

        for (int value : data.values()) {
            if (value > maxValue) {
                maxValue = value;
            }
        }

        int maxHeight = getHeight() - 60; // Hauteur maximale pour les barres
        int x = startX;
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            int value = entry.getValue();
            int barHeight = (int) ((double) value / maxValue * maxHeight);
            int startYAdjusted = startY - barHeight;
            g2d.setColor(Color.RED);
            g2d.fillRect(x, startYAdjusted, barWidth, barHeight);
            g2d.setColor(Color.BLACK);
            String label = entry.getKey();
            int labelWidth = g2d.getFontMetrics().stringWidth(label);
            // Dessiner le libellé avec une marge à gauche pour l'espacement
            g2d.drawString(label, x + (barWidth - labelWidth) / 2, startY + 15);
            g2d.drawString(String.valueOf(value), x + (barWidth - g2d.getFontMetrics().stringWidth(String.valueOf(value))) / 2, startYAdjusted - 5);
            x += (barWidth + spacing);
        }
    }

    
}
