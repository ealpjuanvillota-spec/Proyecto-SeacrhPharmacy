
package Models;

public class Suppliers {
    private int id;
    private String name;
    private String description;
    private String address;
    private String telephone;
    private String email;
    private String city;
    private String created;
    private String update;
    
    
    public Suppliers(){
        
    }
    
    public Suppliers(int id, String name, String description, String address, String telephone, String email, String city, String created, String update){
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.city = city;
        this.created = created;
        this.update = update;
    }
    
    public int getid(){
        return id;
    }
    
    public void setid(int id){
        this.id = id;
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
    
    public String getaddress(){
        return address;
    }
    
    public void setaddress(String address){
        this.address = address;
        
    }
    
    public String gettelephone(){
        return telephone;
    }
    
    public void settelephone(String telephone){
        this.telephone = telephone;
    }
    
    public String getemail(){
        return email;
    }
    
    public void setemail(String email){
        this.email = email;
    }
    
    public String getcity(){
        return city;
    }
    
    public void setcity(String city){
        this.city = city;
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
    
}
