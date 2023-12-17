package ru.emdavl.graph;

public record Edge(Node first, Node second) implements Comparable<Edge> {

    public static Edge of(Node first, Node second) {

        return new Edge(Node.min(first, second), Node.max(first, second));
    }

    @Override
    public int compareTo(Edge e2) {
        int i = first.getNumber().compareTo(e2.first.getNumber());

        if (i == 0) {
            return second.getNumber().compareTo(e2.second.getNumber());
        }

        return i;
    }

    public Node getAnother(Node node) {
        return node == first ? second : first;
    }
}
