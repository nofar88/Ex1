# EX1
## undirected  weighted graph:
### The project was about a weighted undirected graph,
 ### A graph consisting of nodes and edges where each edge has a weight
### We got 3 interfaces that we had to implement: node_info, weighted_grapg, weighted_graph_algorithms.

## Implementation classes:

 1. `NodeInfo` - A class that implements node_info and represents a single vertex.
 2. `EdgeData` - A class that implements edge_data and represents a single edge.
 3. `WGraph_DS` - A class that implements weighted_graph and represents a weighted non-directional graph that can contain multiple nodes and edges. I used HashMap data structure because the methods of this data structure have really good complexity that helped me be efficient, for example add, delete, and other functions all on average of O(1).
 4. `WGraph_algo` - A class that impements weighted_graph_algorithms and executes and computes algorithms on graphs.
    

## Remarks & notes:

 - I implemented each of the classes according to the interface I got for it (or created), and several additional hepler functions.
 - I chose to make edge_data interface to represent the edgesbecause this makes it easier to store information on the edges and to have better access to them. The class EdgeData is the class that implements edge_data.
 - `EdgeData` and `NodeInfo` are inner calsses inside `WGraph_DS`, according to the implementations' instructions (regarding `NodeInfo`, which I also applied on `EdgeData` because of the similarity in their roles in a graph).
 - When I needed to implement weighted_graph I chose to do so using two HashMap data structures, when the first HashMap is for the nodes, and the second HashMap is for the edges. The edges' map consists of an inner HashMap for each node, so that I have easier access between the edges and the verticies they connect, and the neighbors of the node.
 - in the classes `NodeInfo, EdgeData, WGraph_DS` I added an **Equality function** used for comparing the actual contents of two objects of the same type (rather than their addresses in the memory).
 - In `EdgeData` I implemented basic functions such as getters and setters of the nodes, tag, weight and information.
 - `NodeInfo` has two constructors, one which does not receive any arguments and creates a default node with increasing ID (to ensure there aren't two nodes with the same key), and another constructor which gets all the required parameters as arguments.
 - My project includes test files to all of the mentioned classes and functionality.
## Function explanations:

**hasEdge**- checks if there is a edge between two nodes
**getEdge**- Returns the edge with its weight.
**connect**- creates a edge between two nodes, if the edge we want to create exists then just update the weight of the edge.
**removeNode**- Deleting a node in a graph, when I delete a node in a graph I have to take care of deleting the edges that are connected to the deleted node, i.e. I have to delete myself from my list of neighbors,
**removeEdge**- Deleting a edge in a graph, when I delete a edge I have to make sure that the deletion of the edge is to the two nodes to which it is attached. I.e. two-way erasure.
**init**- A function that takes care of initializing the graph.
**isConnected**- checks if the graph is a linked graph or not. I implemented the function so the idea behind it is to use my knowledge that  if I can not get from one node to another node i.e. there is no path between them then the distance is infinite.
I used the shortestPathDist function that I implemented, the function knows how to go through the whole graph. So I know that if I sent to a function and got an infinity that means there is no path between two particular nodes then the graph is not a link.

**shortestPathDist**- I implemented the function by the **Dijkstra** algorithm that I learned in the lecture. The algorithm goes through all the nodes in the graph, and solves the problem of finding the "cheapest" route from a source node to a target node in a weighted graph.
The algorithm works like this: at first all the nodes are marked as not visited, and their distance is defined as infinity, except for the first node whose distance will be set to 0. Then I go over all the neighbors of that node and update their weight, and insert them to a queue.
Then, one by one, I take out a node from the queue which has the minimum weight, and check for the node's neighbors. **I always update the weight to be the cheapest weight.**
The algorithm is over when we finished going through all the nodes in the queue.
**shortestPath**- Returns a list of vertices with the shortest path, the implemention of the function is done using a Dijkstra algorithm.
**save**- saves to the file the graph I am working on.
**load**- Restores the graph from the file.





![](https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Edsger_Wybe_Dijkstra.jpg/300px-Edsger_Wybe_Dijkstra.jpg)
The man behind Dijkstra algorithm
