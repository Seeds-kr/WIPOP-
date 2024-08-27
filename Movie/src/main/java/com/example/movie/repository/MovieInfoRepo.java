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

    @Query("select  m.id , m.movieName, m.movieNameK, m.genres, m.movieInfo, m.posterURL, m.releaseDate, m.rating from MovieInfoEntity as m where m.genres LIKE %:genre%")
    Page<Object[]> getMovieWithGenre(Pageable pageable,String genre);

    @Query("select  m.id , m.movieName, m.movieNameK, m.genres, m.movieInfo, m.posterURL, m.releaseDate, m.rating from MovieInfoEntity as m where m.movieName LIKE %:name% or m.movieNameK Like %:name%")
    Page<Object[]> getMovieWithName(Pageable pageable,String name);

    @Query("select  m.id , m.movieName, m.movieNameK, m.genres, m.movieInfo, m.posterURL, m.releaseDate, m.rating from MovieInfoEntity as m where m.movieInfo LIKE %:info%")
    Page<Object[]> getMovieWithInfo(Pageable pageable,String info);

    @Query("select  m.id , m.movieName, m.movieNameK, m.genres, m.movieInfo, m.posterURL, m.releaseDate, m.rating from MovieInfoEntity as m where m.releaseDate LIKE %:date%")
    Page<Object[]> getMovieWithDate(Pageable pageable,String date);

    @Query("select  m.id , m.movieName, m.movieNameK, m.genres, m.movieInfo, m.posterURL, m.releaseDate, m.rating from MovieInfoEntity as m where m.rating >= :Rate")
    Page<Object[]> getMovieWithRate(Pageable pageable,double Rate);

}
