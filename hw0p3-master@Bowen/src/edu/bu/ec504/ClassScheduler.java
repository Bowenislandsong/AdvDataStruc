package edu.bu.ec504;

import javafx.util.Pair;

import java.io.InputStream;
import java.util.*;

/**
 * Implements a Class Scheduler for EC504, homework 0.
 * Written by Prof. Ari Trachtenberg, Fall 2017.
 */


public abstract class ClassScheduler {
    // CONSTRUCTORS
    /**
     * Constructs a edu.bu.ec504.ClassScheduler object from standard input
     */
    public ClassScheduler() {
        this(System.in);
    }

    /**
     * Reads in all the fields needed to create a edu.bu.ec504.ClassScheduler object from given input stream,
     * according to the input format described on on the HomeworkZero topic of the class wiki.
     */
    public ClassScheduler(InputStream in) {
        // Initialize fields
        classLength = new HashMap<>();
        conflicts = new ClassConflictMap();

        // Input fields from input stream
        Scanner input = new Scanner(in);

        rr = input.nextInt();
        nn = input.nextInt();
        for (int ii=0; ii<nn; ii++)
            classLength.put(new SchoolClass(ii), input.nextInt());
        while (input.hasNext()) {
            SchoolClass newSC = new SchoolClass(input.nextInt());
            conflicts.addConflict(new SchoolClass(input.nextInt()), newSC);
        }
    }

    // METHODS

    /**
     * @return A schedule satisfying all the constraints of the problem
     */
    public abstract AssignmentSchedule makeSchedule();

    /**
     * @return true iff #class1 and #class2 conflict if starting at #ts1 and #ts2, respectively
     */
    public Boolean isTimeConflictQ(SchoolClass class1, TimeSlot ts1, SchoolClass class2, TimeSlot ts2) {
        Integer class1Start = ts1.rawDatum();
        Integer class2Start = ts2.rawDatum();
        Integer class1Len = classLength.get(class1);
        Integer class2Len = classLength.get(class2);

        // class2 is in the time frame of class1
        if ((class1Start <= class2Start) && (class1Start+class1Len > class2Start))
            return true;
        if ((class2Start <= class1Start) && (class2Start+class2Len > class1Start))
            return true;

        return false;

    }

    /**
     * Determines whether the given class assignment is compatible with the provided schedule.
     * @param theAssignment The existing assignments against which to check this new class assignment
     * @param theClass The new class to consider assigning
     * @param theTS The proposed timeslot for the new class
     * @param theRoom THe room in which the proposed class is being assigned
     * @return true iff an assignment of #theClass to timeslot #theTS and room #theRoom
     * conflicts with assignment schedule #theAssignment or conflict map that is part of the Class Scheduler
     */
    Boolean isConflictQ(AssignmentSchedule theAssignment, SchoolClass theClass, TimeSlot theTS, SchoolRoom theRoom) {
        // iterate through matching timeslots
        for (Map.Entry<SchoolClass, Pair<TimeSlot, SchoolRoom>> entry : theAssignment.getAssignments()) {
            SchoolClass compClass = entry.getKey();
            TimeSlot compTS = entry.getValue().getKey();
            SchoolRoom compRoom = entry.getValue().getValue();

            if (theClass.equals( compClass)) // the class is already assigned
                return true;
            if (isTimeConflictQ(theClass,theTS,compClass,compTS)) {       // timeslot conflict
                if (theRoom.equals(compRoom)) // the room is already assigned
                    return true;
                if (conflicts.isConflicted(theClass,compClass)) // scheduler rule conflict
                    return true;
            }
        }

        return false; // no conflicts found
    }

    /**
     * Verifies that a given class assignments conform with the rules associated with this scheduler.
     * @param theAssignment The assignment to verify
     * @return true iff the given assignment schedule satisfies all the rules provided in the homework
     */
    public Boolean verifyAssignment(AssignmentSchedule theAssignment) {
        // 0.  Every class must be assigned
        for (int ii = 0; ii < nn; ii++)
            if (!theAssignment.hasAssignment(ii))
                return false;

        // 1.  For every timeslot, there is no conflict in rooms or classes
        for (Map.Entry<SchoolClass, Pair<TimeSlot, SchoolRoom>> entry1 : theAssignment.getAssignments())
            for (Map.Entry<SchoolClass, Pair<TimeSlot, SchoolRoom>> entry2 : theAssignment.getAssignments())
                if (entry1 != entry2) { // don't compare an entry to itself
                    SchoolClass class1 = entry1.getKey();
                    TimeSlot ts1 = entry1.getValue().getKey();
                    SchoolRoom room1 = entry1.getValue().getValue();

                    SchoolClass class2 = entry2.getKey();
                    TimeSlot ts2 = entry2.getValue().getKey();
                    SchoolRoom room2 = entry2.getValue().getValue();

                    // sanity checks
                    if (class1.rawDatum() < 0 || class1.rawDatum() > nn ||
                            class2.rawDatum() < 0 || class2.rawDatum() > nn)
                        return false;
                    if (room1.rawDatum() < 0 || room1.rawDatum() > rr ||
                            room2.rawDatum() < 0 || room2.rawDatum() > rr)
                        return false;

                    // conflict checks
                    if (isTimeConflictQ(class1,ts1,class2,ts2)) {         // conflicts only occur over intersecting timeslots
                        if ((room1.equals(room2)) ||                             // they share the same room over the intersecting timeslots
                                conflicts.isConflicted(class1, class2))   // the classes conflict in the conflict map
                            return false;
                    }
                }

        // All checks passed
        return true;
    }

    // FIELDS

    /**
     * The number of classes being scheduled.
     */
    protected final int nn;

    /**
     * The number of rooms into which classes are scheduled.
     */
    protected final int rr;

    /**
     * Maps school classes to their length of time (in timeslot units)
     */
    protected final Map<SchoolClass,Integer> classLength;

    /**
     * If [i,j] is in the conflict map, then classes i and j cannot be scheduled for the same time.
     */
    protected final ClassConflictMap conflicts;

    // CONSTANTS
}
