package com.telusko.springmvcboot.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.telusko.springmvcboot.model.WorkOutHistory;
import com.telusko.springmvcboot.model.gymplan;


public interface WorkOutHistoryRepo extends JpaRepository<WorkOutHistory, Integer> {

	@Query("select exercise_id from WorkOutHistory where id= (select max(id) from WorkOutHistory)")
	String findLastExerciseDone ();
}
