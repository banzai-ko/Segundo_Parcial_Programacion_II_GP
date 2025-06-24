package controllers;
import models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Inventario;
import models.Producto;

import java.io.IOException;
import java.util.List;

public class ViewController {

    @FXML
    private ListView<Producto> listViewProductos;

    private Inventario inventario = new Inventario();

    private ObservableList<Producto> productosObservable;

    @FXML
    public void initialize() {
        // Cargar productos desde JSON
        InventarioJson serializador = new InventarioJson(new com.google.gson.reflect.TypeToken<List<Producto>>(){}.getType());
        List<Producto> lista = serializador.cargar("productos.json");
        if (lista != null) {
            lista.forEach(inventario::agregarProducto);
        }

        productosObservable = FXCollections.observableArrayList(inventario.listarProductos());
        listViewProductos.setItems(productosObservable);

        // Mostrar texto legible en ListView (si Producto no tiene toString adecuado, puedes usar cell factory)
        listViewProductos.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Producto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.toString());
            }
        });
    }

    @FXML
    public void onAgregar() {
        abrirFormulario(null);
    }

    @FXML
    public void onModificar() {
        Producto seleccionado = listViewProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Seleccione un producto para modificar.");
            return;
        }
        abrirFormulario(seleccionado);
    }

    @FXML
    public void onEliminar() {
        Producto seleccionado = listViewProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Seleccione un producto para eliminar.");
            return;
        }
        inventario.listarProductos().remove(seleccionado);
        guardarCambios();
        actualizarLista();
    }

    private void abrirFormulario(Producto producto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FormProducto.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(producto == null ? "Agregar Producto" : "Modificar Producto");
            stage.initModality(Modality.APPLICATION_MODAL);

            FormController controller = loader.getController();
            controller.setInventario(inventario);
            controller.setProducto(producto);
            controller.setOnGuardarCallback(() -> {
                guardarCambios();
                actualizarLista();
            });

            stage.showAndWait();

        } catch (IOException e) {
            mostrarError("No se pudo abrir el formulario: " + e.getMessage());
        }
    }

    private void guardarCambios() {
        InventarioJson serializador = new InventarioJson(new com.google.gson.reflect.TypeToken<List<Producto>>(){}.getType());
        serializador.guardar(inventario.listarProductos(), "productos.json");
        archivoMedicamentosPorVencer();
    }

    private void actualizarLista() {
        productosObservable.setAll(inventario.listarProductos());
    }
    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    private void archivoMedicamentosPorVencer() {
        String rutaArchivo = "medicamentos_por_vencer.txt";

        inventario.medicamentosPorVencer(30, rutaArchivo);  

        System.out.println("Archivo generado: " + rutaArchivo);
    }
}
