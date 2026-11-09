package Controllers;

import java.awt.event.ActionEvent;
import Models.Employees;
import Models.EmployeesDao;
import static Models.EmployeesDao.id_user;
import static Models.EmployeesDao.rol_user;
import Views.SystemView;
import java.awt.event.MouseListener;
import java.util.List;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EmployeesController implements ActionListener, MouseListener, KeyListener {
    private Employees employee;
    private EmployeesDao employeeDao;
    private SystemView views;
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();
    
    public EmployeesController(Employees employee, EmployeesDao employeeDao, SystemView views){
        this.employee = employee;
        this.employeeDao = employeeDao;
        this.views = views;
        
        if (rol == null || rol.equals("")) {
            rol = "Administrador";
            EmployeesDao.rol_user = "Administrador";
        }
        
        // Boton registrar Empleado
        this.views.getBtn_register_employee().addActionListener(this);
        // Boton modificar Empleado
        this.views.getBtn_update_employee().addActionListener(this);
        // Poner a la escucha de la tabla
        this.views.getEmployees_table().addMouseListener(this);
        // txt Buscar a la escucha
        this.views.txt_search_employee.addKeyListener(this);
        // Boton de Eliminar
        this.views.getBtn_delete_employee().addActionListener(this);
        // Boton de Cancelar
        this.views.getBtn_cancel_employee().addActionListener(this);
        //Boton Modificar Contraseña
        this.views.btn_modify_data.addActionListener(this);
        //Colocar el label a la escucha
       this.views.jLabelPloyees.addMouseListener(this);
        
        listAllEmployees();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == views.getBtn_register_employee()){
            if(views.txt_employee_id.getText().equals("") 
                || views.txt_employee_fullname.getText().equals("") 
                || views.txt_employee_username.getText().equals("") 
                || views.txt_employee_address.getText().equals("") 
                || views.txt_employee_telephone.getText().equals("") 
                || views.txt_employee_email.getText().equals("") 
                || views.cmb_rol.getSelectedItem().toString().equals("") 
                || String.valueOf(views.txt_employee_password.getPassword()).equals("")) {
                
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                employee.setid(Integer.parseInt(views.txt_employee_id.getText().trim()));
                employee.setfull_name(views.txt_employee_fullname.getText().trim());
                employee.setusername(views.txt_employee_username.getText().trim());
                employee.setaddress(views.txt_employee_address.getText().trim());
                employee.settelephone(views.txt_employee_telephone.getText().trim());
                employee.setemail(views.txt_employee_email.getText().trim());
                employee.setpassword(String.valueOf(views.txt_employee_password.getPassword()));
                employee.setrol(views.cmb_rol.getSelectedItem().toString());
                
                if(employeeDao.registerEmployeeQuery(employee)){
                    cleanTable();
                    cleanFields();
                    listAllEmployees();
                    JOptionPane.showMessageDialog(null, "Empleado registrado con exito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrio un error al registrar un empleado");
                }
            }
        } else if(ae.getSource() == views.getBtn_update_employee()){
            if(views.txt_employee_id.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Selecciona una fila de la tabla para continuar");
            } else {
                if(views.txt_employee_id.getText().equals("")
                        || views.txt_employee_fullname.getText().equals("")
                        || views.cmb_rol.getSelectedItem().toString().equals("")){
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    employee.setid(Integer.parseInt(views.txt_employee_id.getText().trim()));
                    employee.setfull_name(views.txt_employee_fullname.getText().trim());
                    employee.setusername(views.txt_employee_username.getText().trim());
                    employee.setaddress(views.txt_employee_address.getText().trim());
                    employee.settelephone(views.txt_employee_telephone.getText().trim());
                    employee.setemail(views.txt_employee_email.getText().trim());
                    employee.setpassword(String.valueOf(views.txt_employee_password.getPassword()).trim());
                    employee.setrol(views.cmb_rol.getSelectedItem().toString());
                    
                    if(employeeDao.updateEmployeeQuery(employee)){
                        cleanTable();
                        listAllEmployees();
                        views.getBtn_register_employee().setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Datos modificados exitosamente");
                        cleanFields();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar los datos");
                    }
                }
            }
        } else if(ae.getSource() == views.getBtn_delete_employee()){
            int row = views.getEmployees_table().getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Debes seleccionar un empleado para eliminarlo");
            } else if(views.getEmployees_table().getValueAt(row, 0).equals(id_user)){
                JOptionPane.showMessageDialog(null, "No puede eliminar el usuario autenticado");
            } else {
                int id = Integer.parseInt(views.getEmployees_table().getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿En realidad quieres eliminar este empleado?");
                if(question == 0 && employeeDao.deleteEmployeeQuery(id) != false){
                    cleanTable();
                    cleanFields();
                    views.getBtn_register_employee().setEnabled(true);
                    views.txt_employee_password.setEnabled(true);
                    listAllEmployees();
                    JOptionPane.showMessageDialog(null, "El usuario fue eliminado con exito");
                }
            }
        } else if(ae.getSource() == views.getBtn_cancel_employee()){
            cleanFields();
            views.getBtn_register_employee().setEnabled(true);
            views.txt_employee_password.setEnabled(true);
            views.txt_employee_id.setEditable(true);
            views.txt_employee_id.setEnabled(true);
        } else if(ae.getSource() == views.btn_modify_data){
            String password = String.valueOf(views.txt_password_modify.getPassword());
            String password_confirm = String.valueOf(views.txt_password_modify_confirm.getPassword());
            if(!password.equals("") && !password_confirm.equals("")){
                if(password.equals(password_confirm)){
                    employee.setpassword(String.valueOf(views.txt_password_modify.getPassword()));
                    if(employeeDao.updateEmployeePassword(employee) != false){
                        JOptionPane.showMessageDialog(null, "Se modifico correctamente el Password");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar el Password");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Los Password no Coinciden");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            }
        }
    }

    public void listAllEmployees(){
        System.out.println("Rol actual del usuario: '" + rol + "'");
        if(rol.equals("Administrador")){
            model = (DefaultTableModel) views.getEmployees_table().getModel();
            List<Employees> list = employeeDao.listEmployeesQuery(views.txt_search_employee.getText());
            System.out.println( "Empleados encontrados en la BD: " + list.size());
            
            Object[] row = new Object[7];
            for(int i = 0; i < list.size(); i++){
                row[0] = list.get(i).getid();
                row[1] = list.get(i).getfull_name();
                row[2] = list.get(i).getusername();
                row[3] = list.get(i).getaddress();
                row[4] = list.get(i).gettelephone();
                row[5] = list.get(i).getemail();
                row[6] = list.get(i).getrol();
                model.addRow(row);
            }
        } else {
            System.out.println("La condición del rol no coincidió ('Administrador'). Rol actual: " + rol);
        }
    }
    
    // Limpiar los campos
    public void cleanFields(){
        views.txt_employee_id.setText("");
        views.txt_employee_id.setEditable(true);
        views.txt_employee_fullname.setText("");
        views.txt_employee_username.setText("");
        views.txt_employee_address.setText("");
        views.txt_employee_telephone.setText("");
        views.txt_employee_email.setText("");
        views.txt_employee_password.setText("");
        views.txt_employee_password.setEnabled(true);
        if(views.cmb_rol.getItemCount() > 0){
            views.cmb_rol.setSelectedIndex(0);
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        if(me.getSource() == views.getEmployees_table()){
            int row = views.getEmployees_table().rowAtPoint(me.getPoint());
            views.txt_employee_id.setText(views.getEmployees_table().getValueAt(row, 0).toString());
            views.txt_employee_fullname.setText(views.getEmployees_table().getValueAt(row, 1).toString());
            views.txt_employee_username.setText(views.getEmployees_table().getValueAt(row, 2).toString());
            views.txt_employee_address.setText(views.getEmployees_table().getValueAt(row, 3).toString());
            views.txt_employee_telephone.setText(views.getEmployees_table().getValueAt(row, 4).toString());
            views.txt_employee_email.setText(views.getEmployees_table().getValueAt(row, 5).toString());
            views.cmb_rol.setSelectedItem(views.getEmployees_table().getValueAt(row, 6).toString());
            
            views.txt_employee_id.setEditable(false);
            views.txt_employee_password.setEnabled(false);
            views.getBtn_register_employee().setEnabled(false);
        } else if(me.getSource() == views.jLabelPloyees){
            if(rol.equals("Administrador")){
                views.jTabbedPane1.setSelectedIndex(3); 
                cleanTable();
                cleanFields();
                listAllEmployees();
            } else {
                views.jTabbedPane1.setEnabledAt(3, false);
                views.jLabelPloyees.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No tiene privilegios de administrador para acceder a Empleados");
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {}

    @Override
    public void mouseReleased(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}

    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) {}

    @Override
    public void keyReleased(KeyEvent ke) {
        if(ke.getSource() == views.txt_search_employee){
            cleanTable();
            listAllEmployees();
        }
    }
    
    public void cleanTable(){
        for(int i = 0; i < model.getRowCount(); i++){
            model.removeRow(i);
            i = i - 1;
        }
    }
}