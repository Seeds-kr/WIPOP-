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
    @Query("select  m.id ,m.movieId, m.movieName, m.movieNameK, m.genres, m.movieInfo, m.posterURL, m.releaseDate, m.rating " +
            "from MovieInfoEntity as m")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select  m.id ,m.movieId, m.movieName, m.movieNameK, m.genres, m.movieInfo, m.posterURL, m.releaseDate, m.rating " +
            "from MovieInfoEntity as m where m.id = :id")
    List<Object[]> getMovieWithAll(Long id);

    @Query("select  m.id ,m.movieId, m.movieName, m.movieNameK, m.genres, m.movieInfo, m.posterURL, m.releaseDate, m.rating " +
            "from MovieInfoEntity as m where m.genres LIKE %:genre% order by RAND()")
    Page<Object[]> getListWithGenre(Pageable pageable,String genre);

    @Query("select  m.id ,m.movieId, m.movieName, m.movieNameK, m.genres, m.movieInfo, m.posterURL, m.releaseDate, m.rating " +
            "from MovieInfoEntity as m where m.genres LIKE %:genre% and m.genres Like %:genreClass%")
    Page<Object[]> getMovieWithGenre(Pageable pageable,String genre,String genreClass);

    @Query("select  m.id ,m.movieId, m.movieName, m.movieNameK, m.genres, m.movieInfo, m.posterURL, m.releaseDate, m.rating " +
            "from MovieInfoEntity as m where (m.movieName LIKE %:name% or m.movieNameK Like %:name%) and m.genres Like %:genreClass%")
    Page<Object[]> getMovieWithName(Pageable pageable,String name,String genreClass);

    @Query("select  m.id ,m.movieId, m.movieName, m.movieNameK, m.genres, m.movieInfo, m.posterURL, m.releaseDate, m.rating " +
            "from MovieInfoEntity as m where m.movieInfo LIKE %:info% and m.genres Like %:genreClass%")
    Page<Object[]> getMovieWithInfo(Pageable pageable,String info,String genreClass);

    @Query("select  m.id ,m.movieId, m.movieName, m.movieNameK, m.genres, m.movieInfo, m.posterURL, m.releaseDate, m.rating " +
            "from MovieInfoEntity as m where m.releaseDate LIKE %:date% and m.genres Like %:genreClass%")
    Page<Object[]> getMovieWithDate(Pageable pageable,String date,String genreClass);

    @Query("select  m.id ,m.movieId, m.movieName, m.movieNameK, m.genres, m.movieInfo, m.posterURL, m.releaseDate, m.rating " +
            "from MovieInfoEntity as m where m.rating >= :Rate and m.genres Like %:genreClass%")
    Page<Object[]> getMovieWithRate(Pageable pageable,double Rate,String genreClass);

}
