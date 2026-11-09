package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class EmployeesDao {
    // Instanciar conexion
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    // Variables para enviar datos entre interfaces
    public static int id_user = 0;
    public static String full_name_user = "";
    public static String username_user = "";
    public static String address_user = "";
    public static String telephone_user = "";
    public static String email_user = "";
    public static String rol_user = "";
    
    public Employees loginQuery(String user, String password){
        String query = "SELECT * FROM employees WHERE username = ? AND password = ?";
        Employees employee = null;
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            // Enviar parametros
            pst.setString(1, user);
            pst.setString(2, password);
            
            rs = pst.executeQuery();
            
            if(rs.next()){
                employee = new Employees();
                employee.setid(rs.getInt("id"));
                employee.setfull_name(rs.getString("full_name"));
                employee.setusername(rs.getString("username"));
                employee.setaddress(rs.getString("address"));
                employee.settelephone(rs.getString("telephone"));
                employee.setemail(rs.getString("email"));
                employee.setrol(rs.getString("rol"));
                
                // Guardar los datos en las variables estáticas de sesión
                id_user = rs.getInt("id");
                full_name_user = rs.getString("full_name");
                username_user = rs.getString("username");
                address_user = rs.getString("address");
                telephone_user = rs.getString("telephone");
                email_user = rs.getString("email");
                rol_user = rs.getString("rol");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al obtener el empleado " + e);
        }
        return employee;
    }
   
    // Registrar Empleados
    public boolean registerEmployeeQuery(Employees employee) {
        String query = "INSERT INTO employees (id, full_name, username, address, "
                + "telephone, email, password, rol, created, `update`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";  

        Timestamp datetime = new Timestamp(new Date().getTime());
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, employee.getid()); 
            pst.setString(2, employee.getfull_name()); 
            pst.setString(3, employee.getusername());
            pst.setString(4, employee.getaddress());
            pst.setString(5, employee.gettelephone());
            pst.setString(6, employee.getemail());
            pst.setString(7, employee.getpassword());
            pst.setString(8, employee.getrol());
            
            pst.setTimestamp(9, datetime);
            pst.setTimestamp(10, datetime);
            
            pst.execute();
            
            return true;
           
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al registrar al empleado " + e);
            return false;
        }
    }
    
    // Lista de Empleado
    public List<Employees> listEmployeesQuery(String value){
        List<Employees> list_employees = new ArrayList<>();
        String query = "SELECT * FROM employees ORDER BY rol ASC";
        String query_search_employee = "SELECT * FROM employees WHERE id LIKE '%" + value + "%'";
        
        try{
            conn = cn.getConnection();
            if(value.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            }else{
                pst = conn.prepareStatement(query_search_employee);
                rs = pst.executeQuery();
            }
            
            while(rs.next()){
               Employees employee = new Employees();
               
               employee.setid(rs.getInt("id"));
               employee.setfull_name(rs.getString("full_name"));
               employee.setusername(rs.getString("username"));
               employee.setaddress(rs.getString("address"));
               employee.settelephone(rs.getString("telephone"));
               employee.setemail(rs.getString("email"));
               employee.setrol(rs.getString("rol"));
               
               list_employees.add(employee);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_employees;
    }
        
    // Modificar Empleado
    public boolean updateEmployeeQuery(Employees employee){
        String query = "UPDATE employees SET full_name = ?, username = ?, "
                + "address = ?, telephone = ?, email = ?, rol = ?, `update` = ? "
                + "WHERE id = ?";
        
        Timestamp dateTime = new Timestamp(new Date().getTime());
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setString(1, employee.getfull_name());
            pst.setString(2, employee.getusername());
            pst.setString(3, employee.getaddress());
            pst.setString(4, employee.gettelephone());
            pst.setString(5, employee.getemail());
            pst.setString(6, employee.getrol());
            pst.setTimestamp(7, dateTime);
            pst.setInt(8, employee.getid());
            
            pst.execute();
            return true;
                
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al modificar al empleado " + e);
            return false;
        }
    }
        
    // Eliminar Empleado
    public boolean deleteEmployeeQuery(int id){
        String query = "DELETE FROM employees WHERE id = " + id;
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "No se puede eliminar un empleado que tenga relacion con otra tabla");
            return false;
        }
    }
        
    // Cambiar password
    public boolean updateEmployeePassword(Employees employee){
        String query = "UPDATE employees SET password = ? WHERE username = ?";
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, employee.getpassword());
            pst.setString(2, username_user);
            pst.executeUpdate();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar el password " + e);
            return false;
        }        
    }
}