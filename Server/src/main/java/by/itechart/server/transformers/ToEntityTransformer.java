package by.itechart.server.transformers;

/**
 * Functional interface that provides rule for conversion of DTO to Entity.
 *
 * @param <E> entity type
 */
@FunctionalInterface
public interface ToEntityTransformer<E> {

    /**
     * Method that converts User DTO to User Entity.
     *
     * @return converted entity
     */
    E transformToEntity();
}