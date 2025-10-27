package proyecto.models.tratamientos;

public class Cirugia extends Tratamiento{
    private String tipoCirugia;

    public Cirugia(String nombre, int duracion, double precio, String tipoCirugia) {
        super(nombre, duracion, precio);
        this.tipoCirugia = tipoCirugia;
    }

    public String getTipoCirugia() {
        return tipoCirugia;
    }
    public void setTipoCirugia(String tipoCirugia) {
        this.tipoCirugia = tipoCirugia;
    }

    @Override
    public double calcularCosto() {
        double costoBase = super.calcularCosto();
        double recargo = 0.2 * costoBase; // Recargo del 20% para cirugías
        return costoBase + recargo;
    }
}
