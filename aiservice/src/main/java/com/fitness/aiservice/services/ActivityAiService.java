package com.fitness.aiservice.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {

    private final GeminiService geminiService;

    public Recommendation generateRecommendation(Activity activity) {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getAnswer(prompt);
        log.info("RESPONSE FROM THE AI: {}", aiResponse);
        return processAiResponse(activity,aiResponse);
    }

    private Recommendation processAiResponse(Activity activity,String aiResponse){
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(aiResponse);

            JsonNode textNode = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text");


            String JsonContent = textNode.asText()
                    .replaceAll("```json\\n","")
                    .replaceAll("\\n```", "")
                    .trim();

//            log.info("PASSED RESPONSE FROM THE AI: {}", JsonContent);

//            Extract the Data
            JsonNode analysisJson = mapper.readTree(JsonContent);
            JsonNode analysisNode = analysisJson.path("analysis");
            StringBuilder fullAnalysis = new StringBuilder();

//            Crete This Method 
            addAnalysisSection(fullAnalysis,analysisNode,"overall","Overall:");
            addAnalysisSection(fullAnalysis,analysisNode,"pace","Pace:");
            addAnalysisSection(fullAnalysis,analysisNode,"heartRate","Hear Rate:");
            addAnalysisSection(fullAnalysis,analysisNode,"caloriesBurned","Calories:");

            List<String> improvement =extractImprovements(analysisJson.path("improvements"));
            List<String> suggestions =extractSuggestions(analysisJson.path("suggestions"));
            List<String> safety =extractSafetyGuidelines(analysisJson.path("safety"));

            return Recommendation.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .activityType(activity.getType())
                    .recommendation(fullAnalysis.toString().trim())
                    .improvements(improvement)
                    .suggestions(suggestions)
                    .safety(safety)
                    .createdAt(LocalDateTime.now())
                    .build();




        }catch (Exception e){
            e.printStackTrace();

            return createDefoultRecommendation(activity);
        }

    }

    private Recommendation createDefoultRecommendation(Activity activity) {
        return Recommendation.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .activityType(activity.getType())
                .recommendation("Unable To generate detailed analysis")
                .improvements(Collections.singletonList("Continue With Your Current Run Time "))
                .suggestions(Collections.singletonList("Consider consulting a fitness Professional "))
                .safety(Arrays.asList(
                        "Always warm up before the exercise",
                        "Stay hydrated",
                        "Listen to your body"
                ))
                .createdAt(LocalDateTime.now())
                .build();
    }

    // this method get the safety
    private List<String> extractSafetyGuidelines(JsonNode safetyNode) {
        List<String>  safety=  new ArrayList<>();

        if (safetyNode.isArray()) {
            safetyNode.forEach(item -> safety.add(item.asText()));

        }
        return safety.isEmpty() ?
                Collections.singletonList("Follow general Guid line ") :
                safety;

    }

    // this method get the suggestions
    private List<String> extractSuggestions(JsonNode suggestionsNode) {
        List<String>  suggestions=  new ArrayList<>();

        if (suggestionsNode.isArray()){
            suggestionsNode.forEach(suggestion -> {
                String workOut = suggestion.path("workout").asText();
                String  description = suggestion.path("description").asText();
                suggestions.add(String.format("%s: %s",workOut,description));
            });
        }
        return suggestions.isEmpty() ?
                Collections.singletonList("No Specific suggestions provide ") :
                suggestions;
    }

    // this  method are get the Recommendation
    private List<String> extractImprovements(JsonNode improvementsNode) {
        List<String> improvements = new ArrayList<>();
        if (improvementsNode.isArray()){
            improvementsNode.forEach( improvement -> {
                String area = improvement.path("area").asText();
                String  detail = improvement.path("recommendation").asText();
                improvements.add(String.format("%s: %s",area,detail));
            });
        }
        return improvements.isEmpty() ?
                Collections.singletonList("No Specific improvement provide ") :
                improvements;
    }

//    this method  get the [overall ,pace ,heartRate, caloriesBurned ] in text format
    private void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {

        if (!analysisNode.path(key).isMissingNode()){
                fullAnalysis.append(prefix)
                        .append(analysisNode.path(key).asText())
                        .append("\n\n");
        }
    }

    private String createPromptForActivity(Activity activity) {
        return String.format("""
        Analyze this fitness activity and provide detailed recommendations in the following format
        {
            "analysis": {
                "overall": "Overall analysis here",
                "pace": "Pace analysis here",
                "heartRate": "Heart rate analysis here",
                "CaloriesBurned": "Calories Burned here"
            },
            "improvements": [
                {
                    "area": "Area name",
                    "recommendation": "Detailed Recommendation"
                }
            ],
            "suggestions": [
                {
                    "workout": "Workout name",
                    "description": "Detailed workout description"
                }
            ],
            "safety": [
                "Safety point 1",
                "Safety point 2"
            ]
        }

        Analyze this activity:
        Activity Type: %s
        Duration: %d minutes
        Calories Burned: %d
        Additional Metrics: %s

        Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
        Ensure the response follows the EXACT JSON format shown above.
        """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMetrics()
        );
    }
}