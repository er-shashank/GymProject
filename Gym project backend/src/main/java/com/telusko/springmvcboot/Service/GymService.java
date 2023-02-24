package com.telusko.springmvcboot.Service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.google.gson.Gson;
import com.telusko.springmvcboot.Repository.GymRepo;
import com.telusko.springmvcboot.Repository.WorkOutHistoryRepo;
import com.telusko.springmvcboot.model.WorkOutHistory;
import com.telusko.springmvcboot.model.gymplan;

@Service
public class GymService {

    @Autowired
    GymRepo repo;

    @Autowired
    WorkOutHistoryRepo workHist;

    public void updatePR(gymplan planWithPR) {
        Optional<gymplan> planToUpadte = repo.findById(planWithPR.getId());

        if (!planWithPR.toString().equals(planToUpadte.get().toString())) {
            planToUpadte.get().setExercise1(planWithPR.getExercise1());
            planToUpadte.get().setExercise2(planWithPR.getExercise2());
            planToUpadte.get().setExercise3(planWithPR.getExercise3());
            planToUpadte.get().setExercise4(planWithPR.getExercise4());
            planToUpadte.get().setExercise5(planWithPR.getExercise5());

            repo.save(planToUpadte.get());

        }

    }

    public gymplan getGymplanById(int id) {
        String plan = repo.find(id);
        String[] planList = plan.split(",");
        return new gymplan(Integer.parseInt(planList[0]), planList[1], planList[2], planList[3], planList[4],
                planList[5], planList[6]);
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

        if(lastWorkOutstring == null) return 1;



		Integer lastWorkOut = Integer.parseInt(lastWorkOutstring);
		int totalWorkOutAvailable = repo.totalWorkOutAvailable();
        int nextworkOut=((lastWorkOut + 1) % (totalWorkOutAvailable+1));


		return (nextworkOut==0)? 1: nextworkOut;
    }


    

}
