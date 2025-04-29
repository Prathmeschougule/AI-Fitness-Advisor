package com.fitness.aiservice.dto;

import lombok.Data;

import java.util.List;


@Data
public class RecommendtionRequest {

    private String id;

    private String activityId;
    private String userId;
    private String activityType;
}
