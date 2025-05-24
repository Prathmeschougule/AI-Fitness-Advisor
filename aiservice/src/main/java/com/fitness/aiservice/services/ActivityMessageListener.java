package com.fitness.aiservice.services;

import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAiService aiService;
    private  final RecommendationRepository recommendationRepo;


    @RabbitListener(queues = "activity.queue")
    public void processActivity(Activity activity) {
        try {
            log.info("Received activity for processing: {}", activity.getId());
//            String recommendation = aiService.generateRecommendation(activity);
//            log.info("Generated Recommendation: {}

            Recommendation recommendation = aiService.generateRecommendation(activity);
            recommendationRepo.save(recommendation);

        } catch (Exception e) {
            log.error("Error processing activity {}: {}", activity.getId(), e.getMessage(), e);
            throw e; // Re-throw to let RabbitMQ handle retries or dead-letter queue
        }
    }
}