package models;

import java.awt.*;

public abstract class Sprite {
    protected int x = 0;
    protected int y = 0;
    protected Color couleur = Color.BLACK;

    public Sprite(int x, int y, Color couleur) {
        this.x = x;
        this.y = y;
        this.couleur = couleur;
    }

    public abstract void dessiner(Graphics2D dessin);
}
