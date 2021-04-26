public class CustomException extends Exception {

    CustomException(String msg){
        super(msg);
    }

    public void printStackTrace(){
        System.err.println(this.getMessage());
    }
}
