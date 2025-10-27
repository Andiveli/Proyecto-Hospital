package proyecto.models.users;

import java.util.ArrayList;

public class Paciente extends Persona {
    private String tipoSeguro;
    private ArrayList<String> historialCitas;

    public Paciente(int id, String nombre, String telefono, String tipoSeguro) {
        super(id, nombre, telefono);
        this.tipoSeguro = tipoSeguro;
        this.historialCitas = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Nombre: " + getNombre() + ", Teléfono: " + getTelefono() + ", Tipo de Seguro: " + tipoSeguro;
    }

    public String getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(String tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public ArrayList<String> getHistorialCitas() {
        return historialCitas;
    }

    public void setHistorialCitas(ArrayList<String> historialCitas) {
        this.historialCitas = historialCitas;
    }

   
}
