package com.telusko.springmvcboot.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workouthistory")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkOutHistory {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private int id;

	@Column(name = "body_part")
	private String body_part;
	
	@Column(name = "date")
	private Date date;

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


	@Column(name = "user_id")
	private Long userId;


	@Column(name = "exercise_id")
	private Integer exerciseId;


	@Override
	public String toString() {
		return "WorkOutHistory{" +
				"id=" + id +
				", body_part='" + body_part + '\'' +
				", date='" + date + '\'' +
				", exercise1='" + exercise1 + '\'' +
				", exercise2='" + exercise2 + '\'' +
				", exercise3='" + exercise3 + '\'' +
				", exercise4='" + exercise4 + '\'' +
				", exercise5='" + exercise5 + '\'' +
				", userId=" + userId +
				", exerciseId=" + exerciseId +
				'}';
	}
}

