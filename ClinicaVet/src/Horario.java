public class Horario {
    //Atributos
    private int hora, minutos;

    //Construtor
    public Horario(int hora, int minutos){
        this.hora = hora;
        this.minutos = minutos;
    }

    //Getters
    public int getHora(){ return hora; }

    public int getMinutos(){ return minutos; }

    //Método para verificar horário disponível para consulta
}
