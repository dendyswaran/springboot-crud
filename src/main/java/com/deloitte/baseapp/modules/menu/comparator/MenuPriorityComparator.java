package com.deloitte.baseapp.modules.menu.comparator;

import com.deloitte.baseapp.modules.menu.entities.Menu;

import java.util.Comparator;

public class MenuPriorityComparator implements Comparator<Menu> {

    /**
     *
     * the smaller the number the sooner the menu is rendered, if left has smaller number (greater priority) than right,
     * left - right will be a negative value indicating left (isSmaller/sooner to be ordered and rendered)
     *
     * @param menuLeft the first object to be compared.
     * @param menuRight the second object to be compared.
     * @return an integer,
     *              a negative integer indicate that the left element is smaller than the right element,
     *              a positive integer indicate that the left element is greater than the left element,
     *              0 indicate that the left element is equivalent to the right element
     */
    @Override
    public int compare(Menu menuLeft, Menu menuRight) {
        return menuLeft.getPriority() - menuRight.getPriority();
    }
}
