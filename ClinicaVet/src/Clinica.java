import java.time.LocalDate;
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
        System.out.println("Valor total para o tutor " + tutor.getNome() + ": R$ " + total);
        return total;
    }

    public void imprimirDocumento(Imprimivel documento) { 
        System.out.println(documento.gerarConteudoImpressao());
    }

    public void cadastrarTutor(Tutor novoTutor){
        if (buscarTutor(novoTutor.getCpf()) != null) {
            System.out.println("Tutor com CPF " + novoTutor.getCpf() + " já está cadastrado.");
            return;
        }
        this.tutores.add(novoTutor);
        System.out.println("Tutor " + novoTutor.getNome() + " cadastrado com sucesso!");
    }

    public void cadastrarAnimal(Animal novoAnimal){
        if (this.tutores.contains(novoAnimal.getTutor())) {
            this.animais.add(novoAnimal);
            novoAnimal.getTutor().addAnimal(novoAnimal); 
            System.out.println("Animal " + novoAnimal.getNome() + " cadastrado com sucesso para o tutor " + novoAnimal.getTutor().getNome() + "!");
        } else {
            System.out.println("ERRO: Não foi possível cadastrar o animal, pois seu tutor não foi encontrado na clínica.");
        }
    }

    public void cadastrarVeterinario(Veterinario novoVet) {
         if (buscarVeterinario(novoVet.getCpf()) != null) {
            System.out.println("Veterinário com CPF " + novoVet.getCpf() + " já está cadastrado.");
            return;
        }
        this.veterinarios.add(novoVet);
        System.out.println("Veterinário " + novoVet.getNome() + " cadastrado com sucesso!");
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

    public void agendar(Agendamento agendamento) {
        agendamentos.add(agendamento);
        System.out.println("Agendamento para " + agendamento.getAnimal().getNome() + " realizado!");
    }

    public boolean cancelarAgendamento(Agendamento agendamento) {
        boolean removido = agendamentos.remove(agendamento);
        if (removido) {
            System.out.println("Agendamento cancelado com sucesso.");
        } else {
            System.out.println("Agendamento não encontrado.");
        }
        return removido;
    }
    
    public void aplicarVacina(Animal animal, Vacina vacina, LocalDate dataDeAplicacao, LocalDate dataDeValidade) {
        VacinaAplicada novaAplicacao = new VacinaAplicada(vacina, dataDeAplicacao, dataDeValidade);
        animal.getCartaoVacina().adicionarVacinaAplicada(novaAplicacao);
        System.out.println("Vacina '" + vacina.getNome() + "' aplicada em " + animal.getNome() + ".");
    }

    public void consultarVacinasAVencer(Animal animal, int mes, int ano) {
        System.out.println("\n--- Consultando vacinas a vencer em " + String.format("%02d", mes) + "/" + ano + " para " + animal.getNome() + " ---");
        
        List<VacinaAplicada> vacinasDoAnimal = animal.getCartaoVacina().getVacinasAplicadas();
        boolean encontrou = false;

        for (VacinaAplicada vacinaApp : vacinasDoAnimal) {
            LocalDate dataValidade = vacinaApp.getDataDeValidade();
            
            if (dataValidade.getMonthValue() == mes && dataValidade.getYear() == ano) {
                System.out.println("- Vacina: " + vacinaApp.getVacina().getNome() + " (Validade: " + dataValidade.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ")");
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma vacina encontrada com vencimento para esta data.");
        }
        System.out.println("--------------------------------------------------");
    }
}
