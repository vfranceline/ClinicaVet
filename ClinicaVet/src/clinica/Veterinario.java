package clinica;

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

    /**
     * Retorna o valor da consulta com base na especialidade do veterinário.
     * @return O valor da consulta em double.
     */
    public double getPrecoPorEspecialidade() {
        switch (this.especialidade) {
            case "Cardiologia":
            case "Neurologia":
            case "Ortopedia":
                return 350.00;
            case "Oftalmologia":
            case "Dermatologia":
                return 300.00;
            case "Odontologia":
                return 280.00;
            case "Clínico Geral":
            default:
                return 250.00;
        }
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
