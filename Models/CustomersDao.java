package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class CustomersDao {
    //Instanciar Conexion
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    //Registrar Cliente
    public boolean registerCustomersQuery(Customers customer){
     
        String query = "INSERT INTO customers (id, full_name, address, telephone, email, created, `update`) VALUES (?,?,?,?,?,?,?)";
        
        Timestamp dateTime = new Timestamp(new Date().getTime());
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, customer.getid());
            pst.setString(2, customer.getfull_name());
            pst.setString(3, customer.getaddress());
            pst.setString(4, customer.gettelephone());
            pst.setString(5, customer.getemail());
            pst.setTimestamp(6, dateTime);
            pst.setTimestamp(7, dateTime);
            
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al registrar el cliente " + e);
            return false;
        }
    }
        
    //Listar Clientes 
    public List<Customers> lisCustomersQuery(String value){
        List<Customers> list_customers = new ArrayList<>();
        
        String query = "SELECT * FROM customers";
        String query_search_customer = "SELECT * FROM customers WHERE id LIKE '%" + value + "%'";
        
        try{
            conn = cn.getConnection();
            if(value.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_customer);
                rs = pst.executeQuery();
            }
        
            while(rs.next()){
                Customers customer = new Customers();
                customer.setid(rs.getInt("id"));
                customer.setfull_name(rs.getString("full_name"));
                customer.setaddress(rs.getString("address"));
                customer.settelephone(rs.getString("telephone"));
                customer.setemail(rs.getString("email"));
                
                list_customers.add(customer);
            }
            
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString()); 
        }
        return list_customers;
    }
    
    // Modificar Cliente
    public boolean updateCustomersQuery(Customers customer){
        String query = "UPDATE customers SET full_name = ?, address = ?, telephone = ?, "
                + "email = ?, `update` = ? WHERE id = ?";
        
        Timestamp dateTime = new Timestamp(new Date().getTime());
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setString(1, customer.getfull_name());
            pst.setString(2, customer.getaddress());
            pst.setString(3, customer.gettelephone());
            pst.setString(4, customer.getemail());
            pst.setTimestamp(5, dateTime);
            pst.setInt(6, customer.getid());
            
            pst.execute();
            return true; 
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al modificar datos del cliente " + e);
            return false;
        }
    } 
    
    // Eliminar Cliente
    public boolean deleteCustomersQuery(int id){
        String query = "DELETE FROM customers WHERE id = ?";
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "No se puede eliminar un cliente "
             + "que tenga relacion con otra tabla " + e);
            return false;
        }
    }
    
    public Customers searchCustomer(int id){
        String query = "SELECT cu.id, cu.full_name FROM customers cu WHERE cu.id = ?";
        Customers customer = new Customers();
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                customer.setid(rs.getInt("id"));
                customer.setfull_name(rs.getString("full_name"));
            }

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return customer;
    }
}