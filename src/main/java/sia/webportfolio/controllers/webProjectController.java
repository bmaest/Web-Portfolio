package sia.webportfolio.controllers;

import org.springframework.stereotype.Controller;
import  org.springframework.web.bind.annotation.GetMapping;

@Controller
public class webProjectController {
    @GetMapping("/web-project") public String webProject() {
        return "web-project";
    }
}
