/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.LocalDate;
import exceptions.*;
import java.util.Objects;
/**
 *
 * @author box
 */
public abstract class Producto {
    protected String nombreComercial;
    protected String dosis;
    protected LocalDate fechaVencimiento;

    public Producto(String nombreComercial, String dosis, LocalDate fechaVencimiento) {
        if (fechaVencimiento.isBefore(LocalDate.now())) {
            throw new ProductoVencidoException();
        }
        this.nombreComercial = nombreComercial;
        this.dosis = dosis;
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getNombreComercial() { 
        return nombreComercial; 
    }
    
    public String getDosis() { 
        return dosis; 
    }
    
    public LocalDate getFechaVencimiento() { 
        return fechaVencimiento; 
    }

    public void setFechaVencimiento(LocalDate fecha) {
        if (fecha.isBefore(LocalDate.now())) {
            throw new ProductoVencidoException();
        }
        this.fechaVencimiento = fecha;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Producto{");
        sb.append("nombreComercial=").append(nombreComercial);
        sb.append(", dosis=").append(dosis);
        sb.append(", fechaVencimiento=").append(fechaVencimiento);
        sb.append('}');
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Producto producto = (Producto) obj;
        return nombreComercial.equalsIgnoreCase(producto.nombreComercial) &&
        dosis.equalsIgnoreCase(producto.dosis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreComercial.toLowerCase(), dosis.toLowerCase());
    }
}