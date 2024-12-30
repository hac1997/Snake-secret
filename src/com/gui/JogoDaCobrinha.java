package gui;

import javax.swing.JFrame;
import telas.PainelDoJogo;
import telas.PainelMultiplayer;
import telas.TelaInicial;

public class JogoDaCobrinha extends JFrame {

    public static int tamBloco = 20;

    public JogoDaCobrinha() {
        setTitle("Jogo da Cobrinha");
        setSize(613, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Cria os painéis necessários
        PainelDoJogo painelDoJogo = new PainelDoJogo();
        PainelMultiplayer painelM = new PainelMultiplayer();
        TelaInicial telaInicial = new TelaInicial(painelDoJogo, painelM);

        

        // Adiciona a tela inicial ao JFrame
        add(telaInicial);

        // Exibe o JFrame
        setVisible(true);
    }
}

