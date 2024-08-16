package com.example.movie.Controller;

import com.example.movie.Service.MovieService;
import com.example.movie.repository.MovieRepository;
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
//kobis api에서 정보 받아오는 컨트롤러
public class MovieController {

    private final MovieService movieService;
    private final MovieRepository movieRepository;

    @GetMapping("/movie")//박스오피스 영화 순위를 받아옵니다.
    public String getAPI() {

        HashMap<String, Object> result = new HashMap<String, Object>();
        String jsonInString = "";
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);
            //API url, key 입력하는 부분
            String url = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";

            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url + "?" + "key=82ca741a2844c5c180a208137bb92bd7&targetDt=20120101").build();

            //이 한줄의 코드로 API를 호출해 MAP타입으로 전달 받는다.
            ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
            result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
            result.put("header", resultMap.getHeaders()); //헤더 정보 확인
            result.put("body", resultMap.getBody()); //실제 데이터 정보 확인
            //전체 정보 받아오는 부분
            LinkedHashMap lm = (LinkedHashMap) resultMap.getBody().get("boxOfficeResult");
            ArrayList<Map> dboxoffList = (ArrayList<Map>) lm.get("dailyBoxOfficeList");

            LinkedHashMap mnList = new LinkedHashMap<>();
            int i=0;
            //필요한 정보만 받아오는 부분
            for (Map obj : dboxoffList) {
                mnList.put("객체 번호" + i, obj.get("rnum"));
                mnList.put("영화 이름" + i, obj.get("movieNm"));
                mnList.put("영화 매출" + i, obj.get("salesAcc"));
                mnList.put("영화 누적 관객수" + i, obj.get("audiAcc"));
            //DB에 정보 전달하는 부분
                //MovieDTO movieDTO = new MovieDTO();
                //movieDTO.setMovieName(mnList.get("영화 이름" + i).toString());
                //movieRepository.save(MovieEntity.toMovieEntity(movieDTO));

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
//코드 참고 https://corine2.tistory.com/157
}
