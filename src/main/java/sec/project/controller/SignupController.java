package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.data.Database;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {
    
    Database db = new Database("jdbc:sqlite:signups.db");

    @Autowired
    private SignupRepository signupRepository;
    
    @Autowired
    private UserDetailsService userDetailsService;

//    @RequestMapping("*")
//    public String defaultMapping() {
//        return "redirect:/form";
//    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address) throws SQLException {
        Connection conn = db.getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO Signup (name, address) VALUES ('" + name + "', '" + address + "')");
        
        stmt.close();
        conn.close();
        
        signupRepository.save(new Signup(name, address));
        return "done";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage() {
        return "adminPage";
    }
}
