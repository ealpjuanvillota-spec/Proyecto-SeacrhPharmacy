package Models;

public class Employees {
    private int id;
    private String full_name;
    private String username;
    private String address;
    private String telephone;
    private String email;
    private String password;
    private String rol;
    private String created;
    private String update;

    public Employees() {
    }
    
    public Employees(int id, String full_name, String username, String address, String telephone, String email, String password, String rol, String created, String update){
        this.id = id;
        this.full_name = full_name;
        this.username = username;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.created = created;
        this.update = update;
    }
    
    public int getid(){
        return id;
    }
    
    public void setid(int id){
        this.id = id;
    }
    
    public String getfull_name(){
        return full_name;
    }
    
    public void setfull_name(String full_name){
        this.full_name = full_name;
    }
    
    public String getusername(){
        return username;
    }
    
    public void setusername(String username){
        this.username = username;
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
    
    public String getpassword(){
        return password;
    }
    
    public void setpassword(String password){
        this.password = password;
    }
    
    public String getrol(){
        return rol;
    }
    
    public void setrol(String rol){
        this.rol = rol;
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
