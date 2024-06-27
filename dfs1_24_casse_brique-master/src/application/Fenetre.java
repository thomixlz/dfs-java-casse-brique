package application;

import models.Balle;
import models.Barre;
import models.Brique;
import models.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Fenetre extends Canvas implements KeyListener {

    public static final int LARGEUR = 500;
    public static final int HAUTEUR = 700;

    protected boolean toucheEspace = false;
    protected boolean gauchePressee = false;
    protected boolean droitePressee = false;

    private static int score = 0;
    private boolean jeuTermine = false;

    ArrayList<Balle> listeBalles = new ArrayList<>();
    ArrayList<Sprite> listeSprites = new ArrayList<>();
    ArrayList<Brique> listeBriques = new ArrayList<>();
    Barre barre;

    Fenetre() throws InterruptedException {

        JFrame fenetre = new JFrame();

        this.setSize(LARGEUR, HAUTEUR);
        this.setBounds(0, 0, LARGEUR, HAUTEUR);
        this.setIgnoreRepaint(true);
        this.setFocusable(false);

        fenetre.pack();
        fenetre.setSize(LARGEUR, HAUTEUR);
        fenetre.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fenetre.setResizable(false);
        fenetre.requestFocus();
        fenetre.addKeyListener(this);

        Container panneau = fenetre.getContentPane();
        panneau.add(this);

        fenetre.setVisible(true);
        this.createBufferStrategy(2);

        this.demarrer();
    }

    public static void incrementerScore() {
        score++;
    }

    public void demarrer() throws InterruptedException {

        barre = new Barre();
        listeSprites.add(barre);

        Balle balle = new Balle(100, 200, Color.BLACK, 25, barre);
        balle.setListeBriques(listeBriques);
        balle.setListeSprites(listeSprites);
        listeBalles.add(balle);
        listeSprites.add(balle);

        int briqueLargeur = 50;
        int briqueHauteur = 20;
        int margeH = 5;
        int margeV = 5;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                int x = j * (briqueLargeur + margeH);
                int y = i * (briqueHauteur + margeV);
                Brique brique = new Brique(x, y, briqueLargeur, briqueHauteur, Color.RED);
                listeBriques.add(brique);
                listeSprites.add(brique);
            }
        }

        while (true) {

            if (jeuTermine) {
                continue;
            }

            if (gauchePressee) {
                barre.deplacerGauche();
            }
            if (droitePressee) {
                barre.deplacerDroite();
            }

            Graphics2D dessin = (Graphics2D) this.getBufferStrategy().getDrawGraphics();
            dessin.setColor(Color.WHITE);
            dessin.fillRect(0, 0, LARGEUR, HAUTEUR);

            for (Balle b : listeBalles) {
                b.deplacement();
            }

            for (Sprite s : listeSprites) {
                s.dessiner(dessin);
            }

            if (toucheEspace) {
                Balle nouvelleBalle = new Balle(200, 400, Color.BLUE, 50, barre);
                nouvelleBalle.setListeBriques(listeBriques);
                nouvelleBalle.setListeSprites(listeSprites);
                listeBalles.add(nouvelleBalle);
                toucheEspace = false;
            }

            // Affichage du score
            dessin.setColor(Color.BLACK);
            dessin.drawString("Score: " + score, LARGEUR - 100, HAUTEUR - 10);

            dessin.dispose();
            this.getBufferStrategy().show();
            Thread.sleep(1000 / 60);

            // Vérification si toutes les briques sont cassées
            if (listeBriques.isEmpty()) {
                afficherMessageGagner();
                jeuTermine = true;
            }
        }
    }

    private void afficherMessageGagner() {
        JFrame fenetre = (JFrame) SwingUtilities.getWindowAncestor(this);
        JOptionPane.showMessageDialog(fenetre, "GAGNER!", "Fin de Partie", JOptionPane.INFORMATION_MESSAGE);

        int option = JOptionPane.showConfirmDialog(fenetre, "Voulez-vous rejouer?", "Rejouer", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Réinitialiser le jeu
            score = 0;
            jeuTermine = false;
            listeBriques.clear();
            listeSprites.clear();
            listeBalles.clear();
            barre = new Barre();
            listeSprites.add(barre);
            Balle balle = new Balle(100, 200, Color.GREEN, 30, barre);
            balle.setListeBriques(listeBriques);
            balle.setListeSprites(listeSprites);
            listeBalles.add(balle);
            listeSprites.add(balle);
            int briqueLargeur = 50;
            int briqueHauteur = 20;
            int margeH = 5;
            int margeV = 5;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 10; j++) {
                    int x = j * (briqueLargeur + margeH);
                    int y = i * (briqueHauteur + margeV);
                    Brique brique = new Brique(x, y, briqueLargeur, briqueHauteur, Color.RED);
                    listeBriques.add(brique);
                    listeSprites.add(brique);
                }
            }
        } else {
            System.exit(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            toucheEspace = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            gauchePressee = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            droitePressee = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            toucheEspace = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            gauchePressee = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            droitePressee = false;
        }
    }

    public static void main(String[] args) {
        try {
            new Fenetre();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setTitle(String casseBrique) {
        this.setTitle("Casse Brique");
    }
}
