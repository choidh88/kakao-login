package com.example.kakaologin.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.example.kakaologin.DTO.KakaoIdDto;
import com.example.kakaologin.DTO.KakaoTokenDto;
import com.example.kakaologin.Service.KakaoLoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class KakaoLoginController {
    private final KakaoLoginService kakaoLoginService;

    KakaoLoginController(KakaoLoginService kakaoLoginService) {
        this.kakaoLoginService = kakaoLoginService;
    }

    @GetMapping("/")
    String index() {
        return "index.html";
    }

    @GetMapping("/login")
    RedirectView login() {
        String page = kakaoLoginService.login();
        return new RedirectView(page);
    }

    @GetMapping("/redirect")
    RedirectView redirected(@RequestParam String code, HttpServletRequest request) {
        KakaoTokenDto ktd = kakaoLoginService.getToken(code);

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60 * 60); // 60 sec/min * 60 min/hour = 1시간 당 초
        session.setAttribute("ktd", ktd);

        return new RedirectView("/");
    }

    @GetMapping("/me")
    @ResponseBody
    KakaoIdDto me(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return new KakaoIdDto();

        KakaoTokenDto ktd = (KakaoTokenDto) session.getAttribute("ktd");
        KakaoIdDto kid = kakaoLoginService.requestUser(ktd.getAccess_token());
        return kid;
    }
}
