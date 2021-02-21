package com.jsimplec.places.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jsimplec.places.listener.LoggedModelChangeListener;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Map;

import static java.util.Collections.emptyMap;

@Data
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(LoggedModelChangeListener.class)
public class LoggedModel {

  @Transient
  @JsonIgnore
  private Map<String, String> prevFields = emptyMap();
}
