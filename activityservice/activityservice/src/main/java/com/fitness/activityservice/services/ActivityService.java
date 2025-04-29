package com.fitness.activityservice.services;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    private  final  UserValidationService userValidationService;

    public ActivityResponse trackActivity(ActivityRequest request) {

        boolean isValidUser = userValidationService.validateUser(request.getUserId());
        if (isValidUser){
            throw  new RuntimeException("Invalid User" + request.getUserId());
        }

        Activity activity =Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCalorieBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();
        Activity saveActivity =activityRepository.save(activity);
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
