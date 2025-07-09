package clinica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Classe central do sistema, que atua como uma fachada para a lógica de negócio.
 * Ela gerencia todas as listas de dados (animais, tutores, funcionários, etc.) e centraliza
 * as operações principais da clínica, como cadastros, agendamentos e faturamento.
 * A GUI interage diretamente com esta classe, que delega as responsabilidades para as
 * outras classes do modelo.
 */

public class Clinica {

    private List<Animal> animais;
    private List<Tutor> tutores;
    private List<Veterinario> veterinarios;
    private List<Funcionario> funcionarios;
    private List<Agendamento> agendamentos; 
    private List<String> especialidades;
    private List<Vacina> vacinasDisponiveis;
    
    public Clinica() {
        this.animais = new ArrayList<>();
        this.tutores = new ArrayList<>();
        this.veterinarios = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.agendamentos = new ArrayList<>();
        this.especialidades = new ArrayList<>(Arrays.asList("Cardiologia", "Oftalmologia", "Dermatologia", "Ortopedia", "Neurologia", "Odontologia", "Clínico Geral"));
        this.vacinasDisponiveis = new ArrayList<>(Arrays.asList(
            new Vacina("V10 (Múltipla)", 120.00),
            new Vacina("Antirrábica (Raiva)", 80.00),
            new Vacina("Gripe Canina", 95.00),
            new Vacina("Leishmaniose (Calazar)", 150.00),
            new Vacina("Felin-O-Vax (Quádrupla Felina)", 130.00)
        ));
    } 

    /**
     * Calcula o valor total de uma lista de itens faturáveis (consultas, vacinas, etc.).
     * opera sobre a interface Faturavel,
     * tratando diferentes tipos de objetos (Consulta, Vacina) de forma uniforme.
     *
     * @param tutor O tutor que receberá a cobrança.
     * @param itens A lista de serviços e produtos a serem cobrados.
     * @return O valor total calculado.
     */
    public double emitirCobranca(Tutor tutor, List<Faturavel> itens) {
        double total = 0;
        for (Faturavel item : itens) {
            total += item.calcularValor();
        }
        // System.out.println("Valor total para o tutor " + tutor.getNome() + ": R$ " + total);
        return total;
    }

    /**
     * Imprime o conteúdo de um documento que implementa a interface Imprimivel.
     * trata diferentes tipos de documentos (Prontuario,
     * CartaoVacina, FaturaTutor) com a mesma chamada de método.
     *
     * @param documento O documento a ser impresso no console.
     */
    public void imprimirDocumento(Imprimivel documento) { 
        System.out.println(documento.gerarConteudoImpressao());
    }

    // --- Métodos de Cadastro e Remoção (Operações CRUD) ---
    public String cadastrarTutor(Tutor novoTutor){
        if (buscarTutor(novoTutor.getCpf()) != null) {
            return "Tutor com CPF " + novoTutor.getCpf() + " já está cadastrado.";
            
        }
        this.tutores.add(novoTutor);
        return "Tutor " + novoTutor.getNome() + " cadastrado com sucesso!";
    }

    public String removerTutor(Tutor tutor){
        for(Tutor t : tutores){
            if(t.getCpf().equals(tutor.getCpf())){
                tutores.remove(tutor);
                return "Tutor removido";
            }
        }
        return "ERRO: não foi possivel encontrar o tutor selecionado";
    }

    public String cadastrarAnimal(Animal novoAnimal){
        if (this.tutores.contains(novoAnimal.getTutor())) {
            this.animais.add(novoAnimal);
            novoAnimal.getTutor().addAnimal(novoAnimal); 
            return "Animal " + novoAnimal.getNome() + " cadastrado com sucesso para o tutor " 
                                + novoAnimal.getTutor().getNome() + "!";
        } else {
            return "ERRO: Não foi possível cadastrar o animal, pois seu tutor não foi encontrado na clínica.";
        }
    }

    public String removerAnimal(Animal animal){
        if (animais.contains(animal)) {
            animais.remove(animal);
            Tutor tutor = animal.getTutor();
            if (tutor != null) {
                tutor.getAnimais().remove(animal);
            }
            return "Animal removido com sucesso!";
        } else {
            return "ERRO: Animal não encontrado na clínica.";
        }
    }

    public String cadastrarVeterinario(Veterinario novoVet) {
         if (buscarVeterinario(novoVet.getCpf()) != null) {
            return "Veterinário com CPF " + novoVet.getCpf() + " já está cadastrado.";
        }
        this.veterinarios.add(novoVet);
        return "Veterinário " + novoVet.getNome() + " cadastrado com sucesso!";
    }

    public String removerVeterinario(Veterinario vet){
        for (Veterinario v : veterinarios) {
            if (v.getCpf().equals(vet.getCpf())) {
                veterinarios.remove(vet);
                return "Veterinário removido com sucesso!";
            }
        }
        return "ERRO: não foi possível encontrar o veterinário selecionado";
    }

    public String cadastrarFuncionario(Funcionario novoFunc) {
        if (buscarFuncionario(novoFunc.getCpf()) != null || buscarVeterinario(novoFunc.getCpf()) != null) {
           return "Funcionário com CPF " + novoFunc.getCpf() + " já está cadastrado.";
       }
       this.funcionarios.add(novoFunc);
       return "Funcionário " + novoFunc.getNome() + " cadastrado com sucesso!";
    }

    public String removerFuncionario(Funcionario func) {
        if (funcionarios.remove(func)) {
            return "Funcionário removido com sucesso!";
        }
        return "ERRO: não foi possível encontrar o funcionário selecionado";
    }

    // --- Métodos de Busca ---
    public Funcionario buscarFuncionario(String cpf) {
        for (Funcionario func : funcionarios) {
            if (func.getCpf().equals(cpf)) {
                return func;
            }
        }
        return null;
    }

    public Tutor buscarTutor(String cpf){
        for(Tutor tutor : tutores){
            if(tutor.getCpf().equals(cpf)){
                return tutor;
            }
        }
        return null;
    }

    public Veterinario buscarVeterinario(String cpf){
        for(Veterinario veterinario : veterinarios){
            if(veterinario.getCpf().equals(cpf)){
                return veterinario;
            }
        }
        return null;
    }

    public Animal buscarAnimal(String nome) {
        for (Animal animal : animais) {
            if (animal.getNome().equalsIgnoreCase(nome)) {
                return animal;
            }
        }
        return null;
    }

    /**
     * Agenda uma consulta, verificando se já não existe um agendamento
     * para a mesma data, hora e especialidade.
     * @param novoAgendamento O agendamento a ser criado.
     */
    public String agendar(Agendamento novoAgendamento) {
        for (Agendamento agendamentoExiste : this.agendamentos) {
            if (agendamentoExiste.getDataConsulta().equals(novoAgendamento.getDataConsulta()) &&
                agendamentoExiste.getHora().equals(novoAgendamento.getHora()) &&
                agendamentoExiste.getEspecialidade().equalsIgnoreCase(novoAgendamento.getEspecialidade())) {

                return "ERRO: Já existe um agendamento para a especialidade '" + novoAgendamento.getEspecialidade() +
                    "' neste mesmo horário.";
            }
        }
        this.agendamentos.add(novoAgendamento);
        return "Agendamento para " + novoAgendamento.getAnimal().getNome() + " realizado com sucesso!";
    }

    public boolean cancelarAgendamento(Agendamento agendamento) {
        boolean removido = agendamentos.remove(agendamento);
        if(removido){
            System.out.println("Agendamento cancelado com sucesso.");
        } else {
            System.out.println("Agendamento não encontrado.");
        }
        return removido;
    }

    /**
     * Centraliza a lógica para registrar uma consulta no prontuário do animal.
     * @param animal O animal que está sendo consultado.
     * @param consulta O objeto da consulta com todos os detalhes.
     * @return Uma string de confirmação.
     */
    public String realizarConsulta(Animal animal, Consulta consulta) {
        if (animal != null && consulta != null) {
            animal.adicionarConsulta(consulta);
            return "Consulta registrada no prontuário de " + animal.getNome() + ".";
        } else {
            return "Erro: Animal ou consulta inválidos.";
        }
    }
    
    /**
     * Registra a aplicação de uma vacina no cartão do animal.
     * @param animal O animal a ser vacinado.
     * @param vacina O tipo de vacina.
     * @param dataDeAplicacao A data da aplicação.
     * @param dataDeValidade A data de vencimento da vacina.
     * @return Uma string de confirmação.
     */
    public String aplicarVacina(Animal animal, Vacina vacina, LocalDate dataDeAplicacao, LocalDate dataDeValidade) {
        VacinaAplicada novaAplicacao = new VacinaAplicada(vacina, dataDeAplicacao, dataDeValidade);
        animal.getCartaoVacina().adicionarVacinaAplicada(novaAplicacao);
        return "Vacina '" + vacina.getNome() + "' aplicada em " + animal.getNome() + ".";
    }

    
    /**
     * Consulta e retorna uma lista de todas as vacinas aplicadas que vencerão no mês atual.
     * @return Uma lista de objetos VacinaAplicada com o animal associado.
     */
    public List<VacinaAplicada> consultarVacinasVencendoMesAtual() {
        int mesAtual = LocalDate.now().getMonthValue();
        List<VacinaAplicada> vacinasAVencer = new ArrayList<>();

        for (Animal animal : this.animais) {
            for (VacinaAplicada vacinaApp : animal.getCartaoVacina().getVacinasAplicadas()) {
                if (vacinaApp.getDataDeValidade().getMonthValue() == mesAtual) {
                    vacinasAVencer.add(vacinaApp);
                }
            }
        }
        // Ordena a lista por data de validade para melhor visualização
        vacinasAVencer.sort(Comparator.comparing(VacinaAplicada::getDataDeValidade));
        return vacinasAVencer;
    }

    /**
     * Consulta o histórico de consultas de um animal específico.
     * Exibe as informações formatadas no console.
     * @param animal O animal cujo histórico de consultas será consultado.
     */
    public void consultarHistoricoConsultas(Animal animal){
        System.out.println("\n--- Histórico de Consultas de " + animal.getNome() + " ---");
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Prontuario prontuario = animal.getProntuario();

        if(prontuario.getConsultas().isEmpty()){
            System.out.println("Nenhum registro de consulta encontrado para este animal.");
        } else {
            for(Consulta consulta : prontuario.getConsultas()){
                System.out.println("----------------------------------------");
                System.out.println("Data: " + consulta.getDataConsulta().format(formatador));
                System.out.println("Veterinário(a): " + consulta.getVeterinario().getNome());
                System.out.println("Problema Relatado: " + consulta.getProblema());
                System.out.println("Diagnóstico: " + consulta.getDiagnostico());
                System.out.println("Medicação/Tratamento: " + consulta.getMedicamento());
            }
            System.out.println("----------------------------------------");
        }
    }

    //gets
    public List<Tutor> getTutores() {
        return tutores;
    }

    public List<Animal> getAnimais() {
        return animais;
    }

    public List<Veterinario> getVeterinarios() {
        return veterinarios;
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public List<String> getEspecialidade() {
        return especialidades;
    }

    public List<Vacina> getVacinasDisponiveis() {
        return vacinasDisponiveis;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }
}
