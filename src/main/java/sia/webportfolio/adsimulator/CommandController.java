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
            } else if (command.equalsIgnoreCase("Get-ADGroup")) {
                return Collections.singletonMap("output", AdCommandService.getAdGroups());
            } else if (command.equalsIgnoreCase("Get-ADOrganizationalUnit")) {
                return Collections.singletonMap("output", AdCommandService.getAdOrganizationalUnits());
            } else if (command.equalsIgnoreCase("Get-GPO")) {
                return Collections.singletonMap("output", AdCommandService.getGpos());
            }
            else if (command.equalsIgnoreCase("Get-ADDomainController")) {
                return Collections.singletonMap("output", AdCommandService.getAdDomainControllers());
            } else if (command.toLowerCase().startsWith("get-adgroupmember")) {
                String[] parts = command.split("\"");
                if (parts.length >= 2) {
                    String groupName = parts[1].trim();
                    return Collections.singletonMap("output", AdCommandService.getAdGroupMembers(groupName));
                } else {
                    return Collections.singletonMap("output", "<span class='ps-red'>Missing -Identity parameter.</span>");
                }
            }
        }

        return Collections.singletonMap("output",
                "<span class='ps-red'>'" + escape(command) + "' is not recognized as a valid cmdlet.</span>");
    }
}
