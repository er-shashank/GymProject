package com.telusko.springmvcboot.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "workouthistory")

public class WorkOutHistory {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private int id;
	
	@Column(name = "exercise_id")
	private int exercise_id;
	
	@Column(name = "body_part")
	private String body_part;
	
	@Column(name = "date")
	private String date;

	@Column(name = "exercise1")
	private String exercise1;
	
	@Column(name = "exercise2")
	private String exercise2;
	
	@Column(name = "exercise3")
	private String exercise3;
	
	@Column(name = "exercise4")
	private String exercise4;

	@Column(name = "exercise5")
	private String exercise5;
	
	
	
	public WorkOutHistory() {
		super();
	}
	
	public WorkOutHistory(int id, int exercise_id, String body_part, String date, String exercise1, String exercise2,
			String exercise3, String exercise4, String exercise5) {
		super();
		this.id = id;
		this.exercise_id = exercise_id;
		this.body_part = body_part;
		this.date = date;
		this.exercise1 = exercise1;
		this.exercise2 = exercise2;
		this.exercise3 = exercise3;
		this.exercise4 = exercise4;
		this.exercise5 = exercise5;
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getExercise_id() {
		return exercise_id;
	}

	public void setExercise_id(int exercise_id) {
		this.exercise_id = exercise_id;
	}

	public String getBody_part() {
		return body_part;
	}

	public void setBody_part(String body_part) {
		this.body_part = body_part;
	}

	public String getDate() {
		return date;
	} 	

	public void setDate(String date) {
		this.date = date;
	}

	public String getExercise1() {
		return exercise1;
	}

	public void setExercise1(String exercise1) {
		this.exercise1 = exercise1;
	}

	public String getExercise2() {
		return exercise2;
	}

	public void setExercise2(String exercise2) {
		this.exercise2 = exercise2;
	}

	public String getExercise3() {
		return exercise3;
	}

	public void setExercise3(String exercise3) {
		this.exercise3 = exercise3;
	}

	public String getExercise4() {
		return exercise4;
	}

	public void setExercise4(String exercise4) {
		this.exercise4 = exercise4;
	}

	public String getExercise5() {
		return exercise5;
	}

	public void setExercise5(String exercise5) {
		this.exercise5 = exercise5;
	}

}

