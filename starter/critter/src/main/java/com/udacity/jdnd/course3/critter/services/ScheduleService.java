package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.dao.ScheduleDao;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    ScheduleDao scheduleDao;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    @Autowired
    CustomerService ownerService;

    public Schedule createSchedule (Schedule schedule, List<Long> employeeIds, List<Long> petIds)

    {

        List<Employee> employees = employeeService.getAllEmployeesByIds(employeeIds);

        List<Pet> pets = petService.getAllPetByIdsService(petIds);

        schedule.setEmployees(employees);

        schedule.setPets(pets);
        if (schedule == null) {
            schedule = new Schedule ();
        }

        return scheduleDao.save(schedule);

    }

    public List<Schedule> getAllSchedule(){
        return scheduleDao.findAll();
    }

    public List<Schedule> getAllScheduleEmployees(long idEmployee){
        List<Schedule> returnSchedulesWithEmployee = new ArrayList<>();
        Employee employees = employeeService.getEmployee(idEmployee);
        for (Schedule schedule : getAllSchedule()){
            if(schedule.getEmployees().contains(employees)){
                returnSchedulesWithEmployee.add(schedule);
            }
        }
        return returnSchedulesWithEmployee;
    }

    public List<Schedule> getAllSchedulePets(long petId){
        List<Schedule> returnSchedulesWithPets = new ArrayList<>();
        Pet pet = petService.getPet(petId);
        for (Schedule schedule : getAllSchedule()){
            if(schedule.getPets().contains(pet)){
                returnSchedulesWithPets.add(schedule);
            }
        }
        return returnSchedulesWithPets;
    }

    public List<Schedule> getAllScheduleOwner(long ownerId){
        List<Schedule> returnSchedulesWithOwners = new ArrayList<>();
        List<Pet> petsOwner = petService.getPetByOwner(ownerId);
        for (Schedule schedule : getAllSchedule()){
            if(schedule.getPets().containsAll(petsOwner)){
                returnSchedulesWithOwners.add(schedule);
            }
        }
        return returnSchedulesWithOwners;
    }
}
