package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Borda {

    public final ArrayList<Point> borderLine = new ArrayList<>();

    public Borda() {
        for (int i = 0; i < 30; i++) {
            borderLine.add(new Point(i, 0));
            borderLine.add(new Point(0, i));
            borderLine.add(new Point(i, 29));
            borderLine.add(new Point(29, i));
        }
    }
    public void desenhar(Graphics g) {
        g.setColor(Color.WHITE);
        for (Point tilha : borderLine) {
            g.fillRect(tilha.x * 20, tilha.y * 20, 20, 20);
        }
    }

}
