package common.exception;

/**
 * The ViewCartException class serves as a wrapper for unchecked exceptions.
 * You can use this exception to inform about issues related to the Cart view.
 */
// Coupling: Control Coupling - Inherits from AimsException, directly tied to its hierarchy.
public class ViewCartException extends AimsException {

    private static final long serialVersionUID = 1091337136123906298L;

    public ViewCartException() {
        // Constructor
    }

    public ViewCartException(String message) {
        super(message);
    }

    /**
     * Comments on Cohesion:
     * This class exhibits high cohesion since it specifically handles exceptions related to the Cart view.
     * Its methods and attributes are focused on this specific type of exception.
     */
}
