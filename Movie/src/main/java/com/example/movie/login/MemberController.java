package com.example.movie.login;

import com.example.movie.Entity.Member;
import com.example.movie.Service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String addForm(@ModelAttribute Member member){
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute Member member,
                       BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "members/addMemberForm";
        }
        if(memberService.findMemberByLoginId(member.getMemberId()) != null){
            bindingResult.rejectValue("memberId", "error.member", "이미 사용 중인 아이디입니다.");
            return "members/addMemberForm";
        }

        memberService.save(member);
        return "redirect:/";
    }
    @GetMapping("/myPage")
    public String myPage(Model model, HttpSession session){

        Member findMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(findMember == null){
            return "redirect:/login";
        }

        Member member = memberService.findMemberById(findMember.getId());
        model.addAttribute("member", member);
        return "myPage";
    }
}
