package ru.ifmo.se.service.validation;

import jakarta.validation.*;
import ru.ifmo.se.model.City;

import java.util.*;

/**
 * A service responsible for validating data parsed from an input file.
 * <p>
 * This validation is crucial because the input file may have been edited manually,
 * potentially introducing incorrect, malformed, or duplicate data.
 * </p>
 */
public class ValidationService {

    /**
     * Checks whether the list of cities contains any duplicate IDs.
     * <p>This helps ensure the integrity of the data imported from the file.</p>
     *
     * @param cities the list of {@link City} objects to check
     * @return {@code true} if duplicates are found, {@code false} otherwise
     */
    public boolean hasDuplicatesId(LinkedList<City> cities) {
        Set<Long> uniqueIds = new HashSet<>();
        return cities.stream()
                .map(City::getId)
                .anyMatch(id -> !uniqueIds.add(id));
    }

    /**
     * Validates constraints on city objects and their coordinates using Jakarta Bean Validation.
     * <p>This includes checking for missing or invalid field values as defined by annotations
     * on the model classes.</p>
     * <p>If any violations are found, a {@link ValidationException} is thrown with details.</p>
     *
     * @param cities a collection of {@link City} objects to validate
     * @throws ValidationException if constraint violations are detected
     */
    public void validateConstraints(Collection<City> cities) throws ValidationException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violationsCity = new HashSet<>();
        Set<ConstraintViolation<Object>> violationsCoordinates = new HashSet<>();
        for (City city : cities) {
            violationsCity.addAll(validator.validate(city));
            violationsCoordinates.addAll(validator.validate(city.getCoordinates()));
        }
        if (!violationsCity.isEmpty()) {
            throw new ValidationException(
                    getViolationErrorMessage(violationsCity) +
                            getViolationErrorMessage(violationsCoordinates)
            );
        }
    }

    /**
     * Converts a set of constraint violations into a formatted error message.
     *
     * @param constraintViolations the set of violations
     * @return a string containing all violation messages
     */
    private String getViolationErrorMessage(Set<ConstraintViolation<Object>> constraintViolations) {
        StringBuilder errorMessageBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : constraintViolations) {
            errorMessageBuilder.append(System.lineSeparator())
                    .append("- Object type: ").append(violation.getRootBeanClass().getSimpleName())
                    .append(", Field: ").append(violation.getPropertyPath())
                    .append(", Invalid value: ").append(violation.getInvalidValue())
                    .append(", Message: ").append(violation.getMessage());
        }
        errorMessageBuilder.append(System.lineSeparator());
        return errorMessageBuilder.toString();
    }
}
