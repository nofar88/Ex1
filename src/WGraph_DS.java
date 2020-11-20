package ex1.src;


import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph, Serializable {
    public static final int NO_EDGE = -1;

    HashMap<Integer, node_info> nodes;

    HashMap<node_info, HashMap<node_info,edge_data>> edges;


    int edgeSize;
    int modeCount;
    static int id_counter=0;

    public WGraph_DS() {
        nodes = new HashMap<>();
        edges = new HashMap<>();
        edgeSize = 0;
        modeCount = 0;
    }



    @Override
    public node_info getNode(int key) {
        return nodes.get(key);
    }//O(1)

    @Override
    public boolean hasEdge(int node1, int node2) {//O(1)
        if(nodes.containsKey(node1) && nodes.containsKey(node2)) { // Check if the vertices are at all in the graph so we can continue
            node_info x = this.nodes.get(node1);// Holds first vertex

            if (this.edges.get(x) != null) // Check that the vertex list is not empty
                return this.edges.get(x).containsKey(getNode(node2));// Checks if there is any side between the vertices
        }
        return false;
    }

    @Override
    public double getEdge(int node1, int node2) {//O(1)
        if(hasEdge(node1,node2)){// Checks if there is any side between the vertices
            return this.edges.get(getNode(node1)).get(getNode(node2)).getWeight();// Turning to the hashmap of the ribs, selects the first vertex and asks for its weight with the vertex to which it is connected
        }
        return NO_EDGE;// Returns -1
    }

    @Override
    public void addNode(int key) {//O(1)
        if (!nodes.containsKey(key)){ //Checks whether the vertex you want to add whether it exists or not, if it does not exist then
            node_info newNode = new NodeInfo(key); //Create a new vertex
            nodes.put(key, newNode);
            modeCount++;
        }

    }

    @Override
    public void connect(int node1, int node2, double w) { //O(1)
        if(w<0){
            System.err.println("the Weight of edge must be positive");
            return;
        }
        if(node1==node2){
            System.err.println(" There is no edge between a node and itself ");
            return;
        }
        if(getNode(node1)==null || getNode(node2)==null)
        {
            System.err.println("one of the nodes are not exist");
            return;
        }

        node_info in = nodes.get(node1);
        node_info out = nodes.get(node2);

        if(hasEdge(node1,node2)){ // Checking for a rib
            if(getEdge(node1, node2) != w) modeCount++;// If the weight is really different, only then is a change made added
            this.edges.get(in).get(out).setWeight(w); // Update the weight
            return;
        }

        edge_data newEdge= new Edge(w, in , out); // New rib
        if(this.edges.get(in)==null ){ // initialization
            edges.put(in,new HashMap<>());
        }
        if(this.edges.get(out)==null ){
            edges.put(out,new HashMap<>());
        }
        this.edges.get(in).put(out,newEdge);
        this.edges.get(out).put(in,newEdge);
        modeCount++;
        edgeSize++;
    }

    @Override
    public Collection<node_info> getV() { return nodes.values(); }//O(1)

    @Override
    public Collection<node_info> getV(int node_id) {//O(1)
        if(edges.get(getNode(node_id)) != null){ // Checking that the neighbors' hashmap is not empty
            return new ArrayList<>(edges.get(getNode(node_id)).keySet());
        }
        return new ArrayList<node_info>();
    }

    @Override
    public node_info removeNode(int key) {//O(V)
        if(!this.nodes.containsKey(key)){
            return null;
        }
        HashMap<node_info,edge_data> map = edges.get(getNode(key)); // Gets the hashmap from Key's Map who has all the neighbors and ribs
        if(map != null) { // The vertex has neighbors
            for (node_info nei : map.keySet()) {// Goes over each vertex in the list of neighbors
                HashMap<node_info, edge_data> neig_map = edges.get(nei); //I'm going through the list of Key's specific neighbors
                neig_map.remove(getNode(key)); // I delete myself from his list of neighbors,
                edgeSize--;
            }
            edges.remove(getNode(key));
        }
        modeCount++;
        return nodes.remove(key); // Deletes the vertex itself
    }

    @Override
    public void removeEdge(int node1, int node2) {//O(1)
        if( !this.nodes.containsKey(node1) || !this.nodes.containsKey(node2)){
            System.err.println(" one of the nodes not in the graph");
            return;
        }
        if(!hasEdge(node1,node2)){
            System.err.println(" there is no edge between nodes");
            return;
        }

        edges.get(getNode(node1)).remove(getNode(node2));
        edges.get(getNode(node2)).remove(getNode(node1));
        edgeSize--;
        modeCount++;

    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }//O(1)

    @Override
    public int edgeSize() {
        return edgeSize;
    }//O(1)

    @Override
    public int getMC() {
        return modeCount;
    }//O(1)

    public void setMC(int t) {//O(1)
        this.modeCount=t;

    }

    @Override
    public String toString() {//O(1)
        return "WGraph_DS{" +
                "nodes=" + nodes +
                ", edges=" + edges +
                ", edgeSize=" + edgeSize +
                ", modeCount=" + modeCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {//O(1)
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WGraph_DS wGraph_ds = (WGraph_DS) o;

        if (edgeSize != wGraph_ds.edgeSize) return false;
        if (modeCount != wGraph_ds.modeCount) return false;
        if (!nodes.equals(wGraph_ds.nodes)) return false;
        return edges.equals(wGraph_ds.edges);
    }

    @Override
    public int hashCode() {//O(1)
        //
        int result = nodes.hashCode();
        result = 31 * result + edges.hashCode();
        result = 31 * result + edgeSize;
        result = 31 * result + modeCount;
        return result;
    }

    public class NodeInfo implements node_info, Comparable<NodeInfo>, Serializable{

        private int key;
        private int pred;
        private String info;
        private double tag;
        private boolean visited;



        public NodeInfo(int key) {
            this.key = key;
        }

        public NodeInfo() {
            this.key = id_counter;
            id_counter++;
        }

        public int getPred() {
            return pred;
        }//O(1)

        public void setPred(int pred) {
            this.pred = pred;
        }//O(1)

        public boolean isVisited() {
            return visited;
        }//O(1)

        public void setVisited(boolean visited) {
            this.visited = visited;
        }//O(1)


        @Override
        public int getKey() {
            return key;
        }//O(1)

        @Override
        public String getInfo() {
            return info;
        }//O(1)

        @Override
        public void setInfo(String s) {
            this.info=s;
        }//O(1)

        @Override
        public double getTag() {
            return tag;
        }//O(1)

        @Override
        public void setTag(double t) {
            this.tag=t;
        }//O(1)

        @Override
        public int compareTo(NodeInfo o) {//O(1)
            int ans=0;
            if( this.tag>o.tag){
                ans=1;
            }
            else{
                if ((this.tag<o.tag)){
                    ans=-1;
                }
            }
            return ans;
        }

        @Override
        public String toString() {//O(1)
            return "NodeInfo{" +
                    "key=" + key +
                    ", pred=" + pred +
                    ", info='" + info + '\'' +
                    ", tag=" + tag +
                    ", visited=" + visited +
                    '}';
        }

        @Override
        public boolean equals(Object o) {//O(1)
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            NodeInfo nodeInfo = (NodeInfo) o;

            if (key != nodeInfo.key) return false;
            if (pred != nodeInfo.pred) return false;
            if (Double.compare(nodeInfo.tag, tag) != 0) return false;
            if (visited != nodeInfo.visited) return false;
            return info != null ? info.equals(nodeInfo.info) : nodeInfo.info == null;
        }

        @Override
        public int hashCode() {
            return key;
        }//O(1)
    }


    public class Edge implements edge_data, Serializable {
        private node_info source;
        private node_info destination;
        private double weight;
        private String info;
        private double tag;


        public Edge(double weight, node_info source, node_info destination) {
            this.weight = weight;
            this.source=source;
            this.destination=destination;
        }


        @Override
        public node_info getInNode() {
            return source;
        }//O(1)

        @Override
        public node_info getOutNode() {
            return destination;
        }//O(1)

        @Override
        public void setInNode(node_info inNode) {
            this.source = inNode;
        }//O(1)
        @Override
        public void setOutNode(node_info outNode) {
            this.destination = outNode;
        }//O(1)
        @Override
        public void setWeight(double weight) {
            this.weight = weight;
        }//O(1)

        @Override
        public double getWeight() {
            return weight;
        }//O(1)

        @Override
        public String getInfo() {
            return info;
        }//O(1)

        @Override
        public void setInfo(String s) {
            this.info = s;
        }//O(1)

        @Override
        public double getTag() {
            return tag;
        }//O(1)

        @Override
        public void setTag(double t) {
            this.tag = t;
        }//O(1)


        @Override
        public String toString() {//O(1)
            return "Edge{" +
                    "source=" + source +
                    ", destination=" + destination +
                    ", weight=" + weight +
                    ", info='" + info + '\'' +
                    ", tag=" + tag +
                    '}';
        }

        @Override
        public boolean equals(Object o) {//O(1)
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Edge edge = (Edge) o;

            if (Double.compare(edge.weight, weight) != 0) return false;
            if (Double.compare(edge.tag, tag) != 0) return false;
            if (source != null ? !source.equals(edge.source) : edge.source != null) return false;
            if (destination != null ? !destination.equals(edge.destination) : edge.destination != null) return false;
            return info != null ? info.equals(edge.info) : edge.info == null;
        }


    }
}
