package com.telusko.springmvcboot.controller;

import java.util.List;

import com.telusko.springmvcboot.security.exception.ErrorMessages;
import com.telusko.springmvcboot.security.exception.GymException;
import com.telusko.springmvcboot.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import com.telusko.springmvcboot.Repository.GymRepo;
import com.telusko.springmvcboot.Repository.WorkOutHistoryRepo;
import com.telusko.springmvcboot.Service.GymService;
import com.telusko.springmvcboot.model.WorkOutHistory;
import com.telusko.springmvcboot.model.Gymplan;

@RestController
@RequestMapping("/api/gym")
public class GymController {

    @Autowired
    GymRepo repo;

    @Autowired
    WorkOutHistoryRepo workHist;

    @Autowired
    GymService service;

    @Autowired
    AuthService authService;

    @GetMapping("gymplan")

    public Gymplan getGymplanById(@RequestParam(name = "id") int id, @AuthenticationPrincipal User authenticatedUser) {
        String userName = authenticatedUser.getUsername();
        if (userName.equals("") || userName == null) throw new GymException(ErrorMessages.UserExist);
        User noUserLoggedIn = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("No user logged in"));
        return service.getGymplanById(id);
    }

    @GetMapping("/gymPlans/count")
    public int getGymPlans(@AuthenticationPrincipal User authenticatedUser) {
        int numberOfPlans = service.totalNumberOfPlans();
        return numberOfPlans;
    }

    @GetMapping("test")
    public ResponseEntity<String> getTest() {
        return ResponseEntity.ok("Private endpoint accessed");
    }

    @GetMapping("gymhistory/{offset}/{pageSize}")
    public String getAllBooksPaged(@PathVariable int offset, @PathVariable int pageSize) {


        // Page<WorkOutHistory> workHistory = workHist.findAll(PageRequest.of(offset, pageSize));
        // Gson gson = new Gson();
        // String jsonCartList = gson.toJson(workHistory);
        // // if(workHistory.isEmpty()) { throw new NullPointerException("nothing is
        // // there-------------------------->");}
        // return jsonCartList;
        return service.getAllBooksBypages(offset, pageSize);

    }

    @GetMapping("gymhistory")
    public List<WorkOutHistory> getAllBooks() {

        return workHist.findAll();

    }


    @GetMapping("nextwork")
    public int nextWorkout() {


        // String lastWorkOutstring = workHist.findLastExerciseDone();

        // Integer lastWorkOut = Integer.parseInt((lastWorkOutstring == null) ? "2" : lastWorkOutstring);
        // int totalWorkOutAvailable = repo.totalWorkOutAvailable();

        // return (lastWorkOut + 1) % totalWorkOutAvailable;
        return service.nextWorkout();

    }

    @PostMapping("gymhistory")
    public WorkOutHistory putWorkoutRecord(@RequestBody WorkOutHistory work) {
        workHist.save(work);
        return work;

    }

    @PutMapping("updatepr")
    public void updatePRRecord(@RequestBody Gymplan planWithPR) {
        service.updatePR(planWithPR);
    }

    @PostMapping("addnewplan")
    public void addNewplan(@RequestBody Gymplan plan) {

        service.addNewplan(plan);
    }


    //will be deprecated so dont use it
    @DeleteMapping("removenewplans")
    public void removeNewplans() {
        repo.deleteOldRecord();
        repo.resetIdSequence();
    }

}
