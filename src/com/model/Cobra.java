package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Cobra extends KeyAdapter {

    public final ArrayList<Point> segmentos = new ArrayList<>();
    private String direcao = "D"; // Direção atual
    private String direcaoPendente = null; // Direção pendente
    public Point novoRabo;
    public int pontos = 0;
    int tamBloco = 20;

    public Cobra() {
        segmentos.add(new Point(10, 10)); // Posição inicial da cobra
    }

    public void mover() {
        Point cabeca = segmentos.get(0);
        Point novaCabeca = new Point(cabeca);
        switch (this.direcao) {
            case "C" -> novaCabeca.y--;
            case "B" -> novaCabeca.y++;
            case "E" -> novaCabeca.x--;
            case "D" -> novaCabeca.x++;
        }
        segmentos.add(0, novaCabeca);
        novoRabo = segmentos.get(segmentos.size() - 1);
        segmentos.remove(segmentos.size() - 1); // Remove a cauda

        // Aplica a direção pendente, se houver
        if (direcaoPendente != null) {
            direcao = direcaoPendente;
            direcaoPendente = null;
        }
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
        for (int i = 1; i < segmentos.size(); i++) {
            if (verificarColisao(segmentos.get(i))) {
                System.out.println("Autocolisão detectada! Cabeça: " + segmentos.get(0) + ", Segmento: " + segmentos.get(i));
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
        String novaDirecao = null;

        if (tecla == KeyEvent.VK_UP && !this.direcao.equals("B")) {
            novaDirecao = "C";
        } else if (tecla == KeyEvent.VK_DOWN && !this.direcao.equals("C")) {
            novaDirecao = "B";
        } else if (tecla == KeyEvent.VK_LEFT && !this.direcao.equals("D")) {
            novaDirecao = "E";
        } else if (tecla == KeyEvent.VK_RIGHT && !this.direcao.equals("E")) {
            novaDirecao = "D";
        }

        if (novaDirecao != null) {
            direcaoPendente = novaDirecao; // Armazena a nova direção como pendente
        }
    }



}
