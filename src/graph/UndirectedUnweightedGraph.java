package graph;
import java.util.ArrayList;



/**
 * This class implements general operations on a graph as specified by UndirectedGraphADT.
 * It implements a graph where data is contained in Vertex class instances.
 * Edges between verticies are unweighted and undirected.
 * A graph coloring algorithm determines the chromatic number. 
 * Colors are represented by integers. 
 * The maximum number of vertices and colors must be specified when the graph is instantiated.
 * You may implement the graph in the manner you choose. See instructions and course material for background.
 */
 
 public class UndirectedUnweightedGraph<T> implements UndirectedGraphADT<T> {
   // private class variables here.
   
   private int MAX_VERTICES;
   private int MAX_COLORS;
    // TODO: Declare class variables here.

   private java.util.Map<Vertex<T>, java.util.List<Vertex<T>>> map;
   private int numEdges;
   /**
    * Initialize all class variables and data structures. 
   */   
   @SuppressWarnings("unchecked")
   public UndirectedUnweightedGraph (int maxVertices, int maxColors){
      MAX_VERTICES = maxVertices;
      MAX_COLORS = maxColors; 
     // TODO: Implement the rest of this method.
      map = new java.util.HashMap<>();
   }

   /**
    * Add a vertex containing this data to the graph.
    * Throws Exception if trying to add more than the max number of vertices.
   */
   public void addVertex(T data) throws Exception {
    // TODO: Implement this method.
    if (getNumVertices() + 1 > MAX_VERTICES) throw new Exception("directly");
    map.put(new Vertex<T>(data), new java.util.LinkedList<Vertex<T>>());
   }
   
   /**
    * Return true if the graph contains a vertex with this data, false otherwise.
   */   
   public boolean hasVertex(T data){
    // TODO: Implement this method.
    for (Vertex<T> v: map.keySet()){
      if (v.data.equals(data)) return true;
    }
    return false;
   } 

   /**
    * Add an edge between the vertices that contain these data.
    * Throws Exception if one or both vertices do not exist.
   */   
   public void addEdge(T data1, T data2) throws Exception{
    // TODO: Implement this method.
    if ((!hasVertex(data1)) || (!hasVertex(data2))) throw new Exception("vertex not present");
    for (Vertex<T> v: map.keySet()){
      if (v.data.equals(data1)) map.get(v).add(new Vertex<T>(data2));
      if (v.data.equals(data2)) map.get(v).add(new Vertex<T>(data1));
    }
    numEdges++;
   }

   /**
    * Get an ArrayList of the data contained in all vertices adjacent to the vertex that
    * contains the data passed in. Returns an array of zero length if no adjacencies exist in the graph.
    * Throws Exception if a vertex containing the data passed in does not exist.
   */   
   public ArrayList<T> getAdjacentData(T data) throws Exception{
    // TODO: Implement this method.
      if (!hasVertex(data)) throw new Exception();
      Vertex<T> tempV = null;
      for (Vertex<T> v: map.keySet()){
        if (v.data.equals(data)) tempV = v;
      }
      ArrayList<T> output = new ArrayList<T>();
      if (map.get(tempV) == null) return output;
      java.util.Iterator<Vertex<T>> itr = map.get(tempV).iterator();
      
      while (itr.hasNext()){
        Vertex<T> t = itr.next();
        if (t != null)
          output.add(t.data);
      }

      return output;
   }

   public java.util.LinkedList<Integer> getAdjacentColor(T data) throws Exception{
    // TODO: Implement this method.
    if (!hasVertex(data)) throw new Exception();
    Vertex<T> tempV = null;
    for (Vertex<T> v: map.keySet()){
      if (v.data.equals(data)) tempV = v;
    }
    java.util.LinkedList<Integer> output = new java.util.LinkedList<Integer>();
    if (map.get(tempV) == null) return output;
    java.util.Iterator<Vertex<T>> itr = map.get(tempV).iterator();
    
    while (itr.hasNext()){
      Vertex<T> t = itr.next();
      if (t != null)
        output.add(t.color);
    }

    return output;
   }
   
   /**
    * Returns the total number of vertices in the graph.
   */   
   public int getNumVertices(){
    // TODO: Implement this method.
      return map.keySet().size();
   }

   /**
    * Returns the total number of edges in the graph.
   */   
   public int getNumEdges(){
    // TODO: Implement this method.
    /*
    int count = 0; 
    for (Vertex<T> v : map.keySet()) { 
        count += map.get(v).size(); 
    } 
    count = count / 2;
    return count;
    */
    return numEdges;
   }

   /**
    * Returns the minimum number of colors required for this graph as 
    * determined by a graph coloring algorithm.
    * Throws an Exception if more than the maximum number of colors are required
    * to color this graph.
   */   
   public int getChromaticNumber() throws Exception{
    // TODO: Implement this method.
      int highestColorUsed = -1;
      for (Vertex<T> v : map.keySet()){
        if (v.color == -1){
          java.util.LinkedList<Integer> adj = getAdjacentColor(v.data);
          int colorToUse = getLowestUnusedColor(adj);
          if (colorToUse + 1 > MAX_COLORS) throw new Exception();
          setColor(v, colorToUse);
          highestColorUsed = Math.max(highestColorUsed, colorToUse);
        }
      }
      if (highestColorUsed + 1 > MAX_COLORS) throw new Exception();
      return highestColorUsed + 1;
   }
   
   private int getLowestUnusedColor(java.util.LinkedList<Integer> adj) throws Exception{
      java.util.Iterator<Integer> itr = adj.iterator();
      int low = 0;
      while (itr.hasNext()){
        int temp = itr.next();
        if (temp == low && temp != -1){
          low = low + 1;
          itr = adj.iterator();
        }
      }
      return low;
   }

   private void setColor(Vertex<T> v, int color){
     v.color = color;
     for (Vertex<T> vrt : map.keySet()){
        java.util.List<Vertex<T>> list = map.get(vrt);
        for (Vertex<T> vrt2 : list){
          if (vrt2.data.equals(v.data)){
            vrt2.color = color;
          }
        }
     }
   }
}