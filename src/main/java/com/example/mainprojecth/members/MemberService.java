package com.example.mainprojecth.members;

import com.example.mainprojecth.advice.BusinessLogicException;
import com.example.mainprojecth.advice.ExceptionCode;
import com.example.mainprojecth.members.Until.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
@Transactional
//@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;

    private final CustomBeanUtils<Member> beanUtils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberService(MemberRepository memberRepository, CustomBeanUtils<Member> beanUtils, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.beanUtils = beanUtils;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Member createMember(Member member){
        verifyExistsEmail(member.getEmail());
       // String password = member.getPassword();
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRoles("ROLE_USER");
        return memberRepository.save(member);

    }

    /*
    * 회원 가입 등록할때 비밀번호를 암호화해서 저장
    * 이미 가입된 이메일일경우
    * */
    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) //
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }
//    private void validateDuplicateMember(Member member) {
//        Member findMember = memberRepository.findByEmail(member.getEmail());
//        if (findMember != null) {
//            throw new IllegalStateException("이미 가입된 회원입니다.");
//        }
//    }

//------------
//    //@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
//    public static Member updateMember(Member member) {
//        Member findMember = findVerifiedMember(member.getMemberId());
//
//        Optional.ofNullable(member.getName())
//                .ifPresent(name -> findMember.setName(name));
//        Optional.ofNullable(member.getPassword())
//                .ifPresent(password -> findMember.setPassword(password));
//
//        return memberRepository.save(findMember);
//    }

    //위처럼 작성하면 계속 수정해야하는게 생길때마다 늘어나고 가독성이 좋지않아 아래처럼 만든다.
    //member는 수정할것들/ findMember에 업데이트함

    public  Member updateMember(Member member){
        Member findMember = findVerifiedMember(member.getMemberId());
        Member updatingMember = beanUtils.copyNonNullProperties(member,findMember);
        return memberRepository.save(updatingMember);
    }

    public Member loginMember(Member member){
        Optional<Member> optionalMember = memberRepository.findByEmail(member.getEmail());
        Member findMember = optionalMember.orElseThrow(()->new BusinessLogicException(ExceptionCode.EMAIL_NOT_FOUND));
        if(!findMember.getPassword().equals(bCryptPasswordEncoder.encode(member.getPassword()))){
            throw new BusinessLogicException(ExceptionCode.PASSWORD_NOT_FOUND);
        }
        return findMember;
    }
    @Transactional(readOnly = true)
    public Member findVerifiedMember(long memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    @Transactional(readOnly = true)
    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }




    public void deleteMember(long memberId){
        Member findMember = findVerifiedMember(memberId);
        memberRepository.delete(findMember);
    }


}
