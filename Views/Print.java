
package Views;

import Models.Purchase;
import Models.PurchaseDao;
import java.awt.Graphics;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class Print extends javax.swing.JFrame {
    Purchase purchases = new Purchase();
    PurchaseDao purchaseDao = new PurchaseDao();
    DefaultTableModel model = new DefaultTableModel();
    


    public Print(int id) {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Factura de Compra");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        txt_invoice.setText(""+id);
        listAllPurchaseDetails(id);
        calculatePurchase();
        
    }
    
    public void listAllPurchaseDetails(int id){
        List<Purchase> list = purchaseDao.listPurchaseDetailQuery(id);
        model = (DefaultTableModel) purchase_details_table.getModel();
        Object[]  row = new Object[7];
        for(int i = 0; i < list.size(); i++){
            row[0] = list.get(i).getproduct_name();
            row[1] = list.get(i).getpurchase_amount();
            row[2] = list.get(i).getpurchase_price();
            row[3] = list.get(i).getpurchase_subtotal();
            row[4] = list.get(i).getsupplier_name_product();
            row[5] = list.get(i).getpurchase();
            row[6] = list.get(i).getcreated();
            model.addRow(row);
        }
        purchase_details_table.setModel(model);
    }
    
    public void calculatePurchase(){
        double total = 0;
        int numRow = purchase_details_table.getRowCount();
        for(int i = 0; i < numRow; i++){
            total = total + Double.parseDouble(String.valueOf(purchase_details_table.getValueAt(i,3)));
        }
        txt_total.setText(""+total);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        form_print = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_invoice = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        purchase_details_table = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        btn_print_purchase = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        form_print.setBackground(new java.awt.Color(0, 255, 255));
        form_print.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 51, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icono.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 90));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 110, 80));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("FAMACIA DE EMERGENCIA");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 250, 20));

        txt_invoice.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jPanel1.add(txt_invoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 120, 30));

        form_print.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 80));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("Detalles de la Compra");
        form_print.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        purchase_details_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Cantidad", "Precio", "Subtotal", "Proovedor", "Comprado por", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(purchase_details_table);
        if (purchase_details_table.getColumnModel().getColumnCount() > 0) {
            purchase_details_table.getColumnModel().getColumn(0).setMinWidth(100);
            purchase_details_table.getColumnModel().getColumn(5).setMinWidth(110);
            purchase_details_table.getColumnModel().getColumn(6).setMinWidth(80);
        }

        form_print.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 650, 220));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setText("Total");
        form_print.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, -1, -1));

        txt_total.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });
        form_print.add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 430, 140, 30));

        btn_print_purchase.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_print_purchase.setText("Imprimir");
        btn_print_purchase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_print_purchaseActionPerformed(evt);
            }
        });
        form_print.add(btn_print_purchase, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, -1, -1));

        getContentPane().add(form_print, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 630));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
       Toolkit tk = form_print.getToolkit();
       PrintJob pj = tk.getPrintJob(this,null,null);
       Graphics graphics = pj.getGraphics();
       form_print.print(graphics);
       graphics.dispose();
       pj.end();
       
    }//GEN-LAST:event_txt_totalActionPerformed

    private void btn_print_purchaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_print_purchaseActionPerformed
        Toolkit tk = form_print.getToolkit();
    PrintJob pj = tk.getPrintJob(this, "Factura de Compra", null);
    if (pj != null) {
        Graphics graphics = pj.getGraphics();
        if (graphics != null) {
            form_print.print(graphics);
            graphics.dispose();
            pj.end();
    }//GEN-LAST:event_btn_print_purchaseActionPerformed

        
    
    }
}
    public static void main(String args[]) {
      
      
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_print_purchase;
    private javax.swing.JPanel form_print;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable purchase_details_table;
    public javax.swing.JButton txt_invoice;
    public javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
