package GUI;

import clinica.Clinica;
import clinica.Veterinario;

import javax.swing.*;
import java.awt.*;

/**
 * Formulário para o gerenciamento completo de veterinários (inclusão, alteração, exclusão e consulta).
 * Utiliza um layout aprimorado para facilitar a visualização e manipulação dos dados.
 *
 * @author [Seu Nome]
 * @version 1.2
 */
public class FormVeterinario extends JFrame {

    private final Clinica clinica;

    // Componentes da Interface
    private JTextField txtNome, txtCpf, txtEmail, txtTelefone, txtCfmv, txtTurno;
    private JComboBox<String> comboEspecialidade;
    private JList<Veterinario> listaVeterinarios;
    private DefaultListModel<Veterinario> listModel;
    private JButton btnSalvar, btnEditar, btnExcluir;

    private Veterinario vetSelecionado = null; // Armazena o veterinário selecionado na lista

    /**
     * Construtor do formulário de gerenciamento de veterinários.
     * @param clinica A instância da clínica para acesso e manipulação dos dados.
     */
    public FormVeterinario(Clinica clinica) {
        this.clinica = clinica;

        setTitle("Gerenciar Veterinários");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- PAINEL DE CADASTRO (ESQUERDA) ---
        JPanel painelCadastro = new JPanel(new BorderLayout(10, 10));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Dados do Veterinário"));

        // Campos de entrada com GridBagLayout
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
        painelCampos.add(new JLabel("Especialidade:"), gbc(gbc, 0, 4));
        painelCampos.add(new JLabel("CFMV:"), gbc(gbc, 0, 5));
        painelCampos.add(new JLabel("Turno:"), gbc(gbc, 0, 6));

        // TextFields
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtNome = new JTextField(25);
        painelCampos.add(txtNome, gbc(gbc, 1, 0));
        txtCpf = new JTextField();
        painelCampos.add(txtCpf, gbc(gbc, 1, 1));
        txtEmail = new JTextField();
        painelCampos.add(txtEmail, gbc(gbc, 1, 2));
        txtTelefone = new JTextField();
        painelCampos.add(txtTelefone, gbc(gbc, 1, 3));
        comboEspecialidade = new JComboBox<>(clinica.getEspecialidade().toArray(new String[0]));
        painelCampos.add(comboEspecialidade, gbc(gbc, 1, 4));
        txtCfmv = new JTextField();
        painelCampos.add(txtCfmv, gbc(gbc, 1, 5));
        txtTurno = new JTextField();
        painelCampos.add(txtTurno, gbc(gbc, 1, 6));

        painelCadastro.add(painelCampos, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSalvar = new JButton("Salvar");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelCadastro.add(painelBotoes, BorderLayout.SOUTH);

        // --- PAINEL DA LISTA (DIREITA) ---
        listModel = new DefaultListModel<>();
        listaVeterinarios = new JList<>(listModel);
        listaVeterinarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Renderizador para exibir informações relevantes na lista
        listaVeterinarios.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Veterinario) {
                    Veterinario vet = (Veterinario) value;
                    setText(String.format("%s (CFMV: %s)", vet.getNome(), vet.getCfmv()));
                }
                return this;
            }
        });

        JScrollPane scrollPane = new JScrollPane(listaVeterinarios);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Veterinários Cadastrados"));

        // Adiciona os painéis principais à janela
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelCadastro, scrollPane);
        splitPane.setDividerLocation(450);
        add(splitPane, BorderLayout.CENTER);

        // --- AÇÕES E EVENTOS ---
        btnSalvar.addActionListener(e -> salvarVeterinario());
        btnEditar.addActionListener(e -> salvarVeterinario()); // Reutiliza a lógica de salvar
        btnExcluir.addActionListener(e -> excluirVeterinario());

        listaVeterinarios.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                vetSelecionado = listaVeterinarios.getSelectedValue();
                if (vetSelecionado != null) {
                    preencherCampos(vetSelecionado);
                    btnEditar.setEnabled(true);
                    btnExcluir.setEnabled(true);
                    txtCpf.setEditable(false); // CPF não deve ser alterado
                } else {
                    limparCampos();
                    btnEditar.setEnabled(false);
                    btnExcluir.setEnabled(false);
                }
            }
        });

        atualizarListaVeterinarios();
    }

    /**
     * Método auxiliar para configurar GridBagConstraints.
     */
    private GridBagConstraints gbc(GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }

    /**
     * Salva um novo veterinário ou atualiza um existente com base na seleção.
     */
    private void salvarVeterinario() {
        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        String cfmv = txtCfmv.getText().trim();
        String especialidade = (String) comboEspecialidade.getSelectedItem();

        if (nome.isEmpty() || cpf.isEmpty() || cfmv.isEmpty() || especialidade == null) {
            JOptionPane.showMessageDialog(this, "Nome, CPF e CFMV são obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (vetSelecionado == null) { // Cadastrar novo
            if (clinica.buscarVeterinario(cpf) != null) {
                JOptionPane.showMessageDialog(this, "Já existe um veterinário com este CPF.", "CPF Duplicado", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Veterinario novoVet = new Veterinario(nome, cpf, txtEmail.getText(), txtTelefone.getText(), especialidade, cfmv, txtTurno.getText());
            clinica.cadastrarVeterinario(novoVet);
            JOptionPane.showMessageDialog(this, "Veterinário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else { // Atualizar existente
            vetSelecionado.setNome(nome);
            vetSelecionado.setEmail(txtEmail.getText());
            vetSelecionado.setTelefone(txtTelefone.getText());
            vetSelecionado.setEspecialidade(especialidade);
            vetSelecionado.setCfmv(cfmv);
            vetSelecionado.setTurnoDeTrabalho(txtTurno.getText());
            JOptionPane.showMessageDialog(this, "Dados do veterinário atualizados!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }

        limparCampos();
        atualizarListaVeterinarios();
    }

    /**
     * Exclui o veterinário selecionado após confirmação.
     */
    private void excluirVeterinario() {
        if (vetSelecionado == null) return;

        int confirm = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir o veterinário '" + vetSelecionado.getNome() + "'?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            clinica.removerVeterinario(vetSelecionado);
            JOptionPane.showMessageDialog(this, "Veterinário excluído.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            limparCampos();
            atualizarListaVeterinarios();
        }
    }

    /**
     * Recarrega a lista de veterinários a partir da instância da clínica.
     */
    private void atualizarListaVeterinarios() {
        listModel.clear();
        for (Veterinario vet : clinica.getVeterinarios()) { //
            listModel.addElement(vet);
        }
    }

    /**
     * Preenche os campos do formulário com os dados do veterinário selecionado.
     * @param vet O veterinário cujos dados serão exibidos.
     */
    private void preencherCampos(Veterinario vet) {
        txtNome.setText(vet.getNome());
        txtCpf.setText(vet.getCpf());
        txtEmail.setText(vet.getEmail());
        txtTelefone.setText(vet.getTelefone());
        comboEspecialidade.setSelectedItem(vet.getEspecialidade());
        txtCfmv.setText(vet.getCfmv());
        txtTurno.setText(vet.getTurnoDeTrabalho());
    }

    /**
     * Limpa todos os campos de texto e a seleção da lista.
     */
    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        comboEspecialidade.setSelectedIndex(-1);
        txtCfmv.setText("");
        txtTurno.setText("");
        listaVeterinarios.clearSelection();
        vetSelecionado = null;
        txtCpf.setEditable(true);
    }
}