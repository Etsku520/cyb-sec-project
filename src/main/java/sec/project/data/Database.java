
package sec.project.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    String dbAdress;

    public Database(String dbAdress) {
        this.dbAdress = dbAdress;
    }
    
    public Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(dbAdress);
        
        try {
            PreparedStatement stmtCreate = conn.prepareStatement("CREATE TABLE Signup (id integer PRIMARY KEY, name varchar(200), address varchar(300))");
            stmtCreate.executeUpdate();
            stmtCreate.close();
        } catch (Exception e) {
            
        }
        
        return conn;
    }
}
