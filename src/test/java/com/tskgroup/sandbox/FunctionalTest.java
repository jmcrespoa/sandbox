package com.tskgroup.sandbox;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.junit.Before;

/**
 * Checks out java 8 functional treats.
 * 
 * @author josemanuel
 */
public class FunctionalTest {

    private List<NestedList> outerList = new ArrayList<>();
    
    @Before
    public void setUp() {
        NestedList firstNestedList = new NestedList();
        firstNestedList.addToInnerList(1);
        firstNestedList.addToInnerList(2);
        firstNestedList.addToInnerList(3);
        NestedList secondNestedList = new NestedList();
        secondNestedList.addToInnerList(4);
        secondNestedList.addToInnerList(5);
        secondNestedList.addToInnerList(6);
        NestedList thirdNestedList = new NestedList();
        thirdNestedList.addToInnerList(7);
        thirdNestedList.addToInnerList(8);
        thirdNestedList.addToInnerList(9);
        outerList.add(firstNestedList);
        outerList.add(secondNestedList);
        outerList.add(thirdNestedList);
    }
    
    @Test
    public void testRetrieveAggregatedListNoFunctional() {
        List<Integer> result = new ArrayList<>();
        for (NestedList eachOuterList : this.outerList)
            for (Integer eachInnerElement : eachOuterList.getInnerList())
                if (eachInnerElement % 2 == 0)
                    result.add(eachInnerElement * eachInnerElement);
        
        assertThat(result, contains(4, 16, 36, 64));
    }
    
    @Test
    public void testRetrieveAggregatedListFunctional() {
        List<Integer> result = this.outerList.stream()
                .flatMap(element -> element.getInnerList().stream())
                .filter(element -> element % 2 == 0)
                .map(element -> element * element)
                .collect(Collectors.toList());
        assertThat(result, contains(4, 16, 36, 64));
    }
    
    public class NestedList {
        
        private List<Integer> innerList = new ArrayList<>();
        
        public NestedList addToInnerList(Integer element) {
            this.innerList.add(element);
            return this;
        }

        public List<Integer> getInnerList() {
            return innerList;
        }

    }
}
