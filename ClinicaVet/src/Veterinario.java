public class Veterinario {

    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String especialidade;
    private String cfmv;

    public Veterinario(String nome, String cpf, String email, String telefone, String especialidade, String cfmv) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.especialidade = especialidade;
        this.cfmv = cfmv;
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
}
