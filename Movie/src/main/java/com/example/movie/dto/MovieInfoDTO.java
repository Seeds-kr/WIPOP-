package com.example.movie.dto;

import com.example.movie.Entity.MovieInfoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MovieInfoDTO {
    private Long id;
    private String movieName;
    private String movieNameK;
    private String posterURL;
    private String releaseDate;
    private String movieInfo;
    private double rating;


    public static MovieInfoDTO toMovieInfoDTO(MovieInfoEntity movieinfoEntity){
        MovieInfoDTO movieInfoDTO = new MovieInfoDTO();
        movieInfoDTO.setId(movieinfoEntity.getId());
        movieInfoDTO.setMovieName(movieinfoEntity.getMovieName());
        movieInfoDTO.setRating(movieinfoEntity.getRating());

        return movieInfoDTO;
    }
}
