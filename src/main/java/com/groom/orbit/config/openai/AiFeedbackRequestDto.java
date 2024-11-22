package com.groom.orbit.config.openai;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class AiFeedbackRequestDto {

  private String model = "gpt-4o-mini";

  private List<Message> messages;

  public AiFeedbackRequestDto(List<Message> messages) {
    this.messages = messages;
  }

  @Getter
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class Message {
    private Role role;
    private List<Content> content;
  }

  @RequiredArgsConstructor
  @Getter
  public enum Role {
    USER("user"),
    SYSTEM("system");
    @JsonValue private final String value;
  }

  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  @Getter
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class Content {
    private Type type;

    private String text;

    public static Content textContent(String text) {
      return new Content(Type.TEXT, text);
    }
  }

  @RequiredArgsConstructor
  @Getter
  public enum Type {
    TEXT("text");
    @JsonValue private final String value;
  }

  public static AiFeedbackRequestDto from(
      String job,
      String academy,
      String career,
      String qualification,
      String experience,
      String etc) {
    return new AiFeedbackRequestDto(
        createMessages(job, academy, career, qualification, experience, etc));
  }

  private static List<Message> createMessages(
      String job,
      String academy,
      String career,
      String qualification,
      String experience,
      String etc) {
    return List.of(
        new Message(Role.SYSTEM, createSystemContents(job)),
        new Message(Role.USER, createContents(academy, career, qualification, experience, etc)));
  }

  private static List<Content> createSystemContents(String job) {
    return List.of(Content.textContent(Prompt.getSystemPrompt(job)));
  }

  private static List<Content> createContents(
      String academy, String career, String qualification, String experience, String etc) {
    return List.of(
        Content.textContent(Prompt.getUserPrompt(academy, career, qualification, experience, etc)));
  }
}
