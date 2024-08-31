package com.example.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@Data
@AllArgsConstructor
//리스트 페이지 요청
public class PageRequestDTO<DTO, EN> {
    private int page;
    private int size;
    private String type;
    private String keyword;

    public PageRequestDTO(){ //초기값
        this.page = 1;
        this.size = 12;
    }

    public Pageable getPageable(Sort sort){
        //이 메소드가 pageRequestDTO의 진짜목적. JPA쪽에서 사용하는 Pageable 타입의 객체를 생성
        //sort만 별도의 파라미터로 받는 이유: 나중에 다양한 상황에서 사용하기 위해
        return PageRequest.of(page -1, size, sort);
    }

}
