package proyecto.models.users;

import java.util.ArrayList;

import proyecto.enums.TipoSeguro;

public class Paciente extends Persona {
    private TipoSeguro tipoSeguro;
    private ArrayList<String> historialCitas;

    public Paciente(int id, String nombre, String apellido, String correo, String telefono, TipoSeguro tipoSeguro) {
        super(id, nombre, apellido, correo, telefono);
        this.tipoSeguro = tipoSeguro;
        this.historialCitas = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Nombre: " + getNombre() + ", Teléfono: " + getTelefono() + ", Tipo de Seguro: " + tipoSeguro;
    }

    public TipoSeguro getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(TipoSeguro tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public ArrayList<String> getHistorialCitas() {
        return historialCitas;
    }

    public void setHistorialCitas(ArrayList<String> historialCitas) {
        this.historialCitas = historialCitas;
    }


   
}
