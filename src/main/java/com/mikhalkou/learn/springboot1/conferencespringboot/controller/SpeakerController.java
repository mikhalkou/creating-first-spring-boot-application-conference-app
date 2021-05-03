package com.mikhalkou.learn.springboot1.conferencespringboot.controller;

import com.mikhalkou.learn.springboot1.conferencespringboot.model.Speaker;
import com.mikhalkou.learn.springboot1.conferencespringboot.model.Speaker;
import com.mikhalkou.learn.springboot1.conferencespringboot.repository.SpeakerRepository;
import com.mikhalkou.learn.springboot1.conferencespringboot.util.ObjectHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/speaker")
public class SpeakerController {
    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> list() {
        return speakerRepository.findAll();
    }

    @GetMapping("{id}")
    public Speaker get(@PathVariable Long id) {
        Optional<Speaker> speaker = speakerRepository.findById(id);
        if (speaker.isPresent()) {
            return speaker.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Speaker with id " + id + " does not exist.");
        }
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public Speaker create(@RequestBody final Speaker speaker) {
        return speakerRepository.saveAndFlush(speaker);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        speakerRepository.deleteById(id);
    }

    @PutMapping("{id}")
    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing ID");
        }
        if (speaker == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing Speaker JSON body");
        } else {
            Set<String> nullFields = ObjectHelper.getNullPropertyNames(speaker, "speakerId", "sessions");
            if (!nullFields.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some fields are missing: " + nullFields);
            }
        }
        Speaker existingSpeaker = speakerRepository.getOne(id);
        BeanUtils.copyProperties(speaker, existingSpeaker, "speakerId", "sessions");
        return speakerRepository.saveAndFlush(existingSpeaker);
    }
}
