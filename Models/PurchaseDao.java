package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDao {
    
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    // Registrar la compra 
    public boolean registerPurchaseQuery(int supplier_id, int employee_id, double total){
        String query = "INSERT INTO purchases (supplier_id, employee_id,total,created)"
                + "VALUES (?,?,?,?)";
        
        Timestamp dateTime = new Timestamp(new Date().getTime());
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, supplier_id);
            pst.setInt(2, employee_id);
            pst.setDouble(3, total);
            pst.setTimestamp(4, dateTime);
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al ingresar la compra " + e);
            return false;
        }
    }
      
    // Registrar detalles de la compra 
    public boolean registerPurchaseDetailQuery(int purchase_id, double purchase_price, int purchase_amount, double purchase_subtotal, int product_id){
        String query = "INSERT INTO purchase_details (purchase_id, purchase_price, purchase_amount, purchase_subtotal, product_id) VALUES (?, ?, ?, ?, ?)";
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, purchase_id);
            pst.setDouble(2, purchase_price);
            pst.setInt(3, purchase_amount);
            pst.setDouble(4, purchase_subtotal);
            pst.setInt(5, product_id);
            
            pst.execute();
            return true;
        
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al registrar detalles de la compra " + e);
            return false;
        }
    }
      
    // Obtener ID de la compra 
    public int purchaseid(){
        int id = 0;
        String query = "SELECT MAX(id) AS id FROM purchases";
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
      
    // Listar todas las compras realizadas
    public List listAllPurchaseQuery(){
        List<Purchase> list_purchases = new ArrayList();
        String query = "SELECT pu.*, su.name AS supplier_name FROM purchases pu " +
                       "INNER JOIN suppliers su ON pu.supplier_id = su.id ORDER BY pu.id ASC";
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            while(rs.next()){
                Purchase purchase = new Purchase();
                purchase.setid(rs.getInt("id"));
                purchase.setsupplier_name_product(rs.getString("supplier_name"));
                purchase.settotal(rs.getDouble("total"));
                purchase.setcreated(rs.getString("created"));
                list_purchases.add(purchase);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list_purchases;
    }
      
    // Listar compras para imprimir factura
    public List listPurchaseDetailQuery(int id){
        List<Purchase> list_purchase = new ArrayList();
        
        String query = "SELECT pu.created, pude.purchase_price, pude.purchase_amount, pude.purchase_subtotal, " +
                       "su.name AS supplier_name, pro.name AS product_name, em.full_name " +
                       "FROM purchases pu " +
                       "INNER JOIN purchase_details pude ON pu.id = pude.purchase_id " +
                       "INNER JOIN products pro ON pude.product_id = pro.id " +
                       "INNER JOIN suppliers su ON pu.supplier_id = su.id " +
                       "INNER JOIN employees em ON pu.employee_id = em.id " +
                       "WHERE pu.id = ?";
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id); 
            rs = pst.executeQuery();
            
            while(rs.next()){
                Purchase purchase = new Purchase();
                purchase.setproduct_name(rs.getString("product_name"));
                purchase.setpurchase_amount(rs.getInt("purchase_amount"));
                purchase.setpurchase_price(rs.getDouble("purchase_price"));
                purchase.setpurchase_subtotal(rs.getDouble("purchase_subtotal"));
                purchase.setsupplier_name_product(rs.getString("supplier_name")); 
                purchase.setcreated(rs.getString("created"));
                purchase.setpurchase(rs.getString("full_name"));
                
                list_purchase.add(purchase);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list_purchase;
    }
}
