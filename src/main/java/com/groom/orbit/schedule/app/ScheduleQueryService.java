package com.groom.orbit.schedule.app;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groom.orbit.common.exception.CommonException;
import com.groom.orbit.common.exception.ErrorCode;
import com.groom.orbit.schedule.dao.Schedule;
import com.groom.orbit.schedule.dao.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleQueryService {

  private final ScheduleRepository scheduleRepository;

  public Schedule findById(Long scheduleId) {
    return scheduleRepository
        .findById(scheduleId)
        .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_SCHEDULE));
  }
}
