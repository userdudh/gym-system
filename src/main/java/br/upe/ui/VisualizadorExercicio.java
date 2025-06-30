package br.upe.ui;

import br.upe.data.beans.Exercicio;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class VisualizadorExercicio {

    public static void mostrarExercicio(String gifPath, String descricao) {
        JFrame frame = new JFrame("Detalhes do Exercício");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        ImageIcon gifIcon = null;

        try {
            gifIcon = new ImageIcon(Objects.requireNonNull(VisualizadorExercicio.class.getResource("/" + gifPath)));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar GIF: " + gifPath + "\n" + e.getMessage(), "Erro de Imagem", JOptionPane.ERROR_MESSAGE);
            gifIcon = new ImageIcon(new byte[0]);
        }

        JLabel gifLabel = new JLabel(gifIcon);
        JTextArea descricaoArea = new JTextArea(descricao);
        descricaoArea.setLineWrap(true);
        descricaoArea.setWrapStyleWord(true);
        descricaoArea.setEditable(false);

        frame.setLayout(new BorderLayout());
        frame.add(gifLabel, BorderLayout.CENTER);
        frame.add(descricaoArea, BorderLayout.SOUTH);

        frame.setVisible(true);
    }


    public void exibirDetalhes(Exercicio exercicio) {
        mostrarExercicio(exercicio.getCaminhoGif(), exercicio.getDescricao());
    }
}