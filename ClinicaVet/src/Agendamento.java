public class Agendamento {
    private Data dataConsulta;
    private Horario hora;
    private String especialidade;

    public Agendamento(Data dataConsulta, Horario hora, String especialidade) {
        this.dataConsulta = dataConsulta;
        this.hora = hora;
        this.especialidade = especialidade;
    }

    public Data getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(Data dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public Horario getHora() {
        return hora;
    }

    public void setHora(Horario hora) {
        this.hora = hora;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
