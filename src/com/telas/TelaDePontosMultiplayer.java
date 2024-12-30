package telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaDePontosMultiplayer extends JPanel {

    private int pontosJogador1;
    private int pontosJogador2;

    private JButton botaoVoltar;

    // Construtor para inicializar a tela de pontos
    public TelaDePontosMultiplayer(int pontos1, int pontos2) {
        this.pontosJogador1 = pontos1;
        this.pontosJogador2 = pontos2;

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

        // Desenha a pontuação no centro da tela
        if(pontosJogador1>pontosJogador2){
            g.drawString("Jogador 1 venceu com " + pontosJogador1 + " Pontos", 90, 200);
        }
        else if(pontosJogador2>pontosJogador1){
            g.drawString("Jogador 2 venceu com " + pontosJogador2 + " Pontos", 90, 200);
        }
        else{
            g.drawString("Deu empate", 200, 200);
        }

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
