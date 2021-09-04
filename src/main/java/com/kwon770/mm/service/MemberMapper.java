package com.kwon770.mm.service;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.web.dto.MemberInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    List<MemberInfoDto> membersToMemberInfoDtos(List<Member> members);

    MemberInfoDto memberToMemberInfoDto(Member member);
}
