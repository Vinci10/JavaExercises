package logic;

import model.Product;

public interface DataFormatBuilder {
    void addString(String name, String value);
    void addNumber(String name, Number value);
    void startArray(String name) throws Exception;
    void addProductToArray(Product product, int quantity) throws Exception;
    void endArray() throws Exception;
    String getResult() throws Exception;
    String getOutputType();
    void reset();
}
