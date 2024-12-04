package com.groom.orbit.ai.app;

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

import com.groom.orbit.member.app.dto.response.GetFeedbackResponseDto;
import com.groom.orbit.resume.app.dto.GetResumeResponseDto;
import com.groom.orbit.resume.app.dto.ResumeResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenAiService {

  private final ChatModel chatModel;

  @Value("classpath:/templates/ai-feedback-prompt.txt")
  private Resource aiFeedbackPrompt;

  public GetFeedbackResponseDto getFeedback(String interestJobs, GetResumeResponseDto dto) {
    BeanOutputConverter<GetFeedbackResponseDto> converter =
        new BeanOutputConverter<>(GetFeedbackResponseDto.class);
    String format = converter.getFormat();

    PromptTemplate promptTemplate = new PromptTemplate(aiFeedbackPrompt);
    String response =
        callChatModel(
            promptTemplate,
            Map.of(
                "job", interestJobs,
                "academy", convertDtoToString(dto.academyList()),
                "career", convertDtoToString(dto.careerList()),
                "qualification", convertDtoToString(dto.qualificationList()),
                "experience", convertDtoToString(dto.experienceList()),
                "etc", convertDtoToString(dto.etcList()),
                "format", format));

    return converter.convert(response);
  }

  private String convertDtoToString(List<ResumeResponseDto> data) {
    return String.join("\n", data.stream().map(ResumeResponseDto::title).toList());
  }

  private String callChatModel(PromptTemplate promptTemplate, Map<String, Object> variables) {
    Prompt prompt = promptTemplate.create(variables);
    ChatResponse response = chatModel.call(prompt);

    return response.getResult().getOutput().getContent();
  }
}
