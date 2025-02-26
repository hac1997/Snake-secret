package telas;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;
import model.Borda;
import model.Cobra;
import model.Cobra2;
import model.Comida;
import model.Obstaculos;

public class PainelMultiplayer extends JPanel implements Runnable {

    private Cobra2 cobra2;
    private Cobra cobra;
    private Comida comida;
    private Borda borda;
    private Obstaculos obstaculos;
    private Thread gameThread;
    private boolean emJogo = false;
    private boolean multiplayer = false;
    private String mapaEscolhido = "Mapa 1";
    private int countdown = 3; // Contagem regressiva inicial
    private boolean contagemAtiva = false; // Controla a contagem regressiva
    private long ultimoTempoContagem; // Para controlar o tempo da contagem
    private final int FPS = 10; // 10 atualizações por segundo

    public PainelMultiplayer() {
        setPreferredSize(new Dimension(610, 620));
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    public void selecionarMapa(String mapaSelecionado) {
        this.mapaEscolhido = mapaSelecionado;
    }

    public void iniciarMultiplayer() {
        this.multiplayer = true;
        iniciarJogo();
    }

    void iniciarJogo() {
        cobra = new Cobra();
        cobra2 = new Cobra2();
        addKeyListener(cobra);
        addKeyListener(cobra2);

        comida = new Comida();
        borda = new Borda();
        obstaculos = new Obstaculos("src/res/maps/" + mapaEscolhido + ".txt");

        countdown = 3;
        contagemAtiva = true;
        ultimoTempoContagem = System.nanoTime();

        // Inicia a thread do jogo
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // Intervalo em nanosegundos (1s / FPS)
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
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
        if (contagemAtiva) {
            long tempoAtual = System.nanoTime();
            if ((tempoAtual - ultimoTempoContagem) >= 1000000000) { // 1 segundo
                countdown--;
                ultimoTempoContagem = tempoAtual;
                if (countdown <= 0) {
                    contagemAtiva = false;
                    emJogo = true; // Inicia o movimento após a contagem
                }
            }
        }
    
        if (emJogo) {
            cobra.mover();
            cobra2.mover();
    
            if (cobra.verificarColisao(comida.getPosicao())) {
                cobra.crescer();
                comida.gerarNovaPosicao();
                while (comida.testarPosBorda(borda) || comida.testarPosCobra(cobra) || comida.testarPosObs(obstaculos) || comida.testarPosCobra(cobra2)) {
                    comida.gerarNovaPosicao();
                }
            }
            if (cobra2.verificarColisao(comida.getPosicao())) {
                cobra2.crescer();
                comida.gerarNovaPosicao();
                while (comida.testarPosBorda(borda) || comida.testarPosCobra(cobra) || comida.testarPosObs(obstaculos) || comida.testarPosCobra(cobra2)) {
                    comida.gerarNovaPosicao();
                }
            }
    
            // Verifica colisões e termina o jogo imediatamente
            if (cobra.verificarColisaoComCorpo() || cobra.verificarColisaoComBordas(borda) || cobra.verificarColisaoComObstaculos(obstaculos)
                    || cobra.verificarColisaoCobra(cobra2)) {
                emJogo = false;
                mostrarTelaDePontosMultiplayer(false, false); // Jogador 2 venceu por colisão
            }
            if (cobra2.verificarColisaoComCorpo() || cobra2.verificarColisaoComBordas(borda) || cobra2.verificarColisaoComObstaculos(obstaculos)
                    || cobra2.verificarColisaoCobra(cobra)) {
                emJogo = false;
                mostrarTelaDePontosMultiplayer(true, false); // Jogador 1 venceu por colisão
            }
    
            // Verifica se um jogador está 5 pontos à frente
            if (Math.abs(cobra.getPontos() - cobra2.getPontos()) >= 5) {
                emJogo = false;
                if (cobra.getPontos() > cobra2.getPontos()) {
                    mostrarTelaDePontosMultiplayer(true, true); // Jogador 1 venceu por pontos
                } else {
                    mostrarTelaDePontosMultiplayer(false, true); // Jogador 2 venceu por pontos
                }
            }
        }
    }

    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        comida.desenhar(g);
        cobra.desenhar(g);
        cobra2.desenhar(g);
        borda.desenhar(g);
        obstaculos.desenhar(g);

        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("PONTOS PLAYER 1: " + cobra.pontos, 25, 15);
        g.setColor(Color.BLUE);
        g.drawString("PONTOS PLAYER 2: " + cobra2.pontos, 400, 15);

        if (countdown > 0) {
            g.setFont(new Font("Arial", Font.BOLD, 80));
            g.setColor(new Color(255, 255, 255, 150)); // Branco transparente
            g.drawString(String.valueOf(countdown), getWidth() / 2 - 20, getHeight() / 2);
        }
    }


    private void mostrarTelaDePontosMultiplayer(boolean jogador1Venceu, boolean venceuPorPontos) {
        // Imprime as posições da Cobra do Jogador 1
        System.out.println("Posição final da Cobra do Jogador 1:");
        for (Point segmento : cobra.segmentos) {
            System.out.println("(" + segmento.x + ", " + segmento.y + ")");
        }
    
        // Imprime as posições da Cobra do Jogador 2
        System.out.println("Posição final da Cobra do Jogador 2:");
        for (Point segmento : cobra2.segmentos) {
            System.out.println("(" + segmento.x + ", " + segmento.y + ")");
        }
    
        // Exibe a tela de pontos
        TelaDePontosMultiplayer telaDePontosMultiplayer = new TelaDePontosMultiplayer(cobra.getPontos(), cobra2.getPontos(), jogador1Venceu, venceuPorPontos);
        Container parent = getParent();
        parent.remove(this);
        parent.add(telaDePontosMultiplayer);
        parent.revalidate();
        parent.repaint();
    }

    // Método para parar a thread (opcional, caso precise)
    public void pararJogo() {
        gameThread = null;
    }
}