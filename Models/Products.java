
package Models;

public class Products {
    private int id;
    private int code;
    private String name;
    private String description;
    private Double unit_price;
    private int product_quantity;
    private String created;
    private String update;
    private int category_id;
    private String category_name;
    
    public Products(){
        
    }
    
    public Products(int id, int code, String name, String description, Double unit_price, int product_quantity, String created, String update, String category_name){
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.unit_price = unit_price;
        this.product_quantity = product_quantity;
        this.created = created;
        this.update = update;
        this.category_name = category_name;
        
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
    
    public String getname(){
        return name;
    }
    
    public void setname(String name){
        this.name = name;
    }
    
    public String getdescription(){
        return description;
    }
    
    public void setdescription(String description){
        this.description = description;
    }
    
  public Double getunit_price(){
      return unit_price;
  }
   
   public void setunit_price(double unit_price){
       this.unit_price = unit_price;
   }
   
   public int getproduct_quantity(){
       return product_quantity;
   }
   
   public void setproduct_quantity(int product_quantity){
       this.product_quantity = product_quantity;
   }
   
   public String getcreated(){
       return created;
   }
   
   public void setcreated(String created){
       this.created = created;
   }
   
   public String getupdate(){
       return update;
   }
   
   public void setupdate(String update){
       this.update = update;
   }
   
   public int getcategory_id(){
       return category_id;
   }
   
   public void setcategory_id(int category_id){
       this.category_id = category_id;
   }
   
   public String getcategory_name(){
       return category_name;
   }
   
   public void setcategory_name(String category_name){
       this.category_name = category_name;
        
    
    }
    
}
