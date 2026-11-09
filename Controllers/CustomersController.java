package Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Models.Customers;
import Models.CustomersDao;
import Views.SystemView;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class CustomersController implements ActionListener, MouseListener, KeyListener {
    // Encapsular Variables
    private Customers customer;
    private CustomersDao customerDao;
    private SystemView views;
    DefaultTableModel model = new DefaultTableModel();
   
    // Constructor
    public CustomersController(Customers customer, CustomersDao customerDao, SystemView views){
        this.customer = customer;
        this.customerDao = customerDao;
        this.views = views;
        
        // Boton de registrar cliente "Escucha"
        this.views.btn_register_customer.addActionListener(this);
      
        // Tabla
        this.views.customers_table.addMouseListener(this);
      
        // Buscador
        this.views.txt_search_customers.addKeyListener(this);
        
        // Modificar BTN
        this.views.btn_update_customer.addActionListener(this);
        
        //Eliminar
        this.views.btn_delete_customer.addActionListener(this);
        
        //Cancelar
        this.views.btn_cancel_customer.addActionListener(this);
        
        //Clientes JLabel
        this.views.jLabelCustomers.addMouseListener(this);
        
        // Listar clientes al iniciar
        listAllCustomers();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == views.btn_register_customer){
            if (views.txt_customer_id.getText().equals("")
            || views.txt_customer_fullname.getText().equals("")
            || views.txt_customer_address.getText().equals("")
            || views.txt_customer_telephones.getText().equals("")
            || views.txt_customer_email.getText().equals("")) {
            
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                customer.setid(Integer.parseInt(views.txt_customer_id.getText().trim()));
                customer.setfull_name(views.txt_customer_fullname.getText().trim());
                customer.setaddress(views.txt_customer_address.getText().trim());
                customer.settelephone(views.txt_customer_telephones.getText().trim());
                customer.setemail(views.txt_customer_email.getText().trim());
                
                if(customerDao.registerCustomersQuery(customer)){
                    JOptionPane.showMessageDialog(null, "Cliente registrado con exito");
                    cleanTable();
                    cleanFields();
                    listAllCustomers();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar cliente");
                }
            }
        } else if(ae.getSource() == views.btn_update_customer){
            if(views.txt_customer_id.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            } else {
                 if (views.txt_customer_id.getText().equals("")
                || views.txt_customer_fullname.getText().equals("")
                || views.txt_customer_address.getText().equals("")
                || views.txt_customer_telephones.getText().equals("")
                || views.txt_customer_email.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                 } else {
                    
                     customer.setid(Integer.parseInt(views.txt_customer_id.getText().trim()));
                     customer.setfull_name(views.txt_customer_fullname.getText().trim());
                     customer.setaddress(views.txt_customer_address.getText().trim());
                     customer.setemail(views.txt_customer_email.getText().trim());
                     customer.settelephone(views.txt_customer_telephones.getText().trim());
                     
                    
                     if(customerDao.updateCustomersQuery(customer)){
                         cleanTable();
                         cleanFields();
                         listAllCustomers();
                         views.btn_register_customer.setEnabled(true);
                         views.txt_customer_id.setEditable(true);
                         JOptionPane.showMessageDialog(null, "Datos del cliente modificados con exito");
                     } else {
                         JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar cliente");
                     }
                 }
            }
        }else if(ae.getSource() == views.btn_delete_customer){
            int row = views.customers_table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Debes seleccionar un cliente para eliminarlo");
            }else{
                int id = Integer.parseInt(views.customers_table.getValueAt(row,0).toString());
                int question = JOptionPane.showConfirmDialog(null, "En realidad quieres eliminar este cliente?");
                if(question == 0 && customerDao.deleteCustomersQuery(id)  !=false){
                    cleanFields();
                    cleanTable();
                    listAllCustomers();
                    views.btn_register_customer.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Cliente eliminado con exito");
                }
            }
        }else if(ae.getSource() == views.btn_cancel_customer){
            views.btn_register_customer.setEnabled(true);
            cleanFields();
            
        }
    }
    
    public void cleanFields(){
        views.txt_customer_id.setText("");
        views.txt_customer_id.setEditable(true);
        views.txt_customer_fullname.setText("");
        views.txt_customer_address.setText("");
        views.txt_customer_telephones.setText("");
        views.txt_customer_email.setText("");
    }
    
    // Listar Clientes
    public void listAllCustomers(){
        List<Customers> list = customerDao.lisCustomersQuery(views.txt_search_customers.getText());
        model = (DefaultTableModel) views.customers_table.getModel();
        Object[] row = new Object[5];
        for(int i = 0; i < list.size(); i++){
            row[0] = list.get(i).getid();
            row[1] = list.get(i).getfull_name();
            row[2] = list.get(i).getaddress();
            row[3] = list.get(i).gettelephone();
            row[4] = list.get(i).getemail();
            model.addRow(row);
        }
        views.customers_table.setModel(model);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if(me.getSource() == views.customers_table){
            int row = views.customers_table.rowAtPoint(me.getPoint());
            views.txt_customer_id.setText(views.customers_table.getValueAt(row, 0).toString());
            views.txt_customer_fullname.setText(views.customers_table.getValueAt(row, 1).toString());
            views.txt_customer_address.setText(views.customers_table.getValueAt(row, 2).toString());
            views.txt_customer_telephones.setText(views.customers_table.getValueAt(row, 3).toString());
            views.txt_customer_email.setText(views.customers_table.getValueAt(row, 4).toString());
            
            views.btn_register_customer.setEnabled(false);
            views.txt_customer_id.setEditable(false);
        }else if(me.getSource() == views.jLabelCustomers){
            views.jTabbedPane1.setSelectedIndex(2);
            cleanTable();
            cleanFields();
            listAllCustomers();
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if(ke.getSource() == views.txt_search_customers){
            cleanTable();
            listAllCustomers();
        }
    }
    
    // Limpiar Tabla
    public void cleanTable(){
        for(int i = 0; i < model.getRowCount(); i++){
            model.removeRow(i);
            i = i - 1;
        }
    }
    
    // Limpiar Formulario
    public void clearForm(){
        views.txt_customer_id.setText("");
        views.txt_customer_fullname.setText("");
        views.txt_customer_address.setText("");
        views.txt_customer_telephones.setText("");
        views.txt_customer_email.setText("");
        views.txt_customer_id.setEditable(true);
        views.btn_register_customer.setEnabled(true);
    }
}