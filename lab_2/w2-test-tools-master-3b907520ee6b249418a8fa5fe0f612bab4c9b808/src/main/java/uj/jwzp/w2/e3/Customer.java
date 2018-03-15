package uj.jwzp.w2.e3;

public class Customer {
    private long id;
    private String name;
    private String address;

    public Customer(long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

}
