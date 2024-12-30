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
import model.Comida;
import model.Obstaculos;

public class PainelDoJogo extends JPanel {

    private Cobra cobra;
    private Comida comida;
    private Borda borda;
    private Obstaculos obstaculos;
    private Timer timer;
    private boolean emJogo = false;
    private String mapaEscolhido = "Mapa 1"; // Default

    public PainelDoJogo() {
        setPreferredSize(new Dimension(610, 620));
        setBackground(Color.BLACK);
        setFocusable(true);


    }

    public void selecionarMapa(String mapaSelecionado) {
        this.mapaEscolhido = mapaSelecionado;
    }


    void iniciarJogo() {
        cobra = new Cobra();
        addKeyListener(cobra);

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

    private void actionPerformed(ActionEvent e) {
        if (emJogo) {
            cobra.mover();  // Mover a cobra primeiro

            // Verificar se a cobra colidiu com a comida
            if (cobra.verificarColisao(comida.getPosicao())) {
                cobra.crescer(); // A cobra cresce quando come a comida
                comida.gerarNovaPosicao(); // Gera uma nova posição para a comida
                while(comida.testarPosBorda(borda)||comida.testarPosCobra(cobra)||comida.testarPosObs(obstaculos)){
                    comida.gerarNovaPosicao();
                }
            }

            // Verificar colisão com o corpo e as bordas após o movimento e crescimento
            if (cobra.verificarColisaoComCorpo() || cobra.verificarColisaoComBordas(borda) || cobra.verificarColisaoComObstaculos(obstaculos)) {
                emJogo = false;
                timer.stop(); // Para o jogo
                mostrarTelaDePontos();

            }
        }
        repaint(); // Repaint do painel após a atualização
    }
    
    private void mostrarTelaDePontos() {
        TelaDePontos telaDePontos = new TelaDePontos(cobra.getPontos());
                // Muda para a tela de pontos
        Container parent = getParent();
        parent.remove(this);
        parent.add(telaDePontos);
        parent.revalidate();
        parent.repaint();
    }

}
