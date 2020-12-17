package com.udacity.jdnd.course3.critter.dao;

import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CustomerDao extends JpaRepository<Customer, Long> {

    @Query(value = "select c from Customer c where c.id = (select p.owner.id from Pet p where p.id = ?1)")
    Customer getCustomerByPets(long id);
}
