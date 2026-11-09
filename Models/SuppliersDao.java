package Models;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class SuppliersDao {
    // Instanciar Conexión
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    // Registrar Proveedor
    public boolean registerSuppliersQuery(Suppliers suppliers) {
        String query = "INSERT INTO suppliers (name, descripcion, address, telephone, email, "
                   + "city, created, `update`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, suppliers.getname());
            pst.setString(2, suppliers.getdescription());
            pst.setString(3, suppliers.getaddress());
            pst.setString(4, suppliers.gettelephone());
            pst.setString(5, suppliers.getemail());
            pst.setString(6, suppliers.getcity());
            pst.setTimestamp(7, dateTime);
            pst.setTimestamp(8, dateTime);

            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar proveedor " + e);
            return false;
        }
    }

    // Listar Proveedores
    public List<Suppliers> listSuppliersQuery(String value) {
        List<Suppliers> list_suppliers = new ArrayList<>();
        String query = "SELECT * FROM suppliers";
        String query_search_suppliers = "SELECT * FROM suppliers WHERE name LIKE ?";

        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_suppliers);
                pst.setString(1, "%" + value + "%");
                rs = pst.executeQuery();
            }

            while (rs.next()) {
                Suppliers suppliers = new Suppliers();
                suppliers.setid(rs.getInt("id"));
                suppliers.setname(rs.getString("name"));
                suppliers.setdescription(rs.getString("descripcion"));
                suppliers.setaddress(rs.getString("address"));
                suppliers.settelephone(rs.getString("telephone"));
                suppliers.setemail(rs.getString("email"));
                suppliers.setcity(rs.getString("city"));
                // Se agregan los campos de auditoría que faltaban por mapear
                suppliers.setcreated(rs.getString("created"));
                suppliers.setupdate(rs.getString("update"));
                
                list_suppliers.add(suppliers);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list_suppliers;
    }

    // Modificar Proveedor
    public boolean updateSuppliersQuery(Suppliers suppliers) {
        String query = "UPDATE suppliers SET name = ?, descripcion = ?, address = ?, "
                   + "telephone = ?, email = ?, city = ?, `update` = ? WHERE id = ?";
        
        Timestamp dateTime = new Timestamp(new Date().getTime());
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, suppliers.getname());
            pst.setString(2, suppliers.getdescription());
            pst.setString(3, suppliers.getaddress()); 
            pst.setString(4, suppliers.gettelephone());
            pst.setString(5, suppliers.getemail());
            pst.setString(6, suppliers.getcity());
            pst.setTimestamp(7, dateTime);
            pst.setInt(8, suppliers.getid());
            
            pst.execute();
            return true;
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar datos del proveedor " + e);
            return false;
        }
    }
        
    // Eliminar Proveedor
    public boolean deleteSupppliersQuery(int id) {
        String query = "DELETE FROM suppliers WHERE id = ?";
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id); 
            pst.execute();
            return true;
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar al proveedor " + e);
            return false;
        }
    }
}