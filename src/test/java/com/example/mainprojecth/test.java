package com.example.mainprojecth;

import com.example.mainprojecth.members.Member;
import com.example.mainprojecth.members.MemberDto;
import com.example.mainprojecth.members.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
public class test {


    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember() {
        MemberDto.Post memberFormDto = MemberDto.Post.builder()
                .email("test@email.com")
                .name("테스트")
                .password("1111")
                .build();
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest() {
        Member member = createMember();
        Member savedMember = memberService.createMember(member);

        assertEquals(member.getEmail(), savedMember.getEmail());
    }
}