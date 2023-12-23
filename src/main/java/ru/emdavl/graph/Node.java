package ru.emdavl.graph;

import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(exclude = "neighbours")
@AllArgsConstructor
@RequiredArgsConstructor
public class Node {

    private List<Node> neighbours = new ArrayList<>();
    private final Integer number;


    public static Node min(Node first, Node second) {
        return first.getNumber() > second.getNumber() ? second : first;
    }

    public static Node max(Node first, Node second) {
        return first.getNumber() < second.getNumber() ? second : first;
    }

    public void setNeighbours(Node... neighbours) {
        this.neighbours = Arrays.stream(neighbours)
                .sorted(Comparator.comparing(n -> n.getNeighbours().size()))
                .collect(Collectors.toList());

        Collections.reverse(this.neighbours);
    }

    @Override
    public String toString() {
        return number.toString();
    }
}
