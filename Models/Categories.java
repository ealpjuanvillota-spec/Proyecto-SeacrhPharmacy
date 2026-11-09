
package Models;

public class Categories {
    //Creacion de variables
    private int id;
    private String name;
    private String created;
    private String update;
    
    public Categories(){
        
    }
    
    public Categories(int id, String name, String created, String update){
        this.id = id;
        this.name = name;
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
