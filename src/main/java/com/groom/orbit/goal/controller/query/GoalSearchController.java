package com.groom.orbit.goal.controller.query;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groom.orbit.common.dto.ResponseDto;
import com.groom.orbit.goal.app.dto.response.GoalSearchDetailResponseDto;
import com.groom.orbit.goal.app.query.GoalSearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goal/search")
public class GoalSearchController {

  private final GoalSearchService goalSearchService;

  @GetMapping("/{goal_id}")
  public ResponseDto<GoalSearchDetailResponseDto> getSearchDetail(
      @PathVariable("goal_id") Long goalId) {
    return ResponseDto.ok(goalSearchService.findGoal(goalId));
  }
}
