package GUI;

import clinica.Animal;
import clinica.VacinaAplicada;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Janela de diálogo para visualizar e buscar no cartão de vacinas de um animal.
 * @author [Seu Nome]
 * @version 1.1
 */
public class FormVisualizarVacinas extends JDialog {

    private final List<VacinaAplicada> todasAsVacinas;
    private final DefaultListModel<VacinaAplicada> listModel = new DefaultListModel<>();

    public FormVisualizarVacinas(Frame parent, Animal animal) {
        super(parent, "Cartão de Vacinas - " + animal.getNome(), true);
        setSize(550, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Armazena a lista completa de vacinas do animal
        this.todasAsVacinas = animal.getCartaoVacina().getVacinasAplicadas(); //

        // --- PAINEL DE BUSCA (TOPO) ---
        JPanel painelBusca = new JPanel(new BorderLayout(5, 5));
        painelBusca.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        painelBusca.add(new JLabel("Buscar Vacina por Nome:"), BorderLayout.WEST);

        JTextField txtBusca = new JTextField();
        painelBusca.add(txtBusca, BorderLayout.CENTER);
        add(painelBusca, BorderLayout.NORTH);

        // --- LISTA DE VACINAS (CENTRO) ---
        JList<VacinaAplicada> listaVacinas = new JList<>(listModel);
        listaVacinas.setCellRenderer(new VacinaListCellRenderer()); // Renderizador customizado
        atualizarLista(todasAsVacinas); // Popula a lista inicialmente

        JScrollPane scrollPane = new JScrollPane(listaVacinas);
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
                filtrarVacinas(txtBusca.getText());
            }
        });
    }

    /**
     * Filtra a lista de vacinas com base no termo de busca.
     * @param termo O nome da vacina a ser buscada.
     */
    private void filtrarVacinas(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            atualizarLista(todasAsVacinas); // Se a busca estiver vazia, mostra todas as vacinas
            return;
        }

        String termoBuscaLower = termo.toLowerCase();
        DefaultListModel<VacinaAplicada> modeloFiltrado = new DefaultListModel<>();
        for (VacinaAplicada vacinaApp : todasAsVacinas) {
            if (vacinaApp.getVacina().getNome().toLowerCase().contains(termoBuscaLower)) {
                modeloFiltrado.addElement(vacinaApp);
            }
        }
        listModel.clear();
        for (int i = 0; i < modeloFiltrado.size(); i++) {
            listModel.addElement(modeloFiltrado.getElementAt(i));
        }
    }

    /**
     * Popula o listModel com a lista de vacinas fornecida.
     * @param vacinas A lista de VacinaAplicada a ser exibida.
     */
    private void atualizarLista(List<VacinaAplicada> vacinas) {
        listModel.clear();
        for (VacinaAplicada vacina : vacinas) {
            listModel.addElement(vacina);
        }
    }

    /**
     * Renderizador customizado para exibir os detalhes da VacinaAplicada na JList.
     */
    private static class VacinaListCellRenderer extends DefaultListCellRenderer {
        private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            // Usa um JLabel para renderizar o conteúdo em HTML, permitindo quebras de linha
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof VacinaAplicada) {
                VacinaAplicada vacinaApp = (VacinaAplicada) value;
                String texto = String.format("<html><body style='width: 350px; padding: 5px;'>" +
                        "<b>Vacina:</b> %s<br>" +
                        "<b>Aplicação:</b> %s<br>" +
                        "<b>Validade:</b> %s" +
                        "<hr></body></html>",
                        vacinaApp.getVacina().getNome(), //
                        vacinaApp.getDataDeAplicacao().format(FORMATADOR), //
                        vacinaApp.getDataDeValidade().format(FORMATADOR)); //
                label.setText(texto);
            }
            return label;
        }
    }
}