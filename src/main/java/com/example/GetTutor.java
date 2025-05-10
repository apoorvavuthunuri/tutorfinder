package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
public class GetTutor {

    @Autowired
    private TutorService tutorService;


    @GetMapping("/gettutors")
    public List<Tutor> getTutors() {
        System.out.println("Getting successful");
        return tutorService.getAllTutors();
    }

    @GetMapping("/tutor/{id}")
    public ResponseEntity<?> getTutor(@PathVariable("id") Long tutorId) {
        Optional<Tutor> tutor = tutorService.getTutorById(tutorId);
        if (tutor.isPresent()) {
            return ResponseEntity.ok(tutor.get());
        } else {
            throw new TutorNotFoundException("Tutor not found with id: " + tutorId);
        }
    }

    @GetMapping("/gettutors/subject/{subject}")
    public List<Tutor> getTutorsBySubject(@PathVariable("subject") String subject) {
        return tutorService.getTutorsBySubject(subject);
    }
} 