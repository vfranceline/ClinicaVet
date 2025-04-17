public class Funcionario {
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String turnoDeTrabalho;

    public Funcionario(String nome, String cpf, String email, String telefone, String turnoDeTrabalho) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.turnoDeTrabalho = turnoDeTrabalho;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTurnoDeTrabalho() {
        return turnoDeTrabalho;
    }

    public void setTurnoDeTrabalho(String turnoDeTrabalho) {
        this.turnoDeTrabalho = turnoDeTrabalho;
    }
}
