/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 *
 * @author box
 */
public class ProductoVencidoException extends RuntimeException {
    public ProductoVencidoException() {
        super("No se puede registrar un producto ya vencido.");
    }
}
