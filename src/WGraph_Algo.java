package ex1.src;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {

    private weighted_graph graph;

    public WGraph_Algo() {
        this.graph = new WGraph_DS();
    }


    @Override
    public void init(weighted_graph g) {// O(1)
        this.graph = g;
    }

    @Override
    public weighted_graph getGraph() {
        return graph;
    }//O(1)

    @Override
    public weighted_graph copy() {//O(V+(E*V))
        WGraph_DS graphcopy = new WGraph_DS();
        for (node_info nodes : graph.getV()) {
            int key = nodes.getKey();
            graphcopy.addNode(key);
            graphcopy.getNode(key).setInfo(graph.getNode(key).getInfo());
            graphcopy.getNode(key).setTag(graph.getNode(key).getTag());
        }
        for (node_info nodes : graph.getV()) {
            for (node_info edges : graph.getV(nodes.getKey())) {
                graphcopy.connect(nodes.getKey(), edges.getKey(), graph.getEdge(nodes.getKey(), edges.getKey()));
            }
        }
        graphcopy.setMC(graph.getMC());
        return graphcopy;

    }

    @Override
    public boolean isConnected() {//O(E*LOG2(V))
        if (this.graph.getV().size() < 2) {
            return true;
        }
        int src = ((node_info) graph.getV().toArray()[0]).getKey();// Conversion to array and then option for first node
        int dest = ((node_info) graph.getV().toArray()[1]).getKey();// Conversion to array and then access to the second node
        shortestPathDist(src,dest); // Sending to function without caring about the result
        for (node_info node:graph.getV()){ // Running on all nodes
            if(((WGraph_DS.NodeInfo)node).getTag()==Double.MAX_VALUE){ // If there is some node that is detached from the rest then its distance is equal to infinity and then the graph is not linked
             return false;
            }
        }
          return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {//O(E*LOG2(V)) Realized according to Elizabeth's Pesado
        if (graph.getV().size()==0){
            return -1;
        }
        if(graph.getNode(src) == null || graph.getNode(dest) == null)
        {
            return -1;
        }

        for (node_info nodes : graph.getV()) {// A loop that starts up
            ((WGraph_DS.NodeInfo) nodes).setTag(Double.MAX_VALUE);
            ((WGraph_DS.NodeInfo) nodes).setPred(-1);
            ((WGraph_DS.NodeInfo) nodes).setVisited(false);
        }
        WGraph_DS.NodeInfo s = (WGraph_DS.NodeInfo) graph.getNode(src);//Holding the start node
        s.setTag(0);// The node from which we begin
        PriorityQueue<WGraph_DS.NodeInfo> queue = new PriorityQueue<WGraph_DS.NodeInfo>();
        for (node_info nodes : graph.getV()) {// Run on the nodes of the graph
            queue.add((WGraph_DS.NodeInfo) nodes); // Add to the priority queue all the vertices of the graph, when the distance of all of them is equal to infinity except the first vertex is zero
        }
        while (!queue.isEmpty()) {
            WGraph_DS.NodeInfo u = queue.poll(); // Pull out the node with the minimum value
            for (node_info nei : graph.getV(u.getKey())) {// Go through all the neighbors of u which is the node that removed
                if (!((WGraph_DS.NodeInfo) nei).isVisited()) { // As long as we have not visited this node already
                    double t = ((WGraph_DS.NodeInfo) u).getTag() + graph.getEdge(nei.getKey(), u.getKey());//t = holds the distance -Calculate the distance to the node where you are


                    if (((WGraph_DS.NodeInfo) nei).getTag() > t) { // Checks whether to update the distance of the neighbors of the current vertex that is smaller
                        ((WGraph_DS.NodeInfo) nei).setTag(t);
                        ((WGraph_DS.NodeInfo) nei).setPred(u.getKey()); // Update where I got to the node

                         //Do some action that will update the queue
                        queue.remove(nei);
                        queue.add((WGraph_DS.NodeInfo) nei);

                    }
                }
            }
            u.setVisited(true);
        }
        if(((WGraph_DS.NodeInfo) graph.getNode(dest)).getTag() == Double.MAX_VALUE)// When there is no path between two nodes return -1
            return -1;
        return ((WGraph_DS.NodeInfo) graph.getNode(dest)).getTag(); // Returns the length of the distance
    }


    @Override
    public List<node_info> shortestPath(int src, int dest) {//O(E*LOG2(V))
        if (graph.getNode(src) == null || graph.getNode(dest) == null) return null;
        List<node_info> path = new ArrayList<>();// Start a new list
        List<node_info> pathRe = new ArrayList<>(); // Initialize another list that we will enter in the correct order
        double dist = shortestPathDist(src, dest);
        if(dist==-1)// f there is no track at all then the graph is not a link and I want to access something I can not
            return null;

        path.add(graph.getNode(dest));

        while (src != dest) {
            dest = ((WGraph_DS.NodeInfo) graph.getNode(dest)).getPred();// Update of the distance to be the previous one
            path.add(graph.getNode(dest));
        }
        if (path.size()==0)return null;

        for (int i = path.size() - 1; i >= 0; i--) {
            pathRe.add(path.get(i));
        }
        return pathRe;
    }

    @Override
    public boolean save(String file) { //O(V+(E*V))
        try {
            FileWriter myWriter = new FileWriter(file);


            // A line that copies the nodes to the file
            for (node_info nodes : graph.getV()) {
                myWriter.write(nodes.getKey() + "-" + nodes.getTag() + "-"+ nodes.getInfo() + " ");
            }
            myWriter.write("\n");

            for (node_info nodes : graph.getV()) {
                for (node_info nei : graph.getV(nodes.getKey())) {
                    int neiKey = nei.getKey();
                    int nodesKey = nodes.getKey();
                    myWriter.write(nodesKey + "-" + neiKey + "-" + graph.getEdge(nodesKey, neiKey) + " ");
                }
            }
            myWriter.write("\n");
            myWriter.write(graph.getMC() + " " + graph.edgeSize());

            myWriter.close();
            return true;
        }

        catch(Exception ex)
        {
            return false;
        }
    }

    @Override
    public boolean load(String file) {//O(E+V)
        try
        {
            WGraph_DS newGraph = new WGraph_DS();


            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);

            //We split the first row into an array so we know the amount of nodes
            String[] nodes = myReader.nextLine().split(" ");
            for (int i =0 ; i<nodes.length; i++){
                String[] node = nodes[i].split("-");
                int key = Integer.parseInt(node[0]);
                if(node[2].equals("null"))
                    node[2] = "";
                newGraph.addNode(key);
                newGraph.getNode(key).setTag(Double.parseDouble(node[1]));
                newGraph.getNode(key).setInfo(node[2]);
            }
            // Creating a new array in which we connected the two vertices and a side through using the known function
            String[] edge = myReader.nextLine().split(" ");
            for (int i =0 ; i<edge.length; i++){
                String[] connect = edge[i].split("-");
                newGraph.connect(Integer.parseInt(connect[0]),Integer.parseInt(connect[1]),Double.parseDouble(connect[2]));
            }

            String[] extraData = myReader.nextLine().split(" ");
            newGraph.setMC(Integer.parseInt(extraData[0]));

            myReader.close();
            graph = newGraph; //Once we have managed to copy everything we will copy to the inner graph
            return true;
        }

        catch(Exception e)
        {
           return false;
        }


    }




}
