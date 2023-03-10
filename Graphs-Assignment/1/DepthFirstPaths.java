import java.io.FileNotFoundException;
import java.util.Scanner;

public class DepthFirstPaths {
    private boolean[] marked;    // marked[v] = is there an s-v path?
    private int[] edgeTo;        // edgeTo[v] = last edge on s-v path
    private final int s;         // source vertex

    /**
     * Computes a path between {@code s} and every other vertex in graph {@code G}.
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public DepthFirstPaths(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        validateVertex(s);
        dfs(G, s);
    }

    /**
     * Function that marks the vertices which has a connection to the vertex v and adds 
     * 
     * @param G
     * @param v
     */
    // depth first search from v
    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    /**
     * Is there a path between the source vertex {@code s} and vertex {@code v}?
     * @param v the vertex
     * @return {@code true} if there is a path, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns a path between the source vertex {@code s} and vertex {@code v}, or
     * {@code null} if no such path.
     * @param  v the vertex
     * @return the sequence of vertices on a path between the source vertex
     *         {@code s} and vertex {@code v}, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Bag<Integer> path = new Bag<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.add(x);
        path.add(s);
        return path;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * Unit tests the {@code DepthFirstPaths} data type.
     *
     * @param args the command-line arguments
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);

        String filename = "theDatabase.txt";
        String delimiter = " ";

        System.out.print("Enter place to travel from: ");
        String from = in.nextLine();
        System.out.print("Enter place to travel to: ");
        String to = in.nextLine();
        in.close();

        SymbolGraph sg = new SymbolGraph(filename, delimiter); 
        Graph G = sg.graph();

        int start = sg.indexOf(from);
        int end = sg.indexOf(to);

        DepthFirstPaths dfs = new DepthFirstPaths(G, start);

        if(dfs.hasPathTo(end)){
            System.out.println("\n" + sg.nameOf(start) + " to " + sg.nameOf(end));
            for(int x : dfs.pathTo(end)) System.out.println("-" + sg.nameOf(x));
        }
        else{
            System.out.println(sg.nameOf(start) + " to " + sg.nameOf(end) + " not connected ");
        }
    }
}