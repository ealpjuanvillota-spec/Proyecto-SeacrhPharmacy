package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProductsDao {
    
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    // Registrar Productos
    public boolean registerProductsQuery(Products products){
        String query = "INSERT INTO products (code, name, description, unit_price, created, `update`, "
                + "category_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        Timestamp dateTime = new Timestamp(new Date().getTime());
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setInt(1, products.getcode());
            pst.setString(2, products.getname());
            pst.setString(3, products.getdescription());
            pst.setDouble(4, products.getunit_price());
            pst.setTimestamp(5, dateTime);
            pst.setTimestamp(6, dateTime);
            pst.setInt(7, products.getcategory_id()); 
            
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al registrar al producto " + e);
            return false;
        }
    }
        
    // Listar Productos
    public List<Products> listProductsQuery(String value){
        List<Products> list_products = new ArrayList<>();
        
        String query = "SELECT pro.*, ca.name AS category_name FROM products pro INNER JOIN categories ca ON pro.category_id = ca.id";
        String query_search_products = "SELECT pro.*, ca.name AS category_name FROM products pro INNER JOIN categories ca ON pro.category_id = ca.id WHERE pro.name LIKE '%" + value + "%'";
        
        try{
            conn = cn.getConnection();
            if(value.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_products);
                rs = pst.executeQuery();
            }
            
            while(rs.next()){
                Products products = new Products();
                
                products.setid(rs.getInt("id"));
                products.setcode(rs.getInt("code"));
                products.setname(rs.getString("name"));
                products.setdescription(rs.getString("description"));
                products.setunit_price(rs.getDouble("unit_price"));
                products.setproduct_quantity(rs.getInt("product_quantity"));
                products.setcategory_name(rs.getString("category_name"));
                
                list_products.add(products);
            }
            
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_products;
    }
    
    // Modificar Producto
    public boolean updateProductsQuery(Products products){
        String query = "UPDATE products SET code = ?, name = ?, description = ?, "
                + "unit_price = ?, `update` = ?, category_id = ? WHERE id = ?";
    
        Timestamp dateTime = new Timestamp(new Date().getTime());
    
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setInt(1, products.getcode());
            pst.setString(2, products.getname());
            pst.setString(3, products.getdescription());
            pst.setDouble(4, products.getunit_price());
            pst.setTimestamp(5, dateTime);
            pst.setInt(6, products.getcategory_id());
            pst.setInt(7, products.getid());
            
            pst.execute();
            return true;
           
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al modificar el producto " + e);
            return false;
        }
    }
    
    // Eliminar Producto
    public boolean deleteProductsQuery(int id){
        String query = "DELETE FROM products WHERE id = ?";
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "No se puede eliminar el producto si tiene relación con otra tabla " + e);
            return false;
        }
    }
        
    // Buscar Producto
    public Products searchProductsQuery(int id){
        String query = "SELECT pro.*, ca.name AS category_name FROM products pro INNER JOIN categories ca ON pro.category_id = ca.id WHERE pro.id = ?";
        
        Products products = new Products();
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            
            if(rs.next()){
                products.setid(rs.getInt("id"));
                products.setcode(rs.getInt("code"));
                products.setname(rs.getString("name"));
                products.setdescription(rs.getString("description"));
                products.setunit_price(rs.getDouble("unit_price"));
                products.setcategory_id(rs.getInt("category_id"));
                products.setcategory_name(rs.getString("category_name"));
            }
            
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return products;
    }
    
    // Buscar producto por codigo
    public Products searchcode(int code){
        String query = "SELECT pro.id, pro.name, pro.unit_price, pro.product_quantity FROM products pro WHERE pro.code = ?";
        Products products = new Products();
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setInt(1, code);
            rs = pst.executeQuery();
            if(rs.next()){
                products.setid(rs.getInt("id"));
                products.setname(rs.getString("name"));
                products.setunit_price(rs.getDouble("unit_price"));
                products.setproduct_quantity(rs.getInt("product_quantity"));
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return products;
    }
   
    // Traer Cantidad de productos
    public Products searchid(int id){
        String query = "SELECT pro.product_quantity FROM products pro WHERE pro.id = ?";
        Products products = new Products();
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                products.setproduct_quantity(rs.getInt("product_quantity"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return products;
    }

    // Actualizar stock
    public boolean updateStockQuery(int amount, int product_id){
        String query = "UPDATE products SET product_quantity = ? WHERE id = ?";
        try{
           conn = cn.getConnection();
           pst = conn.prepareStatement(query);
           pst.setInt(1, amount);
           pst.setInt(2, product_id);
           pst.execute();
           return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
}