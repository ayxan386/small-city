package com.jsimplec.places.listener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsimplec.places.model.ReviewRatingModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoggedModelChangeListenerTest {

  @InjectMocks
  LoggedModelChangeListener changeListener;
  @Mock
  RedisTemplate<String, String> redisTemplate;
  @Mock
  ObjectMapper objectMapper;

  Map<String, String> fields = Map.of("id", "1", "value", "hello");
  private ReviewRatingModel reviewRatingModel;


  @BeforeEach
  void setUp() throws IOException {
    LoggedModelChangeListener.objectMapper = objectMapper;
    LoggedModelChangeListener.redisTemplate = redisTemplate;
    reviewRatingModel = ReviewRatingModel
        .builder()
        .id(1L)
        .content("hello")
        .build();
  }

  @Test
  void postSave() throws IOException {
    doNothing()
        .when(redisTemplate).convertAndSend(anyString(), any());
    when(objectMapper.convertValue(any(), any(TypeReference.class))).thenReturn(fields);
    changeListener.postSave(reviewRatingModel);
    verify(objectMapper).convertValue(any(), any(TypeReference.class));
    verify(redisTemplate).convertAndSend(anyString(), any());
  }

  @Test
  void postUpdate() {

    doNothing()
        .when(redisTemplate).convertAndSend(anyString(), any());
    when(objectMapper.convertValue(any(), any(TypeReference.class))).thenReturn(fields);
    changeListener.postUpdate(reviewRatingModel);
    verify(objectMapper).convertValue(any(), any(TypeReference.class));
    verify(redisTemplate).convertAndSend(anyString(), any());
  }

  @Test
  void setupFields() {
    when(objectMapper.convertValue(any(), any(TypeReference.class))).thenReturn(fields);
    changeListener.setupPreviousFields(reviewRatingModel);
    verify(objectMapper).convertValue(any(), any(TypeReference.class));
  }

}