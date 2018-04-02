package logic;

import model.Product;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

@Service
public class XMLBuilder implements DataFormatBuilder {
    private Document doc;
    private Element root;
    private Element array;
    private Transformer transformer;
    private DocumentBuilder docBuilder;

    public XMLBuilder() throws Exception {
        TransformerFactory tf = TransformerFactory.newInstance();
        tf.setAttribute("indent-number", 4);
        transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        docBuilder = docFactory.newDocumentBuilder();
        doc = docBuilder.newDocument();
        root = doc.createElement("transaction");
        doc.appendChild(root);
    }

    @Override
    public void addNumber(String name, Number value) {
        Element numberElement = doc.createElement(name);
        numberElement.appendChild(doc.createTextNode(value.toString()));
        root.appendChild(numberElement);
    }

    @Override
    public void addString(String name, String value) {
        Element stringElement = doc.createElement(name);
        stringElement.appendChild(doc.createTextNode(value));
        root.appendChild(stringElement);
    }

    @Override
    public void startArray(String name) throws Exception {
        if (array == null) {
            array = doc.createElement(name);
            root.appendChild(array);
        } else
            throw new Exception("Array has been started");
    }

    @Override
    public void addProductToArray(Product product, int quantity) throws Exception {
        if (array != null) {
            Element item = doc.createElement("item");
            array.appendChild(item);
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(product.getName()));
            item.appendChild(name);
            Element quantityE = doc.createElement("quantity");
            quantityE.appendChild(doc.createTextNode(Integer.toString(quantity)));
            item.appendChild(quantityE);
            Element price = doc.createElement("price");
            price.appendChild(doc.createTextNode(Double.toString(product.getPrice())));
            item.appendChild(price);
        } else
            throw new Exception("Array has not been started");
    }

    @Override
    public void endArray() throws Exception {
        if(array != null) {
            array = null;
        } else
            throw new Exception("Array has not been started");
    }

    @Override
    public String getResult() throws Exception {
        if(array != null)
            throw new Exception("Array is still opened");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.getBuffer().toString();
    }

    @Override
    public void reset() {
        doc = docBuilder.newDocument();
        root = doc.createElement("transaction");
        doc.appendChild(root);
    }

    @Override
    public String getOutputType() {
        return ".xml";
    }
}
