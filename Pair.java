public class Pair<T1,T2> {
    private T1 first;
    private T2 second;

    Pair(T1 first, T2 second){
        this.first = first;
        this.second = second;
    }

    public T1 getFirst(){
        return this.first;
    }

    public T2 getSecond(){
        return this.second;
    }

    public void setFirst(T1 first){
        this.first = first;
    }

    public void setSecond(T2 second){
        this.second = second;
    }

    public static <T1,T2> Pair<T1,T2> makePair(T1 first, T2 second){
        return new Pair<T1,T2>(first,second);
    }
}
