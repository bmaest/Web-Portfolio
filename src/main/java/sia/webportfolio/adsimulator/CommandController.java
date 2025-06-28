package sia.webportfolio.adsimulator;

import sia.webportfolio.adsimulator.AdCommandService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CommandController {
    private String escape(String val) {
        return val.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    @PostMapping("/command")
    public Map<String, String> executeCommand(@RequestBody Map<String, String> request) {
        String command = request.get("command");
        if (command != null) {
            command = command.trim();
            if (command.equalsIgnoreCase("Get-ADUser")) {
                return Collections.singletonMap("output", AdCommandService.getAdUsers());
            } else if (command.equalsIgnoreCase("Get-ADComputer")) {
                return Collections.singletonMap("output", AdCommandService.getAdComputers());
            }
        }

        return Collections.singletonMap("output",
                "<span class='ps-red'>'" + escape(command) + "' is not recognized as a valid cmdlet.</span>");
    }
}
