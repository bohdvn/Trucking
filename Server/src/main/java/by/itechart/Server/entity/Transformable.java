package by.itechart.Server.entity;

@FunctionalInterface
public interface Transformable<T> {
    T transform();
}