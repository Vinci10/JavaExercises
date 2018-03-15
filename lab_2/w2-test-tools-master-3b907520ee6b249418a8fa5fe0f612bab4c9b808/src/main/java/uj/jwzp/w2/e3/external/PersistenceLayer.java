package uj.jwzp.w2.e3.external;

import uj.jwzp.w2.e3.Customer;
import uj.jwzp.w2.e3.Item;

import java.util.List;

public interface PersistenceLayer {

    boolean saveTransaction(Customer customer, Item item, int quantity);

    boolean saveItem(Item item);

    boolean saveCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomerById(int id);

    List<Item> getAllItems();

    Item getItemByName(String name);

    void loadDiscountConfiguration();
}
