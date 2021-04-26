package sample;
import java.util.*;
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
                concurrentSort(this.storage, this.storage, this.jumlah);
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

    public Skill removeElement(int index) throws CustomException{
        Skill dummy;
        try{
            dummy = this.storage.get(index);
            this.jumlah.set(index, this.jumlah.get(index)-1);
            if (this.jumlah.get(index) == 0){
                this.jumlah.remove(index);
                this.storage.remove(index);
            }
            InventoryBase.banyakItem--;
        }catch (Exception e){
            throw new CustomException("Invalid index");
        }

        return dummy;
    }

    public void removeXElement(int index, int count) throws CustomException{
        try {
            this.storage.get(index);
            if (this.jumlah.get(index) < count){
                throw new CustomException("You dont have " + count + " items");
            }
            this.jumlah.set(index, this.jumlah.get(index)-count);
            if (this.jumlah.get(index) == 0){
                this.storage.remove(index);
                this.jumlah.remove(index);
            }
            InventoryBase.banyakItem -= count;
        } catch (Exception e) {
            throw new CustomException("Invalid index");
        }
    }

    public void showStorage(){
        for (int i = 0; i < this.storage.size(); i++){
            System.out.println(String.format("%d. %-40s %s", i+1, this.storage.get(i).toString(), "\t\tjumlahnya " + this.jumlah.get(i).toString()));
        }
    }

    public static void concurrentSort(final Vector<Skill> key, Vector<Skill> v1, Vector<Integer> v2) throws CustomException{

        if(v1.size() != key.size() || v2.size() != key.size())
            throw new CustomException("Semua vektor harus berukuran sama");

        if (key.size() < 2){
            return;
        }

        List<Integer> indeks = new ArrayList<Integer>();
        for(int i =0; i<key.size(); i++){
            indeks.add(i);
        }

        //sort indeks berdasarkan key
        Collections.sort(indeks, new Comparator<Integer>(){
            @Override public int compare(Integer i, Integer j){
                return key.get(i).compareTo(key.get(j));
            }
        });

        Map<Integer, Integer> swapMap = new HashMap<Integer, Integer> (indeks.size());
        List<Integer> swapFrom = new ArrayList<Integer>(indeks.size()), swapTo = new ArrayList<Integer>(indeks.size());
        Set<Integer> keyMap = new HashSet<Integer>();


        for (int i=0; i < key.size(); i++){
            int k = indeks.get(i);
            while (i!=k && keyMap.contains(k)){
                k = swapMap.get(k);
            }

            swapFrom.add(i);
            swapTo.add(k);
            swapMap.put(i,k);
            keyMap.add(i);
        }

        for (int i =0; i < v1.size(); i++){
            Collections.swap(v1, swapFrom.get(i), swapTo.get(i));
        }

        for (int i =0; i < v2.size(); i++){
            Collections.swap(v2, swapFrom.get(i), swapTo.get(i));
        }



    }
}
