package com.example.movie.Service;

import com.example.movie.Entity.Member;
import com.example.movie.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId, String password){
        return memberRepository.findMemberByMemberId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

}
