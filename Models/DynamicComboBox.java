package Models;

public class DynamicComboBox {
    private int id;
    private String name;

    // Único constructor con parámetros
    public DynamicComboBox(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
