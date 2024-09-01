package com.example.movie.Controller;


import com.example.movie.Entity.Member;
import com.example.movie.Entity.MovieInfoEntity;
import com.example.movie.Service.MovieRecommendationService;
import com.example.movie.login.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@Slf4j
public class HomeController {

    private final MovieRecommendationService movieRecommendationService;

    public HomeController(MovieRecommendationService movieRecommendationService) {
        this.movieRecommendationService = movieRecommendationService;
    }

    @GetMapping("/")
    public String home(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            Model model) {

        if (loginMember == null) {
            return "home";
        }

        // 추천 영화를 가져와서 모델에 추가
        List<MovieInfoEntity> recommendedMovies = movieRecommendationService.getRecommendedMovies(loginMember);
        model.addAttribute("member", loginMember);
        model.addAttribute("recommendedMovies", recommendedMovies);

        return "loginhome"; // 업데이트된 home.html을 반환
    }
}

