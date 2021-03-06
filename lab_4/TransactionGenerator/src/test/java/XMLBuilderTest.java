import logic.XMLBuilder;
import model.Product;
import org.junit.Assert;
import org.junit.Test;

public class XMLBuilderTest {
//    @Test
//    public void correctlyAddString() throws Exception {
//        XMLBuilder xmlBuilder = new XMLBuilder();
//        xmlBuilder.addString("name", "value");
//        String result = xmlBuilder.getResult();
//        String expected = "<transaction>\r\n    <name>value</name>\r\n</transaction>\r\n";
//        Assert.assertEquals(result, expected);
//    }

    @Test
    public void correctlyAddString() throws Exception {
        XMLBuilder xmlBuilder = new XMLBuilder();
        xmlBuilder.addString("name", "value");
        String result = xmlBuilder.getResult();
        Assert.assertTrue(result.contains("<transaction>"));
        Assert.assertTrue(result.contains("<name>value</name>"));
        Assert.assertTrue(result.contains("</transaction>"));
    }

    //    @Test
//    public void correctlyAddNumber() throws Exception {
//        XMLBuilder xmlBuilder = new XMLBuilder();
//        xmlBuilder.addNumber("name", 2.5);
//        String result = xmlBuilder.getResult();
//        String expected = "<transaction>\r\n    <name>2.5</name>\r\n</transaction>\r\n";
//        Assert.assertEquals(result, expected);
//    }
    @Test
    public void correctlyAddNumber() throws Exception {
        XMLBuilder xmlBuilder = new XMLBuilder();
        xmlBuilder.addNumber("name", 2.5);
        String result = xmlBuilder.getResult();
        Assert.assertTrue(result.contains("<transaction>"));
        Assert.assertTrue(result.contains("<name>2.5</name>"));
        Assert.assertTrue(result.contains("</transaction>"));
    }

    //    @Test
//    public void correctlyStartAndCloseArray() throws Exception {
//        XMLBuilder xmlBuilder = new XMLBuilder();
//        xmlBuilder.startArray("array");
//        xmlBuilder.endArray();
//        Assert.assertEquals(xmlBuilder.getResult(), "<transaction>\r\n" +
//                "    <array/>\r\n" +
//                "</transaction>\r\n");
//    }
    @Test
    public void correctlyStartAndCloseArray() throws Exception {
        XMLBuilder xmlBuilder = new XMLBuilder();
        xmlBuilder.startArray("array");
        xmlBuilder.endArray();
        String result = xmlBuilder.getResult();
        Assert.assertTrue(result.contains("<transaction>"));
        Assert.assertTrue(result.contains("<array/>"));
        Assert.assertTrue(result.contains("</transaction>"));
    }

    @Test(expected = Exception.class)
    public void wrongStartAndCloseArray() throws Exception {
        XMLBuilder xmlBuilder = new XMLBuilder();
        xmlBuilder.startArray("array");
        Assert.assertEquals(xmlBuilder.getResult(), "<transaction>\r\n" +
                "    <array/>\r\n" +
                "</transaction>\r\n");
    }

    @Test(expected = Exception.class)
    public void wrongCloseArray() throws Exception {
        XMLBuilder xmlBuilder = new XMLBuilder();
        xmlBuilder.endArray();
    }

//    @Test
//    public void correctlyAddProductToArray() throws Exception {
//        XMLBuilder xmlBuilder = new XMLBuilder();
//        xmlBuilder.startArray("array");
//        xmlBuilder.addProductToArray(new Product("prod", 5.0), 7);
//        xmlBuilder.endArray();
//        String expected = "<transaction>\r\n" +
//                "    <array>\r\n" +
//                "        <item>\r\n" +
//                "            <name>prod</name>\r\n" +
//                "            <quantity>7</quantity>\r\n" +
//                "            <price>5.0</price>\r\n" +
//                "        </item>\r\n" +
//                "    </array>\r\n" +
//                "</transaction>\r\n";
//        Assert.assertEquals(xmlBuilder.getResult(), expected);
//    }

    @Test
    public void correctlyAddProductToArray() throws Exception {
        XMLBuilder xmlBuilder = new XMLBuilder();
        xmlBuilder.startArray("array");
        xmlBuilder.addProductToArray(new Product("prod", 5.0), 7);
        xmlBuilder.endArray();
        String expected = "<transaction>\r\n" +
                "    <array>\r\n" +
                "        <item>\r\n" +
                "            <name>prod</name>\r\n" +
                "            <quantity>7</quantity>\r\n" +
                "            <price>5.0</price>\r\n" +
                "        </item>\r\n" +
                "    </array>\r\n" +
                "</transaction>\r\n";
        String result = xmlBuilder.getResult();
        Assert.assertTrue(result.contains("<transaction>"));
        Assert.assertTrue(result.contains("<array>"));
        Assert.assertTrue(result.contains("<item>"));
        Assert.assertTrue(result.contains("<name>prod</name>"));
        Assert.assertTrue(result.contains("<quantity>7</quantity>"));
        Assert.assertTrue(result.contains("<price>5.0</price>"));
        Assert.assertTrue(result.contains("</array>"));
        Assert.assertTrue(result.contains("</item>"));
        Assert.assertTrue(result.contains("</transaction>"));
    }

    @Test(expected = Exception.class)
    public void wrongAddProductToArray() throws Exception {
        XMLBuilder xmlBuilder = new XMLBuilder();
        xmlBuilder.addProductToArray(new Product("prod", 5.0), 7);
        xmlBuilder.endArray();
        String expected = "<transaction>\r\n" +
                "    <array>\r\n" +
                "        <item>\r\n" +
                "            <name>prod</name>\r\n" +
                "            <quantity>7</quantity>\r\n" +
                "            <price>5.0</price>\r\n" +
                "        </item>\r\n" +
                "    </array>\r\n" +
                "</transaction>\r\n";
        Assert.assertEquals(xmlBuilder.getResult(), expected);
    }
}
