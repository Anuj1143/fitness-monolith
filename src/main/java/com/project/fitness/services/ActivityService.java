package com.project.fitness.services;

import com.project.fitness.dto.ActivityRequest;
import com.project.fitness.dto.ActivityResponse;
import com.project.fitness.dto.UserResponse;
import com.project.fitness.model.Activity;
import com.project.fitness.model.User;
import com.project.fitness.repositories.ActivityRepository;
import com.project.fitness.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private  final ActivityRepository activityRepository;
    private  final UserRepository userRepository;
    public @Nullable ActivityResponse trackActivity(ActivityRequest request) {
        User user=userRepository.findById(request.getUserId()).orElseThrow(()->new RuntimeException("Invalid user: " + request.getUserId()));
        Activity activity=Activity.builder()
                .user(user)
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMatrics())

                .build();
     Activity savedActivity=activityRepository.save(activity);
     return mapToResponse(savedActivity);
    }

    private  ActivityResponse mapToResponse(Activity savedActivity) {
        ActivityResponse response=new ActivityResponse();
        response.setId(savedActivity.getId());
        response.setType(savedActivity.getType());
        response.setUserId(savedActivity.getUser().getId());
        response.setDuration(savedActivity.getDuration());
        response.setCaloriesBurned(savedActivity.getCaloriesBurned());
        response.setAdditionalMatrics(savedActivity.getAdditionalMetrics());
        response.setStartTime(savedActivity.getStartTime());
        response.setCreatedAt(savedActivity.getCreatedAt());
        response.setUpdatedAt(savedActivity.getUpdatedAt());
        return  response;
    }

    public  List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activityList=activityRepository.findByUserId(userId);
        return activityList.stream().map(this::mapToResponse).collect(Collectors.toList());
    }
}
