
package aiassignment6;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;



/**
 *
 * Maeda Hanafi
 * CSC481 AI
 * Assignment 6
 */
public class MainApp {

    /*
     * 1: Randomly create an initial population of programs from the available
primitives (more on this in Section 2.2).
2: repeat
3: Execute each program and ascertain its fitness.
4: Select one or two program(s) from the population with a probability
based on fitness to participate in genetic operations (Section 2.3).
5: Create new individual program(s) by applying genetic operations with
specified probabilities (Section 2.4).
6: until an acceptable solution is found or some other stopping condition
is met (e.g., a maximum number of generations is reached).
7: return the best-so-far individual.
     */
    ArrayList<Node> generation = new ArrayList<Node>();
    int n;
    int m;
     InitializeGenerate gen;
    public MainApp(){
        GP();
    }
    
    public static void main(String[] args) {
        new MainApp();
    }

    public void GP(){
        //ask for m and n
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter in N:");
        n = scan.nextInt();
        System.out.println("Enter in M:");
        Scanner scan1 = new Scanner(System.in);
        m = scan1.nextInt();
        
        
        //1: Randomly create an initial population of programs from the available primitives
         gen = new InitializeGenerate();
        gen.start();
        for(int i=0;i<n; i++){
            System.out.println("Tree in index "+i);
            String growthStrategy = "";
            if(i%2==0){
                growthStrategy = "grow";
            } else {
                growthStrategy = "full";
            }
            generation.add(gen.GPgenerate(growthStrategy));
            
        }
        int ctr=1;
        for(int k=0; k<m-n; k=k+2){
            System.out.println("Crossover Operation number "+ctr+": ");
            ctr++;
            //2: Execute each program and ascertain its fitness.
            for(int i=0; i< generation.size(); i++){
                 generation.get(i).fitness = this.calcFitness(generation.get(i));
            }
            //sort based on fitness
            this.sort();

            //3: Select one or two program(s) from the population with a probability
            //based on fitness to participate in genetic operations
            double sum=0;
            for(int i=0; i< generation.size(); i++){
                 generation.get(i).probability = this.getProbability(generation.get(i));
                 //System.out.println(generation.get(i).fitness+" : "+generation.get(i).probability);
                 sum=sum+generation.get(i).probability;

            }

            //find parents by
            //randomly selecting two programs between 0 to n
            int  rand1 = 0;
            int rand2=1;

            //4: Create new individual program(s) by applying genetic operations with
            //specified probabilities
            Crossover operator = new Crossover(this.generation.get(rand1), this.generation.get(rand2));
            operator.start();

            this.generation.add(operator.getClone1());
            this.generation.add(operator.getClone2());
        }
        System.out.println("Mutation");
        mutation();
    }
    
    public void mutation(){
        Random rand = new Random();
        int randIndex = rand.nextInt(m-2)+1;
        //mutation
        generation.get(randIndex).setChild1(gen.GPgenerate("grow"));
        
    }

    public void sort(){
         for(int x=0; x<generation.size(); x++){
            int index_of_max = x;
            for(int y=x; y<generation.size(); y++){
                if(generation.get(index_of_max).fitness<generation.get(y).fitness){
                    index_of_max = y;
                }
            }
            
            Node temp = generation.get(x).getClone();
            generation.set(x, generation.get(index_of_max));
            generation.set(index_of_max, temp);
        }
       
    }
    public double getProbability(Node root){
        double dem = 0;
        for(int i=0; i<=n-1; i++){
            dem = dem + (root.fitness -  generation.get(i).fitness);
        }
        return (root.fitness -  generation.get(n-1).fitness)/dem;
    }
    public double calcFitness(Node root){ //simply evaluates the tree
        if(root.getChild1()==null && root.getChild2()==null){//its a child node
            double db =  (double)(Integer.parseInt(root.getData()+""));

            return db;
        }
        if(root.getData()=='+'){
            return calcFitness(root.getChild1())+calcFitness(root.getChild2());
        }else if(root.getData()=='-'){
            return calcFitness(root.getChild1())-calcFitness(root.getChild2());
        }else if(root.getData()=='*'){
            return calcFitness(root.getChild1())*calcFitness(root.getChild2());
        }else if(root.getData()=='/'){
            double y = calcFitness(root.getChild2());
            if(y==0){
                return Math.pow(10, 10);
            }
            return calcFitness(root.getChild1())+y;
        }else if(root.getData()=='s'){
            return Math.sin(calcFitness(root.getChild2())) * calcFitness(root.getChild1());
        }else if(root.getData()=='e'){
            return Math.exp(calcFitness(root.getChild2())) * calcFitness(root.getChild1());
        }else{
            return 0;
        }
    }
}

    