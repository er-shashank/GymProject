package com.telusko.springmvcboot.Repository;

import com.telusko.springmvcboot.model.primarykey.GymPlanPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.telusko.springmvcboot.model.Gymplan;


public interface GymRepo extends JpaRepository<Gymplan, GymPlanPrimaryKey>, CrudRepository<Gymplan, GymPlanPrimaryKey> {

	@Query("select g.gymPlanPrimaryKey.userId,body_part,exercise1,exercise2,exercise3,exercise4,exercise5 from Gymplan g where g.gymPlanPrimaryKey.userId= :userId")
	String find (@Param("userId") Long userId);
	
	@Query("select count(1) from Gymplan g where g.gymPlanPrimaryKey.userId= :userId")
	int totalWorkOutAvailable (@Param("userId")Long userId);


	//need to update
	@Modifying
	@Transactional
	@Query("delete from Gymplan  where id not in (1,2,3)")
	void deleteOldRecord ();


	//need to update
	@Modifying
	@Transactional
	@Query(value="alter table Gymplan auto_increment = 3",nativeQuery = true)
	void resetIdSequence();


	@Query("select max(g.gymPlanPrimaryKey.exerciseId) from Gymplan g where g.gymPlanPrimaryKey.userId= :userId")
	String maxWorkoutId(@Param("userId") Long userId);
}
