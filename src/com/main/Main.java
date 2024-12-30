package main;

import gui.JogoDaCobrinha;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JogoDaCobrinha jogo = new JogoDaCobrinha();
            jogo.setVisible(true);
        });

    }
}
