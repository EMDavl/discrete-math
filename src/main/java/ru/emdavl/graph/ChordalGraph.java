package ru.emdavl.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.emdavl.graph.Edge.of;

public class ChordalGraph {

    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        n1.setEdges(List.of(of(n5, n1), of(n1, n2), of(n1, n6)));
        n2.setEdges(List.of(of(n2,n3), of(n2,n5), of(n1, n2)));
        n3.setEdges(List.of(of(n2,n3), of(n3, n4), of(n3,n5)));
        n4.setEdges(List.of(of(n4,n5), of(n3, n4)));
        n5.setEdges(List.of(of(n4,n5), of(n5, n1), of(n2,n5), of(n3,n5)));
        n6.setEdges(new ArrayList<>());
//
//        List<Edge> edges = Stream.of(of(n5, n1), of(n3, n4), of(n4,n5),of(n1, n2), of(n2,n3), of(n3,n5), of(n2,n5), of(n5,n4))
//                .distinct()
//                .sorted(Edge::compareTo)
//                .toList();
//        System.out.println(edges);

        List<List<Node>> start = new ArrayList<>(List.of(new ArrayList<>(List.of(n6, n5, n4, n3, n2, n1))));
        List<Node> result = new ArrayList<>();

        int i = 0;
        while (!start.isEmpty()) {
            List<Node> nodes = start.get(0);
            List<List<Node>> touched = new ArrayList<>();

            if (nodes.isEmpty()) {
                start.remove(nodes);
                continue;
            }

            Node node = nodes.remove(0);
            if (nodes.isEmpty()) {
                start.remove(nodes);
            }
            touched.add(nodes);
            start.add(0, new ArrayList<>());
            result.add(node);
            System.out.printf("Iteration %d . Node: %d . List: %s Result: %s%n", i++, node.getNumber(), start, result);

            for (Node neighbour : node.getNeighbours()) {
                Optional<List<Node>> listWithNeighbourOpt = start.stream().filter(list -> list.contains(neighbour)).findFirst();
                if (listWithNeighbourOpt.isEmpty()) {
                    continue;
                }
                List<Node> listWithNeighbour = listWithNeighbourOpt.get();
                System.out.printf("Processing neighbour %d of node %d%n", neighbour.getNumber(), node.getNumber());

                if (!touched.contains(listWithNeighbour)) {
                    System.out.printf("List, which is contains node %d is %s%n", neighbour.getNumber(), listWithNeighbour);

                    List<Node> l = new ArrayList<>();
                    start.add(start.indexOf(listWithNeighbour), l);
                    l.add(neighbour);
                    listWithNeighbour.remove(neighbour);
                    if (listWithNeighbour.isEmpty()) {
                        start.remove(listWithNeighbour);
                    }
                    touched.add(listWithNeighbour);
                } else {
                    System.out.printf("List, which is contains node %d is %s%n", neighbour.getNumber(), listWithNeighbour);

                    List<Node> list = start.get(start.indexOf(listWithNeighbour) - 1);
                    list.add(neighbour);
                    listWithNeighbour.remove(neighbour);
                    if (listWithNeighbour.isEmpty()) {
                        start.remove(listWithNeighbour);
                    }
                }
            }
        }
        System.out.println(result);
    }
}
