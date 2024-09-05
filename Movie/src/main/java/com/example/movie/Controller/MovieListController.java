package com.example.movie.Controller;

import com.example.movie.Service.MovieListService;
import com.example.movie.dto.MovieInfoDTO;
import com.example.movie.dto.PageRequestDTO;
import com.example.movie.repository.MovieInfoRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/movie")
@Slf4j
@RequiredArgsConstructor
//영화 목록 리스트 페이지로 전달
public class MovieListController {
    private final MovieListService movieListService;
    private final MovieInfoRepo movieInfoRepo;
    @GetMapping("list")
    public String list(PageRequestDTO pageRequestDTO, Model model){
        log.info("pageRequestDTO: "+ pageRequestDTO);
        model.addAttribute("result", movieListService.getList(pageRequestDTO));
        return "List";
    }

    @GetMapping("test")
    public String test(){
        return "test";
    }

    @GetMapping("list/result")
    public String result(PageRequestDTO pageRequestDTO,String keyword, String type, String genreClass, String sort, String ad, Model model){
        log.info("pageRequestDTO: "+ pageRequestDTO);
        model.addAttribute("result", movieListService.getResult(pageRequestDTO,type,genreClass,sort,ad,keyword));
        model.addAttribute("keyword", keyword);
        model.addAttribute("type",type);
        model.addAttribute("genreClass", genreClass);
        model.addAttribute("sort",sort);
        model.addAttribute("ad",ad);
        return "result";
    }

    @GetMapping({"/read", "/modify"})
    public String read(long id, String genre,@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("id : " + id);

        MovieInfoDTO movieInfoDTO = movieListService.getMovie(id);
        model.addAttribute("result",movieListService.getGenreMovieList(requestDTO,genre));
        model.addAttribute("dto", movieInfoDTO);
        return "read";
    }
}
