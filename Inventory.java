import java.util.*;


//TODO NGELOMPOKIN POKEMON JIKA DITAMBAH/DIKURANG
public class Inventory<T>{
    protected Vector<T> storage;

    Inventory(){
        this.storage = new Vector<T>();
    }

    public void addElement(T element) throws CustomException{
        if (InventoryBase.banyakItem != InventoryBase.maksItem ){
            this.storage.add(element);
            InventoryBase.banyakItem++;
        }
        else throw (new CustomException("Inventory penuh"));
    }

    public T removeElement(int index) throws CustomException{
        T dummy;
        try{
            dummy = this.storage.get(index);
            this.storage.remove(index);
        }catch (Exception e){
            throw new CustomException("Invalid index");
        }
        InventoryBase.banyakItem--;
        return dummy;
    }

    public void showStorage(){
        for (T t : storage) {
            System.out.println(t.toString());
        }
    }

}


