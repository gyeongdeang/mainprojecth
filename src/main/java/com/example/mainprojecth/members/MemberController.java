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
import javax.validation.constraints.Positive;
import java.sql.SQLOutput;

@RestController
@RequestMapping("/members")
//@RequiredArgsConstructor
@Validated
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private  final MemberMapper memberMapper;

    public MemberController(MemberService memberService, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.memberMapper = memberMapper;
    }

    @PatchMapping("/me/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId, @RequestBody MemberDto.Patch patch){
        patch.setMemberId(memberId);

        Member member = memberService.updateMember(memberMapper.memberPatchToMember(patch));
//        Member member = memberMapper.memberPatchToMember(patch);
//        Member updateMember = MemberService.updateMember(member);


        return new ResponseEntity<>(memberMapper.memberToMemberResponse(member),HttpStatus.OK);

    }

    @GetMapping("/me/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId){
        Member member = memberService.findMember(memberId);
        return new ResponseEntity<>(memberMapper.memberToMemberResponse(member),HttpStatus.OK);

    }



    @DeleteMapping("/me/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId){
        memberService.deleteMember(memberId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

}
