package Controllers;

import Models.DynamicComboBox;
import static Models.EmployeesDao.id_user;
import static Models.EmployeesDao.rol_user;
import Models.Products;
import Models.ProductsDao;
import Models.Purchase;
import Models.PurchaseDao;
import Views.Print;
import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PurchasesController implements ActionListener, MouseListener, KeyListener {

    private Purchase purchase;
    private PurchaseDao purchaseDao;
    private SystemView views;
    Products product = new Products();
    ProductsDao productsDao = new ProductsDao();
    String rol = rol_user;
    private int getIdSupplier = 0;
    private int item = 0;
    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel temp = new DefaultTableModel(); // Inicializado para evitar NullPointerException

    public PurchasesController(Purchase purchase, PurchaseDao purchaseDao, SystemView views) {
        this.purchase = purchase;
        this.purchaseDao = purchaseDao;
        this.views = views;

        // Inicializamos el modelo temporal con la tabla de la vista
        this.temp = (DefaultTableModel) this.views.purchase_table.getModel();

        this.views.txt_purchase_code.addKeyListener(this);
        this.views.txt_purchase_price.addKeyListener(this);
        this.views.txt_purchase_amount.addKeyListener(this);

        //Agregar
        this.views.btn_add_product_to_buy.addActionListener(this);
        
        //Compras
        this.views.btn_confirm_purchase.addActionListener(this);
        
        //Eliminar
        this.views.btn_remove_purchase.addActionListener(this);
        
        //Nuevo
        this.views.btn_new_purchase.addActionListener(this);
        
        //Label
        this.views.jLabelPurchases.addMouseListener(this);
        this.views.jLabelReport.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == views.btn_add_product_to_buy) {
            DynamicComboBox supplier_cmb = (DynamicComboBox) views.txt_proovedores.getSelectedItem();
            int supplier_id = supplier_cmb.getid();
            if (getIdSupplier == 0) {
                getIdSupplier = supplier_id;
            } else {
                if (getIdSupplier != supplier_id) {
                    JOptionPane.showMessageDialog(null, "No puede realizar una misma compra con varios Proveedores");
                } else {
                    // Validación ampliada para evitar NumberFormatException si algún campo está vacío
                    if (views.txt_purchase_code.getText().equals("") || 
                        views.txt_purchase_amount.getText().equals("") || 
                        views.txt_purchases_id.getText().equals("") || 
                        views.txt_purchase_price.getText().equals("")) {
                        
                        JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos (presione Enter en el código del producto).");
                        return;
                    }

                    int amount = Integer.parseInt(views.txt_purchase_amount.getText());
                    String product_name = views.txt_purchase_product_names.getText();
                    double price = Double.parseDouble(views.txt_purchase_price.getText());
                    
                    // Capturamos el ID de la base de datos para los registros internos
                    int purchase_id = Integer.parseInt(views.txt_purchases_id.getText());
                    // Capturamos el código real del producto introducido por el usuario para mostrarlo en la tabla
                    int product_code = Integer.parseInt(views.txt_purchase_code.getText());
                    
                    String supplier_name = supplier_cmb.getname();

                    if (amount > 0) {
                        temp = (DefaultTableModel) views.purchase_table.getModel();
                        for (int i = 0; i < views.purchase_table.getRowCount(); i++) {
                            if (views.purchase_table.getValueAt(i, 1).equals(product_name)) {
                                JOptionPane.showMessageDialog(null, "El producto ya esta registrado en la tabla de compras");
                                return;
                            }
                        }
                        ArrayList list = new ArrayList();
                        item = 1;
                        list.add(item);
                        list.add(product_code); // Se guarda el código del producto en lugar del ID interno
                        list.add(product_name);
                        list.add(amount);
                        list.add(price);
                        list.add(amount * price);
                        list.add(supplier_name);

                        Object[] obj = new Object[6];
                        obj[0] = list.get(1); // Código del producto
                        obj[1] = list.get(2); // Nombre del producto
                        obj[2] = list.get(3); // Cantidad
                        obj[3] = list.get(4); // Precio
                        obj[4] = list.get(5); // Subtotal
                        obj[5] = list.get(6); // Proveedor
                        temp.addRow(obj);

                        views.purchase_table.setModel(temp);
                        cleanFieldsPurchases();
                        views.txt_proovedores.setEnabled(false);
                        views.txt_purchase_code.requestFocus();
                        calculatePurchase();
                    }
                }
            }
        }else if(ae.getSource() == views.btn_confirm_purchase){
            insertPurchase();
        }else if(ae.getSource() == views.btn_remove_purchase){
            model = (DefaultTableModel) views.purchase_table.getModel();
            model.removeRow(views.purchase_table.getSelectedRow());
            calculatePurchase();
            views.txt_purchase_code.requestFocus();
        }else if(ae.getSource() == views.btn_new_purchase){
            cleanTableTemp();
            cleanFieldsPurchases();
        }
    }
    
    public void listAllPurchases(){
        if(rol.equals("Administrador") || rol.equals("Auxiliar")){
            List<Purchase> list = purchaseDao.listAllPurchaseQuery();
            model = (DefaultTableModel) views.table_all_purchase.getModel();
            Object[] row = new Object[4];
            for(int i = 0; i < list.size(); i++){
            row[0] = list.get(i).getid();
            row[1] = list.get(i).getsupplier_name_product();
            row[2] = list.get(i).gettotal();
            row[3] = list.get(i).getcreated();
            model.addRow(row);
            }
            views.table_all_purchase.setModel(model);
        }
    }

    public void cleanFieldsPurchases() {
        views.txt_purchase_product_names.setText("");
        views.txt_purchase_price.setText("");
        views.txt_purchase_amount.setText("");
        views.txt_purchase_code.setText("");
        views.txt_purchase_subtotal.setText("");
        views.txt_purchases_id.setText("");
        views.txt_purchase_total.setText("");
    }

    public void calculatePurchase() {
        double total = 0;
        int numRow = views.purchase_table.getRowCount();
        for (int i = 0; i < numRow; i++) {
            total = total + Double.parseDouble(String.valueOf(views.purchase_table.getValueAt(i, 4)));
        }
        views.txt_purchase_total.setText("" + total);
    }

    private void insertPurchase() {
        double total = Double.parseDouble(views.txt_purchase_total.getText());
        int employee_id = id_user;
        if (purchaseDao.registerPurchaseQuery(getIdSupplier, employee_id, total)) {
            int purchase_id = purchaseDao.purchaseid();
            for (int i = 0; i < views.purchase_table.getRowCount(); i++) {
                // Obtenemos el código ingresado en la tabla temporal y buscamos su ID interno real para los detalles de compra
                int code_product = Integer.parseInt(views.purchase_table.getValueAt(i, 0).toString());
                Products prodInfo = productsDao.searchcode(code_product);
                int product_id = prodInfo.getid();

                int purchase_amount = Integer.parseInt(views.purchase_table.getValueAt(i, 2).toString());
                double purchase_price = Double.parseDouble(views.purchase_table.getValueAt(i, 3).toString());
                double purchase_subtotal = purchase_price * purchase_amount;
                
                purchaseDao.registerPurchaseDetailQuery(purchase_id, purchase_price, purchase_amount, purchase_subtotal, product_id);
                
                product = productsDao.searchid(product_id);
                int amount = product.getproduct_quantity() + purchase_amount;
                productsDao.updateStockQuery(amount, product_id);
            }
            cleanTableTemp();
            JOptionPane.showMessageDialog(null, "Compra generada con exito");
            cleanFieldsPurchases();
            Print print = new Print(purchase_id);
            print.setVisible(true);
        }
    }
    
    public void cleanTableTemp(){
        if (temp != null) {
            for(int i = 0; i < temp.getRowCount(); i++){
                temp.removeRow(i);
                i = i - 1;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if(me.getSource() == views.jLabelPurchases){
            if(rol.equals("Administrador")){
                views.jTabbedPane1.setSelectedIndex(1);
                cleanTableTemp();
                
            }else{
                views.jTabbedPane1.setEnabledAt(1,false);
                views.jLabelPurchases.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No tiene privilegios de Administrador");
            }
        }else if(me.getSource() == views.jLabelReport){
            views.jTabbedPane1.setSelectedIndex(6);
            cleanTable();
            listAllPurchases();
        }
    }
    
    public void cleanTable(){
        if (model != null) {
            for(int i = 0; i < model.getRowCount(); i++){
                model.removeRow(i);
                i = i - 1;
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
        if (ke.getSource() == views.txt_purchase_code) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                if (views.txt_purchase_code.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingresa el codigo del Producto");
                } else {
                    int id = Integer.parseInt(views.txt_purchase_code.getText());
                    product = productsDao.searchcode(id);
                    if (product.getname() != null) {
                        views.txt_purchase_product_names.setText(product.getname());
                        views.txt_purchases_id.setText("" + product.getid());
                        views.txt_purchase_amount.requestFocus();
                    } else {
                        JOptionPane.showMessageDialog(null, "El producto no existe");
                        views.txt_purchase_code.setText("");
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (ke.getSource() == views.txt_purchase_amount || ke.getSource() == views.txt_purchase_price) {
            int quantity = 1;
            double price = 0.0;
            try {
                if (!views.txt_purchase_amount.getText().equals("")) {
                    quantity = Integer.parseInt(views.txt_purchase_amount.getText());
                }
                if (!views.txt_purchase_price.getText().equals("")) {
                    price = Double.parseDouble(views.txt_purchase_price.getText());
                }
                views.txt_purchase_subtotal.setText("" + (quantity * price));
            } catch (NumberFormatException e) {
               
            }
        }
    }
}