package com.mikhalkou.learn.springboot1.conferencespringboot.repository;

import com.mikhalkou.learn.springboot1.conferencespringboot.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
