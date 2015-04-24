package aiassignment6;

import java.util.Random;

/**
 *
 * Maeda Hanafi
 */
public class Crossover {
    private Node root1;
    private Node root2;
   private int index = 0;
   public Crossover(Node root1, Node root2){
       this.root1 = root1;
       this.root2 = root2;
   }
    public void start(){
 
        System.out.println("Tree 1:");
        setIndexAndParent(root1, 0);
        int num1 = index+1;

        System.out.println("Tree 2:");
        index = 0;
        setIndexAndParent(root2, 0);
        int num2 = index+1;
        //crossover tree
       GPcrossover(root1, num1, root2, num2);
    }
    private Node GPcrossover(Node inTree1, int numberOfNodes1, Node inTree2, int numberOfNodes2){
        Random rand = new Random();
        int rand1 = rand.nextInt(numberOfNodes1-2)+1;
        int rand2 = rand.nextInt(numberOfNodes2-2)+1;
        System.out.println("Random index for 1:"+rand1);
        System.out.println("Random index for 2:"+rand2);

        //subtrees based on indexes
        this.findLastIndices(inTree1, rand1, 'o');
        //System.out.println("lastIndex:"+this.lastIndex);
        Node subTree1 = this.getSubtree(inTree1, rand1, this.lastIndex);
        System.out.println("1st subtree:");
        this.preorder(subTree1, 0);

        lastIndex = -1;
        this.findLastIndices(inTree2, rand2,'o');
        //System.out.println("lastIndex:"+this.lastIndex);
        Node subTree2 = this.getSubtree(inTree2, rand2, this.lastIndex);
        System.out.println("2st subtree:");
        this.preorder(subTree2, 0);

        System.out.println("Crossover-----------------------------------------------------------");
        inTree1 = crossOver(inTree1, subTree2, rand1);
        System.out.println("New tree 1:");
        this.preorder(inTree1, 0);

        inTree1 = crossOver(inTree2, subTree1, rand2);
        System.out.println("New tree 2:");
        this.preorder(inTree1, 0);

        return null;
    }
    public Node getClone1(){
        return root1.getClone();
    }
    public Node getClone2(){
        return root2.getClone();
    }
    int lastIndex = -1;
    private void findLastIndices(Node tree, int index, char dir){
        if(tree!=null){
            if( lastIndex==-1){
                if(tree.index==index && (tree.getChild1()==null && tree.getChild2()==null)){//copying leaf node?
                    lastIndex = tree.index;
                    return;
                }
                if(tree.index>index && (tree.getChild1()==null && tree.getChild2()==null)&& dir=='r'){
                    lastIndex = tree.index;
                    return;
                }
            }
                findLastIndices(tree.getChild1(), index, 'l');
                findLastIndices(tree.getChild2(), index, 'r');

        }
        return;
    }
    private Node getSubtree(Node tree, int index, int lastIndex){
       Node cpyTree = null;
        if(tree!=null){
            //System.out.println(tree.getData()+": "+"tree index: "+tree.index+", index: "+index);

            if(index<=tree.index && tree.index<=lastIndex){
                //System.out.println(tree.getData());
                cpyTree = new Node(tree.getData(), getSubtree(tree.getChild1(), index, lastIndex), getSubtree(tree.getChild2(), index, lastIndex));

                return cpyTree;

            }else{

                Node traverse1 = getSubtree(tree.getChild1(), index, lastIndex);
                Node traverse2 = getSubtree(tree.getChild2(), index, lastIndex);
                if(traverse1!=null && traverse2==null)
                    return traverse1;
                if(traverse2!=null && traverse1==null)
                    return traverse2;
            }

        }

        return null;
    }

     private void setIndexAndParent(Node node, int depth) {
        if(node!=null ){
            node.index = index++;
            if(node.getChild1()!=null)
                node.getChild1().setParent(node);
            if(node.getChild2()!=null)
                node.getChild2().setParent(node);

            System.out.println("depth:"+depth+" data:"+node.getData()+" index:"+node.index);
            setIndexAndParent(node.getChild1(), depth+1);
            setIndexAndParent(node.getChild2(), depth+1);
        }
        return;
    }
     public void preorder(Node node, int depth) {
        if(node!=null ){
            System.out.println("depth:"+depth+" data:"+node.getData()+" ");
            preorder(node.getChild1(), depth+1);
            preorder(node.getChild2(), depth+1);
        }
        return;
    }
     private Node crossOver(Node inTree, Node subTree, int index){
        if(inTree!=null){
            //System.out.println(inTree.getData()+": "+"tree index: "+inTree.index+", index: "+index);

            if(index==inTree.index){
                //System.out.println(inTree.getData());
                return subTree;
            }else{
                Node traverse1 = crossOver(inTree.getChild1(), subTree, index);
                Node traverse2 = crossOver(inTree.getChild2(), subTree, index);
                inTree.setChild1(traverse1);
                inTree.setChild2(traverse2);
            }
        }
        return inTree;
     }
}
