package com.kwon770.mm.dto.member;

import com.kwon770.mm.domain.member.SocialTokenType;

public class MemberDto {
    public String generateDbEmail(String email, SocialTokenType socialTokenType) {
        String dbEmail = email;
        if (socialTokenType.equals(SocialTokenType.GOOGLE)) {
            dbEmail += "#G";
        } else if (socialTokenType.equals(SocialTokenType.APPLE)) {
            dbEmail += "#A";
        }

        return dbEmail;
    }

    public String parseEmailFromDbEmail(String email) {
        return email.split("#")[0];
    }
}
