package com.example.movie.Service;


import com.example.movie.Entity.Member;
import com.example.movie.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(Member member){
        memberRepository.save(member);
    }
}
