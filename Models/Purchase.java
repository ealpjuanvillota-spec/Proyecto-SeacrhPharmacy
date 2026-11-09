
package Models;

public class Purchase {
    private int id;
    private int code;
    private String product_name;
    private int purchase_amount;
    private double purchase_price;
    private double purchase_subtotal;
    private double total;
    private String created;
    private String supplier_name_product;
    private String purchase;
    
    public Purchase(){
        
    }
    
    public Purchase(int id, int code, String product_name, int purchase_amount, double purchase_price, double purchase_subtotal, double total, String created, String supplier_name_product, String purchase){
        this.id = id;
        this.code = code;
        this.product_name = product_name;
        this.purchase_amount = purchase_amount;
        this.purchase_price = purchase_price;
        this.purchase_subtotal = purchase_subtotal;
        this.total = total;
        this.created = created;
        this.supplier_name_product = supplier_name_product;
        this.purchase = purchase;
    }
    
    public int getid(){
        return id;
    }
    
    public void setid(int id){
        this.id = id;
    }
    
    public int getcode(){
        return code;
    }
    
    public void setcode(int code){
        this.code = code;
    }
    
    public String getproduct_name(){
        return product_name;
    }
    
    public void setproduct_name(String product_name){
        this.product_name = product_name;
    }
    
    public int getpurchase_amount(){
        return purchase_amount;
    }
    
    public void setpurchase_amount(int purchase_amount){
        this.purchase_amount = purchase_amount;
    }
    
    public double getpurchase_price(){
        return purchase_price;
    }
    
    public void setpurchase_price(double purchase_price){
        this.purchase_price = purchase_price;
    }
    
    public double getpurchase_subtotal(){
        return purchase_subtotal;
    }
    
    public void setpurchase_subtotal(double purchase_subtotal){
        this.purchase_subtotal = purchase_subtotal;
    }
            
    public double gettotal(){
        return total;
    }
    
    public void settotal(double total){
        this.total = total;
    }
    
    public String getcreated(){
        return created;
    }
    
    public void setcreated(String created){
        this.created = created;
    }
    
    public String getsupplier_name_product(){
        return supplier_name_product;
    }
    
    public void setsupplier_name_product(String supplier_name_product){
        this.supplier_name_product = supplier_name_product;
    }
    
    public String getpurchase(){
        return purchase;
    }
    
    public void setpurchase(String purchase){
        this.purchase = purchase;
    }
}