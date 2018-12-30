package sec.project.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sec.project.data.Database;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class ListController {
    @Autowired
    private SignupRepository signupRepository;
    
    Database db = new Database("jdbc:sqlite:signups.db");
    
    @RequestMapping(value = "*", method = RequestMethod.GET)
    public String allSignups(Model model, Authentication authentication) throws SQLException {
        int admin = 1;
        Connection conn = db.getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Signup");
        ResultSet rs = stmt.executeQuery();
        
        List<Signup> signups = new ArrayList<>();
        
        while (rs.next()) {
            signups.add(new Signup(rs.getString("name"), rs.getString("address")));
        }
        
        
        if (authentication == null) {
            admin = 0;
        }
        
        model.addAttribute("signups", signups);
        model.addAttribute("admin", admin);
        
        stmt.close();
        rs.close();
        conn.close();
        return "allSignups";
    }
}
