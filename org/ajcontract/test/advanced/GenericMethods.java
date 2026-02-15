package org.ajcontract.test.advanced;

import java.util.ArrayList;
import java.util.List;
import org.ajcontract.annotation.Ensures;
import org.ajcontract.annotation.Requires;

public class GenericMethods {
    private int a;

    @Ensures("this.a == 0")
    public GenericMethods() {
        a = 0;
    }

    @Requires(precondition = "number1 != null && number1.intValue() >= 0")
    @Ensures("number1.intValue() == getA()")
    public <T extends Number> GenericMethods(T number1) {
        a = number1.intValue();
    }

    @Requires(precondition = "number1 != null && number2 != null && number1.intValue() > 0")
    @Ensures("returnval != null && returnval.intValue() == number1.intValue()")
    public <T extends Number> T genericMethod(T number1, T number2) {
        return number1;
    }

    @Requires(precondition = "list != null && !list.isEmpty()")
    @Ensures("!list.isEmpty() && returnval.get(0) == list.get(0) + 1")
    public static List<Integer> wildcardMethod(List<? extends Integer> list) {
        ArrayList<Integer> returnedList = new ArrayList<Integer>();
        returnedList.add(list.get(0) + 1);
        return returnedList;
    }

    @Requires(precondition = "list != null && !list.isEmpty()")
    @Ensures("returnval.equals(list)")
    public static List<?> wildcardMethod2(List<?> list) {
        return list.subList(0, list.size());
    }

    public int getA() {
        return a;
    }
}
