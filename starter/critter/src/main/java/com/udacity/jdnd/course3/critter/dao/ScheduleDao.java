package com.udacity.jdnd.course3.critter.dao;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ScheduleDao extends JpaRepository<Schedule, Long> {

     List<Schedule> findSchedulesByEmployees(Employee employee);
}
