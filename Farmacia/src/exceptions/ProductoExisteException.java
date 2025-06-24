/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 *
 * @author box
 */
public class ProductoExisteException extends RuntimeException{
    public ProductoExisteException(String mensaje) {
        super(mensaje);
    }
}
