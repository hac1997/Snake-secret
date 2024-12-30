package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public final class Comida {

    public Point posicao;

    public Comida() {
        gerarNovaPosicao();
    }

    public void gerarNovaPosicao() {
        Random random = new Random();
        int x = random.nextInt(26)+1;
        int y = random.nextInt(26)+1;
        posicao = new Point(x, y);
    }

    public boolean testarPosCobra(Cobra cobra) {
        for (Point cob: cobra.segmentos) {
            if (this.posicao.equals(cob)) {
                return true;
            }
        }
        return false;
    }
    public boolean testarPosBorda(Borda borda) {
        for (Point bord : borda.borderLine) {
            if (this.posicao.equals(bord)) {
                return true;
            }
        }
        return false;
    }
    public boolean testarPosObs(Obstaculos obst){
        for (Point bord : obst.obstaculos) {
            if (this.posicao.equals(bord)) {
                return true;
            }
        }
        return false;
    }
    

    public Point getPosicao() {
        return posicao;
    }

    public void desenhar(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(posicao.x * 20, posicao.y * 20, 20, 20);
    }

}
