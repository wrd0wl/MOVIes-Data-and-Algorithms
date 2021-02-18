package src.implementation;

import java.util.*;

public class Graph<T>{
    private HashMap<T, List<T>> graph;

    public Graph(){
        this.graph = new HashMap<>();
    }

    public void addVertex(T vertex) {
        graph.putIfAbsent(vertex, new LinkedList<T>());
    }

    public void addEdge(T source, T destination) {
        if (!graph.containsKey(source)){
            addVertex(source);
        }
        
        if(!graph.get(source).contains(destination)){
            graph.get(source).add(destination);
        }
    }

    public void addToGraph(T[] data){
        T vertex[] = data;
        for(int i = 0; i < vertex.length; i++){
            for(T current : data){
                addEdge(vertex[i], current);    
            }
        }
    }

    public ArrayList<T> getSet(T b) {
        ArrayList<T> list = new ArrayList<T>(graph.get(b));
        return list;
    }

    public void clear() {
        graph.clear();
    }
}
