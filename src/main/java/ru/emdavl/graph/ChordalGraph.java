package ru.emdavl.graph;

import java.util.*;
import java.util.stream.Collectors;

public class ChordalGraph {
// 15 -- 13 если не сдать 
    public static void main(String[] args) {
        List<List<Node>> start = getChordalGraph();
        List<Node> perfectEliminationOrder = lexBFS(start);
        boolean isChordal = checkChordality(perfectEliminationOrder);
        if (!isChordal) {
            System.out.println("Максимальная клика и окраска посчитаны не будут, т.к. граф не хордальный");
            return;
        }
        findMaxClique(perfectEliminationOrder);
        findMinColoring(perfectEliminationOrder);
    }

    private static void findMaxClique(List<Node> perfectEliminationOrder) {
        int maxSize = -1;
        List<Node> maxClique = new ArrayList<>();
        for (int i = 0; i < perfectEliminationOrder.size() - 1; i++) {
            Node currNode = perfectEliminationOrder.get(i);
            List<Node> takenNodes = new ArrayList<>();
            takenNodes.add(currNode);
            for (int j = i + 1; j < perfectEliminationOrder.size(); j++) {
                final int j1 = j;
                if (takenNodes.stream().allMatch(n -> n.getNeighbours().contains(perfectEliminationOrder.get(j1)))) {
                    takenNodes.add(perfectEliminationOrder.get(j));
                }
            }
            if (takenNodes.size() > maxSize) {
                maxSize = takenNodes.size();
                maxClique = takenNodes;
            }
        }
        System.out.printf("- Максимальная клика имеет размер %d и содержит следующие вершины: %s%n", maxSize, maxClique);
    }


    // Использует greedy coloring
    private static void findMinColoring(List<Node> perfectEliminationOrder) {
        ArrayList<Node> list = new ArrayList<>(perfectEliminationOrder);
        Collections.reverse(list);
        Map<Node, Integer> nodeToColor = new HashMap<>();
        Set<Integer> alreadyUsedColors = new HashSet<>();
        int curr = 1000;

        for (Node node : list) {
            Set<Integer> alreadyUsedByNeighbours = node.getNeighbours().stream()
                    .map(nodeToColor::get)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            int nodeColor = -1;
            for (Integer color : alreadyUsedColors) {
                if (!alreadyUsedByNeighbours.contains(color)) {
                    nodeColor = color;
                }
            }
            if (nodeColor == -1) {
                nodeColor = curr++;
                alreadyUsedColors.add(nodeColor);
                nodeToColor.put(node, nodeColor);
            } else {
                nodeToColor.put(node, nodeColor);
            }
        }
        String coloring = nodeToColor.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getKey().getNumber()))
                .map(e -> e.getKey() + " " + e.getValue())
                .collect(Collectors.joining("\n\t", "\n\t", "\n"));
        System.out.printf("- Раскраска: %s - Использовано цветов: %d%n", coloring, alreadyUsedColors.size());
    }

    private static boolean checkChordality(List<Node> perfectEliminationOrder) {
        Map<Node, Set<Node>> map = new HashMap<>();
        map.put(perfectEliminationOrder.get(0), new HashSet<>());

        for (int i = 1; i < perfectEliminationOrder.size(); i++) {
            Node currNode = perfectEliminationOrder.get(i);
            Node nearestNeighbour = null;
            Set<Node> neighbours = new HashSet<>();
            for (int j = i - 1; j >= 0; j--) {
                if (currNode.getNeighbours().contains(perfectEliminationOrder.get(j))) {
                    if (nearestNeighbour == null) {
                        nearestNeighbour = perfectEliminationOrder.get(j);
                    } else {
                        neighbours.add(perfectEliminationOrder.get(j));
                    }
                }
            }
            if (nearestNeighbour == null) {
                map.put(currNode, new HashSet<>());
                continue;
            }
            if (map.get(nearestNeighbour).containsAll(neighbours)) {
                neighbours.add(nearestNeighbour);
                map.put(currNode, neighbours);
            } else {
                System.out.println("- Граф не хордальный");
                return false;
            }
        }
        System.out.println("- Граф хордальный");
        return true;
    }

    // с этим не работало
    private static List<List<Node>> getChordalGraph() {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);

        n1.setNeighbours(n2, n6);
        n2.setNeighbours(n1, n5, n6);
        n3.setNeighbours(n4, n5, n6);
        n4.setNeighbours(n3, n5);
        n5.setNeighbours(n2, n3, n4, n6);
        n6.setNeighbours(n1, n2, n3, n5);

        List<List<Node>> start = new ArrayList<>(List.of(new ArrayList<>(List.of(n6, n5, n3, n2, n4, n1))));
        return start;
    }

//    private static List<List<Node>> getChordalGraph() {
//        Node n1 = new Node(1);
//        Node n2 = new Node(2);
//        Node n3 = new Node(3);
//        Node n4 = new Node(4);
//        Node n5 = new Node(5);
//        Node n6 = new Node(6);
//
//        n1.setNeighbours(n5, n2, n6);
//        n2.setNeighbours(n3, n5, n1, n4);
//        n3.setNeighbours(n2, n4, n5);
//        n4.setNeighbours(n3, n5, n2);
//        n5.setNeighbours(n1, n2, n3, n4);
//        n6.setNeighbours(n1);
//
//        List<List<Node>> start = new ArrayList<>(List.of(new ArrayList<>(List.of(n1, n2, n3, n4, n5, n6))));
//        return start;
//    }

    private static List<List<Node>> getNonChordalGraph() {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);

        n1.setNeighbours(n5, n2);
        n2.setNeighbours(n3, n1);
        n3.setNeighbours(n2, n4);
        n4.setNeighbours(n3, n5);
        n5.setNeighbours(n1, n4);
        n6.setNeighbours(n1);

        List<List<Node>> start = new ArrayList<>(List.of(new ArrayList<>(List.of(n1, n2, n3, n4, n5, n6))));
        return start;
    }

    public static List<Node> lexBFS(List<List<Node>> start) {
        List<Node> result = new ArrayList<>();

        while (!start.isEmpty()) {
            List<Node> nodes = start.get(0);
            Node mainNode = nodes.remove(0);
            if (nodes.isEmpty()) {
                start.remove(nodes);
            }
            result.add(mainNode);
            List<List<Node>> touched = new ArrayList<>();
            for (Node neighbour : mainNode.getNeighbours()) {
                List<Node> list = new ArrayList<>();
                for (int i = 0; i < start.size(); i++) {
                    List<Node> currList = start.get(i);
                    if (currList.contains(neighbour)) {
                        if (touched.contains(currList)) {
                            list = start.get(i - 1);
                            currList.remove(neighbour);
                            list.add(neighbour);
                            if (currList.isEmpty()) {
                                start.remove(i);
                            }
                        } else {
                            currList.remove(neighbour);
                            list.add(neighbour);
                            start.add(i, list);
                            if (currList.isEmpty()) {
                                start.remove(currList);
                            }
                        }
                        break;
                    }
                }
            }
        }
        return result;
    }
}
