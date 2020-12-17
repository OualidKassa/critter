package com.udacity.jdnd.course3.critter.dao;

import com.udacity.jdnd.course3.critter.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PetDao extends JpaRepository<Pet, Long>{
    List<Pet> getPetsByOwnerId(long id);
}
