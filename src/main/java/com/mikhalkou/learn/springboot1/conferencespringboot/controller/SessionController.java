package com.mikhalkou.learn.springboot1.conferencespringboot.controller;

import com.mikhalkou.learn.springboot1.conferencespringboot.model.Session;
import com.mikhalkou.learn.springboot1.conferencespringboot.model.Speaker;
import com.mikhalkou.learn.springboot1.conferencespringboot.repository.SessionRepository;
import com.mikhalkou.learn.springboot1.conferencespringboot.repository.SpeakerRepository;
import com.mikhalkou.learn.springboot1.conferencespringboot.util.ObjectHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/session")
public class SessionController {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    protected SpeakerRepository speakerRepository;

    @GetMapping
    public List<Session> list() {
        return sessionRepository.findAll();
    }

    @GetMapping("{id}")
    public Session get(@PathVariable Long id) {
        Optional<Session> session = sessionRepository.findById(id);
        if (session.isPresent()) {
            return session.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Session with id " + id + " does not exist.");
        }
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public Session create(@RequestBody final Session session) {
        return sessionRepository.saveAndFlush(session);
    }

    @DeleteMapping("{id}")
    //    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        for (Speaker speaker : sessionRepository.getOne(id).getSpeakers()) {
            speakerRepository.delete(speaker);
        }
        sessionRepository.deleteById(id);
    }

    @PutMapping(value = "{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Session update(@PathVariable Long id, @RequestBody Session session) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing ID");
        }
        if (session == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing Session JSON body");
        } else {
            Set<String> nullFields = ObjectHelper.getNullPropertyNames(session, "sessionId", "speakers");
            if (!nullFields.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some fields are missing: " + nullFields);
            }
        }
        Session existingSession = sessionRepository.getOne(id);
        BeanUtils.copyProperties(session, existingSession, "sessionId", "speakers");
        return sessionRepository.saveAndFlush(existingSession);
    }

}
