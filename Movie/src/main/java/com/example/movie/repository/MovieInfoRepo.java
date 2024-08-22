package com.example.movie.repository;

import com.example.movie.Entity.MovieInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 첫번째 인자 : 어떤 Entity인지, 두번째 인자 : pk 어떤 타입인지
public interface MovieInfoRepo extends JpaRepository<MovieInfoEntity, Long>
{
    @Query("select  m.id , m.movieName, m.movieNameK, m.genres, m.movieInfo, m.posterURL, m.releaseDate, m.rating from MovieInfoEntity as m")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select  m.id , m.movieName, m.movieNameK, m.genres, m.movieInfo, m.posterURL, m.releaseDate, m.rating from MovieInfoEntity as m where m.id = :id")
    List<Object[]> getMovieWithAll(Long id);
}
