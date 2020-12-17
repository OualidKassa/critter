package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import com.udacity.jdnd.course3.critter.services.PetService;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDtoToSchedule(scheduleDTO);
        return convertScheduleToScheduleDTO(scheduleService.createSchedule(schedule, scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds()));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
       return convertListScheduleToScheduleDtoList(scheduleService.getAllSchedule());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.getAllSchedulePets(petId).stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.getAllScheduleEmployees(employeeId).stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.getAllScheduleOwner(customerId).stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
    }

    private Schedule convertScheduleDtoToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();

        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setDate(scheduleDTO.getDate());
        schedule.setPets(petService.getAllPetByIdsService(scheduleDTO.getPetIds()));
        schedule.setEmployees(employeeService.getAllEmployeesByIds(scheduleDTO.getEmployeeIds()));
        return schedule;
    }

    private List<ScheduleDTO> convertListScheduleToScheduleDtoList(List<Schedule> schedules){
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        for(Schedule schedule : schedules){
            scheduleDTO.setActivities(schedule.getActivities());
            scheduleDTO.setDate(schedule.getDate());
            scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(emp -> emp.getId()).collect(Collectors.toList()));
            scheduleDTO.setPetIds(schedule.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));
            scheduleDTO.setId(schedule.getId());
        }
        scheduleDTOS.add(scheduleDTO);
        return scheduleDTOS;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){

        List<Long> employeeIds = schedule.getEmployees().stream().map(employee -> employee.getId()) .collect(Collectors.toList());

        List<Long> petIds = schedule.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()); ScheduleDTO scheduleDTO = new ScheduleDTO();

        BeanUtils.copyProperties(schedule, scheduleDTO);

        scheduleDTO.setId(schedule.getId());

        scheduleDTO.setEmployeeIds(employeeIds);

        scheduleDTO.setPetIds(petIds);

        return scheduleDTO;

    }
}
