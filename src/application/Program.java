
package application;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

import db.DB;
import db.DbException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Roberto
 */
public class Program {
    
    public static void main(String[] args){
        
        Connection conn = null;
        Statement st = null;
        try {
            conn = DB.getConnection();
            
            conn.setAutoCommit(false);
            
            st = conn.createStatement();
            
            int rows1 = st.executeUpdate("update seller set BaseSalary = 2900 where DepartmentId = 1");
        
            /*int x = 1;
            if (x < 2){
                throw new SQLException("Fake Error");
            }*/
            
            int rows2 = st.executeUpdate("update seller set BaseSalary = 3900 where DepartmentId = 2");
            
            conn.commit();
            
            System.out.println("rows1 " + rows1);
            System.out.println("rows2 " + rows2);
        }
        catch (SQLException e){
            try {
                conn.rollback();
                throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
            } 
            catch (SQLException e1){
                throw new DbException("Error trying to rollback! Caused by: " + e1.getMessage());
            }
        }
        finally{
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }    
}
