package Controllers;

import Models.Employees;
import Models.EmployeesDao;
import Views.SystemView;
import Views.farmacia;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LongingController implements ActionListener {
    // Encapsular Variables usando la clase 'farmacia'
    private Employees employee;
    private EmployeesDao employees_dao = new EmployeesDao();
    private farmacia login_view;

    // Constructor que recibe la vista farmacia
    public LongingController(Employees employee, EmployeesDao employees_dao, farmacia login_view) {
        this.employee = employee;
        this.employees_dao = employees_dao;
        this.login_view = login_view;
        this.login_view.getBtnEnter().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
       
        String user = login_view.getTxtUserName().getText().trim();
        String password = String.valueOf(login_view.getTxtPassword().getPassword()).trim();
      
        employee = employees_dao.loginQuery(user, password);
        
        if (employee != null && employee.getusername() != null) {
            if (employee.getrol() != null && employee.getrol().equalsIgnoreCase("Administrador")) {
                SystemView admin = new SystemView();
                admin.setVisible(true);
            } else {
                SystemView aux = new SystemView();
                aux.setVisible(true);
            }
            this.login_view.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Usuario o password incorrecto");
        }
    }
}