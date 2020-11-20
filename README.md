# EX1
## undirected  weighted graph:
### The project was about a weighted undirected graph,
 ### A graph consisting of nodes and edges where each edge has a weight
### We got 3 interfaces that we had to implement: node_info, weighted_grapg, weighted_graph_algorithms.

## Files:

 1. `node_info` - A class that represents vertices.
 2. `weighted_grapg` - An interface that represents a weighted non-directional graph that can support multiple nodes. I used hashap
    data structures because the methods of this data structure have
    Really good complexity that helped me be efficient, for example add,
    delete, and other functions all on average O (1).
    3. `weighted_graph_algorithms` - A class that executes and computes algorithms in graphs.
    

## Remarks:

 - I implemented each of the classes according to the interface I got
   for it,  And I added more functions  for my own use.
 - I chose to make another edge_data interface - a class representing
   the edges. I chose to do this to make it easier to store information
   on the edges and access them.
 - When I needed to implement weighted_graph I chose to do so using two hashmap data structures, when the first hashmap Is for the nodes.
   And the second hashmap is for the edges, this hashmap consists of a hashmap within a hashmap, so that I have easier access between the edges and the neighbors of the node.
 - I added in classes of: `node_info, edge_data, weighted_graph` **Equal function** for comparing the contents of objects rather than the addresses in their memory.
 - In the edge class I did basic functions such as: getters and setters of the nodes, tag weight and information.
## Function explanations:

**hasEdge**- checks if there is a edge between two nodes
**getEdge**- Returns the edge with its weight.
**connect**- creates a edge between two nodes, if the edge we want to create exists then just update the weight of the edge.
**removeNode**- Deleting a node in a graph, when I delete a node in a graph I have to take care of deleting the edges that are connected to the deleted node, i.e. I have to delete myself from my list of neighbors,
**removeEdge**- Deleting a edge in a graph, when I delete a edge I have to make sure that the deletion of the edge is to the two nodes to which it is attached. I.e. two-way erasure.
**init**- A function that takes care of initializing the graph.
**isConnected**- checks if the graph is a linked graph or not. I implemented the function so the idea behind it is to use my knowledge that  if I can not get from one node to another node i.e. there is no path between them then the distance is infinite.
I used the shortestPathDist function that I implemented, the function knows how to go through the whole graph. So I know that if I sent to a function and got an infinity that means there is no path between two particular nodes then the graph is not a link.

**shortestPathDist**- I implemented the function by the **Dijkstra** algorithm that I learned in the lecture.the algorithm goes through all the nodes in the graph, and solves the problem of finding the "cheapest" easiest route from node in the graph to the target in a weighted graph.
The algorithm works like this: at first all the nodes are marked as not visited, and their distance is defined as infinity. Except for the first node whose distance will be set to 0. then go over all the neighbors of that node and update their weight.
Then take out of the queue the node that has the minimum value, and check for that node its neighbors. **I always update the weight to be the cheapest weight.**
The algorithm finishes when we have finished going through all the nodes in the queue.
**shortestPath**- Returns a list of vertices with the shortest path, the implemention  of the function is done using a Dijkstra algorithm.
**save**- saves to the file the graph I am working on.
**load**- Restores the graph from the file.





![](https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Edsger_Wybe_Dijkstra.jpg/300px-Edsger_Wybe_Dijkstra.jpg)
The man behind Dijkstra algorithm
