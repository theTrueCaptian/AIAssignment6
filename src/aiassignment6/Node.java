
package aiassignment6;



/**
 *
 * Node class
 * Maeda Hanafi
 */
public class Node {
    private char character;
    private Node parent=null;
    private Node child1=null;
    private Node child2=null;
    int index;
    double fitness;
    double probability;
    public Node(char operator, Node child1, Node child2){
        this.character = operator;
        this.child1 = child1;
        this.child2 = child2;
    }
    public Node(char operator, Node child1, Node child2, double fitness){
        this.character = operator;
        this.child1 = child1;
        this.child2 = child2;
        this.fitness=fitness;
    }
    public void setParent(Node parent){
        this.parent = parent;
    }
    public char getData(){
        return character;
    }
    public Node getChild1(){
        return child1;
    }
    public Node getChild2(){
        return child2;
    }
    public void setChild1(Node child1){
        this.child1 = child1;
    }
    public void setChild2(Node child2){
        this.child2 = child2;
    }
    public boolean isTerminal(){
        if(this.character == '+' || this.character == '-' || this.character == '*' || this.character == '/' || this.character == 's' ){
            return false;
        }
        return true;
    
    }
    public void setData(char inChar){
        this.character = inChar;
    }
    public Node getClone(){        
        return copy(this);
    }
    private Node copy(Node rt){
        if(rt==null)
            return null;
        else{
            return new Node(rt.getData(), copy(rt.child1), copy(rt.child2), rt.fitness);
        }
    }
}
