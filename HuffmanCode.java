/**
 * @author Prénom Nom - Matricule
 * @author Prénom Nom - Matricule
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

class Node implements Comparable<Node> {
    public   char symbol;
    public int frequency;
    Node left;
    Node right;

    Node(char symbol, int frequency) {
        this.symbol = symbol;
        this.frequency = frequency ;
    }


    // Internal Node
    Node(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    boolean isLeaf() {
         return (this.left == null) && (this.right == null);
    }

    @Override
    public int compareTo(Node node) {
        if(this.frequency < node.frequency)
            return -1;
        if(this.frequency == node.frequency ){
            if(this.symbol < node.symbol)
                return -1;
            else if (this.symbol == node.symbol)
                return 0;
        }

        return 1;
    }

    @Override
    public String toString() {
        String resultas = "";
        resultas += this.symbol + "    "  + this.frequency;
        return resultas;
    }
}

class HuffmanCode {
   static  Queue<Node> queue1 = new PriorityQueue<Node>() ;
    /**
     * @param text Texte à analyser
     * @return Fréquence de chaque caractère ASCII sur 8 bits
     */
    private static int[] getCharacterFrequencies(String text) {

        int [] tableauCharAscii = new int [128];//longeur  nombre de caractere table ascii

        for (int i = 0; i < text.length() ; i++) {
            char character = text.charAt(i);
            int assci = (int) character;

            tableauCharAscii[assci]+=1;
        }

        return tableauCharAscii;
    }

    /**
     * @param charFreq Fréquence de caractères
     * @return Nœud racine de l'arbre
     */
    private static Node getHuffmanTree(int[] charFreq) {
        PriorityQueue<Node> queue = new PriorityQueue<Node>() ;

        for (int i = 0; i < charFreq.length ; i++) {
            if(charFreq[i] != 0) {

                int frequency = charFreq[i];
                char indexChar = (char) i;

                Node node = new Node(indexChar,frequency);

                queue.add(node);
            }

        }

        Node root = new Node(null,null);

        while(queue.size() > 1 ){

            Node rootCuur = new Node(null ,null);
            Node n1 = queue.peek();
            queue.poll();

            Node n2 = queue.peek();
            queue.poll();

            rootCuur.frequency = n1.frequency + n2.frequency;

            rootCuur.left = n1;
            rootCuur.right = n2;

            root = rootCuur;
            queue.add(rootCuur);

        }
        return root;
    }

    /**
     * @param node Nœud actuel
     * @param code Code Huffman
     */
    private static void printTable(Node node, String code) {
       if (node == null)
            return;
       if(!node.isLeaf()) {
           printTable(node.left, code + "0");
           printTable(node.right, code + "1");
       }
       if(node.symbol != 0)
       System.out.println(node.toString()+ "    " + code);

    }

    /**
     * @param node Nœud de départ
     */
    private static void printGraph(Node node) {

        Queue<Node> nodeAVisiter = new LinkedList<>() ;
        if(node != null)
            nodeAVisiter.add(node);
        while(!nodeAVisiter.isEmpty()){
            Node nodeCurren = nodeAVisiter.poll();

            //if(!nodeCurren.isLeaf()) {
                System.out.println(nodeCurren.hashCode() + " [Label=" + nodeCurren.frequency + ", shape=rectangle, width=.5]");


           // }
            if(nodeCurren.left != null){
                System.out.println(nodeCurren.hashCode()+" -- "+nodeCurren.left.hashCode()+" [label=0]");
                nodeAVisiter.add(nodeCurren.left);
                }
            if(nodeCurren.right != null){
                System.out.println(nodeCurren.hashCode()+" -- "+nodeCurren.right.hashCode()+" [label=1]");
                nodeAVisiter.add(nodeCurren.right);
                }
        }
       /* if(node == null)
            return;

        System.out.println(node.hashCode()+"  " +node.frequency);

        if(!node.isLeaf()) {
            if (node.left != null){
                 printGraph(node.left);
            }
            if(node.right != null) {
                printGraph(node.right);
            }
        }
          System.out.println(node.hashCode()+"  " +node.frequency+node.symbol);*/


    }

    // Ne pas modifier
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Node root = getHuffmanTree(getCharacterFrequencies(reader.readLine()));

        // Table
        if (args.length == 0 || Arrays.asList(args).contains("table")) {
            System.out.println("Char Freq Code\n---- ---- ----");
            printTable(root, "");
       }

        // Graphe
      if (args.length == 0 || Arrays.asList(args).contains("graph")) {
            printGraph(root);
        }
    }
}
