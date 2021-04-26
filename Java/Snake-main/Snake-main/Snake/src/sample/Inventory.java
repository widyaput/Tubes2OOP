package sample;
import java.util.*;

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

    public T getElement(int idx) throws CustomException{
        T dummy;
        try {
            dummy = this.storage.get(idx);
        } catch (Exception e) {
            throw new CustomException("Invalid index");
        }
        return dummy;
    }


    public void setElement(T element, int idx) throws CustomException{
        try {
            this.storage.set(idx, element);
        } catch (Exception e) {
            throw new CustomException("Invalid index");
        }
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
        for (int i = 0; i < this.storage.size(); i++){
            System.out.println(String.format("%d. %s", i+1, storage.get(i)));
        }
    }

    public int getSize() {
        return storage.size();
    }

    public Vector<T> getStorage(){
        return this.storage;
    }

}





