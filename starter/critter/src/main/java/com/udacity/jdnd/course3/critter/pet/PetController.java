package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.PetService;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        return convertPetToPetDto(petService.savePet(convertPetDtoToPet(petDTO)));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertPetToPetDto(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets() {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.getPetByOwner(ownerId).stream()
                .map(this::convertPetToPetDto).
                        collect(Collectors.toList());
    }

    private PetDTO convertPetToPetDto(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        Customer customer = customerService.getCustomer(pet.getOwner().getId());
        petDTO.setOwnerId(customer.getId());
        return petDTO;
    }

    private Pet convertPetDtoToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        if((Long)petDTO.getOwnerId() != null){
            pet.setOwner(customerService.getCustomer(petDTO.getOwnerId()));
        }
        Customer customersavePets = customerService.getCustomer(petDTO.getOwnerId());
        List<Pet> pets = new ArrayList<>();
        pets.add(pet);
        customersavePets.setPets(pets);
        return pet;
    }

    private List<Pet> convertListPetDtoToListPet(List<PetDTO> petDTOList) {
        List<Pet> petList = new ArrayList<>();
        Pet pet = new Pet();
        long idOwner = 0;
        for(PetDTO petDTO : petDTOList){
            pet.setType(petDTO.getType());
            pet.setNotes(petDTO.getNotes());
            pet.setBirthDate(petDTO.getBirthDate());
            pet.setName(petDTO.getName());
            idOwner = petDTO.getOwnerId();
        }
        pet.setOwner(customerService.getCustomer(idOwner));
        petList.add(pet);
        return petList;
    }

    private List<PetDTO> convertPetListToListPetDto(List<Pet> petList) {
        List<PetDTO> petDTOList = new ArrayList<>();
        PetDTO petDTO = new PetDTO();

        for(Pet pet : petList){
            petDTO.setId(pet.getId());
            petDTO.setOwnerId(pet.getOwner().getId());
            petDTO.setBirthDate(pet.getBirthDate());
            petDTO.setNotes(pet.getNotes());
            petDTO.setType(pet.getType());
            petDTO.setName(pet.getName());
        }
        petDTOList.add(petDTO);
        return petDTOList;
    }
}
