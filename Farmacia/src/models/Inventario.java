/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.time.LocalDate;

/**
 *
 * @author box
 */
public class Inventario {
    private List<Producto> productos;

    public Inventario() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }

    public void modificarProducto(int index, Producto productoNuevo) {
        productos.set(index, productoNuevo);
    }

    public List<Producto> listarProductos() {
        return productos;
    }

    public void medicamentosPorVencer(int dias, String rutaArchivo) {
        List<Medicamento> proximos = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        for (Producto p : productos) {
            if (p instanceof Medicamento med &&
                !med.getFechaVencimiento().isBefore(hoy) &&
                med.getFechaVencimiento().isBefore(hoy.plusDays(dias))) {
                proximos.add(med);
            }
        }

        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            for (Medicamento med : proximos) {
                writer.write(med.toString() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error al escribir archivo: " + e.getMessage());
        }
    }
}
