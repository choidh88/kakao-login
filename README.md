# KAKAO LOGIN
카카오 OAuth 로그인을 자바 스프링으로 구현한 레포지토리입니다.<br>
개인적으로 카카오 OAuth 로그인을 사용하고 참고하기 위해 작성된 레포지토리로, [카카오 로그인 개발자 문서](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#before-you-begin-process) 내 있는 기능 중 필수적이라고 생각하는 기능만 구현했습니다.

## 🗂️ 파일 구조
```
./src/main/java/com/example/kakaologin/
├── Config
│   └── RestTemplateConfig.java
├── Controller
│   └── KakaoLoginController.java
├── DTO
│   ├── KakaoIdDto.java
│   └── KakaoTokenDto.java
├── KakaologinApplication.java
└── Service
    └── KakaoLoginService.java
```

* `RestTemplateConfig.java`: RestTemplate를 쓰기 위한 Config 파일입니다.
* `KakaoLoginController.java`: Controller입니다. 하단 페이지 문단을 참고하시길 바랍니다.
* `KakaoIdDto.java`: id와 접속일을 가져오기 위한 DTO를 설정합니다.
* `KakaoTokenDto.java`: 토큰을 비롯한 관련 정보를 가져오기 위한 DTO를 설정합니다.
* `KakaoLoginService.java`: 각종 서비스를 제공합니다.
* `KakaologinApplication.java`: 메인 함수가 들어있습니다.

## 📃 실행 가능한 페이지
* `/`: 메인 페이지로 `./src/main/resources/static/` 내의 `index.html`가 노출됩니다.
* `/login`: 로그인 과정 중 인가 코드를 요청하는 것을 담당합니다. ([참고](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#before-you-begin-process))
* `/redirect`: `/login`을 통해 리다이렉트되는 페이지로 토큰을 요청합니다. ([참고](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#before-you-begin-process))
* `/me`: 로그인 정보를 출력합니다. 하단 '💻 /me 응답'을 참고하시길 바랍니다.

### 💻 /me 응답

|키|데이터타입|설명|
|:---|:---|:---|
|id|Long|아이디|
|connectedAt|LocalDateTime|접속일자|

## 🏃‍♂️‍➡️ 실행 방법
실행 전 아래와 같이 `.env`를 추가하시고 설정하시길 바랍니다.<br>
Client id와 secret은 카카오 DEV 페이지의 `앱 > 앱 설정 > 앱 > 플랫폼 키`에서 `REST API 키`에 해당하는 키를 설정하여 가져오시면 됩니다.

```env
KAKAO_CLIENT_ID=(본인이 발급받은 CLIENT ID)
KAKAO_CLIENT_SECRET=(본인이 발급받은 CLIENT SECRET)
```

이후 아래와 같이 입력하여 스프링을 구동하시면 됩니다.

```bash
gradle bootRun
```

이제 `/`에 접속한 후 '카카오 로그인'이라 적힌 버튼을 누르고 카카오 로그인을 수행하시면 됩니다.

## ❗ 주의 및 고찰
* 본 레포지토리에서 사용된 `RestTemplate`는 곧 Deprecated될 수 있어 주의가 필요합니다.
* 이를 위해 다른 것으로 바꿔 구현하길 원합니다.
* 앞으로 DB와의 연결을 통해 세션 로그인과 JWT 로그인 방식 등 여러 로그인 방식을 구현하고 싶습니다.

## 📜 라이센스
본 레포지토리는 Unlicense 라이센스를 적용합니다.

## 🔗 참고 문헌
* [카카오 로그인 REST API 문서](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api)
* [카카오 로그인 Github 자료](https://github.com/kakao-tam/developers-java.spring.RestTemplate.git)