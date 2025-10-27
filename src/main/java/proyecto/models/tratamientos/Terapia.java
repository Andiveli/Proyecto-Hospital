package proyecto.models.tratamientos;

public class Terapia extends Tratamiento  {
    private String tipoTerapia;
    public Terapia(String nombre, int duracion, double precio, String tipoTerapia) {
        super(nombre, duracion, precio);
        this.tipoTerapia = tipoTerapia;
    }
    public String getTipoTerapia() {
        return tipoTerapia;
    }
    public void setTipoTerapia(String tipoTerapia) {
        this.tipoTerapia = tipoTerapia;
    }

    @Override
    public double calcularCosto() {
        double costoBase = super.calcularCosto();
        double descuento = 0.15 * costoBase; // Descuento del 15% para terapias
        return costoBase - descuento;
    }
}
