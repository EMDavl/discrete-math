package ru.emdavl.graph;

public class NotChordalGraphException extends RuntimeException {
    public NotChordalGraphException() {
        super("Задан не хордальный граф");
    }
}
