package ru.emdavl.graph;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Node {

    private List<Edge> edges;
    private final Integer number;

    public Node(int number, List<Edge> edges) {
        this.number = number;
        this.edges = edges;
    }

    public Node(int number) {
        this.number = number;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public Integer getNumber() {
        return number;
    }

    public static Node min(Node first, Node second) {
        return first.getNumber() > second.getNumber() ? second : first;
    }


    public static Node max(Node first, Node second) {
        return first.getNumber() < second.getNumber() ? second : first;
    }

    public List<Node> getNeighbours() {
        return getEdges().stream()
                .map(e -> e.getAnother(this))
                .sorted(Comparator.comparing(Node::getNumber))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return number.toString();
    }
}
