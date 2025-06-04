package com.fitness.activityservice.services;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {

    private final ActivityRepository activityRepository;
    private  final  UserValidationService userValidationService;
    private  final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public ActivityResponse trackActivity(ActivityRequest request) {

        boolean isValidUser = userValidationService.validateUser(request.getUserId());
        if (isValidUser){
            throw  new RuntimeException("Invalid User:" + request.getUserId());
        }

        Activity activity =Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();
        Activity saveActivity =activityRepository.save(activity);

//        publish rabbit MQ for AI Processing

        try{

            rabbitTemplate.convertAndSend(exchange,routingKey,saveActivity);
        }catch(Exception e){
           log.error("Failed to publish activity to RabbitMQ:");
        }

        System.out.println("Saved Activity: " + saveActivity);

        return mapToResponse(saveActivity);
    }

    private ActivityResponse mapToResponse(Activity activity){
        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());
        response.setUserId(activity.getUserId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartTime(activity.getStartTime());
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
        response.setCreateAt(activity.getCreateAt());
        response.setUpdateAt(activity.getUpdateAt());

        return response;

    }

    public List<ActivityResponse> getUserActivity(String userId) {

       List<Activity> activities = activityRepository.findByUserId(userId);

       return activities.stream() //convert in to stream
                         .map(this::mapToResponse)   //calling map to response to every item
                         .collect(Collectors.toList());
    }

    public ActivityResponse getActivity(String activityId) {

//        This Method also work :-->

//        Activity activity=activityRepository.findById(activityId)
//                .orElseThrow(()->new RuntimeException("Activity Not Found:"+ activityId));
//        ActivityResponse activityResponse =new ActivityResponse();
//        BeanUtils.copyProperties(activity,activityResponse);
//        return activityResponse;

        return activityRepository.findById(activityId)
                .map(this::mapToResponse)
                .orElseThrow(()-> new RuntimeException("Activity Not Found" + activityId));

    }
}
