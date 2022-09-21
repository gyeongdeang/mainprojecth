package com.example.mainprojecth.members;

import com.example.mainprojecth.audit.Auditable;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Entity

public class Member extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;
    @Column(nullable = false, updatable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    private String roles;


    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public Member(String email) {
        this.email = email;
    }

    @Builder
    public Member(long memberId, String email, String name, String password, String roles) {

        this.email = email;
        this.name = name;
        this.password = password;

    }

    //junit 테스트를 위한

    public static Member createMember(MemberDto.Post post, PasswordEncoder passwordEncoder) {
        Member member = Member.builder()
                .name(post.getName())
                .email(post.getEmail())
                .password(passwordEncoder.encode(post.getPassword()))  //암호화처리

                .build();
        return member;
    }
}


