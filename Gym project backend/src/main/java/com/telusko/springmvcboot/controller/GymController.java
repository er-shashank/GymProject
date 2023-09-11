package com.telusko.springmvcboot.controller;

import java.util.List;

import com.telusko.springmvcboot.security.exception.ErrorMessages;
import com.telusko.springmvcboot.security.exception.GymException;
import com.telusko.springmvcboot.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    GymService gymService;

    @Autowired
    AuthService authService;


    /*
    * tested and working
    * */
    @GetMapping("gymplan")

    public Gymplan getGymplanById(@RequestParam(name = "id") int id, @AuthenticationPrincipal User authenticatedUser) {
        String userName = authenticatedUser.getUsername();
        if (userName.equals("") || userName == null) throw new GymException(ErrorMessages.UserExist);
        User noUserLoggedIn = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("No user logged in"));
        return gymService.getGymplanById(id);
    }

    /*
    * Tested and working
    * */
    @GetMapping("/gymPlans/count")
    public int getGymPlans() {
        int numberOfPlans = gymService.totalNumberOfPlans();
        return numberOfPlans;
    }

    @GetMapping("test")
    public ResponseEntity<String> getTest() {

        return ResponseEntity.ok("Private endpoint accessed");
    }


    @GetMapping("gymhistory/{offset}/{pageSize}")
    public String getAllBooksPaged(@PathVariable int offset, @PathVariable int pageSize) {

        return gymService.getAllHistoryBypages(offset, pageSize);

    }

    @GetMapping("gymhistory")
    public List<WorkOutHistory> getUserHistory() {

//        return workHist.findAll();
        return gymService.getUserHistory();
    }


    /*
    * under test
    * */
    @GetMapping("nextwork")
    public int nextWorkout() {


        // String lastWorkOutstring = workHist.findLastExerciseDone();

        // Integer lastWorkOut = Integer.parseInt((lastWorkOutstring == null) ? "2" : lastWorkOutstring);
        // int totalWorkOutAvailable = repo.totalWorkOutAvailable();

        // return (lastWorkOut + 1) % totalWorkOutAvailable;
        return gymService.nextWorkout();

    }

    @PostMapping("gymhistory")
    public ResponseEntity putWorkoutRecord(@RequestBody WorkOutHistory work) {


        gymService.addRecordInHistory(work);
        return new ResponseEntity(HttpStatus.OK);

    }

    @PutMapping("updatepr")
    public void updatePRRecord(@RequestBody Gymplan planWithPR) {
        gymService.updatePR(planWithPR);
    }

    /*
    * test completed
    * */
    @PostMapping("addnewplan")
    public void addNewplan(@RequestBody Gymplan plan) {

        gymService.addNewplan(plan);
    }


    //will be deprecated so dont use it
    @DeleteMapping("removenewplans")
    public void removeNewplans() {
        repo.deleteOldRecord();
        repo.resetIdSequence();
    }

}
