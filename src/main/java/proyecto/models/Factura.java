package proyecto.models;

import java.time.LocalDateTime;

public class Factura {
    private int numFactura;
    private LocalDateTime fechaEmision;
    private Cita citaId;
    private double montoTotal;
    private String formaPago;

    public Factura(int numFactura, LocalDateTime fechaEmision, Cita citaId, double montoTotal, String formaPago) {
        this.numFactura = numFactura;
        this.fechaEmision = fechaEmision;
        this.citaId = citaId;
        this.montoTotal = montoTotal;
        this.formaPago = formaPago;
    }
    public void generarFactura() {
        System.out.println("Generando factura...");
    }

    public void imprimirFactura() {
        System.out.println("Imprimiendo factura...");
    }

    public void calcularIva() {
        System.out.println("Calculando IVA...");
    }
}
