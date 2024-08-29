package com.example.movie.dto;

import com.example.movie.Entity.MovieInfoEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor

public class MovieInfoDTO {
    private Long id;
    private Long movieId;
    private String movieName;
    private String movieNameK;
    private String posterURL;
    private String releaseDate;
    private String movieInfo;
    private String genres;
    private double rating;


    public static MovieInfoDTO toMovieInfoDTO(MovieInfoEntity movieinfoEntity){
        MovieInfoDTO movieInfoDTO = new MovieInfoDTO();
        movieInfoDTO.setId(movieinfoEntity.getId());
        movieInfoDTO.setMovieName(movieinfoEntity.getMovieName());
        movieInfoDTO.setRating(movieinfoEntity.getRating());
        movieInfoDTO.setGenres(movieinfoEntity.getGenres());
        return movieInfoDTO;
    }
}
