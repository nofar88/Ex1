package ex1.tests;


import ex1.src.WGraph_DS;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class WGraph_DSTest {

    @Test
    public void nodesTest1() {
        WGraph_DS graph = new WGraph_DS();
        for (int i = 0; i < 100; i++) {
            graph.addNode(i);
            graph.getNode(i).setInfo("info " + i);

        }
        assertEquals(100, graph.nodeSize());
        assertEquals(100, graph.getV().size());
        assertEquals(5, graph.getNode(5).getKey());
        assertEquals("info 5", graph.getNode(5).getInfo());
        assertEquals(6, graph.removeNode(6).getKey());
        assertEquals(99,graph.nodeSize() );
        assertEquals(null, graph.removeNode(101));
    }

    @Test
    public void nodesTest2() {
        WGraph_DS graph = new WGraph_DS();
        assertEquals(0, graph.nodeSize());
        assertEquals(0, graph.getV().size());// בודק לי את כמות הקודקודים שיש ברשימה
        assertThrows(NullPointerException.class, () -> graph.getNode(5).getKey());
    }

    @Test
    public void edgesTest1() {
        WGraph_DS graph = new WGraph_DS();
        for (int i = 1; i <= 10; i++) {
            graph.addNode(i);
        }
        graph.connect(1, 2, 5);
        graph.connect(1, 8, 4);
        graph.connect(9, 10, 0);
        graph.connect(5, 2, 3);

        assertEquals(4, graph.edgeSize());
        assertEquals(5, graph.getEdge(1, 2));
        assertEquals(5, graph.getEdge(2, 1));
        assertTrue(graph.hasEdge(1, 8));
        assertTrue(graph.hasEdge(8, 1));
        assertFalse(graph.hasEdge(4, 5));
        assertEquals(2, graph.getV(1).size());
        assertEquals(0, graph.getV(4).size());
        graph.removeEdge(1, 8);
        assertEquals(3, graph.edgeSize());
        assertEquals(1, graph.getV(1).size());
        graph.removeNode(9);
        assertEquals(2, graph.edgeSize());
        assertEquals(0, graph.getV(10).size());
        graph.connect(1, 2, -9);
        assertEquals(2, graph.edgeSize());// If the weight is negative then the number of ribs does not change.
        assertFalse(graph.hasEdge(11, 8));
        assertEquals(16, graph.getMC());
    }

    @Test
    public void  edgesTestEmptyGraph() { // Number of ribs in an empty hip
        WGraph_DS graph = new WGraph_DS();
        assertFalse(graph.hasEdge(1,2));
        graph.removeEdge(1,5);
        assertEquals(0, graph.nodeSize());
        assertEquals(0, graph.edgeSize());
        assertEquals(-1, graph.getEdge(1,6));
        assertEquals(0, graph.getMC());
    }




    @Test
    public void edgesTestnumberOnFullGraph() {// A test that tests a full graph
        WGraph_DS graph = new WGraph_DS();
        for (int i = 1; i <= 5; i++) {
            graph.addNode(i);
        }

        for (int i = 1; i <=5 ; i++) {
            for (int j = 1; j <=5 ; j++) {
                graph.connect(i,j,5);
            }
        }

        assertTrue(graph.hasEdge(1,2));
        graph.removeEdge(1,5);
        assertEquals(9, graph.edgeSize());
        assertEquals(-1, graph.getEdge(1,6));
        assertEquals(16, graph.getMC());
    }

    @Test
    public void testTime(){
        long start = new java.util.Date().getTime();
       // LocalDateTime start= LocalDateTime.now();
        WGraph_DS graph = new WGraph_DS();
        for (int i = 0; i <=Math.pow(10,6) ; i++) {
            graph.addNode(i);
        }
        for (int i = 0; i <=10 ; i++) {
            for (int j = i; j <Math.pow(10,6) ; j++) {
                graph.connect(i,j,1);
            }
        }
        long end =new java.util.Date().getTime();
        double dt= (end-start)/1000.0;
        //System.out.println(dt + "seconds");
        assertTrue(dt < 10);

    }





}
