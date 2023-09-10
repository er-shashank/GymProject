package com.telusko.springmvcboot.Service;

import java.util.Optional;

import com.telusko.springmvcboot.model.primarykey.GymPlanPrimaryKey;
import com.telusko.springmvcboot.security.dto.UserRepo;
import com.telusko.springmvcboot.security.exception.ErrorMessages;
import com.telusko.springmvcboot.security.exception.GymException;
import com.telusko.springmvcboot.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.google.gson.Gson;
import com.telusko.springmvcboot.Repository.GymRepo;
import com.telusko.springmvcboot.Repository.WorkOutHistoryRepo;
import com.telusko.springmvcboot.model.WorkOutHistory;
import com.telusko.springmvcboot.model.Gymplan;

@Service
public class GymService {

    @Autowired
    GymRepo gymRepo;

    @Autowired
    WorkOutHistoryRepo workHist;

    @Autowired
    AuthService authService;

    @Autowired
    UserRepo userRepo;
//    Authentication loggedInUserInfo = SecurityContextHolder.getContext().getAuthentication();


    public void updatePR(Gymplan planWithPR) {
//        Optional<Gymplan> planToUpadte = gymRepo.findById(planWithPR.getId());
        Optional<Gymplan> planToUpadte = gymRepo.findById(planWithPR.getGymPlanPrimaryKey());

        if (!planWithPR.toString().equals(planToUpadte.get().toString())) {
            planToUpadte.get().setExercise1(planWithPR.getExercise1());
            planToUpadte.get().setExercise2(planWithPR.getExercise2());
            planToUpadte.get().setExercise3(planWithPR.getExercise3());
            planToUpadte.get().setExercise4(planWithPR.getExercise4());
            planToUpadte.get().setExercise5(planWithPR.getExercise5());

            gymRepo.save(planToUpadte.get());

        }

    }

    public Gymplan getGymplanById(int id) {
//        String plan = gymRepo.find(id, getLoggedInUserName());
//        String[] planList = plan.split(",");
//        return new Gymplan(Integer.parseInt(planList[0]), planList[1], planList[2], planList[3], planList[4],
//                planList[5], planList[6]);
        GymPlanPrimaryKey gymId= new GymPlanPrimaryKey(getCurrentUserId(),id);
        Gymplan plan = gymRepo.findById(gymId).orElseThrow(()-> new GymException(ErrorMessages.NoGymPlan));

        return plan;
    }

    private Long getCurrentUserId() {
        User currentLoggedInUser = authService.getCurrentUser().orElseThrow(() -> new GymException(ErrorMessages.NoUserLoggedIn));
        Long userId = userRepo.getIdByUserName(currentLoggedInUser.getUsername());
        return userId;
    }

    private static String getLoggedInUserName() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (userName.equals("") || userName == null) throw new GymException(ErrorMessages.NoUserLoggedIn);
        return userName;
    }

    public String getAllBooksBypages(int offset, int pageSize) {

        Page<WorkOutHistory> workHistory = workHist.findAll(PageRequest.of(offset, pageSize));
        Gson gson = new Gson();
        String jsonCartList = gson.toJson(workHistory);
        // if(workHistory.isEmpty()) { throw new NullPointerException("nothing is
        // there-------------------------->");}
        return jsonCartList;

    }

    public int nextWorkout() {
        String lastWorkOutstring = workHist.findLastExerciseDone();

        if (lastWorkOutstring == null) return 1;


        Integer lastWorkOut = Integer.parseInt(lastWorkOutstring);
        int totalWorkOutAvailable = gymRepo.totalWorkOutAvailable();
        int nextworkOut = ((lastWorkOut + 1) % (totalWorkOutAvailable + 1));


        return (nextworkOut == 0) ? 1 : nextworkOut;
    }

    public void addNewplan(Gymplan plan) {
        gymRepo.save(plan);
    }


    public int totalNumberOfPlans() {
        return gymRepo.totalWorkOutAvailable();
    }

}
