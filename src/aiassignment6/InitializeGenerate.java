
package aiassignment6;
import java.util.ArrayList;

import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
/**
 *
 * @author user
 */
public class InitializeGenerate {
    ArrayList<Node> terminals = new ArrayList<Node>();
    ArrayList<Node> operators = new ArrayList<Node>();
    int maxTreeHeight;
    public InitializeGenerate(){
        
    }
    public void start(){
        //ask user to enter an expression in infix notation
        System.out.println("Enter in the expression in infix notation:");
        String expression = new Scanner(System.in).nextLine();
        Node root = createTree(expression);
        initialize(root);

        System.out.println("Enter in maximum tree height:");
        Scanner scan2 = new Scanner(System.in);
        maxTreeHeight = scan2.nextInt();
        

    }

    /*
     * Puts operators and terminals from the tree to the proper arraylists
     */
    public void initialize(Node root){
        if(root.getData()=='+'|| root.getData()=='-' || root.getData()=='*' || root.getData()=='/' || root.getData()=='e' || root.getData()=='s'){
            operators.add(root);
        }else{
            terminals.add(root);
        }
        if(root.getChild1() ==null || root.getChild2()==null){
            return;
        }
        initialize(root.getChild1());
        initialize(root.getChild2());
    }



    public Node GPgenerate( String growthStrategy){
        if(growthStrategy.equals("grow")){
            Node root = grow(0);
            //System.out.println("Using the grow method:");
            preorder(root, 0);
            return root;
        }else{
            Node root = full(0);
            //System.out.println("Using the full method:");
            preorder(root, 0);
            return root;
        }

    }
    public Node full(int depth){
        Node node = null;
        ///* if depth < maximum tree depth
        if(depth<this.maxTreeHeight-1){
            /*  node ← random(I)*/
            node = new Node(random(operators).getData(), full(depth+1), full(depth+1));
        }else{
            /*node ← random(T )*/
            node = new Node(random(terminals).getData(), null, null);
        }
        return node;
    }

    public Node grow(int depth){
        ///*if depth < maximum tree depth
        Node node = null;
        if(depth<maxTreeHeight-1){
            if(depth!=0){
                ArrayList<Node> union = new ArrayList<Node>();
                for(int i=0; i<terminals.size(); i++){
                    union.add(terminals.get(i));
                }
                for(int i=0; i<operators.size(); i++){
                    union.add(operators.get(i));
                }
                ///*node ← random(T ∪ I)
                node = new Node(random(union).getData(),grow(depth+1), grow(depth+1));//this.random(union);
            }else if(depth==0){//set the root
                node = new Node(random(operators).getData(), grow(depth+1), grow(depth+1));//random(operators);
            }
        }else{
            /*node ← random(T )*/
            node = new Node(random(terminals).getData(), null, null);//this.random(terminals);
        }
        return node;
    }

    public Node random(ArrayList<Node> in){
        if(in.size()==1){
            return in.get(0);
        }
        Random rand = new Random();
        int index = rand.nextInt(in.size()-1);
        return in.get(index);

    }

    public void preorder(Node node, int depth) {
        if(node!=null && depth<maxTreeHeight){
            System.out.println("depth:"+depth+" "+node.getData()+" ");
            preorder(node.getChild1(), depth+1);
            preorder(node.getChild2(), depth+1);
        }
        return;
    }

       public Node createTree(String expression){
        //find root first
        String left = "";
        String operator = "";
        String right = "";
        boolean foundRoot = false;
        Node root = null;
        int parantheses = 0;
        //System.out.println("-------------------------------------------------operate on "+expression);
        if(expression.length()==1){
            //System.out.println("CHILD NODE:"+expression+";");
            return new Node(expression.charAt(0), null, null);
        }else{
            //go through each char in expression
            int i=0;
            while(i<expression.length()){
                if(expression.charAt(i)=='('){
                    parantheses++;
                    if(!foundRoot){
                        left = left+expression.charAt(i)+"";
                        //System.out.println("found a ( placed it in left");
                    }else if(foundRoot){
                        right = right+expression.charAt(i);
                        //System.out.println("found a ( placed it in right");
                    }
                }else if(expression.charAt(i)==')'){
                    parantheses--;
                    if(!foundRoot){ //not the root. add it to the left string
                        left = left+expression.charAt(i)+"";
                        //System.out.println("found a ) placed it in left");
                    }else if(foundRoot){
                        right = right+expression.charAt(i);
                        //System.out.println("found a ) placed it in right");
                    }
                }else if((parantheses==0 ) && !foundRoot && (expression.charAt(i)=='+'|| expression.charAt(i)=='-' || expression.charAt(i)=='*' || expression.charAt(i)=='/' || expression.charAt(i)=='e')){
                    //found root
                    operator = expression.charAt(i)+"";
                    //System.out.println("Root:"+expression.charAt(i));
                    foundRoot = true;
                }else if((parantheses==0) && !foundRoot && (expression.charAt(i)=='s' && expression.charAt(i+1)=='i' && expression.charAt(i+2)=='n')){
                    operator = expression.charAt(i) + expression.charAt(i+1) +expression.charAt(i+2)+"";
                    operator = expression.charAt(i)+"";
                    //System.out.println("Root:"+expression.charAt(i));
                    foundRoot = true;
                    i=i+2;
                }else if(!foundRoot){ //not the root. add it to the left string
                    left = left+expression.charAt(i)+"";
                    //System.out.println("left:"+left);
                }else if(foundRoot){
                    right = right+expression.charAt(i);
                    //System.out.println("right:"+right);
                }
                i++;
            }
            //System.out.println("left:"+left);
            //System.out.println("right:"+right);

            left = cleanString(left);
            right = cleanString(right);
            //System.out.println("Operate on "+left);
            //System.out.println("Operate on "+right);
            root = new Node(operator.charAt(0), createTree(left), createTree(right));
            return root;
        }

    }
    public String cleanString(String inString){
        String newString="";
        for(int i=0;i<inString.length(); i++){
            if(inString.charAt(i)!=' '){
                newString+=inString.charAt(i);
            }
        }
        //System.out.println("sub clean string:"+newString);
        if(newString.charAt(0)=='(' && newString.charAt(newString.length()-1)==')'){
            newString = newString.substring(1, newString.length()-1);
        }

        //System.out.println("clean string:"+newString);
        return newString;
    }

}


