package com.example.movie.Service;


import com.example.movie.Entity.MovieInfoEntity;
import com.example.movie.dto.MovieInfoDTO;
import com.example.movie.dto.PageRequestDTO;
import com.example.movie.dto.PageResultDTO;
import com.example.movie.repository.MovieInfoRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieListServiceImpl implements MovieListService{
    private final MovieInfoRepo movieInfoRepository;

    @Transactional
    @Override
    public Long register(MovieInfoDTO movieDTO) {

        Map<String,Object> entityMap = dtoToEntity(movieDTO);
        MovieInfoEntity movie = (MovieInfoEntity) entityMap.get("movie");
        movieInfoRepository.save(movie);
        return movie.getId();
    }
    public PageResultDTO<MovieInfoDTO, Object[]> getList(PageRequestDTO requestDTO){
        Pageable pageable= requestDTO.getPageable(Sort.by("id").ascending());
        Page<Object[]> result = movieInfoRepository.getListPage(pageable);


        Function<Object[],MovieInfoDTO> fn = ( arr->entitiesToDTO(
                (Long) arr[0],
                (String) arr[1],
                (String) arr[2],
                (String) arr[3],
                (String) arr[4],
                (String) arr[5],
                (String) arr[6],
                (double) arr[7]
                )
                );
        return new PageResultDTO<>(result,fn);
    }

    @Override
    public MovieInfoDTO getMovie(Long id) {

        List<Object[]> result = movieInfoRepository.getMovieWithAll(id);
        String movieName = (String) result.get(0)[1];
        String movieNameK = (String) result.get(0)[2];
        String genres = (String) result.get(0)[3];
        String movieInfo = (String) result.get(0)[4];
        String url = (String) result.get(0)[5];
        String releaseDate = (String) result.get(0)[6];
        Double rating = (Double) result.get(0)[7];
        genres = changeGenres(genres);

        return entitiesToDTO(id,movieName, movieNameK, genres, movieInfo,  url, releaseDate, rating);
    }

    //장르 변환기
    public String changeGenres(String genres) {
        if(genres!=null){

            while(true){
                if(genres.contains("28")){
                    genres = genres.replace("28","액션");
                }
                else if(genres.contains("12")){
                    genres = genres.replace("12","모험");
                }
                else if(genres.contains("16")){
                    genres = genres.replace("16","애니메이션");
                }
                else if(genres.contains("35")){
                    genres = genres.replace("35","코미디");
                }
                else if(genres.contains("80")){
                    genres = genres.replace("80","범죄");
                }
                else if(genres.contains("99")){
                    genres = genres.replace("99","다큐멘터리");
                }
                else if(genres.contains("18")){
                    genres = genres.replace("18","드라마");
                }
                else if(genres.contains("10751")){
                    genres = genres.replace("10751","가족");
                }
                else if(genres.contains("14")){
                    genres = genres.replace("14","판타지");
                }
                else if(genres.contains("36")){
                    genres = genres.replace("36","역사");
                }
                else if(genres.contains("27")){
                    genres = genres.replace("27","공포");
                }
                else if(genres.contains("10402")){
                    genres = genres.replace("10402","음악");
                }
                else if(genres.contains("9648")){
                    genres = genres.replace("9648","미스터리");
                }
                else if(genres.contains("10749")){
                    genres = genres.replace("10749","로맨스");
                }
                else if(genres.contains("878")){
                    genres = genres.replace("878","SF");
                }
                else if(genres.contains("10770")){
                    genres = genres.replace("10770","TV 영화");
                }
                else if(genres.contains("53")){
                    genres = genres.replace("53","스릴러");
                }
                else if(genres.contains("10752")){
                    genres = genres.replace("10752","전쟁");
                }
                else if(genres.contains("37")){
                    genres = genres.replace("37","서부");
                }
                else{
                    break;
                }
            }

        }
        return genres;
    }

}
