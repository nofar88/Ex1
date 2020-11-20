package ex1.tests;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WGraph_AlgoTest {


    @Test
    public void copyTest() {
        WGraph_DS graph = new WGraph_DS();
        for (int i = 0; i < 50; i++) {
            graph.addNode(i);
            graph.getNode(i).setInfo("info " + i);
            graph.getNode(i).setTag(i);

        }
        graph.connect(1, 2, 5);
        graph.connect(2, 3, 5);
        graph.connect(3, 4, 5);
        assertEquals(50, graph.nodeSize());
        assertEquals(3, graph.edgeSize());

        WGraph_Algo algo = new WGraph_Algo();
        algo.init(graph);

        assertEquals(graph, algo.getGraph());

        WGraph_DS graphcopy = (WGraph_DS) algo.copy();
        assertEquals(50, graphcopy.nodeSize());
        graphcopy.removeNode(4);
        assertEquals(2, graphcopy.edgeSize());
        assertEquals(49, graphcopy.nodeSize());
        assertEquals(50, graph.nodeSize());

        graphcopy.getNode(30).setTag(100);
        assertEquals(30, graph.getNode(30).getTag());
        assertEquals(100, graphcopy.getNode(30).getTag());

        graphcopy.getNode(30).setInfo("100");
        assertEquals("info 30", graph.getNode(30).getInfo());
        assertEquals("100", graphcopy.getNode(30).getInfo());

        graph.removeNode(15);
        assertEquals(graph, algo.getGraph());
        assertNotEquals(graph,graphcopy);

        assertEquals(15, algo.shortestPathDist(1, 4));
        algo.init(graphcopy);
        assertEquals(-1, algo.shortestPathDist(1, 4));
        assertEquals(10, algo.shortestPathDist(1, 3));
        assertEquals(-1, algo.shortestPathDist(1, 5));

    }

    @Test
    public void emptyGraph() {
        WGraph_DS graph = new WGraph_DS();
        WGraph_Algo algo = new WGraph_Algo();
        algo.init(graph);
        assertTrue(algo.isConnected());
        assertEquals(-1, algo.shortestPathDist(1, 4));
        assertEquals(null, algo.shortestPath(4, 6));

    }

    @Test
    public void weightedGraphNotlinked() {// test that tests a graph that is not a link
        WGraph_DS graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addNode(6);
        graph.connect(1, 2, 5);
        graph.connect(2, 3, 5);
        graph.connect(4, 5, 5);
        graph.connect(2, 6, 10);
        graph.connect(6, 3, 30);

        WGraph_Algo algo = new WGraph_Algo();
        algo.init(graph);
        assertFalse(algo.isConnected());
        assertEquals(10, algo.shortestPathDist(1, 3));
        graph.removeEdge(2, 3);
        assertEquals(45, algo.shortestPathDist(1, 3));
        assertEquals(5, algo.shortestPathDist(1, 2));
        assertEquals(-1, algo.shortestPathDist(3, 5));
    }


    @Test
    public void shorterRouteLinkGraph() {// Checking a short route in a link graph
        WGraph_DS graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addNode(6);
        graph.connect(1, 2, 1);
        graph.connect(1, 3, 2);
        graph.connect(3, 6, 1);
        graph.connect(2, 6, 5);
        graph.connect(2, 4, 1);
        graph.connect(4, 5, 1);
        graph.connect(3, 5, 3);

        WGraph_Algo algo = new WGraph_Algo();
        algo.init(graph);

        assertEquals(3, algo.shortestPathDist(1, 6));
        assertEquals(3, algo.shortestPathDist(1, 5));
        assertEquals(2, algo.shortestPathDist(2, 5));

        assertEquals(5, algo.shortestPathDist(4, 6));
        assertTrue(algo.isConnected());

        List<node_info> temp = algo.shortestPath(1, 6);
        String path = "";
        for (node_info node : temp) {
            path += node.getKey() + "";
        }
        assertEquals("136", path);

        graph.removeEdge(1, 3);
        assertEquals(6, algo.shortestPathDist(1, 6));
        temp = algo.shortestPath(1, 6);
        path = "";
        for (node_info node : temp) {
            path += node.getKey() + "";
        }
        assertEquals("126", path);
        assertTrue(algo.isConnected());

        algo.save("graph1.txt");
        algo.load("graph1.txt");
        assertTrue(algo.isConnected());
    }


    @Test
    public void saveAndLoad() {
        WGraph_DS graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.connect(1, 3, 6);
        graph.connect(3, 4, 10);
        graph.connect(4, 2, 5);
        graph.connect(2, 1, 10);

        WGraph_Algo algo = new WGraph_Algo();
        algo.init(graph);
        assertEquals(10, graph.getEdge(3, 4));
        algo.save("graph2.txt");
        algo.load("graph2.txt");
        assertTrue(algo.isConnected());
        assertEquals(15, algo.shortestPathDist(1, 4));
        assertFalse(algo.load("abc"));
    }

    @Test
    public void extremeCases() {
        WGraph_DS graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(4);
        graph.addNode(6);

        graph.connect(1, 3, 6);
        graph.connect(3, 4, 10);
        graph.connect(4, 2, 5);
        graph.connect(2, 1, 10);
        graph.connect(2, 1, 10);
        graph.connect(2, 1, 10.9);


        WGraph_Algo algo = new WGraph_Algo();
        algo.init(graph);


        assertEquals(10, graph.getEdge(3, 4));
        assertEquals(-1, graph.getEdge(6, 4));
        assertNull(algo.shortestPath(1, 6));
        assertEquals(10, graph.getMC());
        graph.removeNode(2);
        assertEquals(4,graph.nodeSize());
        assertEquals(2, graph.edgeSize());
        assertNull(graph.removeNode(2));
        assertFalse(algo.isConnected());
        assertEquals(16, algo.shortestPathDist(1, 4));
        assertEquals(11, graph.getMC());


    }
}
