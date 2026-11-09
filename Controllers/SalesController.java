package Controllers;

import Models.Customers;
import Models.CustomersDao;
import Models.EmployeesDao;
import static Models.EmployeesDao.rol_user;
import Models.Products;
import Models.ProductsDao;
import Models.Sales;
import Models.SalesDao;
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

public class SalesController implements ActionListener, MouseListener, KeyListener {

    private Sales sale;
    private SalesDao saleDao;
    private SystemView views;
    Products product = new Products();
    ProductsDao productDao = new ProductsDao();
    Customers customer = new Customers();
    CustomersDao customersDao = new CustomersDao();

    private int item = 0;
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel temp;

    public SalesController(Sales sale, SalesDao saleDao, SystemView views) {
        this.sale = sale;
        this.saleDao = saleDao;
        this.views = views;

        // Agregar
        this.views.btn_add_product_sale.addActionListener(this);

        // Vender
        this.views.btn_confirm_sale.addActionListener(this);

        // Eliminar
        this.views.btn_remove_sale.addActionListener(this);

        // Nuevo 
        this.views.btn_new_sale.addActionListener(this);

        // Listeners de teclado
        this.views.txt_sale_product_code.addKeyListener(this);
        this.views.txt_sale_price.addKeyListener(this);
        this.views.txt_sale_customer_id.addKeyListener(this);
        this.views.txt_sale_quantli.addKeyListener(this);
        
        // Listeners de mouse
        this.views.jLabelSales.addMouseListener(this);
        this.views.jLabelReport.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == views.btn_confirm_sale) {
            insertSale();
        } else if (ae.getSource() == views.btn_new_sale) {
            cleanAllFieldsSales();
            cleanTableTemp();
            views.txt_sale_product_code.requestFocus();
        } else if (ae.getSource() == views.btn_remove_sale) {
            model = (DefaultTableModel) views.sales_table.getModel();
            model.removeRow(views.sales_table.getSelectedRow());
            CalculateSales();
            views.txt_sale_product_code.requestFocus();
        } else if (ae.getSource() == views.btn_add_product_sale) {
            if (views.txt_sale_quantli.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Ingrese una Cantidad");
                return;
            }
            int amount = Integer.parseInt(views.txt_sale_quantli.getText());
            String product_name = views.txt_products_name.getText();
            
            double price = Double.parseDouble(views.txt_sale_price.getText());
            int sale_id = Integer.parseInt(views.txt_product_id.getText());
            int stock = Integer.parseInt(views.txt_sale_stok.getText());
            
            double subtotal = amount * price;
            String full_name = views.txt_sale_customer.getText();
            
            if (stock >= amount) {
                item = item + 1;
                temp = (DefaultTableModel) views.sales_table.getModel();
                for (int i = 0; i < views.sales_table.getRowCount(); i++) {
                    if (views.sales_table.getValueAt(i, 1).equals(views.txt_products_name.getText())) {
                        JOptionPane.showMessageDialog(null, "El producto ya esta registrado en la tabla de Ventas");
                        return;
                    }
                }
                ArrayList list = new ArrayList();
                list.add(item);
                list.add(sale_id);
                list.add(product_name);
                list.add(amount);
                list.add(price);
                list.add(subtotal);
                list.add(full_name);
                Object[] obj = new Object[6];
                obj[0] = list.get(1);
                obj[1] = list.get(2);
                obj[2] = list.get(3);
                obj[3] = list.get(4);
                obj[4] = list.get(5);
                obj[5] = list.get(6);
                temp.addRow(obj);
                CalculateSales();
                cleanFieldsSales();
                views.txt_sale_product_code.requestFocus();

            } else {
                JOptionPane.showMessageDialog(null, "Stock no Disponible");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Ingrese una Cantidad");
        }
    }
    
    public void listAllSales(){
        if(rol.equals("Administrador")){
            List<Sales> list = saleDao.listAllSalesQuery();
            model = (DefaultTableModel) views.table_all_sales.getModel();
            Object[] row = new Object[5];
            for(int i = 0; i < list.size(); i++){
                row[0] = list.get(i).getid();
                row[1] = list.get(i).getcustomer_name();
                row[2] = list.get(i).getemployee_id();
                row[3] = list.get(i).gettotal_to_play();
                row[4] = list.get(i).getsale_date();
                model.addRow(row);
            }views.table_all_sales.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if(me.getSource() == views.jLabelSales){
            views.jTabbedPane1.setSelectedIndex(8);
        }else if(me.getSource() == views.jLabelReport){
            if(rol.equals("Administrador")){
                views.jTabbedPane1.setSelectedIndex(6);
                listAllSales();
            }else{
                views.jTabbedPane1.setEnabledAt(7,false);
                views.jLabelReport.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No tienes privilegios de Administrador para esta Parte");
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
    public void keyPressed(KeyEvent ke) {
        if (ke.getSource() == views.txt_sale_product_code) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                if (!"".equals(views.txt_sale_product_code.getText())) {
                    int code = Integer.parseInt(views.txt_sale_product_code.getText());
                    product = productDao.searchcode(code);
                    if (product.getname() != null) {
                        views.txt_products_name.setText(product.getname());
                        views.txt_product_id.setText("" + product.getid());
                        views.txt_sale_stok.setText("" + product.getproduct_quantity());
                        views.txt_sale_price.setText("" + product.getunit_price());
                        views.txt_sale_quantli.requestFocus();
                    } else {
                        JOptionPane.showMessageDialog(null, "No existe ningun producto con este Codigo");
                        cleanFieldsSales();
                        views.txt_sale_product_code.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Ingrese el codigo al producto al Vender");
                }
            }
        } else if (ke.getSource() == views.txt_sale_customer_id) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                if (!"".equals(views.txt_sale_customer_id.getText())) {
                    int customer_id = Integer.parseInt(views.txt_sale_customer_id.getText());
                    customer = customersDao.searchCustomer(customer_id);
                    
                    if (customer.getfull_name() != null) {
                        views.txt_sale_customer.setText("" + customer.getfull_name());
                    } else {
                        views.txt_sale_customer_id.setText("");
                        views.txt_sale_customer.setText(""); 
                        JOptionPane.showMessageDialog(null, "El cliente no Existe");
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (ke.getSource() == views.txt_sale_quantli) {
            int quantity;
            double price;
            if (!views.txt_sale_price.getText().equals("")) {
                price = Double.parseDouble(views.txt_sale_price.getText());
            } else {
                price = product.getunit_price();
            }
            
            if (views.txt_sale_quantli.getText().equals("")) {
                quantity = 1;
                views.txt_sale_subtotal.setText("");
            } else {
                quantity = Integer.parseInt(views.txt_sale_quantli.getText());
                views.txt_sale_subtotal.setText("" + (quantity * price));
            }
        }
    }

    private void insertSale() {
        if (views.txt_sale_customer_id.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la cédula del cliente");
            return;
        }
        int customer_id = Integer.parseInt(views.txt_sale_customer_id.getText());
        int employee_id = EmployeesDao.id_user;
        double total = Double.parseDouble(views.txt_sale_total_to_pay.getText());
        
        if (saleDao.registerSaleQuery(customer_id, employee_id, total)) {
            int sale_id = saleDao.saleid();
            for (int i = 0; i < views.sales_table.getRowCount(); i++) {
                int product_id = Integer.parseInt(views.sales_table.getValueAt(i, 0).toString());
                int sale_quantity = Integer.parseInt(views.sales_table.getValueAt(i, 2).toString());
                double sale_price = Double.parseDouble(views.sales_table.getValueAt(i, 3).toString());
                double sale_subtotal = sale_quantity * sale_price;
                saleDao.registerSaleDetailQuery(product_id, sale_id, sale_quantity, sale_price, sale_subtotal);
                product = productDao.searchid(product_id);
                int amount = product.getproduct_quantity() - sale_quantity;
                productDao.updateStockQuery(amount, product_id);
            }
            JOptionPane.showMessageDialog(null, "Venta Generada");
            cleanTableTemp();
            cleanAllFieldsSales();
        }
    }
    
    private void CalculateSales(){
        double total = 0;
        int numRow = views.sales_table.getRowCount();
        for(int i = 0; i < numRow; i++){
            total = total + Double.parseDouble(String.valueOf(views.sales_table.getValueAt(i,4)));
        }views.txt_sale_total_to_pay.setText(""+total);
    }
    
    public void cleanTable(){
        for(int i = 0; i < model.getRowCount(); i++){
            model.removeRow(i);
            i = i - 1;
        }
    }
    
    public void cleanTableTemp(){
        if(temp != null && temp.getRowCount() > 0){
            for(int i = 0; i < temp.getRowCount(); i++){
                temp.removeRow(i);
                i = i - 1;
            }
        }
    }

    public void cleanFieldsSales(){
        views.txt_sale_product_code.setText("");
        views.txt_products_name.setText("");
        views.txt_sale_quantli.setText("");
        views.txt_product_id.setText("");
        views.txt_sale_price.setText("");
        views.txt_sale_subtotal.setText("");
        views.txt_sale_stok.setText("");
    }
    
    public void cleanAllFieldsSales(){
        views.txt_sale_product_code.setText("");
        views.txt_products_name.setText("");
        views.txt_sale_quantli.setText("");
        views.txt_product_id.setText("");
        views.txt_sale_price.setText("");
        views.txt_sale_subtotal.setText("");
        views.txt_sale_stok.setText("");
        views.txt_sale_customer_id.setText("");
        views.txt_sale_customer.setText("");
        views.txt_sale_total_to_pay.setText("");
    }
}