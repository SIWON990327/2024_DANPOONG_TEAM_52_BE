package com.groom.orbit.ai;

import com.groom.orbit.ai.app.dto.CreateVectorDto;
import com.groom.orbit.ai.app.dto.UpdateVectorGoalDto;
import com.groom.orbit.ai.app.dto.UpdateVectorQuestDto;

public interface VectorService {

  void save(CreateVectorDto dto);

  void updateGoal(UpdateVectorGoalDto dto);

  void updateQuest(UpdateVectorQuestDto updateDto);
}
