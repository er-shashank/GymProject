package com.telusko.springmvcboot.Repository;

import com.telusko.springmvcboot.model.primarykey.GymPlanPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.telusko.springmvcboot.model.Gymplan;

import java.util.List;


public interface GymRepo extends JpaRepository<Gymplan, GymPlanPrimaryKey>, CrudRepository<Gymplan, GymPlanPrimaryKey> {

	@Query(value = "select g.user_id, g.exercise_id ,body_part,exercise1,exercise2,exercise3,exercise4,exercise5 " +
			"from Gymplan g where g.user_Id= :userId order by g.exercise_id", nativeQuery = true)
	List<Gymplan>  getGymPlansOfUser (@Param("userId") Long userId);
	
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

	@Modifying
	@Transactional
	@Query("delete from Gymplan g where g.gymPlanPrimaryKey.userId = :uId and g.gymPlanPrimaryKey.exerciseId= :exId")
	void deletePlan(int exId, Long uId);


	@Modifying
	@Transactional
	@Query("update Gymplan g set g.gymPlanPrimaryKey.exerciseId= g.gymPlanPrimaryKey.exerciseId-1  " +
			"where g.gymPlanPrimaryKey.userId= :currentUserId and g.gymPlanPrimaryKey.exerciseId> :exeId")
	void bulkUpdateExerciseId(int exeId, Long currentUserId);


}
