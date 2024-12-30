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
    private boolean emJogo = false;
    private boolean multiplayer = false;
    private String mapaEscolhido = "Mapa 1"; // Default

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
        // Lógica para multiplayer (talvez mais cobras ou outro comportamento)
        iniciarJogo();
    }

    void iniciarJogo() {
        cobra = new Cobra();
        cobra2 = new Cobra2();
        addKeyListener(cobra);
        addKeyListener(cobra2);

        this.emJogo = true;

        comida = new Comida();
        borda = new Borda();
        obstaculos = new Obstaculos("src/res/maps/" + mapaEscolhido + ".txt"); // Carrega o mapa conforme a escolha
        timer = new Timer(100, e -> actionPerformed(e));
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (emJogo) {
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

        } else {
            g.setColor(Color.WHITE);
            g.drawString("Game Over!", 250, 300);
        }
    }

    private void actionPerformed(ActionEvent e) {
        if (emJogo && timer != null) {
            cobra.mover();
            cobra2.mover();

            // Verificar se a cobra colidiu com a comida
            if (cobra.verificarColisao(comida.getPosicao())) {
                cobra.crescer(); // A cobra cresce quando come a comida
                comida.gerarNovaPosicao(); // Gera uma nova posição para a comida
                while (comida.testarPosBorda(borda) || comida.testarPosCobra(cobra) || comida.testarPosObs(obstaculos) || comida.testarPosCobra(cobra2)) {
                    comida.gerarNovaPosicao();
                }
            }
            if (cobra2.verificarColisao(comida.getPosicao())) {
                cobra2.crescer(); // A cobra cresce quando come a comida
                comida.gerarNovaPosicao(); // Gera uma nova posição para a comida
                while (comida.testarPosBorda(borda) || comida.testarPosCobra(cobra) || comida.testarPosObs(obstaculos) || comida.testarPosCobra(cobra2)) {
                    comida.gerarNovaPosicao();
                }
            }
            // Verificar colisão com o corpo e as bordas após o movimento e crescimento
            if (cobra.verificarColisaoComCorpo() || cobra.verificarColisaoComBordas(borda) || cobra.verificarColisaoComObstaculos(obstaculos)
                    || cobra.verificarColisaoCobra(cobra2)) {
                emJogo = false;
                timer.stop(); // Para o jogo
                mostrarTelaDePontosMultiplayer();
            }
            if (cobra2.verificarColisaoComCorpo() || cobra2.verificarColisaoComBordas(borda) || cobra2.verificarColisaoComObstaculos(obstaculos) || cobra2.verificarColisaoCobra(cobra)) {
                emJogo = false;
                timer.stop(); // Para o jogo
                
                mostrarTelaDePontosMultiplayer();
            }

        }
        repaint(); // Repaint do painel após a atualização
    }

    private void mostrarTelaDePontosMultiplayer() {
        TelaDePontosMultiplayer telaDePontosMultiplayer = new TelaDePontosMultiplayer(cobra.getPontos(), cobra2.getPontos());

        // Muda para a tela de pontos
        Container parent = getParent();
        parent.remove(this);
        parent.add(telaDePontosMultiplayer);
        parent.revalidate();
        parent.repaint();
    }

}
