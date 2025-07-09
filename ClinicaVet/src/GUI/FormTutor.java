package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import clinica.Clinica;
import clinica.Tutor;

/**
 * Formulário para gerenciar tutores na clínica veterinária.
 * Permite cadastrar, editar, excluir e buscar tutores.
 * A interface é dividida em dois painéis: um para cadastro e outro para exibição da lista de tutores.
 */
public class FormTutor extends JFrame {

    private final Clinica clinica;

    // Componentes da Interface
    private JTextField txtNome, txtCpf, txtEmail, txtTelefone, txtEndereco, txtBuscarTutor;
    private JList<Tutor> listaTutores;
    private DefaultListModel<Tutor> listModel;
    private JButton btnSalvar, btnEditar, btnExcluir;

    private Tutor tutorSelecionado = null; // Armazena o tutor selecionado na lista

    public FormTutor(Clinica clinica){
        this.clinica = clinica;

        setTitle("Gerenciar Tutores");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- PAINEL DE CADASTRO (ESQUERDA) ---
        JPanel painelCadastro = new JPanel(new BorderLayout(10, 10));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Dados do Tutor"));

        // Campos de entrada com GridBagLayout para alinhamento
        JPanel painelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        painelCampos.add(new JLabel("Nome:"), gbc(gbc, 0, 0));
        painelCampos.add(new JLabel("CPF:"), gbc(gbc, 0, 1));
        painelCampos.add(new JLabel("Email:"), gbc(gbc, 0, 2));
        painelCampos.add(new JLabel("Telefone:"), gbc(gbc, 0, 3));
        painelCampos.add(new JLabel("Endereço:"), gbc(gbc, 0, 4));

        // TextFields
        gbc.gridx = 1;
        gbc.weightx = 1.0; // Permite que os campos de texto se expandam
        txtNome = new JTextField(20);
        painelCampos.add(txtNome, gbc(gbc, 1, 0));
        txtCpf = new JTextField();
        painelCampos.add(txtCpf, gbc(gbc, 1, 1));
        txtEmail = new JTextField();
        painelCampos.add(txtEmail, gbc(gbc, 1, 2));
        txtTelefone = new JTextField();
        painelCampos.add(txtTelefone, gbc(gbc, 1, 3));
        txtEndereco = new JTextField();
        painelCampos.add(txtEndereco, gbc(gbc, 1, 4));

        painelCadastro.add(painelCampos, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSalvar = new JButton("Salvar");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnEditar.setEnabled(false); // Desabilitado até um item ser selecionado
        btnExcluir.setEnabled(false);

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelCadastro.add(painelBotoes, BorderLayout.SOUTH);

        // --- PAINEL DA LISTA (DIREITA) ---
        JPanel painelLista = new JPanel(new BorderLayout(5, 5));
        painelLista.setBorder(BorderFactory.createTitledBorder("Tutores Cadastrados"));

        // Painel de busca
        JPanel painelBusca = new JPanel(new BorderLayout());
        painelBusca.add(new JLabel("Buscar por Nome:"), BorderLayout.WEST);
        txtBuscarTutor = new JTextField();
        painelBusca.add(txtBuscarTutor, BorderLayout.CENTER);
        painelLista.add(painelBusca, BorderLayout.NORTH);

        // Lista de tutores
        listModel = new DefaultListModel<>();
        listaTutores = new JList<>(listModel);
        listaTutores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Apenas uma seleção por vez

        // Renderizador para mostrar o nome do tutor na lista
        listaTutores.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Tutor) {
                    setText(((Tutor) value).getNome());
                }
                return this;
            }
        });

        // Adiciona a lista dentro de um JScrollPane para rolagem
        JScrollPane scrollPane = new JScrollPane(listaTutores);
        painelLista.add(scrollPane, BorderLayout.CENTER);

        // Adiciona os painéis principais à janela
        add(painelCadastro, BorderLayout.WEST);
        add(painelLista, BorderLayout.CENTER);

        // --- AÇÕES E EVENTOS ---
        btnSalvar.addActionListener(e -> salvarTutor());
        btnEditar.addActionListener(e -> editarTutor());
        btnExcluir.addActionListener(e -> excluirTutor());

        // Listener para o campo de busca
        // Atualiza a lista de tutores conforme o usuário digita
        txtBuscarTutor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarTutores();
            }
        });

        // Listener para a seleção na lista
        listaTutores.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                tutorSelecionado = listaTutores.getSelectedValue();
                if (tutorSelecionado != null) {
                    preencherCampos(tutorSelecionado);
                    btnEditar.setEnabled(true);
                    btnExcluir.setEnabled(true);
                    txtCpf.setEditable(false); // CPF não pode ser editado
                } else {
                    limparCampos();
                    btnEditar.setEnabled(false);
                    btnExcluir.setEnabled(false);
                }
            }
        });

        atualizarListaTutores();
    }

    //metodo para UI
    private GridBagConstraints gbc(GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }

    /**
     * Salva um novo tutor ou atualiza um existente.
     * Ação é baseada se um tutor está selecionado ou não.
     */
    private void salvarTutor() {
        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        String email = txtEmail.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String endereco = txtEndereco.getText().trim();

        if (nome.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CPF são campos obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Se nenhum tutor está selecionado, é um novo cadastro
        if (tutorSelecionado == null) {
            if (clinica.buscarTutor(cpf) != null) {
                JOptionPane.showMessageDialog(this, "Já existe um tutor com este CPF.", "CPF Duplicado", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Tutor novoTutor = new Tutor(nome, cpf, email, telefone, endereco);
            clinica.cadastrarTutor(novoTutor);
            JOptionPane.showMessageDialog(this, "Tutor cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else { // Caso contrário, é uma atualização
            tutorSelecionado.setNome(nome);
            tutorSelecionado.setEmail(email);
            tutorSelecionado.setTelefone(telefone);
            tutorSelecionado.setEndereco(endereco);
            JOptionPane.showMessageDialog(this, "Dados do tutor atualizados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }

        limparCampos();
        atualizarListaTutores();
    }

    /**
     * Prepara o formulário para a edição do tutor selecionado.
     */
    private void editarTutor() {
        if (tutorSelecionado == null) return;

        salvarTutor();
    }

    /**
     * Exclui o tutor selecionado da lista, após confirmação.
     */
    private void excluirTutor() {
        if (tutorSelecionado == null) return;

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o tutor '" + tutorSelecionado.getNome() + "'?\nEsta ação não pode ser desfeita.",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            clinica.removerTutor(tutorSelecionado);
            JOptionPane.showMessageDialog(this, "Tutor excluído com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            limparCampos();
            atualizarListaTutores();
        }
    }

    /**
     * Atualiza a lista de tutores com os dados mais recentes da clínica.
     */
    private void atualizarListaTutores() {
        listModel.clear();
        for (Tutor tutor : clinica.getTutores()) {
            listModel.addElement(tutor);
        }
    }

    /**
     * Preenche os campos do formulário com os dados de um tutor.
     * @param tutor O tutor cujos dados serão exibidos.
     */
    private void preencherCampos(Tutor tutor) {
        txtNome.setText(tutor.getNome());
        txtCpf.setText(tutor.getCpf());
        txtEmail.setText(tutor.getEmail());
        txtTelefone.setText(tutor.getTelefone());
        txtEndereco.setText(tutor.getEndereco());
    }

    /**
     * Limpa todos os campos de entrada do formulário.
     */
    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        listaTutores.clearSelection();
        tutorSelecionado = null;
        txtCpf.setEditable(true); // Libera o CPF para novo cadastro
    }

    /**
     * Filtra a lista de tutores com base no texto digitado no campo de busca.
     */
    private void filtrarTutores() {
        String termoBusca = txtBuscarTutor.getText().toLowerCase();
        listModel.clear();
        for (Tutor tutor : clinica.getTutores()) {
            if (tutor.getNome().toLowerCase().contains(termoBusca)) {
                listModel.addElement(tutor);
            }
        }
    }
}