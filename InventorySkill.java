import java.util.Vector;


//TO DO : GANTI JADI KELAS SKILL
public class InventorySkill extends Inventory<Integer> {
    private Vector<Integer> jumlah;

    InventorySkill(){
        super();
        this.jumlah = new Vector<>();
    }

    public void addElement(Integer element) throws CustomException{
        if (InventoryBase.banyakItem != InventoryBase.maksItem ){
            try {
                this.getIndex(element);
                this.jumlah.set(this.getIndex(element), this.jumlah.get(this.getIndex(element))+1);
            } catch (Exception e) {
                //TODO: handle exception
                this.storage.add(element);
                this.jumlah.add(1);
            }
            InventoryBase.banyakItem++;
        }
        else throw (new CustomException("Inventory penuh"));
    }

    public Integer getIndex(Integer element) throws CustomException{
        for (int i =0; i < this.storage.size(); i++) {
            if (this.storage.get(i).equals(element)){
                return i;
            }
        }
        throw new CustomException("Invalid index");
    }

    // public Integer getAmount(Integer element){
    //     for (int i =0; i < this.storage.size(); i++) {
    //         if (this.storage.get(i).equals(element)){
    //             return this.jumlah.get(i);
    //         }
    //     }
    //     return 0;
    // }

    public Integer removeElement(int index) throws CustomException{
        Integer dummy;
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
            System.out.println(this.storage.get(i).toString() + " jumlahnya " + this.jumlah.get(i).toString());
        }
    }
}
