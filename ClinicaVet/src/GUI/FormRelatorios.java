package GUI;

import clinica.Animal;
import clinica.Clinica;
import clinica.FaturaTutor;
import clinica.Tutor;

import javax.swing.*;
import java.awt.*;

/**
 * Formulário para emissão de documentos e relatórios, como o Prontuário
 * e o Cartão de Vacinas de um animal específico.
 *
 * @author [Seu Nome]
 * @version 1.2
 */
public class FormRelatorios extends JFrame {

    private final Clinica clinica;

    // --- Componentes da Interface ---
    private JComboBox<Animal> comboAnimais;
    private JTextArea areaRelatorio;

    /**
     * Construtor do formulário de relatórios.
     * @param clinica A instância da clínica para obter os dados necessários.
     */
    public FormRelatorios(Clinica clinica) {
        this.clinica = clinica;

        setTitle("Emissão de Documentos e Relatórios");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- PAINEL SUPERIOR (SELEÇÃO E AÇÕES) ---
        JPanel painelSelecao = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        painelSelecao.add(new JLabel("Selecione o Animal:"));
        comboAnimais = new JComboBox<>();
        personalizarComboAnimais();
        atualizarComboAnimais();
        painelSelecao.add(comboAnimais);

        JButton btnProntuario = new JButton("Gerar Prontuário");
        JButton btnCartaoVacina = new JButton("Gerar Cartão de Vacina");
        JButton btnFaturaTutor = new JButton("Gerar Fatura do Tutor");
        painelSelecao.add(btnProntuario);
        painelSelecao.add(btnCartaoVacina);
        painelSelecao.add(btnFaturaTutor);

        // --- ÁREA DE TEXTO CENTRAL PARA EXIBIR O RELATÓRIO ---
        areaRelatorio = new JTextArea();
        areaRelatorio.setEditable(false);
        areaRelatorio.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Fonte monoespaçada para alinhamento
        JScrollPane scrollPane = new JScrollPane(areaRelatorio);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Resultado"));

        // Adiciona os painéis à janela
        add(painelSelecao, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // --- AÇÕES DOS BOTÕES ---
        btnProntuario.addActionListener(e -> gerarProntuario());
        btnCartaoVacina.addActionListener(e -> gerarCartaoVacina());
        btnFaturaTutor.addActionListener(e -> gerarFaturaTutor());
    }

    /**
     * Gera e exibe o prontuário do animal selecionado.
     * Utiliza o método da interface Imprimivel para obter o conteúdo formatado.
     */
    private void gerarProntuario() {
        Animal animal = (Animal) comboAnimais.getSelectedItem();
        if (animal != null) {
            String conteudo = animal.getProntuario().gerarConteudoImpressao(); //
            areaRelatorio.setText(conteudo);
            areaRelatorio.setCaretPosition(0); // Rola para o topo
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um animal.", "Nenhum Animal Selecionado", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Gera e exibe o cartão de vacinas do animal selecionado.
     * Utiliza o método da interface Imprimivel para obter o conteúdo formatado.
     */
    private void gerarCartaoVacina() {
        Animal animal = (Animal) comboAnimais.getSelectedItem();
        if (animal != null) {
            String conteudo = animal.getCartaoVacina().gerarConteudoImpressao(); //
            areaRelatorio.setText(conteudo);
            areaRelatorio.setCaretPosition(0); // Rola para o topo
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um animal.", "Nenhum Animal Selecionado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void gerarFaturaTutor(){
        Animal animalSelecionado = (Animal) comboAnimais.getSelectedItem();

        if(animalSelecionado != null){
            Tutor tutor = animalSelecionado.getTutor();
            FaturaTutor fatura = new FaturaTutor(tutor);
            String conteudo = fatura.gerarConteudoImpressao();
            areaRelatorio.setText(conteudo);
            areaRelatorio.setCaretPosition(0);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um animal para identificar o tutor.", "Nenhum Animal Selecionado", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Carrega e atualiza o ComboBox de animais com os dados da clínica.
     */
    private void atualizarComboAnimais() {
        comboAnimais.removeAllItems();
        for (Animal animal : clinica.getAnimais()) { //
            comboAnimais.addItem(animal);
        }
    }

    /**
     * Personaliza a renderização do ComboBox de animais para exibir o nome e o tutor.
     */
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
}