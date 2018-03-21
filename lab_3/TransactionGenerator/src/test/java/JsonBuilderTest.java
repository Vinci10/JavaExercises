import org.junit.Assert;
import org.junit.Test;

public class JsonBuilderTest {
    @Test
    public void correctlyAddString() throws Exception {
        JsonBuilder jsonBuilder = new JsonBuilder();
        jsonBuilder.addString("name", "value");
        String result = jsonBuilder.getResult();
        String expected = "{\n  \"name\": \"value\"\n}";
        Assert.assertEquals(result, expected);
    }

    @Test
    public void correctlyAddNumber() throws Exception {
        JsonBuilder jsonBuilder = new JsonBuilder();
        jsonBuilder.addNumber("name", 2.5);
        String result = jsonBuilder.getResult();
        String expected = "{\n  \"name\": 2.5\n}";
        Assert.assertEquals(result, expected);
    }

    @Test
    public void correctlyStartAndCloseArray() throws Exception {
        JsonBuilder jsonBuilder = new JsonBuilder();
        jsonBuilder.startArray("array");
        jsonBuilder.endArray();
        Assert.assertEquals(jsonBuilder.getResult(), "{\n  \"array\": []\n}");
    }

    @Test(expected = Exception.class)
    public void wrongStartAndCloseArray() throws Exception {
        JsonBuilder jsonBuilder = new JsonBuilder();
        jsonBuilder.startArray("array");
        Assert.assertEquals(jsonBuilder.getResult(), "{\n  \"array\": []\n}");
    }

    @Test(expected = Exception.class)
    public void wrongCloseArray() throws Exception {
        JsonBuilder jsonBuilder = new JsonBuilder();
        jsonBuilder.endArray();
    }

    @Test
    public void correctlyAddProductToArray() throws Exception {
        JsonBuilder jsonBuilder = new JsonBuilder();
        jsonBuilder.startArray("array");
        jsonBuilder.addProductToArray(new Product("prod", 5.0), 7);
        jsonBuilder.endArray();
        String expected = "{\n  " +
                "\"array\": [\n    " +
                "{\n      \"name\": \"prod\",\n" +
                "      \"quantity\": 7,\n" +
                "      \"price\": 5.0\n    }\n" +
                "  ]\n}";
        Assert.assertEquals(jsonBuilder.getResult(), expected);
    }

    @Test(expected = Exception.class)
    public void wrongAddProductToArray() throws Exception {
        JsonBuilder jsonBuilder = new JsonBuilder();
        jsonBuilder.addProductToArray(new Product("prod", 5.0), 7);
        jsonBuilder.endArray();
        String expected = "{\n  " +
                "\"array\": [\n    " +
                "{\n      \"name\": \"prod\",\n" +
                "      \"quantity\": 7,\n" +
                "      \"price\": 5.0\n    }\n" +
                "  ]\n}";
        Assert.assertEquals(jsonBuilder.getResult(), expected);
    }
}
