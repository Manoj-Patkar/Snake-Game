import java.util.Stack;

public class Sample {
    public static void main(String args[]){
        Stack<Single> stack = new Stack<>();
        stack.push(new Single(1));
        stack.push(new Single(2));
        stack.push(new Single(3));
        stack.push(new Single(4));

        stack.forEach(p-> System.out.println(p.getFi()));


    }

}
class Single{
    private Integer fi;

    Single (int val){
        fi=val;
    }


    public Integer getFi() {
        return fi;
    }

    public void setFi(Integer fi) {
        this.fi = fi;
    }
}
