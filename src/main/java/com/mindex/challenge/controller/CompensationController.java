package com.mindex.challenge.controller;


import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compensation")
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("")
    public Compensation create(@RequestBody Compensation compensation) {
        return compensationService.createCompensation(compensation);
    }

    @GetMapping("/{id}")
    public Compensation read(@PathVariable String id) {
        return compensationService.readCompensation(id);
    }

}
