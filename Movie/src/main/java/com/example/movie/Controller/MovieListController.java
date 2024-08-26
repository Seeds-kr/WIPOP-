package com.example.movie.Controller;

import com.example.movie.Service.MovieListService;
import com.example.movie.dto.MovieInfoDTO;
import com.example.movie.dto.PageRequestDTO;
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
    @GetMapping("list")
    public String list(PageRequestDTO pageRequestDTO, Model model){
        log.info("pageRequestDTO: "+ pageRequestDTO);
        model.addAttribute("result", movieListService.getList(pageRequestDTO));
        return "List";
    }

    @GetMapping("list/result")
    public String result(PageRequestDTO pageRequestDTO,String keyword, Model model){
        log.info("pageRequestDTO: "+ pageRequestDTO);
        model.addAttribute("result", movieListService.getResult(pageRequestDTO,keyword));
        model.addAttribute("keyword", keyword);
        return "result";
    }

    @GetMapping({"/read", "/modify"})
    public String read(long id, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("id : " + id);

        MovieInfoDTO movieInfoDTO = movieListService.getMovie(id);

        model.addAttribute("dto", movieInfoDTO);
        return "read";
    }
}
