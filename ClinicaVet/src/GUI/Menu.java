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

        preparaDadosIniciais(); //testes
        
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
        JButton btnGerenciarFuncionarios = criarButton("Gerenciar Funcionarios");
        JButton btnAtendimento = criarButton("Atendimentos");
        JButton btnRelatorios = criarButton("Relatórios");
        JButton btnAgendamento = criarButton("Agendamentos");

        painelBotoes.add(btnCadastroTutor);
        painelBotoes.add(btnCadastroAnimal);
        painelBotoes.add(btnGerenciarFuncionarios);
        painelBotoes.add(btnAtendimento);
        painelBotoes.add(btnRelatorios);
        painelBotoes.add(btnAgendamento);

        add(painelBotoes, BorderLayout.CENTER);

        //adicionando ações aos botões
        btnCadastroTutor.addActionListener(e -> new FormTutor(this.clinica).setVisible(true));
        btnCadastroAnimal.addActionListener(e -> new FormAnimal(this.clinica).setVisible(true));
        btnGerenciarFuncionarios.addActionListener(e -> new FormTipoFuncionario(this.clinica).setVisible(true));
        btnAtendimento.addActionListener(e -> new FormAtendimento(this.clinica).setVisible(true));
        btnRelatorios.addActionListener(e -> new FormRelatorios(this.clinica).setVisible(true));
        btnAgendamento.addActionListener(e -> new FormAgendamento(this.clinica).setVisible(true));        

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

    private void preparaDadosIniciais() {
    // Cadastro de Tutores
    Tutor tutorJoao = new Tutor("João Silva", "123.456.789-00", "joao.silva@email.com", "(11) 91234-5678", "Rua das Flores, 123");
    clinica.cadastrarTutor(tutorJoao);

    Tutor tutorMaria = new Tutor("Maria Oliveira", "987.654.321-00", "maria.oliveira@email.com", "(11) 99876-5432", "Avenida Central, 456");
    clinica.cadastrarTutor(tutorMaria);

    // Cadastro de Veterinários
    Veterinario vetCarlos = new Veterinario("Carlos Pereira", "111.222.333-44", "carlos.pereira@vet.com", "(11) 91122-3344", "Clínica Geral", "CRMV-SP 556677", "Integral");
    clinica.cadastrarVeterinario(vetCarlos);

    Veterinario vetFernanda = new Veterinario("Fernanda Costa", "222.333.444-55", "fernanda.costa@vet.com", "(11) 92233-4455", "Dermatologia", "CRMV-SP 112233", "Parcial");
    clinica.cadastrarVeterinario(vetFernanda);

    Veterinario vetRoberto = new Veterinario("Roberto Lima", "333.444.555-66", "roberto.lima@vet.com", "(11) 93344-5566", "Ortopedia", "CRMV-SP 334455", "Plantão");
    clinica.cadastrarVeterinario(vetRoberto);

    Veterinario vetAline = new Veterinario("Aline Martins", "444.555.666-77", "aline.martins@vet.com", "(11) 94455-6677", "Neurologia e Comportamento", "CRMV-SP 778899", "Integral");
    clinica.cadastrarVeterinario(vetAline);

    // Cadastro de Animais
    Animal petLuna = new Animal("Luna", "Poodle", tutorJoao, LocalDate.of(2020, 3, 15));
    clinica.cadastrarAnimal(petLuna);

    Animal petThor = new Animal("Thor", "Labrador", tutorMaria, LocalDate.of(2018, 7, 10));
    clinica.cadastrarAnimal(petThor);
}

}
