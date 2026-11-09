
package Models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesDao {
    
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    // Registrar venta principal
    public boolean registerSaleQuery(int customer_id, int employee_id, double total){
        String query = "INSERT INTO sales (customer_id, employee_id, total, sale_date) VALUES (?, ?, ?, ?)";
        
        Timestamp dateTime = new Timestamp(new Date().getTime());
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, customer_id);
            pst.setInt(2, employee_id);
            pst.setDouble(3, total);
            pst.setTimestamp(4, dateTime);
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
        
    // Registrar Detalle de venta (sales_details)
    public boolean registerSaleDetailQuery(int product_id, int sale_id, int sale_quantity, double sale_price, double sale_subtotal) {
    String query = "INSERT INTO sale_details (product_id, sale_id, sale_quantity, sale_price, sale_subtotal) VALUES (?, ?, ?, ?, ?)";
    
    try{
        conn = cn.getConnection();
        pst = conn.prepareStatement(query);
        pst.setInt(1, product_id);
        pst.setInt(2, sale_id);
        pst.setInt(3, sale_quantity);
        pst.setDouble(4, sale_price);
        pst.setDouble(5, sale_subtotal);
        pst.execute();
        return true;
    } catch(SQLException e) {
        System.out.println(e.toString());
        return false;
    }
}
        
        public int saleid(){
            int id = 0;
            String query = "SELECT MEX(id) AS id FROM sales";
            try{
                
                conn = cn.getConnection();
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
                if(rs.next()){
                    id = rs.getInt("id");
                }
                    
                }catch(SQLException e){
                     System.err.println(e.getMessage());
                        }
                return id;
            
        }
        
        public List listAllSalesQuery(){
           List <Sales> list_sales = new ArrayList();
            String query = "SELECT s.id AS invoice, \n" +
"       e.full_name AS employee, \n" +
"       c.full_name AS customer, \n" +
"       s.total, \n" +
"       s.sale_date \n" +
"FROM sales s \n" +
"INNER JOIN customers c ON s.customer_id = c.id \n" +
"INNER JOIN employees e ON s.employee_id = e.id \n" +
"ORDER BY s.id ASC";
            
            try{
                
                conn = cn.getConnection();
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
                while(rs.next()){
                    
                    Sales sale = new Sales();
                    sale.setid(rs.getInt("invoice"));
                    sale.setcustomer_name(rs.getString("customer"));
                    sale.setemployee_name(rs.getString("employee"));
                    sale.settotal_to_play(rs.getDouble("total"));
                    sale.setsale_date(rs.getString("sale_date"));
                    list_sales.add(sale);
                    
                }
                        
                        }
                catch(SQLException e){
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    
            }
            return list_sales;
            
            
        
        
    }
}
