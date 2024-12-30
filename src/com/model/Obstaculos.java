package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Obstaculos {

    public final ArrayList<Point> obstaculos = new ArrayList<>();

    public Obstaculos(String caminhoArquivo) {
        carregarObstaculos(caminhoArquivo);
    }

    public void desenhar(Graphics g) {
        g.setColor(Color.YELLOW);
        for (Point obstaculo : obstaculos) {
            g.fillRect(obstaculo.x * 20, obstaculo.y * 20, 20, 20);
        }
    }

    private void carregarObstaculos(String caminhoArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha = reader.readLine();
            int y = 1; // Linha no tabuleiro
            while (linha != null) {
                for (int x = 0; x < linha.length(); x++) {
                    if (linha.charAt(x) == '1') {
                        obstaculos.add(new Point(x+1, y));
                    }
                }
                y++;
                linha = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo de obstáculos: " + e.getMessage());
        }
    }
}
