package com.example.movie.Service;

import com.example.movie.Entity.Member;
import com.example.movie.Entity.MovieEntity;
import com.example.movie.Entity.Review;
import com.example.movie.dto.ReviewDto;
import com.example.movie.repository.MemberRepository;
import com.example.movie.repository.MovieRepository;
import com.example.movie.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    public void addReview(ReviewDto reviewDto){
        Member findMember = memberRepository.findById(reviewDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        MovieEntity findMovie = movieRepository.findById(reviewDto.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID"));

        Review review = new Review(reviewDto.getBody(), findMember, findMovie, reviewDto.getScore());

        findMember.addReview(review);
        findMovie.addReview(review);

        reviewRepository.save(review);

    }

    public List<Review> getReviewsByMovie(Long movieId){
        MovieEntity findMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie Id"));

        return reviewRepository.findAllByMovie(findMovie);
    }


}
