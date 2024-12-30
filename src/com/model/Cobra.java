package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Cobra extends KeyAdapter {

    public final ArrayList<Point> segmentos = new ArrayList<>();
    private String direcao = "D"; // 'C', 'B', 'E', 'D';
    public Point novoRabo;
    public boolean direcaoAtualizada = false;
    public int pontos = 0;
    int tamBloco = 20;

    public Cobra() {
        segmentos.add(new Point(10, 10)); // Posição inicial da cobra
    }

    public void mover() {
        Point cabeca = segmentos.get(0);
        Point novaCabeca = new Point(cabeca);
        switch (this.direcao) {
            case "C" ->
                novaCabeca.y--;
            case "B" ->
                novaCabeca.y++;
            case "E" ->
                novaCabeca.x--;
            case "D" ->
                novaCabeca.x++;
        }
        segmentos.add(0, novaCabeca);
        novoRabo = segmentos.get(segmentos.size() - 1);
        segmentos.remove(segmentos.size() - 1); // Remove a cauda
    }

    public void crescer() {
        segmentos.add(new Point(novoRabo));
        this.pontos++;

    }

    public void setDirecao(String a) {
        this.direcao = a;

    }

    public String getDirecao() {
        return this.direcao;
    }

    public int getPontos() {
        return this.pontos;
    }

    public boolean verificarColisao(Point ponto) {
        return segmentos.get(0).equals(ponto);
    }

    public boolean verificarColisaoComCorpo() {
        for (int i = 1; i < segmentos.size(); i++) { // Começa no índice 1 para ignorar a cabeça
            if (verificarColisao(segmentos.get(i))) {
                return true;
            }
        }
        return false;
    }
   
    public boolean verificarColisaoCobra(Cobra cobra) {
        for (Point cob: cobra.segmentos) {
            if (verificarColisao(cob)) {
                return true;
            }
        }
        return false;
    }
   
    public boolean verificarColisaoComBordas(Borda borda) {
        for (Point parte : borda.borderLine) {
            if (verificarColisao(parte)) {
                return true;
            }
        }
        return false;
    }

    public boolean verificarColisaoComBordas(int largura, int altura) {
        Point cabeca = segmentos.get(0);
        return cabeca.x < 0 || cabeca.y < 0 || cabeca.x >= largura / 20 || cabeca.y >= altura / 20;
    }

    public boolean verificarColisaoComObstaculos(Obstaculos obst) {
        for (Point parte : obst.obstaculos) {
            if (verificarColisao(parte)) {
                return true;
            }
        }
        return false;
    }

    public void desenhar(Graphics g) {
        g.setColor(Color.GREEN);
        for (Point segmento : segmentos) {
            g.fillRect(segmento.x * tamBloco, segmento.y * tamBloco, tamBloco, tamBloco);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int tecla = e.getKeyCode();

        if (tecla == KeyEvent.VK_UP && !this.getDirecao().equals("B")) {
            this.setDirecao("C");
        } else if (tecla == KeyEvent.VK_DOWN && !this.getDirecao().equals("C")) {
            this.setDirecao("B");
        } else if (tecla == KeyEvent.VK_LEFT && !this.getDirecao().equals("D")) {
            this.setDirecao("E");
        } else if (tecla == KeyEvent.VK_RIGHT && !this.getDirecao().equals("E")) {
            this.setDirecao("D");
        }
        direcaoAtualizada = true;
    }

    public void resetDirecaoAtualizada() {
        this.direcaoAtualizada = false;
    }

}
