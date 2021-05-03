package com.mikhalkou.learn.springboot1.conferencespringboot.repository;

import com.mikhalkou.learn.springboot1.conferencespringboot.model.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
}
