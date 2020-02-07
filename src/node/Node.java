package node;

public class Node {
    // keep these​​​​​​​​‌‌‌‌‌​​‌‌​​​​​​‌​‌​‌‌‌​ fields
    Node left, right;
    int value;

    public Node find(int v){
        if(v > this.value && this.right != null)
            return right.find(v);
        if(v < this.value && this.left != null)
            return left.find(v);
        if(this.value == v)
            return this;
        return null;
    }
}
