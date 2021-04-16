import java.util.Vector;


//TO DO : GANTI JADI KELAS SKILL
public class InventorySkill extends Inventory<Skill> {
    private Vector<Integer> jumlah;

    InventorySkill(){
        super();
        this.jumlah = new Vector<>();
    }

    public void addElement(Skill element) throws CustomException{
        if (InventoryBase.banyakItem != InventoryBase.maksItem ){
            try {
                this.getIndex(element);
                this.jumlah.set(this.getIndex(element), this.jumlah.get(this.getIndex(element))+1);
            } catch (Exception e) {
                this.storage.add(element);
                this.jumlah.add(1);
                this.jumlah.sort((Integer b, Integer a) -> this.storage.get(this.jumlah.indexOf(a)).compareTo(this.storage.get(this.jumlah.indexOf(b))));
                this.storage.sort((Skill a, Skill b) -> b.compareTo(a));

            }
            InventoryBase.banyakItem++;
        }
        else throw (new CustomException("Inventory penuh"));
    }

    public Integer getIndex(Skill element) throws CustomException{
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

    public Skill removeElement(int index) throws CustomException{
        Skill dummy;
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
            System.out.println(String.format("%-40s %s", this.storage.get(i).toString(), "\t\tjumlahnya " + this.jumlah.get(i).toString()));
        }
    }
}
