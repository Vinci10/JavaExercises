package logic;

import model.Product;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVReader {
    public List<Product> read(File file) throws IOException{
        List<Product> products;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            products = getProducts(br);
        }
        return products;
    }
    public List<Product> getProducts(BufferedReader br) throws IOException {
        List<Product> products = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] product = line.split(",");
            if(!product[0].equals("name") && !product[1].equals("price")) {
                products.add(new Product(product[0].replace("\"", ""), Double.parseDouble(product[1])));
            }
        }
        return products;
    }
}
