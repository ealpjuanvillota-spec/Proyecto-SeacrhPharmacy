
package Models;

public class Sales {
    // Variables
    private int id;
    private String sale_date;
    private double total_to_play;
    private int customer_id;
    private String customer_name;
    private int employee_id;
    private String employee_name;
    
    public Sales(){
        
    }
    
    public Sales(int id, String sale_date, double total_to_play, int customer_id, String customer_name, int employee_id, String employee_name ){
        this.id = id;
        this.sale_date = sale_date;
        this.total_to_play = total_to_play;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.employee_id = employee_id;
        this.employee_name = employee_name;
    }
    
    public int getid(){
        return id;
    }
    
    public void setid(int id){
        this.id = id;
    }
    
    public String getsale_date(){
        return sale_date;
    }
    
    public void setsale_date(String sale_date){
        this.sale_date = sale_date;
    }
    
    public double gettotal_to_play(){
        return total_to_play;
    }
    
    public void settotal_to_play(double total_to_play){
        this.total_to_play = total_to_play;
    }
    
    public int getcustomer_id(){
        return customer_id;
    }
    
    public void setcustomer_id(int customer_id){
        this.customer_id = customer_id;
    }
    
    public String getcustomer_name(){
        return customer_name; 
    }
    
    public void setcustomer_name(String customer_name){
        this.customer_name = customer_name;
    }

    public int getemployee_id(){
        return employee_id;
    }

    public void setemployee_id(int employee_id){
        this.employee_id = employee_id;
    }

    public String getemployee_name(){
        return employee_name;
    }

    public void setemployee_name(String employee_name){
        this.employee_name = employee_name;
    }
}