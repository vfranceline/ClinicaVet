package GUI;

import clinica.Animal;
import clinica.Consulta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Formulário para visualizar o prontuário de um animal.
 * Permite buscar consultas por problema ou diagnóstico e exibe detalhes de cada consulta.
 * A interface é dividida em três partes: busca, lista de consultas e botões de ação.
 */
public class FormVisualizarConsultas extends JDialog {

    private final List<Consulta> todasAsConsultas;
    private final DefaultListModel<Consulta> listModel = new DefaultListModel<>();

    public FormVisualizarConsultas(Frame parent, Animal animal) {
        super(parent, "Prontuário - " + animal.getNome(), true);
        setSize(600, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Armazena a lista completa de consultas do animal
        this.todasAsConsultas = animal.getProntuario().getConsultas(); //

        // --- PAINEL DE BUSCA (TOPO) ---
        JPanel painelBusca = new JPanel(new BorderLayout(5, 5));
        painelBusca.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        painelBusca.add(new JLabel("Buscar no Prontuário:"), BorderLayout.WEST);

        JTextField txtBusca = new JTextField();
        painelBusca.add(txtBusca, BorderLayout.CENTER);
        add(painelBusca, BorderLayout.NORTH);

        // --- LISTA DE CONSULTAS (CENTRO) ---
        JList<Consulta> listaConsultas = new JList<>(listModel);
        listaConsultas.setCellRenderer(new ConsultaListCellRenderer()); // Renderizador customizado
        atualizarLista(todasAsConsultas); // Popula a lista inicialmente

        JScrollPane scrollPane = new JScrollPane(listaConsultas);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        add(scrollPane, BorderLayout.CENTER);

        // --- PAINEL DE BOTÕES (ABAIXO) ---
        JButton btnFechar = new JButton("Fechar");
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotao.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        painelBotao.add(btnFechar);
        add(painelBotao, BorderLayout.SOUTH);

        // --- AÇÕES E EVENTOS ---
        btnFechar.addActionListener(e -> dispose());

        // Evento de teclado para filtrar a lista dinamicamente
        txtBusca.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarConsultas(txtBusca.getText());
            }
        });
    }

    /**
     * Filtra a lista de consultas com base no termo de busca.
     * A busca é feita no problema e no diagnóstico.
     * @param termo O texto a ser buscado.
     */
    private void filtrarConsultas(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            atualizarLista(todasAsConsultas);
            return;
        }

        String termoBuscaLower = termo.toLowerCase();
        DefaultListModel<Consulta> modeloFiltrado = new DefaultListModel<>();
        for (Consulta consulta : todasAsConsultas) {
            boolean problemaContem = consulta.getProblema().toLowerCase().contains(termoBuscaLower);
            boolean diagnosticoContem = consulta.getDiagnostico().toLowerCase().contains(termoBuscaLower);

            if (problemaContem || diagnosticoContem) {
                modeloFiltrado.addElement(consulta);
            }
        }
        listModel.clear();
        for (int i = 0; i < modeloFiltrado.size(); i++) {
            listModel.addElement(modeloFiltrado.getElementAt(i));
        }
    }

    /**
     * Popula o listModel com a lista de consultas fornecida.
     * @param consultas A lista de Consulta a ser exibida.
     */
    private void atualizarLista(List<Consulta> consultas) {
        listModel.clear();
        for (Consulta consulta : consultas) {
            listModel.addElement(consulta);
        }
    }

    /**
     * Renderizador customizado para exibir os detalhes da Consulta na JList.
     */
    private static class ConsultaListCellRenderer extends DefaultListCellRenderer {
        private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Consulta) {
                Consulta consulta = (Consulta) value;
                String texto = String.format("<html><body style='width: 400px; padding: 5px;'>" +
                        "<b>Data:</b> %s | <b>Veterinário:</b> %s<br>" +
                        "<b>Problema:</b> %s<br>" +
                        "<b>Diagnóstico:</b> %s<br>" +
                        "<b>Medicação:</b> %s" +
                        "<hr></body></html>",
                        consulta.getDataConsulta().format(FORMATADOR), //
                        consulta.getVeterinario().getNome(), //
                        consulta.getProblema(),
                        consulta.getDiagnostico(),
                        consulta.getMedicamento());
                label.setText(texto);
            }
            return label;
        }
    }
}