package com.groom.orbit.goal.dao.entity;

import java.io.Serializable;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class MemberGoalId implements Serializable {
    private Long member;
    private Long goal;
}

