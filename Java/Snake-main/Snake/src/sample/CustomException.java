package sample;

public class CustomException extends Exception {
    /*
    Daftar flag dan definisinya
    flag            definisi
    0               Inventory penuh
    1               Invalid index
    */

    CustomException(String msg){
        super(msg);
    }

    public void printStackTrace(){
        System.err.println(this.getMessage());
    }
}
