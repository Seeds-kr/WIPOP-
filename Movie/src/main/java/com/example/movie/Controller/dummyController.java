package com.example.movie.Controller;

import com.example.movie.Entity.MovieEntity;
import com.example.movie.Service.GenreService;
import com.example.movie.repository.GenresRepo;
import com.example.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/dummy")

//영화 목록 받아오는 컨트롤러
 public class dummyController {


    private final MovieRepository movieRepository;
    private final GenresRepo genresRepo;
    private final GenreService genreService;

    @GetMapping("/add-movies")//pathname 경로에 있는 영화 목록 파일을 받아옵니다
    public ResponseEntity<?> addMovies() throws IOException {

        File csv = new File("C:\\Users\\parkerpark\\Downloads\\ml-latest-small\\ml-latest-small\\movies (정렬버전).csv");
        BufferedReader br = new BufferedReader(new BufferedReader(new FileReader(csv)));

        String line = "";
        boolean skipFirstLine = true;
        //파일에서 라인마다 데이터 가져오기
        while ((line = br.readLine()) != null) {
            if(skipFirstLine) {
                skipFirstLine = false;
                continue;
            }
            //파일에서 데이터 ,기준으로 자르기
            String[] token = line.split(",");
            Long movieId = Long.parseLong(token[0]);
            double rating = Double.parseDouble(token[token.length - 2]);
            String[] genre = token[token.length - 1].split("\\|");

            StringBuilder title = new StringBuilder();
            for(int i = 1; i < token.length - 2; i++) {
                title.append(token[i]);
                if(i != token.length-3) title.append(",");
            }

            //영화 제목만 남기기
            String movieName = title.toString();
            if(movieName.contains("(")){
                movieName = movieName.substring(0,movieName.indexOf("("));
            }

            //DB 형식에 맞게 변환해서 저장
            movieRepository.save(MovieEntity.builder()
                    .id(movieId)
                    .movieName(movieName)
                    .rating(rating)
                    .genres(Arrays.stream(genre)
                            .map(genreService::findOrCreateNew)
                            .collect(Collectors.toSet()))
                    .build());
        }
        return ResponseEntity.ok().build();
    }

}
