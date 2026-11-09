package Controllers;

import Models.EmployeesDao;
import Views.SystemView;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static Models.EmployeesDao.*;

public class SettingControllers implements MouseListener {

    private SystemView views;

    // Colores
    private final Color COLOR_NORMAL = new Color(18, 45, 61);
    private final Color COLOR_HOVER = new Color(152, 202, 63);

    public SettingControllers(SystemView views) {

        this.views = views;

        // Color inicial de los paneles
        views.jPanel6Customers.setBackground(COLOR_NORMAL);
        views.jPanel5Putchases.setBackground(COLOR_NORMAL);
        views.jPanel4Products.setBackground(COLOR_NORMAL);
        views.jPanel8Employes.setBackground(COLOR_NORMAL);
        views.jPanel10Suplimers.setBackground(COLOR_NORMAL);
        views.jPanel11Categorias.setBackground(COLOR_NORMAL);
        views.jPanel12Reports.setBackground(COLOR_NORMAL);
        views.jPanel13Settings.setBackground(COLOR_NORMAL);

        // MouseListener
        views.jPanel6Customers.addMouseListener(this);
        views.jPanel5Putchases.addMouseListener(this);
        views.jPanel4Products.addMouseListener(this);
        views.jPanel8Employes.addMouseListener(this);
        views.jPanel10Suplimers.addMouseListener(this);
        views.jPanel11Categorias.addMouseListener(this);
        views.jPanel12Reports.addMouseListener(this);
        views.jPanel13Settings.addMouseListener(this);
        views.jLabelSales.addMouseListener(this);
        
        Profile();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

        if (e.getSource() == views.jPanel6Customers) {
            views.jPanel6Customers.setBackground(COLOR_HOVER);

        } else if (e.getSource() == views.jPanel5Putchases) {
            views.jPanel5Putchases.setBackground(COLOR_HOVER);

        } else if (e.getSource() == views.jPanel4Products) {
            views.jPanel4Products.setBackground(COLOR_HOVER);

        } else if (e.getSource() == views.jPanel8Employes) {
            views.jPanel8Employes.setBackground(COLOR_HOVER);

        } else if (e.getSource() == views.jPanel10Suplimers) {
            views.jPanel10Suplimers.setBackground(COLOR_HOVER);

        } else if (e.getSource() == views.jPanel11Categorias) {
            views.jPanel11Categorias.setBackground(COLOR_HOVER);

        } else if (e.getSource() == views.jPanel12Reports) {
            views.jPanel12Reports.setBackground(COLOR_HOVER);

        } else if (e.getSource() == views.jPanel13Settings) {
            views.jPanel13Settings.setBackground(COLOR_HOVER);
        } else if (e.getSource() == views.jLabelSales){
            views.jLabelSales.setBackground(COLOR_HOVER);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

        if (e.getSource() == views.jPanel6Customers) {
            views.jPanel6Customers.setBackground(COLOR_NORMAL);

        } else if (e.getSource() == views.jPanel5Putchases) {
            views.jPanel5Putchases.setBackground(COLOR_NORMAL);

        } else if (e.getSource() == views.jPanel4Products) {
            views.jPanel4Products.setBackground(COLOR_NORMAL);

        } else if (e.getSource() == views.jPanel8Employes) {
            views.jPanel8Employes.setBackground(COLOR_NORMAL);

        } else if (e.getSource() == views.jPanel10Suplimers) {
            views.jPanel10Suplimers.setBackground(COLOR_NORMAL);

        } else if (e.getSource() == views.jPanel11Categorias) {
            views.jPanel11Categorias.setBackground(COLOR_NORMAL);

        } else if (e.getSource() == views.jPanel12Reports) {
            views.jPanel12Reports.setBackground(COLOR_NORMAL);

        } else if (e.getSource() == views.jPanel13Settings) {
            views.jPanel13Settings.setBackground(COLOR_NORMAL);
            
        } else if (e.getSource() == views.jLabelSales) {
             views.jLabelSales.setBackground(new Color(18,45,61));
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    private void Profile() {
        // Cargar los datos en los campos de texto del perfil
        this.views.txt_id_profile.setText("" + EmployeesDao.id_user);
        this.views.txt_name_profile.setText(EmployeesDao.full_name_user);
        this.views.txt_addres_profile.setText(EmployeesDao.address_user);
        this.views.txt_telephone_profile.setText(EmployeesDao.telephone_user); 
        this.views.txt_email_profile.setText(EmployeesDao.email_user);

   
        this.views.txt_id_profile.setEditable(false);
        this.views.txt_name_profile.setEditable(false);
        this.views.txt_addres_profile.setEditable(false);
        this.views.txt_telephone_profile.setEditable(false);
        this.views.txt_email_profile.setEditable(false);
    }
}
