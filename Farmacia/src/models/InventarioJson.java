package models;

import interfaces.*;
import com.google.gson.*;
import interfaces.ISerializable;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class InventarioJson<T> implements ISerializable<T> {
    private final Gson gson;
    private final Type tipoLista;

    public InventarioJson(Type tipoLista) {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
        this.tipoLista = tipoLista;
    }

    @Override
    public void guardar(List<T> lista, String rutaArchivo) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(lista, tipoLista, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar JSON: " + e.getMessage());
        }
    }

    @Override
    public List<T> cargar(String rutaArchivo) {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            List<T> lista = new ArrayList<>();

            Gson gsonSimple = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

            for (JsonElement elem : jsonArray) {
                JsonObject obj = elem.getAsJsonObject();
                T item;
                if (obj.has("objetivo")) {
                    // es suplemento
                    item = (T) gsonSimple.fromJson(elem, Suplemento.class);
                } else {
                    // es medicamento
                    item = (T) gsonSimple.fromJson(elem, Medicamento.class);
                }
                lista.add(item);
            }

            return lista;

        } catch (IOException e) {
            System.err.println("Error al cargar JSON: " + e.getMessage());
            return null;
        }
}

    private static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        @Override
        public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.format(formatter));
        }

        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDate.parse(json.getAsString(), formatter);
        }
    }
}
