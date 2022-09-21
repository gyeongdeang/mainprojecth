package com.example.mainprojecth.sequrity;

import com.example.mainprojecth.advice.BusinessLogicException;
import com.example.mainprojecth.advice.ExceptionCode;
import com.example.mainprojecth.members.Member;
import com.example.mainprojecth.members.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//로그인처리도와
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member memberEntity = memberRepository.findByEmail(username).get();
        if (memberEntity != null) {
            return new PrincipalDetails(memberEntity);
        }
        return null;
    }
}
//
//    @Override
//    public PrincipalDetails loadUserByUsername(String email ) throws UsernameNotFoundException {
//
//       return memberRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException((email)));
//
//    }

//      @Override
//        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//            Optional<Member> optionalMember = memberRepository.findByEmail(username);
//            Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
//
//            // (4)
//            Collection<? extends GrantedAuthority> authorities = authorityUtils.createAuthorities(findMember.getEmail());
//
//            // (5)
//            return new User(findMember.getEmail(), findMember.getPassword(), authorities);
//        }
// }
