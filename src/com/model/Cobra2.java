package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Cobra2 extends Cobra {
    
    private String direcao = "E"; // Direção inicial: Esquerda ('C', 'B', 'E', 'D')
    private String direcaoPendente = null; // Direção pendente para o próximo movimento
    public final ArrayList<Point> segmentos = new ArrayList<>();
    public Point novoRabo;
    public int pontos = 0;
    int tamBloco = 20; // Mantido de Cobra, assumindo que é herdado ou necessário

    public Cobra2() {
        segmentos.add(new Point(20, 20)); // Posição inicial da cobra
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        String novaDirecao = null;

        if (tecla == KeyEvent.VK_W && !this.direcao.equals("B")) {
            novaDirecao = "C";
        } else if (tecla == KeyEvent.VK_S && !this.direcao.equals("C")) {
            novaDirecao = "B";
        } else if (tecla == KeyEvent.VK_A && !this.direcao.equals("D")) {
            novaDirecao = "E";
        } else if (tecla == KeyEvent.VK_D && !this.direcao.equals("E")) {
            novaDirecao = "D";
        }

        if (novaDirecao != null) {
            direcaoPendente = novaDirecao; // Armazena a nova direção como pendente
        }
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

    public int getPontos() {
        return this.pontos;
    }

    public String getDirecao() {
        return this.direcao;
    }

    public boolean verificarColisao(Point ponto) {
        return segmentos.get(0).equals(ponto);
    }

    public void desenhar(Graphics g) {
        g.setColor(Color.BLUE);
        for (Point segmento : segmentos) {
            g.fillRect(segmento.x * tamBloco, segmento.y * tamBloco, tamBloco, tamBloco);
        }
    }
}