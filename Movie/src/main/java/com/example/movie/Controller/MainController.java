package com.example.movie.Controller;

import com.example.movie.Entity.Member;
import com.example.movie.Entity.MovieInfoEntity;
import com.example.movie.Service.MovieRecommendationService;
import com.example.movie.login.SessionConst;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MovieRecommendationService movieRecommendationService;

    @GetMapping("/main")
    public String mainPage(HttpSession session, Model model) {
        // 세션에서 로그인된 사용자 정보를 가져옵니다.
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null) {
            // 로그인된 사용자가 없다면 로그인 페이지로 리디렉션
            return "redirect:/login";
        }

        // 추천 영화를 가져옵니다.
        List<MovieInfoEntity> recommendedMovies = movieRecommendationService.getRecommendedMovies(loginMember);

        // 추천 영화를 모델에 추가하여 뷰에서 사용
        model.addAttribute("recommendedMovies", recommendedMovies);
        model.addAttribute("userName", loginMember.getUserName());

        // 메인 페이지를 반환합니다.
        return "mainPage";
    }
}

