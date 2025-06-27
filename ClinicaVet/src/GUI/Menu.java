package GUI;

import clinica.Clinica;
import clinica.Tutor;
import clinica.Veterinario;
import clinica.Animal;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class Menu extends JFrame {
    private final Clinica clinica;

    //construtor da janela do menu inicial
    public Menu (){
        this.clinica = new Clinica();
        //configurações da janela principal
        setTitle("Clínica Veterinária - Menu Principal");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setLayout(new BorderLayout(10, 10)); // Layout principal

        //painel de titulo
        JPanel painelTitulo = new JPanel();
        painelTitulo.setBackground(new Color(70, 130, 180));
        painelTitulo.setPreferredSize(new Dimension(600, 80));

        JLabel labelTitulo = new JLabel("Bem-vindo à Clínica Veterinária");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        labelTitulo.setForeground(Color.WHITE);
        painelTitulo.add(labelTitulo);
        add(painelTitulo, BorderLayout.NORTH);

        //painel de botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30)); // Espaçamento interno
        painelBotoes.setLayout(new GridLayout(3, 2, 15, 15)); // Grade 3x2 com espaçamento    
        
        //criando botões e adicionando ao painel
        JButton btnCadastroTutor = criarButton("Gerenciar Tutores");
        JButton btnCadastroAnimal = criarButton("Gerenciar Animais");
        JButton btnCadastroVet = criarButton("Gerenciar Veterinários");
        JButton btnAtendimento = criarButton("Atendimentos");
        JButton btnRelatorios = criarButton("Relatórios");
        JButton btnAgendamento = criarButton("Agendamentos");

        painelBotoes.add(btnCadastroTutor);
        painelBotoes.add(btnCadastroAnimal);
        painelBotoes.add(btnCadastroVet);
        painelBotoes.add(btnAtendimento);
        painelBotoes.add(btnRelatorios);
        painelBotoes.add(btnAgendamento);

        add(painelBotoes, BorderLayout.CENTER);

        //TO-DO:
        //adicionando ações aos botões
    }

    public static void main(String[] args) {
        // Tenta aplicar o Look and Feel do sistema para uma aparência nativa
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace(); // Em caso de erro, imprime o stack trace
        }

        // Garante que a interface gráfica seja criada e atualizada na Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> new Menu().setVisible(true));
    }

    /**
     * Método auxiliar para criar e estilizar um JButton.
     * @param texto O texto a ser exibido no botão.
     * @return Um objeto JButton estilizado.
     */
    private JButton criarButton(String texto){
        JButton button = new JButton(texto);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }
}
