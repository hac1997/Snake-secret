package telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaDePontos extends JPanel {

    private int pontos;
    private JButton botaoVoltar;

    // Construtor para inicializar a tela de pontos
    public TelaDePontos(int pontos) {
        this.pontos = pontos;

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
        g.drawString("Pontos: " + pontos, 200, 200);
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
