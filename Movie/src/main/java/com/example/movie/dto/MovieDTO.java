package com.example.movie.dto;

import com.example.movie.Entity.MovieEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//lombok dependency추가
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MovieDTO { //회원 정보를 필드로 정의
    private Long id;
    private String movieName;
    private double rating;

    //lombok 어노테이션으로 getter,setter,생성자,toString 메서드 생략 가능

    public static MovieDTO toMovieDTO(MovieEntity movieEntity){
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movieEntity.getId());
        movieDTO.setMovieName(movieEntity.getMovieName());
        movieDTO.setRating(movieEntity.getRating());

        return movieDTO;
    }
}
