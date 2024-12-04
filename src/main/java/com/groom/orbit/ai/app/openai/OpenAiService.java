package com.groom.orbit.ai.app.openai;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.groom.orbit.ai.app.AiService;
import com.groom.orbit.goal.app.dto.request.CreateGoalRequestDto;
import com.groom.orbit.member.app.dto.response.GetFeedbackResponseDto;
import com.groom.orbit.resume.app.dto.GetResumeResponseDto;
import com.groom.orbit.resume.app.dto.ResumeResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenAiService implements AiService {

  private final ChatModel chatModel;

  @Value("classpath:/templates/ai-feedback-prompt.txt")
  private Resource aiFeedbackPrompt;

  @Value("classpath:/templates/goal-recommend-prompt.txt")
  private Resource goalRecommendPrompt;

  private static final String PARAMETER_LIST_DELIMITER = "\n  -";

  public GetFeedbackResponseDto getMemberFeedback(String interestJobs, GetResumeResponseDto dto) {
    BeanOutputConverter<GetFeedbackResponseDto> converter =
        getConverter(GetFeedbackResponseDto.class);
    String format = converter.getFormat();

    PromptTemplate promptTemplate = new PromptTemplate(aiFeedbackPrompt);
    String response =
        callChatModel(
            promptTemplate,
            Map.of(
                "job", interestJobs,
                "academy", convertResumeDtoToString(dto.academyList()),
                "career", convertResumeDtoToString(dto.careerList()),
                "qualification", convertResumeDtoToString(dto.qualificationList()),
                "experience", convertResumeDtoToString(dto.experienceList()),
                "etc", convertResumeDtoToString(dto.etcList()),
                "format", format));

    return converter.convert(response);
  }

  @Override
  public CreateGoalRequestDto recommendGoal(Long memberId, String goalList) {
    BeanOutputConverter<CreateGoalRequestDto> converter = getConverter(CreateGoalRequestDto.class);
    String format = converter.getFormat();

    PromptTemplate promptTemplate = new PromptTemplate(goalRecommendPrompt);
    return null;
  }

  private <T> BeanOutputConverter<T> getConverter(Class<T> converterClass) {
    return new BeanOutputConverter<>(converterClass);
  }

  private String convertResumeDtoToString(List<ResumeResponseDto> data) {
    return String.join(
        PARAMETER_LIST_DELIMITER, data.stream().map(ResumeResponseDto::title).toList());
  }

  private String callChatModel(PromptTemplate promptTemplate, Map<String, Object> variables) {
    Prompt prompt = promptTemplate.create(variables);
    ChatResponse response = chatModel.call(prompt);

    return response.getResult().getOutput().getContent();
  }
}
