package pl.storeez.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    public final static String NOTIFICATION_ATTR = "notification";

    @GetMapping("/admin")
    public String getAdminPanel() {
        return "admin/admin";
    }
}
