package sia.webportfolio.controllers;

import org.springframework.stereotype.Controller;
import  org.springframework.web.bind.annotation.GetMapping;

@Controller
public class adProjectController {
    @GetMapping("/ad-project") public String adProject() {
        return "ad-project";
    }
}
