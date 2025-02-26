package telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaDePontosMultiplayer extends JPanel {

    private int pontosJogador1;
    private int pontosJogador2;
    private boolean jogador1Venceu;
    private boolean venceuPorPontos;

    private JButton botaoVoltar;

    // Construtor para inicializar a tela de pontos
    public TelaDePontosMultiplayer(int pontos1, int pontos2, boolean jogador1Venceu, boolean venceuPorPontos) {
        this.pontosJogador1 = pontos1;
        this.pontosJogador2 = pontos2;
        this.jogador1Venceu = jogador1Venceu;
        this.venceuPorPontos = venceuPorPontos;

        // Configurações da tela
        setPreferredSize(new Dimension(610, 620));
        setBackground(Color.BLACK);
        setLayout(null);
        botaoTelaInicial();
        add(botaoVoltar);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Chama o método da superclasse para limpar a tela antes de desenhar

        // Configura a fonte e a cor para desenhar a pontuação
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 28));

        // Centraliza o texto horizontalmente
        int centroX = getWidth() / 2;

        // Desenha a mensagem de vitória
        String mensagemVitoria = jogador1Venceu ? "Jogador 1 venceu!" : "Jogador 2 venceu!";
        int larguraTextoVitoria = g.getFontMetrics().stringWidth(mensagemVitoria);
        g.drawString(mensagemVitoria, centroX - (larguraTextoVitoria / 2), 200);

        // Desenha a razão da vitória
        String motivoVitoria = venceuPorPontos ? "Vitória por diferença de pontos!" : "Vitória por colisão!";
        int larguraTextoMotivo = g.getFontMetrics().stringWidth(motivoVitoria);
        g.drawString(motivoVitoria, centroX - (larguraTextoMotivo / 2), 250);

        // Desenha a pontuação dos jogadores
        g.setFont(new Font("Arial", Font.BOLD, 24)); // Reduz um pouco o tamanho da fonte para os pontos
        String pontos1 = "Pontos Jogador 1: " + pontosJogador1;
        String pontos2 = "Pontos Jogador 2: " + pontosJogador2;

        int larguraPontos1 = g.getFontMetrics().stringWidth(pontos1);
        int larguraPontos2 = g.getFontMetrics().stringWidth(pontos2);

        g.drawString(pontos1, centroX - (larguraPontos1 / 2), 300);
        g.drawString(pontos2, centroX - (larguraPontos2 / 2), 350);
    }

    public void botaoTelaInicial() {
        botaoVoltar = new JButton("Voltar à Tela Inicial");
        botaoVoltar.setBounds(180, 400, 250, 50);
        botaoVoltar.setBackground(Color.GREEN);
        botaoVoltar.setForeground(Color.BLACK);
        botaoVoltar.setFont(new Font("Arial", Font.BOLD, 16));
        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltarTelaInicial();
            }
        });
    }

    public void voltarTelaInicial() {
        // Aqui podemos limpar a tela de pontos e retornar à tela inicial
        Container parent = getParent();
        TelaInicial telaInicial = new TelaInicial(new PainelDoJogo(), new PainelMultiplayer());
        parent.remove(this);
        parent.add(telaInicial);
        parent.revalidate();
        parent.repaint();
    }
}