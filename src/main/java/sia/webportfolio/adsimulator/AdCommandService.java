package sia.webportfolio.adsimulator;

import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class AdCommandService {

    // Truncates a string to a max length with ellipsis
    public static String truncate(String value, int maxLength) {
        if (value == null) return "";
        return value.length() > maxLength ? value.substring(0, maxLength - 3) + "..." : value;
    }

    public static String pad(String value, int width) {
        return String.format("%-" + width + "s", value != null ? value : "");
    }

    public static String padStyled(String value, int width) {
        return String.format("%-" + width + "s", value);
    }

    public static String padStyledOrNull(String value, int width) {
        if (value == null || value.isEmpty()) {
            return String.format("<span class='ps-gray'>%-" + width + "s</span>", "<null>");
        } else {
            return String.format("%-" + width + "s", value);
        }
    }

    public static String escape(String val) {
        return val.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

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

    public static String getNestedValue(Element ms, String name, String childTag) {
        NodeList nodes = ms.getElementsByTagName("Obj");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element el = (Element) nodes.item(i);
            if (el.getAttribute("N").equals(name)) {
                NodeList children = el.getElementsByTagName(childTag);
                if (children.getLength() > 0) {
                    return children.item(0).getTextContent();
                }
            }
        }
        return null;
    }

    public static String getAdUsers() {
        try {
            InputStream is = new ClassPathResource("data/users.xml").getInputStream();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
            doc.getDocumentElement().normalize();

            NodeList userNodes = doc.getElementsByTagName("Obj");
            StringBuilder sb = new StringBuilder();

            int nameW = 20, samW = 20, displayW = 25, emailW = 30, titleW = 25;

            sb.append("<div><span class='ps-cyan'>")
                    .append(pad("Name", nameW))
                    .append(pad("SamAccountName", samW))
                    .append(pad("DisplayName", displayW))
                    .append(pad("EmailAddress", emailW))
                    .append(pad("Title", titleW))
                    .append("</span></div>");
            sb.append("<div>").append("-".repeat(nameW + samW + displayW + emailW + titleW)).append("</div>");

            for (int i = 0; i < userNodes.getLength(); i++) {
                Element obj = (Element) userNodes.item(i);
                Element ms = (Element) obj.getElementsByTagName("MS").item(0);

                String name = getValue(ms, "Name");
                String sam = getValue(ms, "SamAccountName");
                String display = getValue(ms, "DisplayName");
                String email = getValue(ms, "EmailAddress");
                String title = getValue(ms, "Title");

                sb.append("<div><span class='ps-white'>")
                        .append(padStyled(truncate(name, nameW), nameW))
                        .append(padStyledOrNull(truncate(sam, samW), samW))
                        .append(padStyledOrNull(truncate(display, displayW), displayW))
                        .append(padStyledOrNull(truncate(email, emailW), emailW))
                        .append(padStyledOrNull(truncate(title, titleW), titleW))
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

            int nameW = 25, osW = 40, ipW = 20, enabledW = 10, logonW = 30;

            sb.append("<div><span class='ps-cyan'>")
                    .append(pad("Name", nameW))
                    .append(pad("OperatingSystem", osW))
                    .append(pad("IPv4Address", ipW))
                    .append(pad("Enabled", enabledW))
                    .append(pad("LastLogonDate", logonW))
                    .append("</span></div>");
            sb.append("<div>").append("-".repeat(nameW + osW + ipW + enabledW + logonW)).append("</div>");

            for (int i = 0; i < computerNodes.getLength(); i++) {
                Element obj = (Element) computerNodes.item(i);
                Element ms = (Element) obj.getElementsByTagName("MS").item(0);

                String name = getValue(ms, "Name");
                String os = getValue(ms, "OperatingSystem");
                String ip = getValue(ms, "IPv4Address");
                String enabled = getValue(ms, "Enabled");
                String lastLogon = getValue(ms, "LastLogonDate");

                sb.append("<div><span class='ps-white'>")
                        .append(padStyled(truncate(name, nameW), nameW))
                        .append(padStyledOrNull(truncate(os, osW), osW))
                        .append(padStyledOrNull(truncate(ip, ipW), ipW))
                        .append(padStyledOrNull(truncate(enabled, enabledW), enabledW))
                        .append(padStyledOrNull(truncate(lastLogon, logonW), logonW))
                        .append("</span></div>");
            }

            return sb.toString();

        } catch (Exception e) {
            return "<span class='ps-red'>Error reading AD computer data: " + escape(e.getMessage()) + "</span>";
        }
    }

    public static String getAdGroups() {
        try {
            InputStream is = new ClassPathResource("data/groups.xml").getInputStream();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
            doc.getDocumentElement().normalize();

            NodeList groupNodes = doc.getElementsByTagName("Obj");
            StringBuilder sb = new StringBuilder();

            int nameW = 30, samW = 30, scopeW = 20, descW = 80;

            sb.append("<div><span class='ps-cyan'>")
                    .append(pad("Name", nameW))
                    .append(pad("SamAccountName", samW))
                    .append(pad("GroupScope", scopeW))
                    .append(pad("Description", descW))
                    .append("</span></div>");
            sb.append("<div>").append("-".repeat(nameW + samW + scopeW + descW)).append("</div>");

            for (int i = 0; i < groupNodes.getLength(); i++) {
                Element obj = (Element) groupNodes.item(i);
                NodeList msList = obj.getElementsByTagName("MS");

                if (msList.getLength() == 0) continue;

                Element ms = (Element) msList.item(0);

                String name = getValue(ms, "Name");
                String sam = getValue(ms, "SamAccountName");
                String scope = getNestedValue(ms, "GroupScope", "ToString");
                String desc = getValue(ms, "Description");

                sb.append("<div><span class='ps-white'>")
                        .append(padStyled(truncate(name, nameW), nameW))
                        .append(padStyledOrNull(truncate(sam, samW), samW))
                        .append(padStyledOrNull(truncate(scope, scopeW), scopeW))
                        .append(padStyledOrNull(truncate(desc, descW), descW))
                        .append("</span></div>");
            }

            return sb.toString();

        } catch (Exception e) {
            return "<span class='ps-red'>Error reading AD group data: " + escape(e.getMessage()) + "</span>";
        }
    }

    public static String getAdOrganizationalUnits() {
        try {
            InputStream is = new ClassPathResource("data/ous.xml").getInputStream();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
            doc.getDocumentElement().normalize();

            NodeList ouNodes = doc.getElementsByTagName("Obj");
            StringBuilder sb = new StringBuilder();

            int nameW = 30, dnW = 60, guidW = 40;

            sb.append("<div><span class='ps-cyan'>")
                    .append(pad("Name", nameW))
                    .append(pad("DistinguishedName", dnW))
                    .append(pad("ObjectGUID", guidW))
                    .append("</span></div>");
            sb.append("<div>").append("-".repeat(nameW + dnW + guidW)).append("</div>");

            for (int i = 0; i < ouNodes.getLength(); i++) {
                Element obj = (Element) ouNodes.item(i);
                Element ms = (Element) obj.getElementsByTagName("MS").item(0);

                String name = getValue(ms, "Name");
                String dn = getValue(ms, "DistinguishedName");
                String guid = getValue(ms, "ObjectGUID");

                sb.append("<div><span class='ps-white'>")
                        .append(padStyled(truncate(name, nameW), nameW))
                        .append(padStyledOrNull(truncate(dn, dnW), dnW))
                        .append(padStyledOrNull(truncate(guid, guidW), guidW))
                        .append("</span></div>");
            }

            return sb.toString();

        } catch (Exception e) {
            return "<span class='ps-red'>Error reading OU data: " + escape(e.getMessage()) + "</span>";
        }
    }

    public static String getGpos() {
        try {
            InputStream is = new ClassPathResource("data/gpos.xml").getInputStream();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
            doc.getDocumentElement().normalize();

            NodeList gpoNodes = doc.getElementsByTagName("Obj");
            StringBuilder sb = new StringBuilder();

            int nameW = 30, idW = 40, statusW = 25, createdW = 25, modifiedW = 25;

            sb.append("<div><span class='ps-cyan'>")
                    .append(pad("DisplayName", nameW))
                    .append(pad("Id", idW))
                    .append(pad("GpoStatus", statusW))
                    .append(pad("CreationTime", createdW))
                    .append(pad("ModificationTime", modifiedW))
                    .append("</span></div>");
            sb.append("<div>").append("-".repeat(nameW + idW + statusW + createdW + modifiedW)).append("</div>");

            for (int i = 0; i < gpoNodes.getLength(); i++) {
                Element obj = (Element) gpoNodes.item(i);
                NodeList msList = obj.getElementsByTagName("MS");

                if (msList.getLength() == 0) continue;

                Element ms = (Element) msList.item(0);

                String name = getValue(ms, "DisplayName");
                String id = getValue(ms, "Id");
                String status = getNestedValue(ms, "GpoStatus", "ToString");
                String created = getValue(ms, "CreationTime");
                String modified = getValue(ms, "ModificationTime");

                sb.append("<div><span class='ps-white'>")
                        .append(padStyled(truncate(name, nameW), nameW))
                        .append(padStyledOrNull(truncate(id, idW), idW))
                        .append(padStyledOrNull(truncate(status, statusW), statusW))
                        .append(padStyledOrNull(truncate(created, createdW), createdW))
                        .append(padStyledOrNull(truncate(modified, modifiedW), modifiedW))
                        .append("</span></div>");
            }

            return sb.toString();

        } catch (Exception e) {
            return "<span class='ps-red'>Error reading GPO data: " + escape(e.getMessage()) + "</span>";
        }
    }

    public static String getAdDomainControllers() {
        try {
            InputStream is = new ClassPathResource("data/domaincontrollers.xml").getInputStream();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
            doc.getDocumentElement().normalize();

            NodeList nodes = doc.getElementsByTagName("Obj");
            StringBuilder sb = new StringBuilder();

            int nameW = 20, ipW = 20, siteW = 30, osW = 40, gcW = 10;

            sb.append("<div><span class='ps-cyan'>")
                    .append(pad("Name", nameW))
                    .append(pad("IPv4Address", ipW))
                    .append(pad("Site", siteW))
                    .append(pad("OperatingSystem", osW))
                    .append(pad("IsGC", gcW))
                    .append("</span></div>");
            sb.append("<div>").append("-".repeat(nameW + ipW + siteW + osW + gcW)).append("</div>");

            for (int i = 0; i < nodes.getLength(); i++) {
                Element obj = (Element) nodes.item(i);
                NodeList msList = obj.getElementsByTagName("MS");
                if (msList.getLength() == 0) continue;

                Element ms = (Element) msList.item(0);

                String name = getValue(ms, "Name");
                String ip = getValue(ms, "IPv4Address");
                String site = getValue(ms, "Site");
                String os = getValue(ms, "OperatingSystem");
                String gc = getValue(ms, "IsGlobalCatalog");

                sb.append("<div><span class='ps-white'>")
                        .append(padStyled(truncate(name, nameW), nameW))
                        .append(padStyledOrNull(truncate(ip, ipW), ipW))
                        .append(padStyledOrNull(truncate(site, siteW), siteW))
                        .append(padStyledOrNull(truncate(os, osW), osW))
                        .append(padStyledOrNull(truncate(gc, gcW), gcW))
                        .append("</span></div>");
            }

            return sb.toString();

        } catch (Exception e) {
            return "<span class='ps-red'>Error reading domain controller data: " + escape(e.getMessage()) + "</span>";
        }
    }

    public static String getAdGroupMembers(String groupName) {
        try {
            InputStream is = new ClassPathResource("data/groupmembers.xml").getInputStream();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
            doc.getDocumentElement().normalize();

            NodeList nodes = doc.getElementsByTagName("Obj");
            StringBuilder sb = new StringBuilder();

            int nameW = 25, typeW = 15;

            sb.append("<div><span class='ps-cyan'>")
                    .append(pad("MemberName", nameW))
                    .append(pad("MemberType", typeW))
                    .append("</span></div>");
            sb.append("<div>").append("-".repeat(nameW + typeW)).append("</div>");

            for (int i = 0; i < nodes.getLength(); i++) {
                Element obj = (Element) nodes.item(i);
                NodeList msList = obj.getElementsByTagName("MS");
                if (msList.getLength() == 0) continue;

                Element ms = (Element) msList.item(0);

                String group = getValue(ms, "GroupName");
                if (group == null || !group.equalsIgnoreCase(groupName)) continue;

                String member = getValue(ms, "MemberName");
                String type = getValue(ms, "MemberType");

                sb.append("<div><span class='ps-white'>")
                        .append(padStyled(truncate(member, nameW), nameW))
                        .append(padStyledOrNull(truncate(type, typeW), typeW))
                        .append("</span></div>");
            }

            return sb.toString();

        } catch (Exception e) {
            return "<span class='ps-red'>Error reading group member data: " + escape(e.getMessage()) + "</span>";
        }
    }
}
