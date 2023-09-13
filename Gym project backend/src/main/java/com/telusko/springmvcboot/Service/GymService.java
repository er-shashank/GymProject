package com.telusko.springmvcboot.Service;

import java.util.List;
import java.util.Optional;

import com.telusko.springmvcboot.model.primarykey.GymPlanPrimaryKey;
import com.telusko.springmvcboot.security.dto.UserRepo;
import com.telusko.springmvcboot.security.exception.ErrorMessages;
import com.telusko.springmvcboot.security.exception.GymException;
import com.telusko.springmvcboot.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
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
//      Optional<Gymplan> planToUpadte = gymRepo.findById(planWithPR.getId());
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
        GymPlanPrimaryKey gymId = new GymPlanPrimaryKey(getCurrentUserId(), id);
        Gymplan plan = gymRepo.findById(gymId).orElseThrow(() -> new GymException(ErrorMessages.NoGymPlan));

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

    /*
     * done and tested
     * */
    public String getAllHistoryBypages(int offset, int pageSize) {
        List<WorkOutHistory> histList = workHist.findAllByUserId(getCurrentUserId());
        Integer totalRecordForUser = histList.size();

        List<WorkOutHistory> userHistoryList = workHist.findHistWithPagination(pageSize * offset, pageSize, getCurrentUserId());

        Page<WorkOutHistory> page = new PageImpl<>(userHistoryList, PageRequest.of(offset, pageSize), totalRecordForUser);
        Gson gson = new Gson();
        String jsonCartList = gson.toJson(page);

        return jsonCartList;

    }

    public int nextWorkout() {
        String lastWorkOutstring = workHist.findLastExerciseDone(getCurrentUserId());

        if (lastWorkOutstring == null) return 0;


        Integer lastWorkOut = Integer.parseInt(lastWorkOutstring);
        int totalWorkOutAvailable = totalNumberOfPlans();
        int nextworkOut = ((lastWorkOut + 1) % (totalWorkOutAvailable));


        return nextworkOut;
    }

    public void addNewplan(Gymplan plan) {
        String maxWorkoutId = gymRepo.maxWorkoutId(getCurrentUserId());

        Integer lastWorkOut;
        if (maxWorkoutId == null)
            lastWorkOut = -1;
        else
            lastWorkOut = Integer.parseInt(maxWorkoutId);

        GymPlanPrimaryKey gymPlanPrimaryKey = new GymPlanPrimaryKey(getCurrentUserId(), lastWorkOut + 1);
        plan.setGymPlanPrimaryKey(gymPlanPrimaryKey);
        gymRepo.save(plan);
    }


    public int totalNumberOfPlans() {
        return gymRepo.totalWorkOutAvailable(getCurrentUserId());
    }

    public List<WorkOutHistory> getUserHistory() {
        return workHist.findAllByUserId(getCurrentUserId());
    }

    public void addRecordInHistory(WorkOutHistory work) {
        work.setUserId(getCurrentUserId());
        workHist.save(work);
    }

    public List<Gymplan> getGymPlansOfUser() {
        return gymRepo.getGymPlansOfUser(getCurrentUserId());
    }

    public boolean removePlan(int exerciseId) {
        try{
            gymRepo.deletePlan(exerciseId, getCurrentUserId());
            gymRepo.bulkUpdateExerciseId(exerciseId, getCurrentUserId());
        }
        catch(Exception e){
            System.out.println(e);
        }
       return true;
    }
}
