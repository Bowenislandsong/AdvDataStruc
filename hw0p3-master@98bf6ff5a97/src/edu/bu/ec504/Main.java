package edu.bu.ec504;

public class Main {

    public static void main(String[] args) {
        System.out.println("Please provide the input: (Control-D to end)");
        ClassScheduler HwExample = new myClassScheduler();

        //// If makeSchedule were implemented, we could simple do:
        // AssignmentSchedule ExampleAssignment = HwExample.makeSchedule();

        AssignmentSchedule ExampleAssignment = new AssignmentSchedule(3);
        ExampleAssignment.addAssignment(new SchoolClass(0), new TimeSlot(0), new SchoolRoom(0));
        ExampleAssignment.addAssignment(new SchoolClass(1), new TimeSlot(0), new SchoolRoom(1));
        ExampleAssignment.addAssignment(new SchoolClass(2), new TimeSlot(2), new SchoolRoom(0));

        System.out.println("The output is:");
        System.out.println(ExampleAssignment.toString());
        System.out.print("This class assignment is ");
        System.out.println(HwExample.verifyAssignment(ExampleAssignment) ? "valid." : "invalid.");
    }
}
