package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.dao.CustomerDao;
import com.udacity.jdnd.course3.critter.dao.PetDao;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    PetDao petDao;

    @Autowired
    CustomerDao customerDao;

    public Pet savePet(Pet pet){
        Pet returnedPet = petDao.save(pet);
        Customer customer = returnedPet.getOwner();
        customer.addPet(returnedPet);
        customerDao.save(customer);
        return returnedPet;
    }

    public  Pet getPet(long id){
        return petDao.getOne(id);
    }

    public List<Pet> getPetByOwner(long id){
        return petDao.getPetsByOwnerId(id);
    }

    public List<Pet> getAllPetByIdsService(List<Long> ids){
        return petDao.findAllById(ids);
    }

}
