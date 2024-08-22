package com.example.movie.repository;

import com.example.movie.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByMemberId(String memberId);
}
