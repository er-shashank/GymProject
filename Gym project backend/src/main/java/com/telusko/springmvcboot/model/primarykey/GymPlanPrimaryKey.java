package com.telusko.springmvcboot.model.primarykey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class GymPlanPrimaryKey implements Serializable {
    private Long userId;
    private Integer exerciseId;
}
