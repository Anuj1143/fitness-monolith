package com.project.fitness.services;

import com.project.fitness.dto.RecommendationRequest;
import com.project.fitness.dto.UserResponse;
import com.project.fitness.model.Activity;
import com.project.fitness.model.Recommendation;
import com.project.fitness.model.User;
import com.project.fitness.repositories.ActivityRepository;
import com.project.fitness.repositories.RecommendationRepository;
import com.project.fitness.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    public Recommendation generateRecommendation(RecommendationRequest request) {
        User user=userRepository.findById(request.getUserId()).orElseThrow(()->new RuntimeException("User not found" + request.getUserId()));
        Activity activity=activityRepository.findById(request.getActivityId()).orElseThrow(()-> new RuntimeException("Activity Not Found: "+ request.getActivityId()));
        Recommendation recommendation=Recommendation.builder()
                .user(user)
                .activity(activity)
                .improvements(request.getImprovements())
                .safety(request.getSafety())
                .suggestions(request.getSuggestions())
                .build();

        return recommendationRepository.save(recommendation);

    }

    public List<Recommendation> getUserRecommendation(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

    public List<Recommendation> getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId);
    }
}
