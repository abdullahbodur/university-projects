public class Token  implements Comparable<Token>{

    public String id;
    public Part part;
    public int order;
    public int credit;

    public Token(int credit,String id, Part part,int order) {
        this.credit = credit;
        this.id = id;
        this.part = part;
        this.order = order;
    }


    @Override
    public int compareTo(Token o) {

        return Integer.compare(this.credit,o.credit);
    }


    @Override
    public String toString() {
        return String.format("%1$s %2$s %3$d%n",this.id,this.part.name,this.credit);
    }
}
