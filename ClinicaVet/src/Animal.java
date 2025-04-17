public class Animal {
    private String nome;
    private String raca;
    private Tutor nomeTutor;
    private Data dataDeNascimento;

    public Animal(String nome, String raca, Tutor nomeTutor, Data dataDeNascimento) {
        this.nome = nome;
        this.raca = raca;
        this.nomeTutor = nomeTutor;
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public Tutor getNomeTutor() {
        return nomeTutor;
    }

    public void setNomeTutor(Tutor nomeTutor) {
        this.nomeTutor = nomeTutor;
    }

    public Data getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Data dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }
}
