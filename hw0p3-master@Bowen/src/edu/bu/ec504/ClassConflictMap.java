package edu.bu.ec504;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a map of conflicts between SchoolClass(es).
 * Created by Ari Trachtenberg on 9/10/17.
 */
public class ClassConflictMap {
    ClassConflictMap() {
        myConflicts = new HashMap<>();
    }

    /**
     * Notes a conflict between two classes.
     */
    void addConflict(SchoolClass class1, SchoolClass class2) {
        Set<SchoolClass> currentSet = new HashSet<>();
        if (myConflicts.containsKey(class1))
            currentSet= myConflicts.get(class1);
        currentSet.add(class2);
        myConflicts.put(class1,currentSet);
    }


    /**
     * @return true iff class1 and class2 conflict under this conflict map
     */
    public Boolean isConflicted(SchoolClass class1, SchoolClass class2) {
        return (myConflicts.containsKey(class1) && myConflicts.get(class1).contains(class2)) ||
                (myConflicts.containsKey(class2) && myConflicts.get(class2).contains(class1));
    }

    protected final Map<SchoolClass, Set<SchoolClass>> myConflicts;
}
