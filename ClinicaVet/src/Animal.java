public class Animal {
    private String nome;
    private String raca;
    private Tutor nomeTutor;
    private Data dataDeNascimento;
    private ArrayList<Consulta> consultas;
    private ArrayList<Vacina> vacinas;

    public Animal(String nome, String raca, Tutor nomeTutor, Data dataDeNascimento) {
        this.nome = nome;
        this.raca = raca;
        this.nomeTutor = nomeTutor;
        this.dataDeNascimento = dataDeNascimento;
        this.consultas = new ArrayList<>();
        this.vacinas = new ArrayList<>();
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

    public ArrayList<Consulta> getConsultas() {
        return consultas;
    }

    public void adicionarConsulta(Consulta consulta) {
        consultas.add(consulta);
    }

    public ArrayList<Vacina> getVacinas() {
        return vacinas;
    }

    public void adicionarVacina(Vacina vacina) {
        vacinas.add(vacina);
    }
}
