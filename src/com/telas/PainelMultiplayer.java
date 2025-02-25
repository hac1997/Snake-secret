package telas;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Borda;
import model.Cobra;
import model.Cobra2;
import model.Comida;
import model.Obstaculos;

public class PainelMultiplayer extends JPanel {

    private Cobra2 cobra2;
    private Cobra cobra;
    private Comida comida;
    private Borda borda;
    private Obstaculos obstaculos;
    private Timer timer;
    private Timer countdownTimer;
    private boolean emJogo = false;
    private boolean multiplayer = false;
    private String mapaEscolhido = "Mapa 1";
    private int countdown = 3; // Contagem regressiva inicial

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
        countdownTimer = new Timer(1000, e -> {
            countdown--;
            if (countdown <= 0) {
                countdownTimer.stop();
                iniciarMovimento();
            }
            repaint();
        });
        countdownTimer.start();
    }

    private void iniciarMovimento() {
        this.emJogo = true;
        timer = new Timer(100, e -> actionPerformed(e));
        timer.start();
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
        g.setColor(Color.blue);
        g.drawString("PONTOS PLAYER 2: " + cobra2.pontos, 400, 15);

        if (countdown > 0) {
            g.setFont(new Font("Arial", Font.BOLD, 80));
            g.setColor(new Color(255, 255, 255, 150)); // Branco transparente
            g.drawString(String.valueOf(countdown), getWidth() / 2 - 20, getHeight() / 2);
        }
    }

    private void actionPerformed(ActionEvent e) {
        if (emJogo && timer != null) {
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

            if (cobra.verificarColisaoComCorpo() || cobra.verificarColisaoComBordas(borda) || cobra.verificarColisaoComObstaculos(obstaculos)
                    || cobra.verificarColisaoCobra(cobra2)) {
                emJogo = false;
                timer.stop();
                mostrarTelaDePontosMultiplayer();
            }
            if (cobra2.verificarColisaoComCorpo() || cobra2.verificarColisaoComBordas(borda) || cobra2.verificarColisaoComObstaculos(obstaculos) || cobra2.verificarColisaoCobra(cobra)) {
                emJogo = false;
                timer.stop();
                mostrarTelaDePontosMultiplayer();
            }
        }
        repaint();
    }

    private void mostrarTelaDePontosMultiplayer() {
        TelaDePontosMultiplayer telaDePontosMultiplayer = new TelaDePontosMultiplayer(cobra.getPontos(), cobra2.getPontos());
        Container parent = getParent();
        parent.remove(this);
        parent.add(telaDePontosMultiplayer);
        parent.revalidate();
        parent.repaint();
    }
}