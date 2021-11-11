package com.kwon770.mm.service;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.web.dto.MemberInfoDto;
import com.kwon770.mm.web.dto.MemberSearchDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    List<MemberInfoDto> membersToMemberInfoDtos(List<Member> members);

    default MemberInfoDto memberToMemberInfoDto(Member member) {
        return new MemberInfoDto(member);
    };

    MemberSearchDto memberToMemberSearchDto(Member member);

    List<MemberSearchDto> membersToMemberSearchDtos(List<Member> members);
}
