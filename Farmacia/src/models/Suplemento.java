/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import java.time.LocalDate;
import enums.ObjetivoSuplemento;


/**
 *
 * @author box
 */
public class Suplemento extends Producto {
    private ObjetivoSuplemento objetivo;

    public Suplemento(String nombreComercial, String dosis, LocalDate fechaVencimiento, ObjetivoSuplemento objetivo) {
        super(nombreComercial, dosis, fechaVencimiento);
        this.objetivo = objetivo;
    }

    public ObjetivoSuplemento getObjetivo() {
        return objetivo;
    }
    
    public void setObjetivo(ObjetivoSuplemento objetivo) {
        this.objetivo = objetivo;
    }

    @Override
    public String toString() {
        return super.toString() + " | Objetivo: " + objetivo;
    }
}
