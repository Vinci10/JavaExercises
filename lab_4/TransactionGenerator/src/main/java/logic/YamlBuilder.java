package logic;

import model.Product;
import org.springframework.stereotype.Service;

@Service
public class YamlBuilder implements DataFormatBuilder {
    private StringBuilder result;
    private boolean arrayIsOpen;

    public YamlBuilder() {
        result = new StringBuilder();
        arrayIsOpen = false;
        result.append("---\n");
    }

    @Override
    public void addString(String name, String value) {
        result.append(name).append(": ").append(value).append("\n");
    }

    @Override
    public void addNumber(String name, Number value) {
        result.append(name).append(": ").append(value).append("\n");
    }

    @Override
    public void startArray(String name) throws Exception {
        if (!arrayIsOpen) {
            arrayIsOpen = true;
            result.append(name).append(":").append("\n");
        } else
            throw new Exception("Array has been started");
    }

    @Override
    public void addProductToArray(Product product, int quantity) throws Exception {
        if (arrayIsOpen) {
            result.append("    - name:   ").append(product.getName())
                    .append("\n").append("      quantity:   ").append(quantity)
                    .append("\n").append("      price:   ").append(product.getPrice())
                    .append("\n").append("\n");
        } else
            throw new Exception("Array has not been started");
    }

    @Override
    public void endArray() throws Exception {
        if (arrayIsOpen)
            arrayIsOpen = false;
        else
            throw new Exception("Array has not been started");
    }

    @Override
    public String getResult() throws Exception {
        if(arrayIsOpen)
            throw new Exception("Array is still opened");
        return result.append("...").toString();
    }

    @Override
    public void reset() {
        result = new StringBuilder();
        arrayIsOpen = false;
        result.append("---\n");
    }

    @Override
    public String getOutputType() {
        return ".yml";
    }
}
