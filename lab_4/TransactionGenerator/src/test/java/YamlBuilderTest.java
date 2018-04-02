import logic.YamlBuilder;
import model.Product;
import org.junit.Assert;
import org.junit.Test;

public class YamlBuilderTest {
    @Test
    public void correctlyAddString() throws Exception {
        YamlBuilder yamlBuilder = new YamlBuilder();
        yamlBuilder.addString("name", "value");
        String result = yamlBuilder.getResult();
        String expected = "---\nname: value\n...";
        Assert.assertEquals(result, expected);
    }

    @Test
    public void correctlyAddNumber() throws Exception {
        YamlBuilder yamlBuilder = new YamlBuilder();
        yamlBuilder.addNumber("name", 2.5);
        String result = yamlBuilder.getResult();
        String expected = "---\nname: 2.5\n...";
        Assert.assertEquals(result, expected);
    }

    @Test
    public void correctlyStartAndCloseArray() throws Exception {
        YamlBuilder yamlBuilder = new YamlBuilder();
        yamlBuilder.startArray("array");
        yamlBuilder.endArray();
        Assert.assertEquals(yamlBuilder.getResult(), "---\narray:\n...");
    }

    @Test(expected = Exception.class)
    public void wrongStartAndCloseArray() throws Exception {
        YamlBuilder yamlBuilder = new YamlBuilder();
        yamlBuilder.startArray("array");
        Assert.assertEquals(yamlBuilder.getResult(), "---\narray:\n...");
    }

    @Test(expected = Exception.class)
    public void wrongCloseArray() throws Exception {
        YamlBuilder yamlBuilder = new YamlBuilder();
        yamlBuilder.endArray();
    }

    @Test
    public void correctlyAddProductToArray() throws Exception {
        YamlBuilder yamlBuilder = new YamlBuilder();
        yamlBuilder.startArray("array");
        yamlBuilder.addProductToArray(new Product("prod", 5.0), 7);
        yamlBuilder.endArray();
        String expected = "---\n" +
                "array:\n" +
                "    - name:   prod\n" +
                "      quantity:   7\n" +
                "      price:   5.0\n\n" +
                "...";
        Assert.assertEquals(yamlBuilder.getResult(), expected);
    }

    @Test(expected = Exception.class)
    public void wrongAddProductToArray() throws Exception {
        YamlBuilder yamlBuilder = new YamlBuilder();
        yamlBuilder.addProductToArray(new Product("prod", 5.0), 7);
        yamlBuilder.endArray();
        String expected = "---\n" +
                "array:\n" +
                "    - name:   prod\n" +
                "      quantity:   7\n" +
                "      price:   5.0\n\n" +
                "...";
        Assert.assertEquals(yamlBuilder.getResult(), expected);
    }
}
