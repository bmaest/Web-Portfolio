package sia.webportfolio.adsimulator;

import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class AdCommandService {

    public static String getAdUsers() {
        try {
            InputStream is = new ClassPathResource("data/users.xml").getInputStream();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
            doc.getDocumentElement().normalize();

            NodeList userNodes = doc.getElementsByTagName("Obj");
            StringBuilder sb = new StringBuilder();

            sb.append("<div><span class='ps-cyan'>")
                    .append(pad("Name", 20))
                    .append(pad("SamAccountName", 20))
                    .append(pad("DisplayName", 25))
                    .append(pad("EmailAddress", 30))
                    .append(pad("Title", 25))
                    .append("</span></div>");
            sb.append("<div>").append("-".repeat(120)).append("</div>");

            for (int i = 0; i < userNodes.getLength(); i++) {
                Element obj = (Element) userNodes.item(i);
                Element ms = (Element) obj.getElementsByTagName("MS").item(0);

                String name = getValue(ms, "Name");
                String sam = getValue(ms, "SamAccountName");
                String display = getValue(ms, "DisplayName");
                String email = getValue(ms, "EmailAddress");
                String title = getValue(ms, "Title");

                sb.append("<div><span class='ps-white'>")
                        .append(padStyled(name, 20))
                        .append(padStyledOrNull(sam, 20))
                        .append(padStyledOrNull(display, 25))
                        .append(padStyledOrNull(email, 30))
                        .append(padStyledOrNull(title, 25))
                        .append("</span></div>");
            }

            return sb.toString();

        } catch (Exception e) {
            return "<span class='ps-red'>Error reading AD user data: " + escape(e.getMessage()) + "</span>";
        }
    }

    public static String getAdComputers() {
        try {
            InputStream is = new ClassPathResource("data/computers.xml").getInputStream();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
            doc.getDocumentElement().normalize();

            NodeList computerNodes = doc.getElementsByTagName("Obj");
            StringBuilder sb = new StringBuilder();

            sb.append("<div><span class='ps-cyan'>")
                    .append(pad("Name", 25))
                    .append(pad("OperatingSystem", 40))
                    .append(pad("IPv4Address", 20))
                    .append(pad("Enabled", 10))
                    .append(pad("LastLogonDate", 30))
                    .append("</span></div>");
            sb.append("<div>").append("-".repeat(125)).append("</div>");

            for (int i = 0; i < computerNodes.getLength(); i++) {
                Element obj = (Element) computerNodes.item(i);
                Element ms = (Element) obj.getElementsByTagName("MS").item(0);

                String name = getValue(ms, "Name");
                String os = getValue(ms, "OperatingSystem");
                String ip = getValue(ms, "IPv4Address");
                String enabled = getValue(ms, "Enabled");
                String lastLogon = getValue(ms, "LastLogonDate");

                sb.append("<div><span class='ps-white'>")
                        .append(padStyled(name, 25))
                        .append(padStyledOrNull(os, 40))
                        .append(padStyledOrNull(ip, 20))
                        .append(padStyledOrNull(enabled, 10))
                        .append(padStyledOrNull(lastLogon, 30))
                        .append("</span></div>");
            }

            return sb.toString();

        } catch (Exception e) {
            return "<span class='ps-red'>Error reading AD computer data: " + escape(e.getMessage()) + "</span>";
        }
    }

    // Pads a string to a fixed width
    public static String pad(String value, int width) {
        return String.format("%-" + width + "s", value != null ? value : "");
    }

    // Pads and styles a non-null value
    public static String padStyled(String value, int width) {
        return String.format("%-" + width + "s", value);
    }

    // Pads and styles a value, or renders <null> in gray
    public static String padStyledOrNull(String value, int width) {
        if (value == null || value.isEmpty()) {
            return String.format("<span class='ps-gray'>%-" + width + "s</span>", "<null>");
        } else {
            return String.format("%-" + width + "s", value);
        }
    }

    // Extracts a value from an XML element by attribute name
    public static String getValue(Element ms, String name) {
        NodeList nodes = ms.getElementsByTagName("*");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element el = (Element) nodes.item(i);
            if (el.getAttribute("N").equals(name)) {
                return el.getTextContent();
            }
        }
        return null;
    }

    // Escapes HTML characters for safe output
    public static String escape(String val) {
        return val.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
