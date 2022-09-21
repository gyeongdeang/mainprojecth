package com.example.mainprojecth.members;

import com.example.mainprojecth.sequrity.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
//@RequiredArgsConstructor
@Validated
@Slf4j
public class AuthController {

    private final MemberService memberService;
    private  final MemberMapper memberMapper;
    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthController(MemberService memberService, MemberMapper memberMapper, MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberService = memberService;
        this.memberMapper = memberMapper;
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity postMember(@RequestBody @Valid MemberDto.Post post){

        Member member = memberMapper.memberPostToMember(post);
        memberService.createMember(member);
        System.out.println("Member Registration Successfully");
        return new ResponseEntity(HttpStatus.CREATED);
    }
    // 회원가입 (바로 DB로)
    @PostMapping("/signup")
    public String join(@RequestBody Member member) {
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRoles("ROLE_USER");
        memberRepository.save(member);
        return "회원 가입 완료";
    }

    @GetMapping("/loginTest")
    public @ResponseBody String loginTest(Authentication authentication) {
        System.out.println("============/loginTest===========");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication : " + principalDetails.getMember());
        return "세션 정보 확인";
    }

    @PostMapping("/token")
    public String token() {

        return "<h1>token</h1>";
    }

//
//    @GetMapping("/sign-form")
//    public String singForm(){
//        return "sing-in";
//    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity loginMember(@RequestBody @Valid MemberDto.Login login){
        Member findMember = memberService.findMember(login.getMemberId());
        if(bCryptPasswordEncoder.matches(login.getPassword(),findMember.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }
        return new ResponseEntity(HttpStatus.OK);

    }
}
