package telas;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;
import model.Borda;
import model.Cobra;
import model.Comida;
import model.Obstaculos;

public class PainelDoJogo extends JPanel implements Runnable {

    private Cobra cobra;
    private Comida comida;
    private Borda borda;
    private Obstaculos obstaculos;
    private Thread gameThread;
    private boolean emJogo = false;
    private String mapaEscolhido = "Mapa 1"; // Default
    private final int FPS = 10; // 10 atualizações por segundo (equivalente ao Timer de 100ms)

    public PainelDoJogo() {
        setPreferredSize(new Dimension(610, 620));
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    public void selecionarMapa(String mapaSelecionado) {
        this.mapaEscolhido = mapaSelecionado;
    }

    public void iniciarJogo() {
        cobra = new Cobra();
        addKeyListener(cobra);
        emJogo = true;
        comida = new Comida();
        borda = new Borda();
        obstaculos = new Obstaculos("src/res/maps/" + mapaEscolhido + ".txt");

        // Inicia a thread do jogo
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // Intervalo em nanosegundos (1s / FPS)
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null && emJogo) {
            update();  // Atualiza a lógica do jogo
            repaint(); // Redesenha o painel

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000; // Converte para milissegundos

                if (remainingTime < 0) {
                    remainingTime = 0; // Evita tempo negativo
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        if (emJogo) {
            cobra.mover(); // Move a cobra
    
            // Verifica colisão com comida
            if (cobra.verificarColisao(comida.getPosicao())) {
                cobra.crescer();
                comida.gerarNovaPosicao();
                while (comida.testarPosBorda(borda) || comida.testarPosCobra(cobra) || comida.testarPosObs(obstaculos)) {
                    comida.gerarNovaPosicao();
                }
            }
    
            // Verifica colisão com corpo, bordas ou obstáculos
            if (cobra.verificarColisaoComCorpo() || cobra.verificarColisaoComBordas(borda) || cobra.verificarColisaoComObstaculos(obstaculos)) {
                emJogo = false;
                mostrarTelaDePontos();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (emJogo) {
            comida.desenhar(g);
            cobra.desenhar(g);
            borda.desenhar(g);
            obstaculos.desenhar(g);
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("PONTOS: " + cobra.pontos, 25, 15);
        } else {
            g.setColor(Color.WHITE);
            g.drawString("Game Over!", 250, 300);
        }
    }

    private void mostrarTelaDePontos() {
        TelaDePontos telaDePontos = new TelaDePontos(cobra.getPontos());
        Container parent = getParent();
        parent.remove(this);
        parent.add(telaDePontos);
        parent.revalidate();
        parent.repaint();
    }

    // Método para parar a thread (opcional, caso precise)
    public void pararJogo() {
        gameThread = null;
    }
}