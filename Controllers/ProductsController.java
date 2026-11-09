package Controllers;

import Models.DynamicComboBox;
import static Models.EmployeesDao.rol_user;
import Models.Products;
import Models.ProductsDao;
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

public class ProductsController implements ActionListener, MouseListener, KeyListener {

    private Products product;
    private ProductsDao productsDao;
    private SystemView views;
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public ProductsController(Products product, ProductsDao productsDao, SystemView views) {
        this.product = product;
        this.productsDao = productsDao;
        this.views = views;

        // Registrar
        this.views.btn_register_product.addActionListener(this);

        //Tabla
        this.views.Products_table.addMouseListener(this);

        //Buscar
        this.views.txt_search_product.addKeyListener(this);

        //Modificar
        this.views.btn_update_product.addActionListener(this);
        
        //Eiminar
        this.views.btn_delete_product.addActionListener(this);
        
        //Cancelar
        this.views.btn_cancel_product.addActionListener(this);
        
        //Label
        this.views.jLabelProducts.addMouseListener(this);
        listAllProducts();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == views.btn_register_product) {
            if (views.txt_product.getText().equals("")
                    || views.txt_product_name.getText().equals("")
                    || views.txt_product_description.getText().equals("")
                    || views.txt_product_unil_price.getText().equals("")
                    || views.cmb_product_category.getSelectedItem().toString().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son Obligatorios");
            } else {
                product.setcode(Integer.parseInt(views.txt_product.getText().trim()));
                product.setname(views.txt_product_name.getText().trim());
                product.setdescription(views.txt_product_description.getText().trim());
                product.setunit_price(Double.parseDouble(views.txt_product_unil_price.getText()));
                DynamicComboBox category_id = (DynamicComboBox) views.cmb_product_category.getSelectedItem();
                product.setcategory_id(category_id.getid());

                if (productsDao.registerProductsQuery(product)) {
                    cleanTable();
                    cleanFields();
                    listAllProducts();
                    JOptionPane.showMessageDialog(null, "Producto registrado con exito");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar Producto");
                }
            }
        } else if (ae.getSource() == views.btn_update_product) {
            if (views.txt_product.getText().equals("")
                    || views.txt_product_name.getText().equals("")
                    || views.txt_product_description.getText().equals("")
                    || views.txt_product_unil_price.getText().equals("")
                    || views.cmb_product_category.getSelectedItem().toString().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son Obligatorios");
            } else {
                product.setcode(Integer.parseInt(views.txt_product.getText()));
                product.setname(views.txt_product_name.getText().trim());
                product.setdescription(views.txt_product_description.getText().trim());
                product.setunit_price(Double.parseDouble(views.txt_product_unil_price.getText()));
                DynamicComboBox category_id = (DynamicComboBox) views.cmb_product_category.getSelectedItem();
                product.setcategory_id(category_id.getid());
                product.setid(Integer.parseInt(views.txt_product_id.getText()));
                if (productsDao.registerProductsQuery(product)) {
                    cleanTable();
                    cleanFields();
                    listAllProducts();
                    JOptionPane.showMessageDialog(null, "Producto modificado con Exito");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al modificar el Producto");
                }
            }

        }else if(ae.getSource() == views.btn_delete_product){
            int row = views.Products_table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Debes seleccionar un producto para poder Eliminarlo");
            }else{
                int id = Integer.parseInt(views.Products_table.getValueAt(row,0).toString());
                int question = JOptionPane.showConfirmDialog(null, "En realidad quieres eliminar este producto?");
                if(question == 0 && productsDao.deleteProductsQuery(id) != false){
                    cleanFields();
                    cleanTable();
                    listAllProducts();
                    views.btn_register_product.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Producto Eliminado con Exito");    
                }else{
                    JOptionPane.showMessageDialog(null, "Error al Eliminar el Producto");
                }
            }
        }else if(ae.getSource() == views.btn_cancel_product){
            cleanFields();
            views.btn_register_product.setEnabled(true);
        }
    }

    public void cleanFields() {
        views.txt_product_id.setText("");
        views.txt_product.setText("");
        views.txt_product_name.setText("");
        views.txt_product_description.setText("");
        views.txt_product_unil_price.setText("");
    }

    public void listAllProducts() {
        if (rol.equals("Administrador") || rol.equals("Auxiliar")) {
            List<Products> list = productsDao.listProductsQuery(views.txt_search_product.getText());
            model = (DefaultTableModel) views.Products_table.getModel();
            Object[] row = new Object[7];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getid();
                row[1] = list.get(i).getcode();
                row[2] = list.get(i).getname();
                row[3] = list.get(i).getdescription();
                row[4] = list.get(i).getunit_price();
                row[5] = list.get(i).getproduct_quantity();
                row[6] = list.get(i).getcategory_name();
                model.addRow(row);
            }
            views.Products_table.setModel(model);
            if (rol.equals("Auxiliar")) {
                views.btn_register_product.setEnabled(false);
                views.btn_update_product.setEnabled(false);
                views.btn_delete_product.setEnabled(false);
                views.btn_cancel_product.setEnabled(false);
                views.txt_product.setEditable(false);
                views.txt_productid.setEditable(false);
                views.txt_product_name.setEditable(false);
                views.txt_product_unil_price.setEditable(false);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == views.Products_table) {
            int row = views.Products_table.rowAtPoint(me.getPoint());
            views.txt_product_id.setText(views.Products_table.getValueAt(row, 0).toString());
            product = productsDao.searchProductsQuery(Integer.parseInt(views.txt_product_id.getText()));
            views.txt_product.setText("" + product.getcode());
            views.txt_products_name.setText(product.getname());
            views.txt_product_description.setText(product.getdescription());
            views.txt_product_unil_price.setText("" + product.getunit_price());
            views.cmb_product_category.setSelectedItem(new DynamicComboBox(product.getcategory_id(), product.getcategory_name()));
            views.btn_register_product.setEnabled(false);
        }else if(me.getSource() == views.jLabelProducts){
            views.jTabbedPane1.setSelectedIndex(0);
            cleanFields();
            cleanTable();
            listAllProducts();
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
        if (ke.getSource() == views.txt_search_product) {

            cleanTable();
            listAllProducts();
        }

    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }
}