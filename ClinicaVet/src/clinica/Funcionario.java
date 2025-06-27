package clinica;

public class Funcionario extends Pessoa{
    protected String turnoDeTrabalho;

    public Funcionario(String nome, String cpf, String email, String telefone, String turnoDeTrabalho) {
        super(nome, cpf, email, telefone);
        this.turnoDeTrabalho = turnoDeTrabalho;
    }

    public String getTurnoDeTrabalho() {
        return turnoDeTrabalho;
    }

    public void setTurnoDeTrabalho(String turnoDeTrabalho) {
        this.turnoDeTrabalho = turnoDeTrabalho;
    }
}
