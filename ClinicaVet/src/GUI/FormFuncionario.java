package GUI;

import clinica.Clinica;
import clinica.Funcionario;

import javax.swing.*;
import java.awt.*;

/**
 * Formulário para o gerenciamento de funcionários gerais (não veterinários).
 * @author [Seu Nome]
 * @version 1.0
 */
public class FormFuncionario extends JFrame {

    private final Clinica clinica;

    // Componentes da Interface
    private JTextField txtNome, txtCpf, txtEmail, txtTelefone, txtTurno;
    private JList<Funcionario> listaFuncionarios;
    private DefaultListModel<Funcionario> listModel;
    private JButton btnSalvar, btnEditar, btnExcluir;

    private Funcionario funcSelecionado = null;

    public FormFuncionario(Clinica clinica) {
        this.clinica = clinica;

        setTitle("Gerenciar Funcionários Gerais");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- PAINEL DE CADASTRO (ESQUERDA) ---
        JPanel painelCadastro = new JPanel(new BorderLayout(10, 10));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Dados do Funcionário"));

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
        painelCampos.add(new JLabel("Turno de Trabalho:"), gbc(gbc, 0, 4));

        // TextFields
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtNome = new JTextField(20);
        painelCampos.add(txtNome, gbc(gbc, 1, 0));
        txtCpf = new JTextField();
        painelCampos.add(txtCpf, gbc(gbc, 1, 1));
        txtEmail = new JTextField();
        painelCampos.add(txtEmail, gbc(gbc, 1, 2));
        txtTelefone = new JTextField();
        painelCampos.add(txtTelefone, gbc(gbc, 1, 3));
        txtTurno = new JTextField();
        painelCampos.add(txtTurno, gbc(gbc, 1, 4));

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
        listaFuncionarios = new JList<>(listModel);
        listaFuncionarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        personalizarListaFuncionarios();

        JScrollPane scrollPane = new JScrollPane(listaFuncionarios);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Funcionários Cadastrados"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelCadastro, scrollPane);
        splitPane.setDividerLocation(400);
        add(splitPane, BorderLayout.CENTER);


        // --- AÇÕES E EVENTOS ---
        btnSalvar.addActionListener(e -> salvarFuncionario());
        btnEditar.addActionListener(e -> salvarFuncionario()); // Reutiliza a lógica de salvar
        btnExcluir.addActionListener(e -> excluirFuncionario());

        listaFuncionarios.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                funcSelecionado = listaFuncionarios.getSelectedValue();
                if (funcSelecionado != null) {
                    preencherCampos(funcSelecionado);
                    btnEditar.setEnabled(true);
                    btnExcluir.setEnabled(true);
                    txtCpf.setEditable(false);
                } else {
                    limparCampos();
                    btnEditar.setEnabled(false);
                    btnExcluir.setEnabled(false);
                }
            }
        });

        atualizarListaFuncionarios();
    }

    private GridBagConstraints gbc(GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }

    private void salvarFuncionario() {
        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();

        if (nome.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (funcSelecionado == null) { // Cadastrar novo
            if (clinica.buscarFuncionario(cpf) != null || clinica.buscarVeterinario(cpf) != null) {
                JOptionPane.showMessageDialog(this, "Já existe um funcionário ou veterinário com este CPF.", "CPF Duplicado", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Funcionario novoFunc = new Funcionario(nome, cpf, txtEmail.getText(), txtTelefone.getText(), txtTurno.getText());
            clinica.cadastrarFuncionario(novoFunc);
            JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else { // Atualizar existente
            funcSelecionado.setNome(nome);
            funcSelecionado.setEmail(txtEmail.getText());
            funcSelecionado.setTelefone(txtTelefone.getText());
            funcSelecionado.setTurnoDeTrabalho(txtTurno.getText());
            JOptionPane.showMessageDialog(this, "Dados do funcionário atualizados!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }

        limparCampos();
        atualizarListaFuncionarios();
    }

    private void excluirFuncionario() {
        if (funcSelecionado == null) return;

        int confirm = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir o funcionário '" + funcSelecionado.getNome() + "'?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            clinica.removerFuncionario(funcSelecionado);
            JOptionPane.showMessageDialog(this, "Funcionário excluído.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            atualizarListaFuncionarios();
        }
    }

    private void preencherCampos(Funcionario func) {
        txtNome.setText(func.getNome());
        txtCpf.setText(func.getCpf());
        txtEmail.setText(func.getEmail());
        txtTelefone.setText(func.getTelefone());
        txtTurno.setText(func.getTurnoDeTrabalho());
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtTurno.setText("");
        listaFuncionarios.clearSelection();
        funcSelecionado = null;
        txtCpf.setEditable(true);
    }

    private void atualizarListaFuncionarios() {
        listModel.clear();
        for (Funcionario func : clinica.getFuncionarios()) {
            listModel.addElement(func);
        }
    }
    
    private void personalizarListaFuncionarios() {
        listaFuncionarios.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Funcionario) {
                    setText(((Funcionario) value).getNome());
                }
                return this;
            }
        });
    }
}