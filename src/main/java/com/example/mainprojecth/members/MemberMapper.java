package com.example.mainprojecth.members;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostToMember(MemberDto.Post requestBody);
    Member memberPatchToMember(MemberDto.Patch requestBody);
    Member memberLoginToMember(MemberDto.Login requestBody);
    MemberDto.Response memberToMemberResponse(Member member);

//    default MemberDto.Response memberToMemberResponse(Member member){
//        MemberDto.Response response = new MemberDto.Response();
//        response.setMemberId(member.getMemberId());
//        response.setEmail(member.getEmail());
//        response.setName(member.getName());
//        response.setCreatedAt(member.getCreatedAt());
//        response.setUpdateAt(member.getUpdateAt());
//    }

}
