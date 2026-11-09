package Controllers;

import Models.Categories;
import Models.CategoriesDao;
import Models.DynamicComboBox;
import static Models.EmployeesDao.rol_user;
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
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

public class CategoriesController implements ActionListener, MouseListener, KeyListener {

    //Encapsular Variables
    private Categories categories;
    private CategoriesDao categoriesDao;
    private SystemView views;
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public CategoriesController(Categories categories, CategoriesDao categoriesDao, SystemView views) {
        this.categories = categories;
        this.categoriesDao = categoriesDao;
        this.views = views;

        //Registrar
        this.views.btn_register_category.addActionListener(this);

        //Buscar
        this.views.txt_search_category.addKeyListener(this);
        
        //Modificar
        this.views.btn_update_category.addActionListener(this);
        
        //Tabla
        this.views.categories_table.addMouseListener(this);
        
        //Eliminar
        this.views.btn_delete_category.addActionListener(this);
        
        //Cancelar
        this.views.btn_cancel_category.addActionListener(this);
        
        //Menu category
        this.views.jLabelCategories.addMouseListener(this);
        getCategoryName();
        
        AutoCompleteDecorator.decorate(views.cmb_product_category);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == views.btn_register_category) {
            if (views.txt_category_names.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "El campo de nombre es obligatorio");
            } else {
                categories.setname(views.txt_category_names.getText().trim());
                if (categoriesDao.registerCategoriesQuery(categories)) {
                    cleanTable();
                    cleanFields();
                    listAllCategories();
                    JOptionPane.showMessageDialog(null, "Categoria registrada con exito");
                } else {
                    JOptionPane.showMessageDialog(null, "Hubo un error al registrar categoria");
                }
            }
        } else if (ae.getSource() == views.btn_update_category) {
            if (views.txt_category_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para Continuar");
            } else {
                if (views.txt_category_names.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "El campo de nombre es obligatorio");
                } else {
                    categories.setid(Integer.parseInt(views.txt_category_id.getText()));
                    categories.setname(views.txt_category_names.getText().trim());
                    if (categoriesDao.updateCategoriesQuery(categories)) {
                        cleanTable();
                        cleanFields();
                        listAllCategories();
                        views.btn_register_category.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Categoria modificada con exito");
                    } else {
                        JOptionPane.showMessageDialog(null, "Hubo un error al modificar la categoria");
                    }
                }
            }
        }else if(ae.getSource() == views.btn_delete_category){
            int row = views.categories_table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Selecciona la categoria al Eliminar");
            }else{
               int id = Integer.parseInt(views.categories_table.getValueAt(row,0).toString());
               int question = JOptionPane.showConfirmDialog(null, "En realidad quieres eliminar esta Categoria?");
               if(question == 0 && categoriesDao.deleteCategoriesQuery(id) !=false){
                   cleanFields();
                   cleanTable();
                   listAllCategories();
                   
                   views.btn_register_category.setEnabled(true);
                   JOptionPane.showMessageDialog(null, "Categoria eliminada con exito");
                   
               }
            }
        }else if(ae.getSource() == views.btn_cancel_category){
            cleanFields();
            views.btn_register_category.setEnabled(true);
        }
    }
    
    public void cleanFields(){
        views.txt_category_id.setText("");
        views.txt_category_names.setText("");
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == views.categories_table) {
            int row = views.categories_table.rowAtPoint(me.getPoint());
            views.txt_category_id.setText(views.categories_table.getValueAt(row, 0).toString());
            views.txt_category_names.setText(views.categories_table.getValueAt(row, 1).toString());
            views.btn_register_category.setEnabled(false);
        }else if(me.getSource() == views.jLabelCategories){
            if(rol.equals("Administrador")){
                views.jTabbedPane1.setSelectedIndex(5);
                cleanFields();
                cleanTable();
                listAllCategories();
            }else{
                views.jTabbedPane1.setEnabledAt(5,false);
                views.jLabelCategories.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No tienes los privilegios de Administrador");
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
        if (ke.getSource() == views.txt_search_category) {
            cleanTable();
            listAllCategories();
        }
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    public void listAllCategories() {
        if (rol.equals("Administrador")) {
            List<Categories> list = categoriesDao.listCategoriesQuery(views.txt_search_category.getText());
            model = (DefaultTableModel) views.categories_table.getModel();
            Object[] row = new Object[2];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getid();
                row[1] = list.get(i).getname();
                model.addRow(row);
            }
            views.categories_table.setModel(model);
        }
    }
    
    //Mostrar el nombre de las Categorias
    public void getCategoryName(){
        List<Categories> list = categoriesDao.listCategoriesQuery(views.txt_search_category.getText());
        for(int i = 0; i< list.size(); i++){
            int id = list.get(i).getid();
            String name = list.get(i).getname();
            views.cmb_product_category.addItem(new DynamicComboBox(id,name));
        }
    }

}