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
import java.util.Objects;
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
                (Long) arr[1],
                (String) arr[2],
                (String) arr[3],
                (String) arr[4],
                (String) arr[5],
                (String) arr[6],
                (String) arr[7],
                (double) arr[8]
                )
                );
        return new PageResultDTO<>(result,fn);
    }

    @Override
    public PageResultDTO<MovieInfoDTO, Object[]> getResult(PageRequestDTO requestDTO,String type,String sort,String ad,String keyword){
        Pageable pageable;
        Page<Object[]> result;

        //정렬 기준
        if(Objects.equals(sort, "title")){
            if(Objects.equals(ad, "ac")){
                pageable = requestDTO.getPageable(Sort.by("movieName").ascending());
            }
            else{
                pageable = requestDTO.getPageable(Sort.by("movieName").descending());
            }
        }
        else if(Objects.equals(sort, "genre")){
            keyword = changeGenresR(keyword);
            if(Objects.equals(ad, "ac")){
                pageable = requestDTO.getPageable(Sort.by("genres").ascending());
            }
            else{
                pageable = requestDTO.getPageable(Sort.by("genres").descending());
            }
        }
        else if(Objects.equals(sort, "releaseDate")){
            if(Objects.equals(ad, "ac")){
                pageable = requestDTO.getPageable(Sort.by("releaseDate").ascending());
            }
            else{
                pageable = requestDTO.getPageable(Sort.by("releaseDate").descending());
            }
        }
        else if(Objects.equals(sort, "rating")){
            if(Objects.equals(ad, "ac")){
                pageable = requestDTO.getPageable(Sort.by("rating").ascending());
            }
            else{
                pageable = requestDTO.getPageable(Sort.by("rating").descending());
            }
        }
        else{
            if(Objects.equals(ad, "ac")){
                pageable = requestDTO.getPageable(Sort.by("id").ascending());
            }
            else{
                pageable = requestDTO.getPageable(Sort.by("id").descending());
            }
        }

        //검색 기준
        if(Objects.equals(type, "title")){
            result = movieInfoRepository.getMovieWithName(pageable,keyword);
        }
        else if(Objects.equals(type, "genre")){
            keyword = changeGenresR(keyword);
            result = movieInfoRepository.getMovieWithGenre(pageable,keyword);
        }
        else if(Objects.equals(type, "keyword")){
            result = movieInfoRepository.getMovieWithInfo(pageable,keyword);
        }
        else if(Objects.equals(type, "releaseDate")){
            result = movieInfoRepository.getMovieWithDate(pageable,keyword);
        }
        else if(Objects.equals(type, "rating")){
            result = movieInfoRepository.getMovieWithRate(pageable,Double.parseDouble(keyword));
        }
        else{
            result = movieInfoRepository.getMovieWithName(pageable,keyword);
        }


        Function<Object[],MovieInfoDTO> fn = ( arr->entitiesToDTO(
                (Long) arr[0],
                (Long) arr[1],
                (String) arr[2],
                (String) arr[3],
                (String) arr[4],
                (String) arr[5],
                (String) arr[6],
                (String) arr[7],
                (double) arr[8]
        )
        );
        return new PageResultDTO<>(result,fn);
    }

    @Override
    public MovieInfoDTO getMovie(Long id) {

        List<Object[]> result = movieInfoRepository.getMovieWithAll(id);
        Long movieId = (Long) result.get(0)[1];
        String movieName = (String) result.get(0)[2];
        String movieNameK = (String) result.get(0)[3];
        String genres = (String) result.get(0)[4];
        String movieInfo = (String) result.get(0)[5];
        String url = (String) result.get(0)[6];
        String releaseDate = (String) result.get(0)[7];
        Double rating = (Double) result.get(0)[8];
        genres = changeGenres(genres);

        return entitiesToDTO(id,movieId,movieName, movieNameK, genres, movieInfo,  url, releaseDate, rating);
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

    //장르 역변환기
    public String changeGenresR(String genres) {
        if(genres!=null){

            while(true){
                if(genres.contains("액션")){
                    genres = genres.replace("액션","28");
                }
                else if(genres.contains("모험")){
                    genres = genres.replace("모험","12");
                }
                else if(genres.contains("애니메이션")){
                    genres = genres.replace("애니메이션","16");
                }
                else if(genres.contains("코미디")){
                    genres = genres.replace("코미디","35");
                }
                else if(genres.contains("범죄")){
                    genres = genres.replace("범죄","80");
                }
                else if(genres.contains("다큐멘터리")){
                    genres = genres.replace("다큐멘터리","99");
                }
                else if(genres.contains("드라마")){
                    genres = genres.replace("드라마","18");
                }
                else if(genres.contains("가족")){
                    genres = genres.replace("가족","10751");
                }
                else if(genres.contains("판타지")){
                    genres = genres.replace("판타지","14");
                }
                else if(genres.contains("역사")){
                    genres = genres.replace("역사","36");
                }
                else if(genres.contains("공포")){
                    genres = genres.replace("공포","27");
                }
                else if(genres.contains("음악")){
                    genres = genres.replace("음악","10402");
                }
                else if(genres.contains("미스터리")){
                    genres = genres.replace("미스터리","9648");
                }
                else if(genres.contains("로맨스")){
                    genres = genres.replace("로맨스","10749");
                }
                else if(genres.contains("SF")){
                    genres = genres.replace("SF","878");
                }
                else if(genres.contains("TV 영화")){
                    genres = genres.replace("TV 영화","10770");
                }
                else if(genres.contains("스릴러")){
                    genres = genres.replace("스릴러","53");
                }
                else if(genres.contains("전쟁")){
                    genres = genres.replace("전쟁","10752");
                }
                else if(genres.contains("서부")){
                    genres = genres.replace("서부","37");
                }
                else{
                    break;
                }
            }

        }
        return genres;
    }

}
