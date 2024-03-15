package com.allancode.customer;

import com.allancode.exception.DuplicateResourceException;
import com.allancode.exception.ResourceNotFound;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerService(@Qualifier("jpa") CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public List<Customer> getAllCustomers(){
        return customerDAO.selectAllCustomers();
    }


    public Customer getCustomer(Integer id){
        return customerDAO.selectCustomerById(id)
                .orElseThrow(() -> new ResourceNotFound("customer with id does not exist"));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        //check if email exists
        if (customerDAO.existsCustomerWithEmail(customerRegistrationRequest.email())){
            throw new DuplicateResourceException("Email already taken");
        }
        //add
        customerDAO.insertCustomer(new Customer(customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age()));

    }


}
