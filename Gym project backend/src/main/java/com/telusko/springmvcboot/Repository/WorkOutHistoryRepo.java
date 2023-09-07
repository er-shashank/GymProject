package com.telusko.springmvcboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.telusko.springmvcboot.model.WorkOutHistory;


public interface WorkOutHistoryRepo extends JpaRepository<WorkOutHistory, Integer> {

	@Query("select exercise_id from WorkOutHistory where id= (select max(id) from WorkOutHistory)")
	String findLastExerciseDone ();
}
