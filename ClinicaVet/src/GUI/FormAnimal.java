package GUI;

import clinica.Animal;
import clinica.Clinica;
import clinica.Tutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Formulário para gerenciamento de animais, permitindo cadastro, edição e exclusão.
 * Associa cada animal a um tutor previamente cadastrado.
 */
public class FormAnimal extends JFrame {

    private final Clinica clinica;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Componentes da Interface
    private JComboBox<Tutor> comboTutores;
    private JTextField txtNome, txtRaca, txtDataNascimento, txtBuscarAnimal;
    private JList<Animal> listaAnimais;
    private DefaultListModel<Animal> listModel;
    private JButton btnSalvar, btnExcluir, btnConsultarVacinas;

    private Animal animalSelecionado = null;

    /**
     * Construtor do formulário de gerenciamento de animais.
     * @param clinica A instância da clínica para manipulação dos dados.
     */
    public FormAnimal(Clinica clinica) {
        this.clinica = clinica;

        setTitle("Gerenciar Animais");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- PAINEL DE CADASTRO (ESQUERDA) ---
        JPanel painelCadastro = new JPanel(new BorderLayout(10, 10));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Dados do Animal"));

        JPanel painelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels e Campos
        painelCampos.add(new JLabel("Nome:"), gbc(gbc, 0, 0));
        txtNome = new JTextField(20);
        painelCampos.add(txtNome, gbc(gbc, 1, 0));

        painelCampos.add(new JLabel("Raça:"), gbc(gbc, 0, 1));
        txtRaca = new JTextField();
        painelCampos.add(txtRaca, gbc(gbc, 1, 1));

        painelCampos.add(new JLabel("Nascimento (dd/MM/yyyy):"), gbc(gbc, 0, 2));
        txtDataNascimento = new JTextField();
        painelCampos.add(txtDataNascimento, gbc(gbc, 1, 2));

        painelCampos.add(new JLabel("Tutor:"), gbc(gbc, 0, 3));
        comboTutores = new JComboBox<>();
        personalizarComboTutor();
        painelCampos.add(comboTutores, gbc(gbc, 1, 3));

        painelCadastro.add(painelCampos, BorderLayout.CENTER);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSalvar = new JButton("Salvar");
        btnExcluir = new JButton("Excluir");
        btnConsultarVacinas = new JButton("Consultar Vacinas");
        btnExcluir.setEnabled(false);
        btnConsultarVacinas.setEnabled(false);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnConsultarVacinas);
        painelCadastro.add(painelBotoes, BorderLayout.SOUTH);

        // --- PAINEL DA LISTA (DIREITA) ---
        JPanel painelLista = new JPanel(new BorderLayout(5, 5));
        painelLista.setBorder(BorderFactory.createTitledBorder("Animais Cadastrados"));

        JPanel painelBusca = new JPanel(new BorderLayout());
        painelBusca.add(new JLabel("Buscar por Nome:"), BorderLayout.WEST);
        txtBuscarAnimal = new JTextField();
        painelBusca.add(txtBuscarAnimal, BorderLayout.CENTER);
        painelLista.add(painelBusca, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        listaAnimais = new JList<>(listModel);
        personalizarListaAnimais();
        JScrollPane scrollPane = new JScrollPane(listaAnimais);
        painelLista.add(scrollPane, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelCadastro, painelLista);
        splitPane.setDividerLocation(400);
        add(splitPane, BorderLayout.CENTER);

        // --- AÇÕES E EVENTOS ---
        atualizarComboTutores();
        atualizarListaAnimais();

        btnSalvar.addActionListener(e -> salvarAnimal());
        btnExcluir.addActionListener(e -> excluirAnimal());
        btnConsultarVacinas.addActionListener(e -> exibirVacinas());
        
        
        txtBuscarAnimal.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarAnimais();
            }
        });

        listaAnimais.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                animalSelecionado = listaAnimais.getSelectedValue();
                if (animalSelecionado != null) {
                    preencherCampos(animalSelecionado);
                    btnExcluir.setEnabled(true);
                    btnConsultarVacinas.setEnabled(true);
                } else {
                    limparCampos();
                    btnExcluir.setEnabled(false);
                    btnConsultarVacinas.setEnabled(false);
                }
            }
        });
    }

    private GridBagConstraints gbc(GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }

    /**
     * Salva um novo animal ou atualiza um existente.
     */
    private void salvarAnimal() {
        String nome = txtNome.getText().trim();
        String raca = txtRaca.getText().trim();
        String dataStr = txtDataNascimento.getText().trim();
        Tutor tutor = (Tutor) comboTutores.getSelectedItem();

        if (nome.isEmpty() || raca.isEmpty() || dataStr.isEmpty() || tutor == null) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            LocalDate dataNascimento = LocalDate.parse(dataStr, dateFormatter);

            if (animalSelecionado == null) { // Novo animal
                Animal novoAnimal = new Animal(nome, raca, tutor, dataNascimento);
                clinica.cadastrarAnimal(novoAnimal); 
                JOptionPane.showMessageDialog(this, "Animal cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else { // Atualizar animal
                animalSelecionado.setNome(nome);
                animalSelecionado.setRaca(raca);
                animalSelecionado.setDataDeNascimento(dataNascimento);
                animalSelecionado.setTutor(tutor);
                JOptionPane.showMessageDialog(this, "Dados do animal atualizados!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }

            limparCampos();
            atualizarListaAnimais();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Utilize dd/MM/yyyy.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Exclui o animal selecionado.
     */
    private void excluirAnimal() {
        if (animalSelecionado == null) return;

        int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir o animal '" + animalSelecionado.getNome() + "'?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            clinica.removerAnimal(animalSelecionado);
            JOptionPane.showMessageDialog(this, "Animal excluído (simulação).", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            limparCampos();
            atualizarListaAnimais();
        }
    }

    /**
     * Preenche os campos com os dados do animal selecionado.
     * @param animal O animal a ser exibido.
     */
    private void preencherCampos(Animal animal) {
        txtNome.setText(animal.getNome());
        txtRaca.setText(animal.getRaca());
        txtDataNascimento.setText(animal.getDataDeNascimento().format(dateFormatter));
        comboTutores.setSelectedItem(animal.getTutor());
    }

    /**
     * Limpa os campos e a seleção.
     */
    private void limparCampos() {
        txtNome.setText("");
        txtRaca.setText("");
        txtDataNascimento.setText("");
        comboTutores.setSelectedIndex(-1);
        listaAnimais.clearSelection();
        animalSelecionado = null;
    }

    /**
     * Carrega e atualiza a lista de animais cadastrados.
     */
    private void atualizarListaAnimais() {
        listModel.clear();
        for (Animal animal : clinica.getAnimais()) { //
            listModel.addElement(animal);
        }
    }

    /**
     * Carrega e atualiza o ComboBox de tutores.
     */
    private void atualizarComboTutores() {
        comboTutores.removeAllItems();
        for (Tutor tutor : clinica.getTutores()) { //
            comboTutores.addItem(tutor);
        }
    }

    /**
     * Personaliza a renderização do ComboBox de tutores para exibir apenas o nome.
     */
    private void personalizarComboTutor() {
        comboTutores.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Tutor) {
                    setText(((Tutor) value).getNome());
                }
                return this;
            }
        });
    }

    /**
     * Personaliza a renderização da lista de animais.
     */
    private void personalizarListaAnimais() {
        listaAnimais.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Animal) {
                    Animal a = (Animal) value;
                    setText(String.format("%s (%s) - Tutor: %s", a.getNome(), a.getRaca(), a.getTutor().getNome()));
                }
                return this;
            }
        });
    }

    /**
     * Filtra a lista de animais com base no texto digitado no campo de busca.
     */
    private void filtrarAnimais() {
        String termoBusca = txtBuscarAnimal.getText().toLowerCase();
        listModel.clear();
        for (Animal animal : clinica.getAnimais()) {
            if (animal.getNome().toLowerCase().contains(termoBusca)) {
                listModel.addElement(animal);
            }
        }
    }

    /**
     * Exibe o cartão de vacinas do animal selecionado.
     */
    private void exibirVacinas() {
        if (animalSelecionado != null) {
            // Cria e exibe a janela de diálogo com as vacinas
            FormVisualizarVacinas dialogo = new FormVisualizarVacinas(this, animalSelecionado);
            dialogo.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um animal para consultar as vacinas.", "Nenhum Animal Selecionado", JOptionPane.WARNING_MESSAGE);
        }
    }

}