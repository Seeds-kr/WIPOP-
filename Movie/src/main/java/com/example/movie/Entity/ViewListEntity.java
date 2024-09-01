package com.example.movie.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Viewlist")  // 테이블 이름을 "Viewlist"로 설정
public class ViewListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "viewlist_id")
    private Long id;  // 고유 식별자 (Primary Key)

    @Column(name = "viewlist_name", nullable = false, length = 100)
    private String viewListName;  // 플레이리스트 이름 (varchar)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;  // 사용자와 다대일 관계

    @ManyToMany
    @JoinTable(
            name = "viewlist_movies",  // 중간 테이블 이름
            joinColumns = @JoinColumn(name = "viewlist_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Set<MovieInfoEntity> movies = new HashSet<>();  // 영화와 다대다 관계

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;  // 생성 날짜

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // 생성자
    public ViewListEntity(String viewListName, Member member) {
        this.viewListName = viewListName;
        this.member = member;
    }

    public void addMovie(MovieInfoEntity movie) {
        this.movies.add(movie);
    }

    public void removeMovie(MovieInfoEntity movie) {
        this.movies.remove(movie);
    }
}
