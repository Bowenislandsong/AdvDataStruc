package edu.bu.ec504;

/**
 * Represents one immutable time slot in the schedule
 * Created by Prof. Ari Trachtenberg on 9/10/17.
 */
public class TimeSlot extends IntegerWrapper {
    /**
     * @param theSlotID ID 0 is the first available slot.  ID k is k time units (e.g. hours) after the first slot.
     */
    public TimeSlot(Integer theSlotID) { super(theSlotID); slotID = theSlotID; }

    /**
     * @return an increment of this timeslot
     */
    public TimeSlot inc() {
        return new TimeSlot(slotID+1);
    }

    private final Integer slotID; // the time ID of the current slot
 }
