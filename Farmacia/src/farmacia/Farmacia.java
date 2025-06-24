/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package farmacia;
import controllers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author box
 */


public class Farmacia extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ListaProductos.fxml"));
        Parent root = loader.load();  // carga una sola vez

        ViewController viewController = loader.getController();

        Scene scene = new Scene(root);  // usa root ya cargado

        stage.setScene(scene);
        stage.setTitle("Gestión de Inventario - Farmacia Don Alberto");

        // Si tienes métodos de cierre para guardar, usa el controlador:
        // stage.setOnCloseRequest(e -> formController.guardarArchivo());

        stage.show();
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
}
