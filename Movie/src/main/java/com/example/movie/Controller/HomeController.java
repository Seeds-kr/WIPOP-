package com.example.movie.Controller;


import com.example.movie.Entity.Member;
import com.example.movie.Entity.MovieInfoEntity;
import com.example.movie.Service.MovieRecommendationService;
import com.example.movie.Service.ViewListService;
import com.example.movie.login.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class HomeController {

    private final MovieRecommendationService movieRecommendationService;
    private final ViewListService viewListService;

    public HomeController(MovieRecommendationService movieRecommendationService, ViewListService viewListService) {
        this.movieRecommendationService = movieRecommendationService;
        this.viewListService = viewListService;
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

    // 선호작 추가 API
    @CrossOrigin(origins = "http://localhost:8080")  // 프론트엔드가 동작하는 도메인
    @PostMapping("/addlist")
    public ResponseEntity<String> addToViewList(@RequestBody Map<String, Long> requestData, HttpServletRequest request) {
        // HttpServletRequest를 통해 세션에서 사용자 정보를 가져옵니다.
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        // JSON에서 movieId 추출
        Long movieId = requestData.get("movieId");

        if (movieId == null) {
            return ResponseEntity.status(400).body("유효하지 않은 영화 ID입니다.");
        }

        // 해당 회원의 view list에 영화 추가
        viewListService.addMovieToViewList(loginMember.getId(), movieId);

        return ResponseEntity.ok("영화가 선호작에 추가되었습니다.");
    }

    @GetMapping("/viewlist")
    public String getViewList(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            Model model) {

        if (loginMember == null) {
            return "redirect:/login";  // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
        }

        // 사용자의 ViewList를 가져옴
        List<MovieInfoEntity> viewListMovies = viewListService.getMoviesInViewList(loginMember.getId());

        // 모델에 ViewList 영화 목록을 추가
        model.addAttribute("viewListMovies", viewListMovies);
        model.addAttribute("member", loginMember);

        return "viewlist";  // viewlist.html을 반환 (Thymeleaf 템플릿 페이지)
    }
}

