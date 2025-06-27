import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Clinica {

    private List<Animal> animais;
    private List<Tutor> tutores;
    private List<Veterinario> veterinarios;
    private List<Funcionario> funcionarios;
    private List<Agendamento> agendamentos; 
    
    public Clinica() {
        this.animais = new ArrayList<>();
        this.tutores = new ArrayList<>();
        this.veterinarios = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.agendamentos = new ArrayList<>();
    } 

    public double emitirCobranca(Tutor tutor, List<Faturavel> itens) {
        double total = 0;
        for (Faturavel item : itens) {
            total += item.calcularValor();
        }
        // System.out.println("Valor total para o tutor " + tutor.getNome() + ": R$ " + total);
        return total;
    }

    public void imprimirDocumento(Imprimivel documento) { 
        System.out.println(documento.gerarConteudoImpressao());
    }

    public String cadastrarTutor(Tutor novoTutor){
        if (buscarTutor(novoTutor.getCpf()) != null) {
            return "Tutor com CPF " + novoTutor.getCpf() + " já está cadastrado.";
            
        }
        this.tutores.add(novoTutor);
        return "Tutor " + novoTutor.getNome() + " cadastrado com sucesso!";
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

    public String cadastrarVeterinario(Veterinario novoVet) {
         if (buscarVeterinario(novoVet.getCpf()) != null) {
            return "Veterinário com CPF " + novoVet.getCpf() + " já está cadastrado.";
        }
        this.veterinarios.add(novoVet);
        return "Veterinário " + novoVet.getNome() + " cadastrado com sucesso!";
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

    public String agendar(Agendamento agendamento) {
        agendamentos.add(agendamento);
        return "Agendamento para " + agendamento.getAnimal().getNome() + " realizado!";
    }

    public String cancelarAgendamento(Agendamento agendamento) {
        boolean removido = agendamentos.remove(agendamento);
        if (removido) {
            return "Agendamento cancelado com sucesso.";
        } else {
            return "Agendamento não encontrado.";
        }
    }

    /**
     * Centraliza a lógica para realizar e registrar uma consulta no prontuário do animal.
     * @param animal O animal que está sendo consultado.
     * @param consulta O objeto da consulta com todos os detalhes.
     */
    public String realizarConsulta(Animal animal, Consulta consulta) {
        if (animal != null && consulta != null) {
            animal.adicionarConsulta(consulta);
            return "Consulta registrada no prontuário de " + animal.getNome() + ".";
        } else {
            return "Erro: Animal ou consulta inválidos.";
        }
    }
    
    public String aplicarVacina(Animal animal, Vacina vacina, LocalDate dataDeAplicacao, LocalDate dataDeValidade) {
        VacinaAplicada novaAplicacao = new VacinaAplicada(vacina, dataDeAplicacao, dataDeValidade);
        animal.getCartaoVacina().adicionarVacinaAplicada(novaAplicacao);
        return "Vacina '" + vacina.getNome() + "' aplicada em " + animal.getNome() + ".";
    }

    /**
     * Consulta e exibe as vacinas de um animal que vencerão nos próximos 30 dias a partir de uma data de referência.
     * @param animal O animal a ser consultado.
     * @param dataReferencia A data a partir da qual a verificação será feita (ex: hoje).
     */
    public void consultarVacinasAVencer(Animal animal, LocalDate dataReferencia) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataLimite = dataReferencia.plusDays(30);

        System.out.println("\n--- Consultando vacinas a vencer para '" + animal.getNome() + "' (entre " + dataReferencia.format(formatador)
                            + " e " + dataLimite.format(formatador) + ") ---");
        
        List<VacinaAplicada> vacinasDoAnimal = animal.getCartaoVacina().getVacinasAplicadas();
        boolean encontrou = false;

        for (VacinaAplicada vacinaApp : vacinasDoAnimal) {
            LocalDate dataValidade = vacinaApp.getDataDeValidade();
            
            // Verifica se a data de validade está DEPOIS da data de referência
            // E ANTES (ou no mesmo dia) da data limite de 30 dias.
            if (!dataValidade.isBefore(dataReferencia) && !dataValidade.isAfter(dataLimite)) {
                System.out.println("- Vacina: " + vacinaApp.getVacina().getNome() + 
                                    " (Validade: " + dataValidade.format(formatador) + ")");
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma vacina encontrada com vencimento para esta data.");
        }
        System.out.println("--------------------------------------------------");
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
}
