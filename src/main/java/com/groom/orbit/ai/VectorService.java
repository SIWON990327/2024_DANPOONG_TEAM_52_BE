package com.groom.orbit.ai;

import com.groom.orbit.ai.app.dto.CreateVectorDto;
import com.groom.orbit.ai.app.dto.UpdateVectorGoalDto;

public interface VectorService {

  void save(CreateVectorDto dto);

  void updateGoal(UpdateVectorGoalDto dto);
}
