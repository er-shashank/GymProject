package com.telusko.springmvcboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.telusko.springmvcboot.model.WorkOutHistory;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface WorkOutHistoryRepo extends JpaRepository<WorkOutHistory, Integer> {

	@Query("select exerciseId from WorkOutHistory " +
			"where date= (select max(date) from WorkOutHistory where userId= :userId)" +
			"and userId= :userId")
	String findLastExerciseDone (@Param("userId") Long userId);

	@Query("select id,body_part,date,exercise1,exercise2,exercise3,exercise4,exercise5,userId,exerciseId" +
			"  from WorkOutHistory where userId= :userId order by date DESC")
	List<WorkOutHistory> findAllByUserId(@Param("userId") Long userId);
}
