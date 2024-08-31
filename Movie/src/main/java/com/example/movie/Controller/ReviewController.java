package com.example.movie.Controller;

import com.example.movie.Entity.Member;
import com.example.movie.Entity.MovieInfoEntity;
import com.example.movie.Entity.Review;
import com.example.movie.Service.MovieInfoService;
import com.example.movie.Service.ReviewService;
import com.example.movie.dto.ReviewDto;
import com.example.movie.login.SessionConst;
import com.example.movie.repository.MovieInfoRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final MovieInfoService movieInfoService;

    @GetMapping("/{movieId}")
    public String getMovie(@PathVariable Long movieId, Model model){
        List<Review> reviews = reviewService.getReviewsByMovie(movieId);
        MovieInfoEntity movie = movieInfoService.findMovie(movieId);

        ReviewDto reviewDto = new ReviewDto();

        model.addAttribute("reviewDto", reviewDto);
        model.addAttribute("reviews", reviews);
        model.addAttribute("movie", movie);
        model.addAttribute("movieId", movieId);

        return "review";// 템플릿
    }

    @GetMapping("/{movieId}/add")
    public String ReviewForm(@RequestParam Long movieId, Model model){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setMovieId(movieId);

        model.addAttribute("reviewDto", reviewDto);
        return "add_comment";
    }

    @PostMapping("/{movieId}/add")
    public String addReview(@ModelAttribute ReviewDto reviewDto, HttpServletRequest request){
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        reviewDto.setMemberId(member.getId());
        reviewService.addReview(reviewDto);

        return "redirect:/review/movie/" + reviewDto.getMovieId();
    }
}
