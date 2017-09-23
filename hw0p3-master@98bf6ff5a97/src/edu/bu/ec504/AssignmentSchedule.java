package edu.bu.ec504;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Prof. Ari Trachtenberg on 9/10/17.
 */
public class AssignmentSchedule {
    // CONSTRUCTORS
    /**
     * Initialize an Assignment schedule for a given number of classes.
     */
    public AssignmentSchedule(Integer theNumClasses) {
        numClasses=theNumClasses;
        myAssignment = new HashMap<>();
    }

    // METHODS

    /**
     * @return true iff #theClass has as an assignment in this schedule
     */
    public Boolean hasAssignment(SchoolClass theClass) {
        return myAssignment.containsKey(theClass);
    }

    /**
     * Variant of {@link #hasAssignment(SchoolClass)}.
     */
    public Boolean hasAssignment(Integer classID) {
        return hasAssignment(new SchoolClass(classID));
    }

    /**
     * Adds a class assignment to the assignment schedule
     * @param theClass The class to assign
     * @param theTimeSlot The time slot at which the class is to start
     * @param theRoom The room in which the class is to meet.
     */
    public void addAssignment(SchoolClass theClass, TimeSlot theTimeSlot, SchoolRoom theRoom) {
        myAssignment.put(theClass, new Pair<>(theTimeSlot, theRoom));}

    /**
     * @return An assignment corresponding to the given school class.
     * @throws AssignmentNotFoundException if no assignment exists for the class.
     */
    public Pair<TimeSlot,SchoolRoom> getAssignment(SchoolClass theClass) throws AssignmentNotFoundException {
        if (!hasAssignment(theClass))
            throw new AssignmentNotFoundException();
        return myAssignment.get(theClass);
    }

    /**
     * A variant of {@link #getAssignment(SchoolClass)} bu with a class ID provided.
     */
    public Pair<TimeSlot,SchoolRoom> getAssignment(Integer classID) throws AssignmentNotFoundException {
        return getAssignment(new SchoolClass(classID));
    }

    /**
     * @return A set of the assignments in the current object.
     */
    public Set<Map.Entry<SchoolClass, Pair<TimeSlot,SchoolRoom>>> getAssignments() {
        return myAssignment.entrySet();
    }

    /**
     * @return the assignment in the  output format described in the homework assignment.
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Integer ii=0; ii<numClasses; ii++) {
            try {
                Pair<TimeSlot,SchoolRoom> datum = getAssignment(ii);
                result.append(datum.getKey());
                result.append(" ");
                result.append(datum.getValue());
                result.append("\n");
            }
            catch (AssignmentNotFoundException exp) {
                return("Invalid schedule:  class " + ii + " is missing from the schedule.");
            }
        }
        return result.toString();
    }


    // FIELDS
    private final Map<SchoolClass, Pair<TimeSlot,SchoolRoom>> myAssignment;

    private final Integer numClasses; // the number of classes represented by this assignment

    // EXCEPTIONS
    class AssignmentNotFoundException extends Exception {}

}
