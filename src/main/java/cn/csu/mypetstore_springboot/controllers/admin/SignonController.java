package cn.csu.mypetstore_springboot.controllers.admin;

import cn.csu.mypetstore_springboot.Services.VerificationCodeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class SignonController {
    private final VerificationCodeService verificationCodeService;

    public SignonController(VerificationCodeService verificationCodeService) {
        this.verificationCodeService = verificationCodeService;
    }


    @RequestMapping("/signon/form")
    public String SignonForm() {
        return "/admin/SignonForm";
    }

    @RequestMapping("/signon/v-code")
    public ResponseEntity<byte[]> getVCode(HttpSession session) {
        // For demonstration purposes, let's assume you have generated a byte array representing the image
        String code = verificationCodeService.randGenerateCode();
        session.setAttribute("v-code", code);
        byte[] imageBytes = verificationCodeService.getImageBytes(code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Set appropriate content type

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

}
