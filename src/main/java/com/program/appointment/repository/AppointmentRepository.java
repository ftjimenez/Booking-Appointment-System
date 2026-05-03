package com.program.appointment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.program.appointment.model.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByOwnerUsernameOrderByIdAsc(String username);

    Optional<Appointment> findByIdAndOwnerUsername(Long id, String username);
}