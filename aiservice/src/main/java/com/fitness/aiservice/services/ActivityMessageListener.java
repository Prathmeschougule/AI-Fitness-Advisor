package com.fitness.aiservice.services;

import com.fitness.aiservice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAiService aiService;

    @RabbitListener(queues = "activity.queue")
    public void processActivity(Activity activity) {
        try {
            log.info("Received activity for processing: {}", activity.getId());
            String recommendation = aiService.generateRecommendation(activity);
            log.info("Generated Recommendation: {}", recommendation);
        } catch (Exception e) {
            log.error("Error processing activity {}: {}", activity.getId(), e.getMessage(), e);
            throw e; // Re-throw to let RabbitMQ handle retries or dead-letter queue
        }
    }
}