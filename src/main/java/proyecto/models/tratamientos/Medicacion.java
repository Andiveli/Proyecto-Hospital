package proyecto.models.tratamientos;

public class Medicacion extends Tratamiento {
    private String tipoMedicacion;
    public Medicacion(String nombre, int duracion, double precio, String tipoMedicacion) {
        super(nombre, duracion, precio);
        this.tipoMedicacion = tipoMedicacion;
    }
    public String getTipoMedicacion() {
        return tipoMedicacion;
    }
    public void setTipoMedicacion(String tipoMedicacion) {
        this.tipoMedicacion = tipoMedicacion;
    }

    @Override
    public double calcularCosto() {
        double costoBase = super.calcularCosto();
        double descuento = 0.1 * costoBase; // Descuento del 10% para medicaciones
        return costoBase - descuento;
    }
}
