package by.itechart.server.transformers;
/**
 * Functional interface that provides rule for conversion of Entity to DTO.
 *
 * @param <D> dto type
 */
@FunctionalInterface
public interface ToDtoTransformer<D> {

    /**
     * Method that converts User Entity to User DTO.
     *
     * @return converted dto
     */
    D transformToDto();
}