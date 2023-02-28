package com.telusko.springmvcboot;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.telusko.springmvcboot.Repository.GymRepo;
import com.telusko.springmvcboot.Repository.WorkOutHistoryRepo;
import com.telusko.springmvcboot.Service.GymService;
import com.telusko.springmvcboot.model.WorkOutHistory;
import com.telusko.springmvcboot.model.gymplan;

@CrossOrigin
@RestController
public class GymController {

	@Autowired
	GymRepo repo;

	@Autowired
	WorkOutHistoryRepo workHist;

	@Autowired
	GymService service;

	@GetMapping("gymplan")

	public gymplan getGymplanById(@RequestParam(name = "id") int id) {
		return service.getGymplanById(id);
	}

	// @GetMapping("gymplans")
	// public List<gymplan> getGymPlans() {
	// 	List<gymplan> plan = repo.findAll();

	// 	return plan;

	// }

	@GetMapping("test")
	public String getTest() {
		return "It is working";
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
	public void updatePRRecord(@RequestBody gymplan planWithPR) {
		service.updatePR(planWithPR);
	}
	@PostMapping("addnewplan")
	public void addNewplan(@RequestBody gymplan plan) {
		service.addNewplan(plan);
	}

	@DeleteMapping("removenewplans")
	public void removeNewplans(){
		repo.deleteOldRecord();
		repo.resetIdSequence();
	}

}
