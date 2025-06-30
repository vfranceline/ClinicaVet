package GUI;

import clinica.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


public class FormAtendimento extends JFrame {

    private final Clinica clinica;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // --- Componentes Globais ---
    private JComboBox<Animal> comboAnimais;
    private Animal animalSelecionado;

    // --- Componentes da Aba de Consulta ---
    private JComboBox<Veterinario> comboVeterinarios;
    private JTextArea txtProblema, txtDiagnostico;
    private JTextField txtMedicamento, txtPrecoConsulta;

    // --- Componentes da Aba de Vacina ---
    private JTextField txtNomeVacina, txtPrecoVacina, txtDataValidade;

    
    public FormAtendimento(Clinica clinica) {
        this.clinica = clinica;

        setTitle("Realizar Atendimento");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- PAINEL SUPERIOR: SELEÇÃO DO ANIMAL ---
        JPanel painelSelecao = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelSelecao.setBorder(BorderFactory.createTitledBorder("Seleção do Paciente"));
        painelSelecao.add(new JLabel("Selecione o Animal:"));

        comboAnimais = new JComboBox<>();
        personalizarComboAnimais();
        atualizarComboAnimais();
        painelSelecao.add(comboAnimais);
        add(painelSelecao, BorderLayout.NORTH);

        // Define o animal selecionado inicial
        if (comboAnimais.getItemCount() > 0) {
            animalSelecionado = (Animal) comboAnimais.getSelectedItem();
        }

        // Listener para atualizar a seleção do animal
        comboAnimais.addActionListener(e -> animalSelecionado = (Animal) comboAnimais.getSelectedItem());

        // --- PAINEL CENTRAL: ABAS DE ATENDIMENTO ---
        JTabbedPane abas = new JTabbedPane();
        abas.setFont(new Font("SansSerif", Font.PLAIN, 14));
        abas.addTab(" Registar Consulta ", criarAbaConsulta());
        abas.addTab(" Aplicar Vacina ", criarAbaVacina());

        add(abas, BorderLayout.CENTER);
    }

    /**
     * Cria e retorna o painel (aba) para registro de consultas.
     * @return JPanel configurado para o registro de consultas.
     */
    private JPanel criarAbaConsulta() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel campos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels e Campos
        campos.add(new JLabel("Veterinário:"), gbc(gbc, 0, 0));
        comboVeterinarios = new JComboBox<>();
        personalizarComboVeterinarios();
        atualizarComboVeterinarios();
        campos.add(comboVeterinarios, gbc(gbc, 1, 0, 2));

        campos.add(new JLabel("Problema:"), gbc(gbc, 0, 1));
        txtProblema = new JTextArea(4, 20);
        campos.add(new JScrollPane(txtProblema), gbc(gbc, 1, 1, 2));

        campos.add(new JLabel("Diagnóstico:"), gbc(gbc, 0, 2));
        txtDiagnostico = new JTextArea(4, 20);
        campos.add(new JScrollPane(txtDiagnostico), gbc(gbc, 1, 2, 2));

        campos.add(new JLabel("Medicamentos:"), gbc(gbc, 0, 3));
        txtMedicamento = new JTextField();
        campos.add(txtMedicamento, gbc(gbc, 1, 3, 2));

        campos.add(new JLabel("Preço (R$):"), gbc(gbc, 0, 4));
        txtPrecoConsulta = new JTextField();
        campos.add(txtPrecoConsulta, gbc(gbc, 1, 4, 2));

        JButton btnSalvarConsulta = new JButton("Salvar Consulta e Gerar Fatura");
        btnSalvarConsulta.addActionListener(e -> salvarConsulta());

        painel.add(campos, BorderLayout.CENTER);
        painel.add(btnSalvarConsulta, BorderLayout.SOUTH);

        return painel;
    }

    /**
     * Cria e retorna o painel (aba) para aplicação de vacinas.
     * @return JPanel configurado para a aplicação de vacinas.
     */
    private JPanel criarAbaVacina() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel campos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels e Campos
        campos.add(new JLabel("Nome da Vacina:"), gbc(gbc, 0, 0));
        txtNomeVacina = new JTextField(20);
        campos.add(txtNomeVacina, gbc(gbc, 1, 0, 5));

        campos.add(new JLabel("Preço (R$):"), gbc(gbc, 0, 1));
        txtPrecoVacina = new JTextField();
        campos.add(txtPrecoVacina, gbc(gbc, 1, 1, 5));

        campos.add(new JLabel("Validade (dd/MM/yyyy):"), gbc(gbc, 0, 2));
        txtDataValidade = new JTextField();
        campos.add(txtDataValidade, gbc(gbc, 1, 2, 5));

        JButton btnSalvarVacina = new JButton("Salvar Vacina e Gerar Fatura");
        btnSalvarVacina.addActionListener(e -> salvarVacina());

        painel.add(campos, BorderLayout.CENTER);
        painel.add(btnSalvarVacina, BorderLayout.SOUTH);

        return painel;
    }

    /**
     * Salva as informações da consulta, adiciona ao prontuário do animal e gera a cobrança.
     */
    private void salvarConsulta() {
        if (animalSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um animal para o atendimento.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Veterinario vet = (Veterinario) comboVeterinarios.getSelectedItem();
            String problema = txtProblema.getText();
            String diagnostico = txtDiagnostico.getText();
            double preco = Double.parseDouble(txtPrecoConsulta.getText());

            Consulta novaConsulta = new Consulta(vet, problema, diagnostico, txtMedicamento.getText(), LocalDate.now(), preco); //
            animalSelecionado.adicionarConsulta(novaConsulta); //

            // Gera a cobrança
            List<Faturavel> itens = new ArrayList<>(); //
            itens.add(novaConsulta);
            double total = clinica.emitirCobranca(animalSelecionado.getTutor(), itens); //

            JOptionPane.showMessageDialog(this,
                    String.format("Consulta para '%s' registrada com sucesso!\nValor a ser pago: R$ %.2f", animalSelecionado.getNome(), total),
                    "Atendimento Registrado", JOptionPane.INFORMATION_MESSAGE);

            limparCamposConsulta();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O preço da consulta deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Salva a aplicação de uma vacina, adiciona ao cartão do animal e gera a cobrança.
     */
    private void salvarVacina() {
        if (animalSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um animal para o atendimento.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String nomeVacina = txtNomeVacina.getText();
            double preco = Double.parseDouble(txtPrecoVacina.getText());
            LocalDate dataValidade = LocalDate.parse(txtDataValidade.getText(), dateFormatter);

            Vacina novaVacina = new Vacina(nomeVacina, preco); //
            clinica.aplicarVacina(animalSelecionado, novaVacina, LocalDate.now(), dataValidade); //

            // Gera a cobrança
            List<Faturavel> itens = new ArrayList<>(); //
            itens.add(novaVacina);
            double total = clinica.emitirCobranca(animalSelecionado.getTutor(), itens); //

            JOptionPane.showMessageDialog(this,
                    String.format("Vacina '%s' aplicada em '%s' com sucesso!\nValor a ser pago: R$ %.2f", nomeVacina, animalSelecionado.getNome(), total),
                    "Atendimento Registrado", JOptionPane.INFORMATION_MESSAGE);

            limparCamposVacina();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O preço da vacina deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de data de validade inválido. Use dd/MM/yyyy.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- MÉTODOS AUXILIARES ---

    private GridBagConstraints gbc(GridBagConstraints gbc, int x, int y, int width) {
        gbc.gridx = x; gbc.gridy = y; gbc.gridwidth = width;
        return gbc;
    }
    private GridBagConstraints gbc(GridBagConstraints gbc, int x, int y) {
        return gbc(gbc, x, y, 1);
    }

    private void atualizarComboAnimais() {
        comboAnimais.removeAllItems();
        for (Animal animal : clinica.getAnimais()) { //
            comboAnimais.addItem(animal);
        }
    }

    private void atualizarComboVeterinarios() {
        comboVeterinarios.removeAllItems();
        for (Veterinario vet : clinica.getVeterinarios()) { //
            comboVeterinarios.addItem(vet);
        }
    }

    private void personalizarComboAnimais() {
        comboAnimais.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Animal) {
                    Animal a = (Animal) value;
                    setText(String.format("%s (Tutor: %s)", a.getNome(), a.getTutor().getNome()));
                }
                return this;
            }
        });
    }

    private void personalizarComboVeterinarios() {
        comboVeterinarios.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Veterinario) {
                    setText(((Veterinario) value).getNome());
                }
                return this;
            }
        });
    }

    private void limparCamposConsulta() {
        txtProblema.setText("");
        txtDiagnostico.setText("");
        txtMedicamento.setText("");
        txtPrecoConsulta.setText("");
        if (comboVeterinarios.getItemCount() > 0) comboVeterinarios.setSelectedIndex(0);
    }

    private void limparCamposVacina() {
        txtNomeVacina.setText("");
        txtPrecoVacina.setText("");
        txtDataValidade.setText("");
    }
}