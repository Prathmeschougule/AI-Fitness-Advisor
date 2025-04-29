package com.fitness.aiservice.services;

import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;
//import com.fitness.aiservice.exeption.RecommendationNotFoundException;/
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    public List<Recommendation> getUserRecommendation(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

    public Recommendation getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No Recommendation Found on this Id: " + activityId));
    }

}
