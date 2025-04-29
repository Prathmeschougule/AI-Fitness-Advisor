package com.fitness.aiservice.dto;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RecommendationResponce {

    private String id;
    private String activityId;
    private String userId;
    private String activityType;

    private String recommendation;
    private List<String> improvements;
    private List<String> suggestions;
    private List<String> safety;
    private LocalDateTime createdAt;

}
