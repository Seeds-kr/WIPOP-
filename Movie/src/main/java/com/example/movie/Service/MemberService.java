package com.example.movie.Service;

import com.example.movie.Entity.Member;
import com.example.movie.Entity.ViewListEntity;
import com.example.movie.repository.MemberRepository;
import com.example.movie.repository.ViewListRepository; // ViewListRepository 추가
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final ViewListRepository viewListRepository; // ViewListRepository 추가

    public void save(Member member){
        memberRepository.save(member);

        // 회원가입 시 ViewListEntity 생성 및 저장
        ViewListEntity viewList = new ViewListEntity(member.getUserName(), member);
        viewListRepository.save(viewList);
    }

    public Member findMemberById(Long id){
        Optional<Member> member = memberRepository.findById(id);
        return member.orElse(null);
    }

    public Member findMemberByLoginId(String loginId){
        Optional<Member> member = memberRepository.findMemberByMemberId(loginId);
        return member.orElse(null);
    }
}