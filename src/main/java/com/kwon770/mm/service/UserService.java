package com.kwon770.mm.service;

import com.kwon770.mm.domain.user.User;
import com.kwon770.mm.domain.user.UserRepository;
import com.kwon770.mm.provider.security.JwtAuthToken;
import com.kwon770.mm.provider.security.JwtAuthTokenProvider;
import com.kwon770.mm.web.dto.UserInfoDto;
import com.kwon770.mm.web.dto.UserSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final JwtAuthTokenProvider jwtAuthTokenProvider;
    private final UserRepository userRepository;

    public JwtAuthToken getAuthTokenByJwtToken(String jwtToken) {
        return jwtAuthTokenProvider.convertAuthToken(jwtToken);
    }

    public UserInfoDto getUserInfoDtoBySocialToken(String socialToken) {
        String email = getEmailBySocialTokenFromGoogle(socialToken);
        return getUserInfoDtoByEmail(email);
    }

    private String getEmailBySocialTokenFromGoogle(String socialToken) {
        try {
            URL url = new URL("https://www.googleapis.com/userinfo/v2/me");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + socialToken);

            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String responseBody = response.toString();
            return responseBody.split(",")[1].split("\"")[3];
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : 올바르지 않은 socialToken 입니다. socialToken=" + socialToken);
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public Long register(UserSaveDto userSaveDto) {
        return userRepository.save(userSaveDto.toEntity()).getId();
    }

    public JwtAuthToken createAuthToken(UserInfoDto userInfoDto) {
        return jwtAuthTokenProvider.createAuthToken(userInfoDto.getEmail(), userInfoDto.getRole().getCode());
    }

    public List<UserInfoDto> getUserInfoDtoList() {
        List<User> users = userRepository.findAll();

        return UserMapper.INSTANCE.usersToUserInfoDtos(users);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id가 일치하는 유저가 없습니다. id=" + id));
    }

    public UserInfoDto getUserInfoDtoById(Long id) {
        return UserMapper.INSTANCE.userToUserInfoDto(getUserById(id));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("email이 일치하는 유저가 없습니다. email=" + email));
    }

    public UserInfoDto getUserInfoDtoByEmail(String email) {
        return UserMapper.INSTANCE.userToUserInfoDto(getUserByEmail(email));
    }

    public void updateUserByEmail(String email, UserSaveDto userSaveDto) {
        User user = getUserByEmail(email);
        user.update(userSaveDto);
    }

    public void deleteUserById(Long id) {
        userRepository.delete(getUserById(id));
    }

    public void deleteUserByEmail(String email) {
        userRepository.delete(getUserByEmail(email));
    }
}
