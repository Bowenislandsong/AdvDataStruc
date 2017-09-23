package edu.bu.ec504;



import java.util.ArrayList;


/**
 * Student implementation of the ClassScheduler
 * Created by Prof. Ari Trachtenberg on 9/10/17.
 */
public class myClassScheduler extends ClassScheduler {

    private ArrayList<Integer> RnT = new ArrayList<>();  // room and the last time
    @Override
    public AssignmentSchedule makeSchedule() {
        AssignmentSchedule result=new AssignmentSchedule(nn);
        //rr number of rooms
        //nn number of classes
        //classLength() class name class time
        //NewSc, conflicts

        //conflicted class that has to take up time
        //list of conflicted classes and their time, put them in the same room





        for ( Integer ii = 0; ii<nn; ii++){
            for (Integer jj = ii+1; jj<nn; jj++) {
                if (conflicts.isConflicted(new SchoolClass(ii),new SchoolClass(jj))) {
                    if (!result.hasAssignment(new SchoolClass(ii))) {
                        if (RnT.size()<1){Newroom();}
                        result.addAssignment(new SchoolClass(ii), new TimeSlot(RnT.get(0)), new SchoolRoom(0)); //rm change needed
                        addHrToRm(0,ii);
                    }
                    if (!result.hasAssignment(new SchoolClass(jj))) {
                        result.addAssignment(new SchoolClass(jj), new TimeSlot(RnT.get(0)), new SchoolRoom(0));
                        addHrToRm(0,jj);
                    }
                }
            }if (!result.hasAssignment(new SchoolClass(ii))) {
                Integer rm = autononrelativesort(ii);
                result.addAssignment(new SchoolClass(ii), new TimeSlot(RnT.get(rm)-classduration(ii)), new SchoolRoom(rm)); //rm change needed
            }

        }

        //if more room, small chunk timed classes sort them first






        // do something smarter here

        return result;
    }

    //use a new room
    private boolean Newroom(){
        if (RnT.size()<rr) {
            RnT.add(0);
            return true;
        }
        return false;
    }
    //add hr to a room, if the room not exist recurr to get it, return false if room exceeds nn
    private boolean addHrToRm(Integer room, Integer schoolclass){
        if (RnT.size()>room)
        RnT.set(room,RnT.get(room)+classduration(schoolclass));
        else if(Newroom())
            addHrToRm(room,schoolclass);
        else
            return false;

        return true;
    }

    //return class duration based on schoolclass
    private int classduration(int Classnum){
        return classLength.get(new SchoolClass(Classnum));
    }
    //this is for not conflicted classes, when it exceed the time of room with the most time, sort it in another room
    //return rm and take hr
    private Integer autononrelativesort(int schoolclass){
        int lastrm = RnT.size()-1;
        int dur = classduration(schoolclass);
        if ( lastrm+1>1 && RnT.get(0)<RnT.get(lastrm)+dur){
            addHrToRm(lastrm+1,schoolclass);
            return lastrm+1;
        }
        else if (lastrm+1>0 && addHrToRm(lastrm+1,schoolclass)){
            return lastrm+1;
        }else if(addHrToRm(0,schoolclass)){
            return 0;//at this point size changed
        }
        return 0;
    }

}
