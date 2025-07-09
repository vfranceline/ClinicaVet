package GUI;

import clinica.Agendamento;
import clinica.Animal;
import clinica.Clinica;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;

public class FormAgendamento extends JFrame{
    private final Clinica clinica;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private JComboBox<Animal> comboAnimais;
    private JComboBox<String> comboEspecialidade;
    private JComboBox<String> comboHorarios;
    private JTextField txtData;
    private JList<Agendamento> listaAgendamentos;
    private DefaultListModel<Agendamento> listModel;
    private JButton btnAgendar, btnCancelar;

    public FormAgendamento(Clinica clinica) {
        this.clinica = clinica;

        setTitle("Agendamento de Consultas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        // --- PAINEL DE CRIAÇÃO DE AGENDAMENTO (ESQUERDA) ---
        JPanel painelAgendar = new JPanel(new BorderLayout(10, 10));
        painelAgendar.setBorder(BorderFactory.createTitledBorder("Novo Agendamento"));

        JPanel painelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels e Campos
        painelCampos.add(new JLabel("Animal:"), gbc(gbc, 0, 0));
        comboAnimais = new JComboBox<>();
        personalizarComboAnimais();
        atualizarComboAnimais();
        painelCampos.add(comboAnimais, gbc(gbc, 1, 0));

        painelCampos.add(new JLabel("Especialidade:"), gbc(gbc, 0, 1));
        comboEspecialidade = new JComboBox<>(clinica.getEspecialidade().toArray(new String[0]));
        painelCampos.add(comboEspecialidade, gbc(gbc, 1, 1));

        painelCampos.add(new JLabel("Data (dd/MM/yyyy):"), gbc(gbc, 0, 2));
        txtData = new JTextField(LocalDate.now().format(dateFormatter));
        painelCampos.add(txtData, gbc(gbc, 1, 2));

        painelCampos.add(new JLabel("Horário:"), gbc(gbc, 0, 3));
        comboHorarios = new JComboBox<>(gerarHorariosDisponiveis());
        painelCampos.add(comboHorarios, gbc(gbc, 1, 3));

        painelAgendar.add(painelCampos, BorderLayout.CENTER);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAgendar = new JButton("Agendar");
        btnCancelar = new JButton("Cancelar Agendamento");
        btnCancelar.setEnabled(false); // Habilitado ao selecionar um item na lista para cancelar
        painelBotoes.add(btnAgendar);
        painelBotoes.add(btnCancelar);
        painelAgendar.add(painelBotoes, BorderLayout.SOUTH);

        // --- PAINEL DA LISTA DE AGENDAMENTOS (DIREITA) ---
        listModel = new DefaultListModel<>();
        listaAgendamentos = new JList<>(listModel);
        personalizarListaAgendamentos();
        atualizarListaAgendamentos();
        JScrollPane scrollPane = new JScrollPane(listaAgendamentos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Agendamentos Confirmados"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelAgendar, scrollPane);
        splitPane.setDividerLocation(400);
        add(splitPane, BorderLayout.CENTER);

        // --- AÇÕES E EVENTOS ---
        btnAgendar.addActionListener(e -> realizarAgendamento());
        btnCancelar.addActionListener(e -> cancelarAgendamentoSelecionado());

        listaAgendamentos.addListSelectionListener(e -> {
            // Habilita ou desabilita o botão de cancelar conforme a seleção
            btnCancelar.setEnabled(listaAgendamentos.getSelectedIndex() != -1);
        });
    }

    /**
     * Realiza o agendamento de uma consulta.
     */
    private void realizarAgendamento() {
        Animal animal = (Animal) comboAnimais.getSelectedItem();
        String especialidade = (String) comboEspecialidade.getSelectedItem();
        String horario = (String) comboHorarios.getSelectedItem();

        if (animal == null || especialidade == null) {
            JOptionPane.showMessageDialog(this, "Selecione um animal e informe a especialidade.", "Dados Incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
                LocalDate data = LocalDate.parse(txtData.getText(), dateFormatter);
                Agendamento novoAgendamento = new Agendamento(data, horario, especialidade, animal);

                String resultado = clinica.agendar(novoAgendamento); // Chama o método centralizado

                if (resultado.startsWith("ERRO")) {
                    JOptionPane.showMessageDialog(this, resultado, "Erro no Agendamento", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, resultado, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    atualizarListaAgendamentos();
                }   

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Data em formato inválido. Use dd/MM/yyyy.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            }
    }

    /**
     * Cancela o agendamento que está selecionado na lista.
     */
    private void cancelarAgendamentoSelecionado() {
        Agendamento agendamentoSelecionado = listaAgendamentos.getSelectedValue();
        if (agendamentoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um agendamento para cancelar.", "Nenhum Item Selecionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja cancelar este agendamento?",
                "Confirmar Cancelamento",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean sucesso = clinica.cancelarAgendamento(agendamentoSelecionado); //
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Agendamento cancelado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                atualizarListaAgendamentos();
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível encontrar o agendamento para cancelar.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Gera os horários de atendimento da clínica em intervalos de 20 minutos,
     * @return Um array de Strings com os horários.
     */
    private String[] gerarHorariosDisponiveis() {
        List<String> horarios = new ArrayList<>();
        // Manhã: 8:00 às 12:00
        for (int h = 8; h < 12; h++) {
            for (int m = 0; m < 60; m += 20) {
                horarios.add(String.format("%02d:%02d", h, m));
            }
        }
        // Tarde: 14:00 às 18:00
        for (int h = 14; h < 18; h++) {
            for (int m = 0; m < 60; m += 20) {
                horarios.add(String.format("%02d:%02d", h, m));
            }
        }
        return horarios.toArray(new String[0]);
    }

    /**
     * Carrega e atualiza a lista de agendamentos buscando os dados
     * mais recentes da instância da clínica.
     */
    private void atualizarListaAgendamentos() {
        listModel.clear(); // Limpa a lista visual

        // Busca a lista atualizada de agendamentos da clínica
        List<Agendamento> agendamentosAtuais = clinica.getAgendamentos();

        // Adiciona cada agendamento ao modelo da lista
        if (agendamentosAtuais != null) {
            agendamentosAtuais.sort(Comparator.comparing(Agendamento::getDataConsulta).thenComparing(Agendamento::getHora));
            for (Agendamento ag : agendamentosAtuais) {
                listModel.addElement(ag);
            }
        }
    }

    /**
     * Carrega e atualiza o ComboBox de animais.
     */
    private void atualizarComboAnimais() {
        comboAnimais.removeAllItems();
        for (Animal animal : clinica.getAnimais()) { //
            comboAnimais.addItem(animal);
        }
    }

    // --- Métodos de UI e Configuração ---
    private GridBagConstraints gbc(GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 1.0;
        return gbc;
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

    private void personalizarListaAgendamentos() {
        listaAgendamentos.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Agendamento) {
                    Agendamento a = (Agendamento) value;
                    setText(String.format("%s às %s - %s para %s",
                            a.getDataConsulta().format(dateFormatter),
                            a.getHora(),
                            a.getEspecialidade(),
                            a.getAnimal().getNome()));
                }
                return this;
            }
        });
    }

}
