import java.util.ArrayList;

public class Veterinario extends Funcionario {
    protected String especialidade;
    protected String cfmv;
    private ArrayList<Consulta> consultas;

    public Veterinario(String nome, String cpf, String email, String telefone, String especialidade, String cfmv, String turnoDeTrabalho) {
        super(nome, cpf, email, telefone, turnoDeTrabalho);
        this.especialidade = especialidade;
        this.cfmv = cfmv;
        this.consultas = new ArrayList<>();
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getCfmv() {
        return cfmv;
    }

    public void setCfmv(String cfmv) {
        this.cfmv = cfmv;
    }
    
    public ArrayList<Consulta> getConsultas() {
        return consultas;
    }
    
    public void adicionarConsulta(Consulta consulta) {
        consultas.add(consulta);
    }

    public void removerConsulta(Consulta consulta) {
        consultas.remove(consulta);
    }
}
