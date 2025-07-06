package GUI;

import clinica.Clinica;
import javax.swing.*;
import java.awt.*;

/**
 * Janela para selecionar o tipo de funcionário a ser gerenciado.
 * @author [Seu Nome]
 * @version 1.0
 */
public class FormTipoFuncionario extends JFrame {

    private final Clinica clinica;

    public FormTipoFuncionario(Clinica clinica) {
        this.clinica = clinica;

        setTitle("Seleção de Tipo de Funcionário");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel lblTitulo = new JLabel("Selecione o tipo de funcionário que deseja gerenciar:", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblTitulo, BorderLayout.NORTH);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 20, 0));

        JButton btnVeterinario = new JButton("Veterinário");
        JButton btnFuncionarioGeral = new JButton("Funcionário Geral");

        // Estilo dos botões
        styleButton(btnVeterinario);
        styleButton(btnFuncionarioGeral);

        painelBotoes.add(btnVeterinario);
        painelBotoes.add(btnFuncionarioGeral);

        add(painelBotoes, BorderLayout.CENTER);

        // Ações dos botões
        btnVeterinario.addActionListener(e -> {
            new FormVeterinario(this.clinica).setVisible(true);
            this.dispose(); // Fecha a janela de seleção
        });

        btnFuncionarioGeral.addActionListener(e -> {
            new FormFuncionario(this.clinica).setVisible(true);
            this.dispose(); // Fecha a janela de seleção
        });
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
    }
}