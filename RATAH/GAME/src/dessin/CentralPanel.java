package dessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JPanel;
import game.Civilisation;
import game.Population;

public class CentralPanel extends JPanel {

    private List<Civilisation> civilisations;

    public void setCivilisations(List<Civilisation> civilisations) {
        this.civilisations = civilisations;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (civilisations != null) {
            int squareSize = 50;
            int gap = 5; // Ajustez cette valeur pour contrôler l'espace entre les carrés
            int horizontalGap = 10; // Ajustez cette valeur pour contrôler l'espace horizontal entre les carrés
            int yOffset = 0; // Décalage vertical initial

            for (int i = 0; i < civilisations.size(); i++) {
                int xOffset = 0; // Réinitialise le décalage horizontal à chaque itération

                // Dessiner les carrés pour chaque civilisation
                drawSquare(g, xOffset , yOffset, squareSize, Color.RED, civilisations.get(i), "Population");
                drawSquare(g, xOffset + 10 + squareSize + horizontalGap, yOffset, squareSize, Color.GREEN, civilisations.get(i), "Militaire");
                drawSquare(g, xOffset + 2 * (gap + squareSize + horizontalGap * 2), yOffset, squareSize, Color.BLUE, civilisations.get(i), "Chercheur");

                // Ajuste le décalage vertical pour la prochaine ligne
                yOffset += gap + squareSize + 40; // Ajoutez plus d'espace vertical
            }
        } else {
            System.out.println("null");
        }
    }

    private void drawSquare(Graphics g, int xOffset, int yOffset, int squareSize, Color color, Civilisation civilisation, String type) {
        g.setColor(color);
        g.fillRect(xOffset, yOffset, squareSize, squareSize);

        // Ajouter le listener au carré
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                // Vérifier si le clic est dans le carré
                if (mouseX >= xOffset && mouseX <= xOffset + squareSize && mouseY >= yOffset && mouseY <= yOffset + squareSize) {
                    // Créer une population lorsque le carré est cliqué
                    Population population = civilisation.createPopulation(199, 100);

                    // Vérifier si la population a été créée avec succès
                    if (population != null) {
                        System.out.println(type + " pour la civilisation " + civilisation.getNom() + " créée.");
                    } else {
                        System.out.println("La création de la population pour la civilisation " + civilisation.getNom() + " a échoué.");
                    }
                }
            }
        });
    }


}