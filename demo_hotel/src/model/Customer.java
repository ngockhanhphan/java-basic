package model;

public class Customer {
    String firstName;
    String lastName;
    String email;

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return "First Name: " + this.firstName
                + ", Last Name: " + this.lastName
                + ", Email: " + this.email;
    }
}
