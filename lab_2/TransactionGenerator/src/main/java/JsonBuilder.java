import com.google.gson.*;

public class JsonBuilder {
    private JsonObject json;
    private JsonArray array;
    private String arrayName;

    public JsonBuilder() {
        json = new JsonObject();
    }

    public void addString(String name, String value) {
        json.addProperty(name, value);
    }

    public void addNumber(String name, Number value) {
        json.addProperty(name, value);
    }

    public void startArray(String name) {
        arrayName = name;
        array = new JsonArray();
    }

    public void addProductToArray(Product product, int quantity) throws Exception {
        if (array != null && arrayName != null) {
            JsonObject object = new JsonObject();
            object.addProperty("name", product.getName());
            object.addProperty("quantity", quantity);
            object.addProperty("price", product.getPrice());
            array.add(object);
        } else {
            throw new Exception("Array has not started");
        }
    }

    public void endArray() throws Exception {
        if(array != null && arrayName != null) {
            json.add(arrayName, array);
            arrayName = null;
            array = null;
        }
        else
            throw new Exception("Array has not started");
    }

    public String getResult() throws Exception {
        if(array != null || arrayName != null)
            throw new Exception("Array is still opened");
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        return gson.toJson(json);
    }
}
