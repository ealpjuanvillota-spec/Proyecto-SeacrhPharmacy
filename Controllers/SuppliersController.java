package Controllers;

import Models.Categories;
import Models.DynamicComboBox;
import static Models.EmployeesDao.rol_user;
import Models.Suppliers;
import Models.SuppliersDao;
import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SuppliersController implements ActionListener, MouseListener, KeyListener {

    //Encapsular variables
    private Suppliers supplier;
    private SuppliersDao supplierDao;
    String rol = rol_user;
    private SystemView views;
    DefaultTableModel model = new DefaultTableModel();

    public SuppliersController(Suppliers supplier, SuppliersDao supplierDao, SystemView views) {
        this.supplier = supplier;
        this.supplierDao = supplierDao;
        this.views = views;

        //Registrar
        this.views.btn_register_supplier.addActionListener(this);

        //Tabla
        this.views.supplier_table.addMouseListener(this);

        //Buscar
        this.views.txt_search_supplier.addKeyListener(this);

        //Modificar
        this.views.btn_update_supplier.addActionListener(this);
        
        //Eliminar 
        this.views.btn_delete_supplier.addActionListener(this);
        
        //Cancelar
        this.views.btn_cancel_supplier.addActionListener(this);
        
        //Label Proovedores
        this.views.jLabelSuplimers.addMouseListener(this);
        getSupplierName();
        // Llamada  para que cargue los datos al abrir la vista
        listAllSuppliers();
    }
                                          
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == views.btn_register_supplier) {
            if (views.txt_supplier_name.getText().equals("")
                    || views.txt_supplier_description.getText().equals("")
                    || views.txt_supplier_adrees.getText().equals("")
                    || views.txt_supplier_telephone.getText().equals("")
                    || views.txt_supplier_email.getText().equals("")
                    || views.cmb_suplier_city.getSelectedItem().toString().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");

            } else {
                supplier.setname(views.txt_supplier_name.getText().trim());
                supplier.setdescription(views.txt_supplier_description.getText().trim());
                supplier.setaddress(views.txt_supplier_adrees.getText().trim());
                supplier.settelephone(views.txt_supplier_telephone.getText().trim());
                supplier.setemail(views.txt_supplier_email.getText().trim());
                supplier.setcity(views.cmb_suplier_city.getSelectedItem().toString());
                if (supplierDao.registerSuppliersQuery(supplier)) {
                    cleanTable();
                    cleanFields();
                    listAllSuppliers();
                    JOptionPane.showMessageDialog(null, "Proovedor registrado con exito");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar proovedor");
                }
            }

        } else if (ae.getSource() == views.btn_update_supplier) {
            if (views.txt_supplier_id.equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona un proovedor para continuar");
            } else {
                if (views.txt_supplier_name.getText().equals("")
                        || views.txt_supplier_description.getText().equals("")
                        || views.txt_supplier_adrees.getText().equals("")
                        || views.txt_supplier_telephone.getText().equals("")
                        || views.txt_supplier_email.getText().equals("")
                        || views.cmb_suplier_city.getSelectedItem().toString().equals("")) {

                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    supplier.setname(views.txt_supplier_name.getText().trim());
                    supplier.setdescription(views.txt_supplier_description.getText().trim());
                    supplier.setaddress(views.txt_supplier_adrees.getText().trim());
                    supplier.settelephone(views.txt_supplier_telephone.getText().trim());
                    supplier.setemail(views.txt_supplier_email.getText().trim());
                    supplier.setcity(views.cmb_suplier_city.getSelectedItem().toString());
                    supplier.setid(Integer.parseInt(views.txt_supplier_id.getText().trim()));

                    if (supplierDao.updateSuppliersQuery(supplier)) {
                        cleanTable();
                        cleanFields();
                        listAllSuppliers();
                        JOptionPane.showMessageDialog(null, "Proovedor modificado con exito");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al modificar proovedor");
                    }

                }
            }
        }else if(ae.getSource() == views.btn_delete_supplier){
            int row = views.supplier_table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Debes seleccionar el proovedor que deseas eliminar");
            }else{
                int id = Integer.parseInt(views.supplier_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "En realidad quieres eliminar este proovedor?");
                if(question == 0 && supplierDao.deleteSupppliersQuery(id)  != false){
                    cleanFields();
                    cleanTable();
                    listAllSuppliers();
                    JOptionPane.showMessageDialog(null, "Proovedor eliminado con exito");
                }else{
                    JOptionPane.showMessageDialog(null, "Error al eliminar proovedor");
                }
            }
        }else if(ae.getSource() == views.btn_cancel_supplier){
            cleanFields();
            views.btn_register_supplier.setEnabled(true);
        }
    }

    public void cleanFields() {
        views.txt_supplier_id.setText("");
        views.txt_supplier_id.setEditable(true);
        views.txt_supplier_name.setText("");
        views.txt_supplier_description.setText("");
        views.txt_supplier_adrees.setText("");
        views.txt_supplier_telephone.setText("");
        views.txt_supplier_email.setText("");
        views.cmb_suplier_city.setSelectedIndex(0);
    }

    //Listar Proovedores
    public void listAllSuppliers() {
        // Corregido "Adimistrador" por "Administrador"
        if (rol.equals("Administrador")) {
            List<Suppliers> list = supplierDao.listSuppliersQuery(views.txt_search_supplier.getText());
            model = (DefaultTableModel) views.supplier_table.getModel();
            Object[] row = new Object[7];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getid();
                row[1] = list.get(i).getname();
                row[2] = list.get(i).getdescription();
                row[3] = list.get(i).getaddress();
                row[4] = list.get(i).gettelephone();
                row[5] = list.get(i).getemail();
                row[6] = list.get(i).getcity();
                model.addRow(row);
            }
            views.supplier_table.setModel(model);
        }

    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == views.supplier_table) {
            int row = views.supplier_table.rowAtPoint(me.getPoint());
            views.txt_supplier_id.setText(views.supplier_table.getValueAt(row, 0).toString());
            views.txt_supplier_name.setText(views.supplier_table.getValueAt(row, 1).toString());
            views.txt_supplier_description.setText(views.supplier_table.getValueAt(row, 2).toString());
            views.txt_supplier_adrees.setText(views.supplier_table.getValueAt(row, 3).toString());
            views.txt_supplier_telephone.setText(views.supplier_table.getValueAt(row, 4).toString());
            views.txt_supplier_email.setText(views.supplier_table.getValueAt(row, 5).toString());
            views.cmb_suplier_city.setSelectedItem(views.supplier_table.getValueAt(row, 6).toString());

            views.btn_register_supplier.setEnabled(false);
            views.txt_supplier_id.setEditable(false);
        }else if(me.getSource() == views.jLabelSuplimers){
            if(rol.equals("Administrador")){
                views.jTabbedPane1.setSelectedIndex(4);
                cleanFields();
                cleanTable();
                listAllSuppliers();
            }else{
                views.jTabbedPane1.setEnabledAt(4, false);
                views.jLabelSuplimers.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No tienes privilegios de admistrador para acceder a esta vista");
            }
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
        if (ke.getSource() == views.txt_search_supplier) {
            cleanTable();
            listAllSuppliers();

        }

    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }
    
     //Mostrar el nombre de Proovedor
    public void getSupplierName(){
        List<Suppliers> list = supplierDao.listSuppliersQuery(views.txt_search_supplier.getText());
        for(int i = 0; i< list.size(); i++){
            int id = list.get(i).getid();
            String name = list.get(i).getname();
            views.txt_proovedores.addItem(new DynamicComboBox(id,name));
        }
    }


}
