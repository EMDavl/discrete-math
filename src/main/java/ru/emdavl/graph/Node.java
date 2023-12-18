package ru.emdavl.graph;

import lombok.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(exclude = "neighbours")
@AllArgsConstructor
@RequiredArgsConstructor
public class Node {

    private List<Node> neighbours;
    private final Integer number;


    public static Node min(Node first, Node second) {
        return first.getNumber() > second.getNumber() ? second : first;
    }

    public static Node max(Node first, Node second) {
        return first.getNumber() < second.getNumber() ? second : first;
    }

    public void setNeighbours(Node... neighbours) {
        this.neighbours = Arrays.stream(neighbours)
                .sorted(Comparator.comparing(Node::getNumber))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return number.toString();
    }
}
