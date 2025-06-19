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

    /**
     * calcula e exibe o valor total de um atendimento
     * @param tutor tutor responsável pelo pagamento
     * @param itens lista de itens que implementam a interface Faturavel (Consultas, Vacinas) 
     */
    public double emitirCobranca(Tutor tutor, List<Faturavel> itens) {
        double total = 0;
        for (Faturavel item : itens) {
            total += item.calcularValor();
        }
        System.out.println("Valor total para o tutor " + tutor.getNome() + ": R$ " + total);
        return total;
    }

    /**
     * print um documento genérico
     * @param documento objeto que implementa a interface Imprimivel (Prontuario, CartaoVacina) 
     */
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

    public void realizarConsulta(Animal animal, Consulta consulta) {
        animal.adicionarConsulta(consulta);
        System.out.println("Consulta registrada para " + animal.getNome());
    }

    // public void aplicarVacina(Animal animal, Vacina vacina, String data, String validade) {
    //     vacina.setDataDeVacina(data);
    //     vacina.setValidade(validade);
    //     animal.adicionarVacina(vacina); 
    //     System.out.println("Vacina " + vacina.getNome() + " aplicada em " + animal.getNome() + " na data " + data);
    // }


    // public void consultarVacinasAVencer(Animal animal, int mes, int ano) {
    //     System.out.println("--- Vacinas a vencer em " + mes + "/" + ano + " para " + animal.getNome() + " ---");
    //     for (Vacina vacina : animal.getVacinas()) { // Supondo método getVacinas()
    //         String[] partes = vacina.getValidade().split("/"); // Supondo formato "MM/AAAA"
    //         int mesVacina = Integer.parseInt(partes[0]);
    //         int anoVacina = Integer.parseInt(partes[1]);
    //         if (mesVacina == mes && anoVacina == ano) {
    //             System.out.println("- " + vacina.getNome() + " (Validade: " + vacina.getValidade() + ")");
    //         }
    //     }
    // }

}