/*
 * Filename: Graph.java
 
 * Title: Program to find out longest path in weighted directed acyclic graph

 * Problem description: Find out the longest walk in weighted DAG. Walk is determined based on
 * proportional probability of the edge weight.
 * A random edge from the graph can be ignored at the beginning of the walk.
 * Longest walk is determined by number of edges visited.

 * Description: This program is based on BFS traversal of Graph. Following algorithm demonstrates
 *              use of BFS to get longest distance walk from source vertex based on edge weight
 
 * Algorithm:
 *          1. Create an adjacency list for all the vertices in a graph
 *          2. From source vertex s do
 *              2.1 queue.add(s)
 *              2.2 For every adjacent vertex u do
 *                  3.1.1 weight = 0
 *                  3.1.2 if (weight < weight(s, u))
 *                  3.1.2.1 weight = weight(s, u)
 *                  3.1.2.2 nxtNode = u
 *              2.3 Repeat step 2 for vertex u
 *          3. print queue
 */

import java.io.*;
import java.util.*;

// Structure to store node information
class Node {
    int vertex;
    int weight;
}

public class Graph {
    private int vertices;               // Stores number of vertices for particular graph
    private LinkedList<Node> adj[];     // Adjacency list of vertices
    private static int nVertices;       // Stores scanned number of vertices
    private static int nEdges;          // Stores scanned number of edges
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // Initialize graph nodes with its adjacency list
    Graph(int vertices) {
        this.vertices = vertices;   // assign number of vertices
        adj = new LinkedList[vertices];
        for (int idx = 0; idx < vertices; idx++) {
            adj[idx] = new LinkedList();
        }
    }

    // Create adjacency list
    void addEdge (int vertex1, int vertex2, int weight) {
        Node n = new Node();
        n.vertex = vertex2;
        n.weight = weight;

        adj[vertex1].add(n);
    }

    // create topological sort from adjacency list
    void topologicalSortHelper(int vertex, boolean visited[], Stack stack) {
        visited[vertex] = true;

        Iterator<Node> it = adj[vertex].iterator();
        while(it.hasNext()) {
            Node nxtNode = it.next();
            if (!visited[nxtNode.vertex]) {
                topologicalSortHelper(nxtNode.vertex, visited, stack);
            }
        }

        stack.push(new Integer(vertex));
    }

    void getWalkingDistance(Stack stack) {
        Integer vertex = (Integer) stack.pop();
        Iterator<Node> iter = adj[vertex].iterator();
        int totalWeight = 0;

        while(iter.hasNext()) {
            Node nxtNode = iter.next();
            if (nxtNode.vertex == (int) stack.peek()) {
                totalWeight += nxtNode.weight;
            }
        }

        System.out.println("Total weight: " + totalWeight);
    }

    void topologicalSort() {
        Stack stack = new Stack();

        boolean[] visited = new boolean[this.vertices];
        for (int idx = 0; idx < this.vertices; idx++) {
            visited[idx] = false;
        }

        for (int idx = 0; idx < this.vertices; idx++) {
            if (visited[idx] == false) {
                topologicalSortHelper(idx, visited, stack);
            }
        }

        getWalkingDistance(stack);
        // while(stack.empty() == false) {
        //     System.out.println(stack.pop() + " ");
        // }
    }
    

    private static void scan() throws IOException {
        String input = br.readLine();
        String[] graph = input.split(" ");
        nVertices = Integer.parseInt(graph[0]);
        nEdges = Integer.parseInt(graph[1]);
    }

    public static void main (String [] args) {
        try {
            scan();
            while ((2 <= nVertices && nVertices<= 10000) && (1 <= nEdges && nEdges <= 100000)) {
                Graph g = new Graph(nVertices);

                for (int idx = 0; idx < nEdges; idx++) {
                    try {
                        String[] edge = br.readLine().split(" ");
                        g.addEdge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]), Integer.parseInt(edge[2]));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("Following is the topological sort: ");

                g.topologicalSort();

                try {
                    scan();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}