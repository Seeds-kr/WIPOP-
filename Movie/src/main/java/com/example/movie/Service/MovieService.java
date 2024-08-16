package com.example.movie.Service;

import com.example.movie.Entity.MovieEntity;
import com.example.movie.dto.MovieDTO;
import com.example.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service //스프링이 관리해주는 객체 == 스프링 빈
@RequiredArgsConstructor //controller와 같이. final 멤버변수 생성자 만드는 역할
public class MovieService {

    private final MovieRepository movieRepository; // 먼저 jpa, mysql dependency 추가

    public void save(MovieDTO movieDTO) {
        // repsitory의 save 메서드 호출
        MovieEntity movieEntity = MovieEntity.toMovieEntity(movieDTO);
        movieRepository.save(movieEntity);
        //Repository의 save메서드 호출 (조건. entity객체를 넘겨줘야 함)

    }
}