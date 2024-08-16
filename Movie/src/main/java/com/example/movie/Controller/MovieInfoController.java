package com.example.movie.Controller;

import com.example.movie.Entity.MovieInfoEntity;
import com.example.movie.dto.MovieInfoDTO;
import com.example.movie.repository.MovieInfoRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@Controller
@RequiredArgsConstructor
//TMDB api에서 정보 받아오는 컨트롤러
public class MovieInfoController {

    private final MovieInfoRepo movieInfoRepo;

    //movieName으로 영호 상세 정보를 받아옵니다.
    @GetMapping("/movieinfo")
    public String searchAPI(String movieName) {

        HashMap<String, Object> result = new HashMap<String, Object>();
        String jsonInString = "";
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);
            //API url, key 입력하는 부분
            String url = "https://api.themoviedb.org/3/search/movie";
            //임시 제목
            movieName = "The%20godfather";

            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url + "?" + "api_key=92d0cc516f465cac76ef0765774d79f7" + "&language=ko-KR&page=1&query=" + movieName).build();

            //이 한줄의 코드로 API를 호출해 MAP타입으로 전달 받는다.
            ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
            result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
            result.put("header", resultMap.getHeaders()); //헤더 정보 확인
            result.put("body", resultMap.getBody()); //실제 데이터 정보 확인
            //전체 정보 받아오는 부분
            //LinkedHashMap lm = (LinkedHashMap) resultMap.getBody().get("boxOfficeResult");
            ArrayList<Map> dboxoffList = (ArrayList<Map>) resultMap.getBody().getOrDefault("results",0);

            LinkedHashMap mnList = new LinkedHashMap<>();
            int i=0;
            //필요한 정보만 받아오는 부분
            for (Map obj : dboxoffList) {
                mnList.put("객체 번호" + i, obj.getOrDefault("id","null"));
                mnList.put("영화 이름" + i, obj.getOrDefault("title","null"));
                mnList.put("영화 원제" + i, obj.getOrDefault("original_title","null"));
                mnList.put("영화 출시일" + i, obj.getOrDefault("release_date","null"));
                mnList.put("영화 평점" + i, obj.getOrDefault("vote_average","null"));
                mnList.put("영화 소개" + i, obj.getOrDefault("overview","null"));
                mnList.put("영화 포스터" + i, obj.get("poster_path"));
                System.out.println(mnList.get("영화 포스터" + i));

                //DB에 정보 전달하는 부분
                MovieInfoDTO movieInfoDTO = new MovieInfoDTO();
                movieInfoDTO.setMovieNameK(mnList.getOrDefault("영화 이름" + i,"null").toString());
                movieInfoDTO.setMovieName(mnList.getOrDefault("영화 원제" + i,"null").toString());
                movieInfoDTO.setReleaseDate(mnList.getOrDefault("영화 출시일" + i,"null").toString());
                movieInfoDTO.setRating(Double.parseDouble(mnList.getOrDefault("영화 평점" + i,"null").toString()));
                movieInfoDTO.setMovieInfo(mnList.getOrDefault("영화 소개" + i,"null").toString());
                //TMDB에서 포스터 경로가 없으면 null로 저장해서 예외 처리
                if(mnList.get("영화 포스터" + i) == null){
                    movieInfoDTO.setPosterURL("");
                }
                else{
                    movieInfoDTO.setPosterURL(mnList.getOrDefault("영화 포스터" + i,"null").toString());
                }
                movieInfoRepo.save(MovieInfoEntity.toMovieInfoEntity(movieInfoDTO));
                i++;
            }
            ObjectMapper mapper = new ObjectMapper();
            jsonInString = mapper.writeValueAsString(mnList);


        } catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("statusCode", e.getRawStatusCode());
            result.put("body", e.getStatusText());
            System.out.println(e.toString());

        } catch (Exception e) {
            result.put("statusCode", "999");
            result.put("body", "excpetion오류");
            System.out.println(e.toString());
        }
        return jsonInString;
    }
}
