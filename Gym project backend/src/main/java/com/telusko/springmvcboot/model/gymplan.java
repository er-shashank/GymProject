package com.telusko.springmvcboot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "gymplan")
public class gymplan 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private int id;
	
	@Column(name = "body_part")
	private String body_part;
	
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
	
	
	public gymplan() {
		super();
	}


	public gymplan(int id, String body_part, String exercise1, String exercise2, String exercise3, String exercise4,
			String exercise5) {
		super();
		this.id = id;
		this.body_part = body_part;
		this.exercise1 = exercise1;
		this.exercise2 = exercise2;
		this.exercise3 = exercise3;
		this.exercise4 = exercise4;
		this.exercise5 = exercise5;
	}


	@Override
	public String toString() {
		return "gymplan [id=" + id + ", body_part=" + body_part + ", exercise1=" + exercise1 + ", exercise2="
				+ exercise2 + ", exercise3=" + exercise3 + ", exercise4=" + exercise4 + ", exercise5=" + exercise5
				+ "]";
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getBody_part() {
		return body_part;
	}


	public void setBody_part(String body_part) {
		this.body_part = body_part;
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
