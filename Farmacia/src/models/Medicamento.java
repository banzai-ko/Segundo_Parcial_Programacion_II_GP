/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import java.time.LocalDate;

/**
 *
 * @author box
 */
public class Medicamento extends Producto {
    
    private boolean requiereReceta;

    public Medicamento(String nombre, String dosis, LocalDate vencimiento, boolean requiereReceta) {
        super(nombre, dosis, vencimiento);
        this.requiereReceta = requiereReceta;
    }

    public boolean isRequiereReceta() { 
        return requiereReceta; 
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Receta: ").append(requiereReceta);
        sb.append('}');
        return sb.toString();
    }
}
