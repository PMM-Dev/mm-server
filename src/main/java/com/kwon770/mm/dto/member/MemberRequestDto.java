package com.kwon770.mm.dto.member;

import com.kwon770.mm.domain.member.Member;
import com.kwon770.mm.domain.member.Role;
import com.kwon770.mm.domain.member.SocialTokenType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class MemberRequestDto {

    private String name;
    private String email;
    private String picture;
    private Role role;
    private String socialToken;
    private SocialTokenType socialTokenType;

    @Builder
    public MemberRequestDto(String name, String email, String picture, Role role, String socialToken, SocialTokenType socialTokenType) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.socialToken = socialToken;
        this.socialTokenType = socialTokenType;
    }

    public void setAppleEntityValue(String email) {
        this.name = email.split("@")[0];
        this.email = email;

    }

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .name(name)
                .email(email)
                .encodedEmail(passwordEncoder.encode(email))
                .picture(picture)
                .role(role)
                .socialTokenType(socialTokenType)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, email);
    }
}
