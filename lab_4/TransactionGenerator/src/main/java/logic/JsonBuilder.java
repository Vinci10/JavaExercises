package logic;

import com.google.gson.*;
import model.Product;
import org.springframework.stereotype.Service;

@Service
public class JsonBuilder implements DataFormatBuilder{
    private JsonObject json;
    private JsonArray array;
    private String arrayName;

    public JsonBuilder() {
        json = new JsonObject();
    }

    @Override
    public void addString(String name, String value) {
        json.addProperty(name, value);
    }
    @Override
    public void addNumber(String name, Number value) {
        json.addProperty(name, value);
    }
    @Override
    public void startArray(String name) throws Exception {
        if(!arrayIsOpen()) {
            arrayName = name;
            array = new JsonArray();
        } else
            throw new Exception("Array has been started");
    }
    @Override
    public void addProductToArray(Product product, int quantity) throws Exception {
        if (arrayIsOpen()) {
            JsonObject object = new JsonObject();
            object.addProperty("name", product.getName());
            object.addProperty("quantity", quantity);
            object.addProperty("price", product.getPrice());
            array.add(object);
        } else {
            throw new Exception("Array has not been started");
        }
    }
    @Override
    public void endArray() throws Exception {
        if(arrayIsOpen()) {
            json.add(arrayName, array);
            arrayName = null;
            array = null;
        }
        else
            throw new Exception("Array has not been started");
    }
    @Override
    public String getResult() throws Exception {
        if(arrayIsOpen())
            throw new Exception("Array is still opened");
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        return gson.toJson(json);
    }

    @Override
    public void reset() {
        json = new JsonObject();
        arrayName = null;
        array = null;
    }

    private boolean arrayIsOpen() {
        if(arrayName == null && array == null)
            return false;
        else
            return true;
    }

    @Override
    public String getOutputType() {
        return ".json";
    }
}
