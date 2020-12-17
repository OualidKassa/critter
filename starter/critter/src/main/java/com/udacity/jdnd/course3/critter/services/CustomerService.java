package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.dao.CustomerDao;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerDao customerDao;

    public Customer saveCustomer(Customer customer) {
        return customerDao.save(customer);
    }

    public List<Customer> getAllCustomers()
    {
        return customerDao.findAll();
    }

    public Customer getCustomer(long id){
        Optional< Customer > optionalCustomer = customerDao.findById(id);
        return optionalCustomer.orElse(null);
    }

    public Customer getCustomerByPetId(long id){
        return customerDao.getCustomerByPets(id);
    }
}
