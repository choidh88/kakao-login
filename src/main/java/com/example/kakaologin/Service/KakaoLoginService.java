package com.example.kakaologin.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.kakaologin.DTO.KakaoIdDto;
import com.example.kakaologin.DTO.KakaoTokenDto;

@Service
public class KakaoLoginService {
    private final String CLIENT_ID;
    private final String CLIENT_SECRET;

    @Autowired
    private RestTemplate restTemplate;

    KakaoLoginService(
            @Value("${kakao.client-id}") String CLIENT_ID,
            @Value("${kakao.client-secret}") String CLIENT_SECRET) {
        this.CLIENT_ID = CLIENT_ID;
        this.CLIENT_SECRET = CLIENT_SECRET;
    }

    private <T> T fetch(String url, HttpMethod method, HttpHeaders headers,
            MultiValueMap<String, String> body, Class<T> t) {
        // entity
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        // fetch
        ResponseEntity<T> response = restTemplate.exchange(url, method, entity, t);
        return response.getBody();
    }

    private <T> T fetchWithAuthHeaders(
            String url, HttpMethod method, String token, MultiValueMap<String, String> body, Class<T> t) {
        // header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(token);

        // fetch
        return fetch(url, method, headers, body, t);
    }

    public String login() {
        return UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com/oauth/authorize")
                .queryParam("client_id", CLIENT_ID)
                .queryParam("redirect_uri", "http://localhost:8080/redirect")
                .queryParam("response_type", "code")
                .build()
                .toUriString();
    }

    public KakaoTokenDto getToken(String code) {
        String url = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", CLIENT_ID);
        body.add("client_secret", CLIENT_SECRET);
        body.add("code", code);

        KakaoTokenDto token = fetch(url, HttpMethod.POST, headers, body, KakaoTokenDto.class);
        return token;
    }

    public KakaoIdDto requestUser(String kakaoAccessToken) {
        String url = "https://kapi.kakao.com/v2/user/me";
        return fetchWithAuthHeaders(url, HttpMethod.GET, kakaoAccessToken, null, KakaoIdDto.class);
    }
}
