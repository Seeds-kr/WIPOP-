package com.example.movie.repository;

import com.example.movie.Entity.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
//현재 사용 안함
@Repository // 첫번째 인자 : 어떤 Entity인지, 두번째 인자 : pk 어떤 타입인지
public interface MovieRepository extends JpaRepository<MovieEntity, Long>
{
    @EntityGraph(value = "Movies.withGenres", type= EntityGraph.EntityGraphType.FETCH)
    Page<MovieEntity> findAll(Pageable pageable);

    @Query("select  m.id , m.movieName, m.rating from MovieEntity as m ")
    Page<Object[]> getListPage(Pageable pageable);
}
