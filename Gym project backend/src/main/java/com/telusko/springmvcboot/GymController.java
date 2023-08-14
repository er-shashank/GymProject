package com.telusko.springmvcboot;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import com.telusko.springmvcboot.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.telusko.springmvcboot.Repository.GymRepo;
import com.telusko.springmvcboot.Repository.WorkOutHistoryRepo;
import com.telusko.springmvcboot.Service.GymService;
import com.telusko.springmvcboot.model.WorkOutHistory;
import com.telusko.springmvcboot.model.gymplan;

@CrossOrigin
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

	public gymplan getGymplanById(@RequestParam(name = "id") int id) {
		User noUserLoggedIn = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("No user logged in"));
		return service.getGymplanById(id);
	}

	// @GetMapping("gymplans")
	// public List<gymplan> getGymPlans() {
	// 	List<gymplan> plan = repo.findAll();

	// 	return plan;

	// }

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
