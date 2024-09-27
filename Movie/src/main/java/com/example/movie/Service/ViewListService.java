package com.example.movie.Service;


import com.example.movie.Entity.MovieInfoEntity;
import com.example.movie.Entity.ViewListEntity;
import com.example.movie.repository.MovieInfoRepo;
import com.example.movie.repository.ViewListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewListService {

    private final ViewListRepository viewListRepository;
    private final MovieInfoRepo movieRepository;


    public void addMovieToViewList(Long memberId, Long movieId) {
        ViewListEntity viewList = viewListRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalStateException("View list가 존재하지 않습니다."));
        MovieInfoEntity movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalStateException("해당 영화가 존재하지 않습니다."));

        viewList.addMovie(movie);  // ViewList 엔티티에서 영화를 추가하는 메서드가 필요함
        viewListRepository.save(viewList);
    }

    @Transactional(readOnly = true)
    public List<MovieInfoEntity> getMoviesInViewList(Long memberId) {
        // 사용자의 ViewList를 가져옴
        ViewListEntity viewList = viewListRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalStateException("해당 회원의 ViewList가 존재하지 않습니다."));

        // Set을 List로 변환해서 반환
        return new ArrayList<>(viewList.getMovies());
    }


}

