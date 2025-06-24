/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import java.util.List;

/**
 *
 * @author box
 */
public interface ISerializable<T> {
    void guardar(List<T> lista, String rutaArchivo);
    List<T> cargar(String rutaArchivo);
}
