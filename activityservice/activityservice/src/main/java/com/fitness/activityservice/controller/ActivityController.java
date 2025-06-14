package com.fitness.activityservice.controller;
import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.services.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
public class ActivityController {

    private ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponse>trackActivity(@RequestBody ActivityRequest request,@RequestHeader("X-User-ID") String userId){
        if (userId != null){
            request.setUserId(userId);
        }
        return ResponseEntity.ok(activityService.trackActivity(request));
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>>getUserActivity(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(activityService.getUserActivity((userId)));
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResponse>getActivity(@PathVariable String activityId) {
        return ResponseEntity.ok(activityService.getActivity((activityId)));
    }
}
