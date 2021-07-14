
class Item {
    private String s = new String(" 2000 ");

    public void append(String a){
        s+=a;
    }
    public void op2(){
        append(".1 ");
    }
    public void op3(){
        append(".2 ");
    }
    public void op4(){
        append(".3 ");
    }
    public String toString(){
        return s;
    }
}
class NewItem extends Item{

    //Change Method
    public void op1(){
        append(" n1 ");
    }
    public void ap4(){
        super.op4();
        append(" n4 ");
    }

}
class NewNewItem extends NewItem{
    //Change Method
    public void op2(){
        super.op2();
        append(" n3 ");
    }
    public void op4(){
        append(" nn4 ");
    }
}
public class Library2{
    //test the new class;
    public static void main(String[] args){
        NewNewItem x = new NewNewItem();
        x.op1();
        x.op2();
        x.op3();
        x.op4();
        System.out.println(x.toString());

    }
}


