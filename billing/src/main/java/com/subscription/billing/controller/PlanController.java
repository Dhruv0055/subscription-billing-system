package com.subscription.billing.controller;

import com.subscription.billing.entity.Plan;
import com.subscription.billing.repository.PlanRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/plans")
public class PlanController {

    private final PlanRepository planRepository;

    public PlanController(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @PostMapping
    public Plan create(@RequestBody Plan plan) {
        return planRepository.save(plan);
    }

    @GetMapping
    public List<Plan> getAll() {
        return planRepository.findAll();
    }

    @GetMapping("/{id}")
    public Plan getById(@PathVariable Long id) {
        return planRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Plan update(@PathVariable Long id, @RequestBody Plan p) {
        return planRepository.findById(id).map(plan -> {
            plan.setName(p.getName());
            plan.setPrice(p.getPrice());
            plan.setDurationInDays(p.getDurationInDays());
            return planRepository.save(plan);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        planRepository.deleteById(id);
    }
}
