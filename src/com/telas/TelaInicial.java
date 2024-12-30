package telas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class TelaInicial extends JPanel {

    private final PainelDoJogo painelDoJogo;
    private final PainelMultiplayer painelMultiplayer;

    private JComboBox<String> mapaComboBox;
    private JButton multiplayerButton;
    private Image imageMapa;

    public TelaInicial(PainelDoJogo painelDoJogo, PainelMultiplayer painelM) {
        this.painelDoJogo = painelDoJogo;
        this.painelMultiplayer = painelM;
        setPreferredSize(new Dimension(610, 620));
        setBackground(Color.BLACK);
        setLayout(null); // Desativa layout automático para posicionar os componentes manualmente

        criarBotaoIniciar();
        criarComboBoxMapa();
        criarBotaoMultiplayer();
        mostrarMapa();
    }

    private void criarBotaoIniciar() {
        JButton botaoIniciar = new JButton("Iniciar Jogo solo");
        botaoIniciar.setBounds(200, 400, 200, 50); // Define posição e tamanho do botão
        botaoIniciar.setBackground(Color.YELLOW);
        botaoIniciar.setForeground(Color.BLACK);
        botaoIniciar.setFont(new Font("Arial", Font.BOLD, 16));

        botaoIniciar.addActionListener(e -> iniciar());

        add(botaoIniciar); // Adiciona o botão ao painel
    }

    private void criarComboBoxMapa() {
        // ComboBox para escolher o mapa
        String[] mapas = {"mapa1", "mapa2", "mapa3", "mapa4"};
        mapaComboBox = new JComboBox<>(mapas);
        mapaComboBox.setBounds(200, 460, 200, 30);
        add(mapaComboBox); // Adiciona o combo box ao painel
        mapaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarMapa();  // Exibe o mapa selecionado
            }
        });
    }

    private void criarBotaoMultiplayer() {
        // Botão para iniciar o jogo em modo multiplayer
        multiplayerButton = new JButton("Modo Multiplayer");
        multiplayerButton.setBounds(200, 500, 200, 50);
        multiplayerButton.setBackground(Color.YELLOW);
        multiplayerButton.setForeground(Color.BLACK);
        multiplayerButton.setFont(new Font("Arial", Font.BOLD, 16));

        multiplayerButton.addActionListener(e -> iniciarMultiplayer());

        add(multiplayerButton); // Adiciona o botão ao painel
    }

    private void iniciar() {
        // Pega o mapa selecionado
        String mapaSelecionado = (String) mapaComboBox.getSelectedItem();
        // Passa o mapa selecionado para o PainelDoJogo
        painelDoJogo.selecionarMapa(mapaSelecionado);
        painelDoJogo.iniciarJogo();
        mudarTelaJogo();
    }
    private void iniciarMultiplayer() {
        String mapaSelecionado = (String) mapaComboBox.getSelectedItem();
        // Passa o mapa selecionado para o PainelDoJogo
        painelMultiplayer.selecionarMapa(mapaSelecionado);
        painelMultiplayer.iniciarJogo();
        mudarTelaJogoMultiplayer();
    }


    private void mudarTelaJogoMultiplayer() {
        java.awt.Container parent = getParent();
        parent.remove(this);
        parent.add(painelMultiplayer);
        painelMultiplayer.requestFocusInWindow(); // Define foco dos controles para o painel do jogo
        parent.revalidate();
        parent.repaint();
    }

    private void mudarTelaJogo() {
        java.awt.Container parent = getParent();
        parent.remove(this);
        parent.add(painelDoJogo);
        painelDoJogo.requestFocusInWindow(); // Define foco dos controles para o painel do jogo
        parent.revalidate();
        parent.repaint();
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString("Bem-vindo ao Snake!", 150, 50);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString("Escolha o mapa e clique em Iniciar para jogar.", 130, 70);
        g.drawImage(imageMapa, 175, 100, 250, 250, this);  // Desenha a imagem a partir do canto superior esquerdo

    }

    private void mostrarMapa() {
        String mapaSelecionado = (String) mapaComboBox.getSelectedItem();
        ImageIcon image = new ImageIcon("src/res/imgs/" + mapaSelecionado + ".png");
        imageMapa = image.getImage();  // Armazenar a imagem carregada

        repaint();  // Solicitar a repintura do painel para exibir a imagem

    }

}
