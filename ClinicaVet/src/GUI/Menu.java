package GUI;

import clinica.*; // Importa todas as classes do modelo de negócio
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Janela principal da aplicação, servindo como o menu de navegação.
 * A partir daqui, o usuário pode acessar todos os outros formulários do sistema.
 */
public class Menu extends JFrame {
    private final Clinica clinica; // Armazena a instância única da classe de negócio.

    /**
     * Construtor do Menu.
     * Inicializa a instância da clínica, prepara dados de teste e constrói a interface.
     */
    public Menu (){
        this.clinica = new Clinica();

        preparaDadosIniciais(); // Método para popular o sistema com dados para facilitar testes.

        // Configurações da janela principal.
        setTitle("Clínica Veterinária - Menu Principal");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela.
        setLayout(new BorderLayout(10, 10));

        // Painel do título, com um estilo visual destacado.
        JPanel painelTitulo = new JPanel();
        painelTitulo.setBackground(new Color(70, 130, 180));
        painelTitulo.setPreferredSize(new Dimension(600, 80));
        JLabel labelTitulo = new JLabel("Bem-vindo à Clínica Veterinária");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        labelTitulo.setForeground(Color.WHITE);
        painelTitulo.add(labelTitulo);
        add(painelTitulo, BorderLayout.NORTH);

        // Painel central com os botões de navegação, organizado em grade.
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        painelBotoes.setLayout(new GridLayout(3, 2, 15, 15)); // Grade 3x2 com espaçamento.

        // Criação e adição dos botões.
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

        // --- Ações dos Botões (Listeners) ---
        // Cada botão abre o formulário correspondente, passando a instância da clínica.
        btnCadastroTutor.addActionListener(e -> new FormTutor(this.clinica).setVisible(true));
        btnCadastroAnimal.addActionListener(e -> new FormAnimal(this.clinica).setVisible(true));
        btnGerenciarFuncionarios.addActionListener(e -> new FormTipoFuncionario(this.clinica).setVisible(true));
        btnAtendimento.addActionListener(e -> new FormAtendimento(this.clinica).setVisible(true));
        btnRelatorios.addActionListener(e -> new FormRelatorios(this.clinica).setVisible(true));
        btnAgendamento.addActionListener(e -> new FormAgendamento(this.clinica).setVisible(true));
    }

    /**
     * Ponto de entrada da aplicação.
     * Configura o Look and Feel para uma aparência nativa e inicializa a janela do menu.
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Garante que a interface seja executada na Event Dispatch Thread (EDT).
        SwingUtilities.invokeLater(() -> new Menu().setVisible(true));
    }

    /**
     * Método auxiliar para criar e estilizar um JButton, promovendo a reutilização
     * de código e a consistência visual.
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

    /**
     * Popula a instância da clínica com dados iniciais para demonstração e teste.
     * Isso evita a necessidade de cadastrar tudo manualmente a cada execução.
     */
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

        // Cadastro de Funcionários Gerais
        Funcionario funcRecepcionista = new Funcionario("Ana Paula", "555.666.777-88", "ana.paula@clinica.com", "(11) 95566-7788", "Manhã");
        clinica.cadastrarFuncionario(funcRecepcionista);

        Funcionario funcAuxiliar = new Funcionario("Bruno Santos", "666.777.888-99", "bruno.santos@clinica.com", "(11) 96677-8899", "Tarde");
        clinica.cadastrarFuncionario(funcAuxiliar);

        Funcionario funcTecnico = new Funcionario("Camila Rocha", "777.888.999-00", "camila.rocha@clinica.com", "(11) 97788-9900", "Integral");
        clinica.cadastrarFuncionario(funcTecnico);

        // Cadastro de Animais
        Animal petLuna = new Animal("Luna", "Poodle", tutorJoao, LocalDate.of(2020, 3, 15));
        clinica.cadastrarAnimal(petLuna);

        Animal petThor = new Animal("Thor", "Labrador", tutorMaria, LocalDate.of(2018, 7, 10));
        clinica.cadastrarAnimal(petThor);
    }


}
