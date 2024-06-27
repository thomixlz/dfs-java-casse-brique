package models;

import application.Fenetre;

import java.awt.*;

public class Barre extends Rectangle {
    private static final int VITESSE = 10;

    public Barre(int x, int y, int largeur, int hauteur, Color couleur) {
        super(x, y, largeur, hauteur, couleur);
    }

    public Barre() {
        super(250 - 75, 600, 150, 15, Color.BLACK);
    }

    public void deplacerDroite() {
        if (x < Fenetre.LARGEUR - largeur) {
            x += VITESSE;
        }
    }

    public void deplacerGauche() {
        if (x > 0) {
            x -= VITESSE;
        }
    }

}
