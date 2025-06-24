package controllers;

import enums.ObjetivoSuplemento;
import exceptions.ProductoExisteException;
import exceptions.ProductoVencidoException;
import interfaces.ISerializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.*;

import java.time.LocalDate;
import java.util.List;

public class FormController {

    @FXML public TextField txtNombre;
    @FXML public TextField txtDosis;
    @FXML public DatePicker dpFechaVencimiento;

    @FXML public RadioButton rbMedicamento;
    @FXML public RadioButton rbSuplemento;

    @FXML public CheckBox cbReceta;
    @FXML public ComboBox<ObjetivoSuplemento> comboObjetivo;

    private final ISerializable<Producto> serializador = new InventarioJson(
            new com.google.gson.reflect.TypeToken<List<Producto>>(){}.getType());

    private Inventario inventario;

    private Producto productoEditar;  // null si es nuevo producto

    private Runnable onGuardarCallback; 

    @FXML
    public void initialize() {
        comboObjetivo.getItems().addAll(ObjetivoSuplemento.values());
        comboObjetivo.setDisable(true);
        cbReceta.setDisable(true);

        ToggleGroup group = new ToggleGroup();
        rbMedicamento.setToggleGroup(group);
        rbSuplemento.setToggleGroup(group);

        rbMedicamento.setOnAction(e -> {
            cbReceta.setDisable(false);
            comboObjetivo.setDisable(true);
        });

        rbSuplemento.setOnAction(e -> {
            cbReceta.setDisable(true);
            comboObjetivo.setDisable(false);
        });
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public void setProducto(Producto producto) {
        this.productoEditar = producto;
        if (producto != null) {
            cargarProducto(producto);
        }
    }

    public void setOnGuardarCallback(Runnable callback) {
        this.onGuardarCallback = callback;
    }

    private void cargarProducto(Producto producto) {
        txtNombre.setText(producto.getNombreComercial());
        txtDosis.setText(producto.getDosis());
        dpFechaVencimiento.setValue(producto.getFechaVencimiento());

        if (producto instanceof Medicamento med) {
            rbMedicamento.setSelected(true);
            cbReceta.setSelected(med.isRequiereReceta());
            cbReceta.setDisable(false);
            comboObjetivo.setDisable(true);
        } else if (producto instanceof Suplemento sup) {
            rbSuplemento.setSelected(true);
            comboObjetivo.setValue(sup.getObjetivo());
            comboObjetivo.setDisable(false);
            cbReceta.setDisable(true);
        }
    }

    @FXML
    public void onGuardar() {
        try {
            String nombre = txtNombre.getText().trim();
            String dosis = txtDosis.getText().trim();
            LocalDate fecha = dpFechaVencimiento.getValue();

            if (nombre.isEmpty() || dosis.isEmpty() || fecha == null) {
                throw new IllegalArgumentException("Complete todos los campos.");
            }

            if (fecha.isBefore(LocalDate.now())) {
                throw new ProductoVencidoException();
            }

            Producto nuevoProducto;

            if (rbMedicamento.isSelected()) {
                boolean receta = cbReceta.isSelected();
                nuevoProducto = new Medicamento(nombre, dosis, fecha, receta);
            } else if (rbSuplemento.isSelected()) {
                ObjetivoSuplemento objetivo = comboObjetivo.getValue();
                if (objetivo == null) throw new IllegalArgumentException("Seleccione un objetivo.");
                nuevoProducto = new Suplemento(nombre, dosis, fecha, objetivo);
            } else {
                throw new IllegalArgumentException("Seleccione tipo de producto.");
            }

            List<Producto> listaProductos = inventario.listarProductos();

            boolean existe = listaProductos.stream()
                    .anyMatch(p -> p.equals(nuevoProducto) && p != productoEditar);

            if (existe) {
                throw new ProductoExisteException("Este producto ya fue registrado.");
            }

            if (productoEditar == null) {
                inventario.agregarProducto(nuevoProducto);
            } else {
                int idx = listaProductos.indexOf(productoEditar);
                if (idx >= 0) {
                    listaProductos.set(idx, nuevoProducto);
                }
            }

            serializador.guardar(listaProductos, "productos.json");

            if (onGuardarCallback != null) {
                onGuardarCallback.run();
            }

            cerrarVentana();

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    public void onCancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

    private void mostrarError(String mensaje) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }
}
