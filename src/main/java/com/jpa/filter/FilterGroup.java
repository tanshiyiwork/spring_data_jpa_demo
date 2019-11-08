package com.jpa.filter;

import com.jpa.operator.GroupOperator;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FilterGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Logger logger = Logger.getLogger(FilterGroup.class);
    private GroupOperator op;
    private FilterItem[] filterItems;
    private FilterGroup[] filterGroups;

    public GroupOperator getGroupOperator() {
        return this.op;
    }

    public FilterItem[] getFilterItems() {
        return this.filterItems;
    }

    public FilterGroup setFilterItems(FilterItem[] items) {
        if (items != null) {
            this.filterItems = items;
        }

        return this;
    }

    public FilterGroup setFilterItems(List<FilterItem> items) {
        if (items != null && items.size() > 0) {
            this.filterItems = (FilterItem[])((FilterItem[])items.toArray(new FilterItem[0]));
        }

        return this;
    }

    public FilterGroup[] getFilterGroups() {
        return this.filterGroups;
    }

    public FilterGroup setFilterGroups(FilterGroup[] filterGroups) {
        this.filterGroups = filterGroups;
        return this;
    }

    public FilterGroup setFilterGroups(List<FilterGroup> fgs) {
        if (fgs != null && fgs.size() > 0) {
            this.filterGroups = (FilterGroup[])((FilterGroup[])fgs.toArray());
        }

        return this;
    }

    public FilterGroup(GroupOperator op) {
        this.op = op;
    }

    public FilterGroup add(FilterItem item1, FilterItem item2, FilterItem... items) {
        ArrayList<FilterItem> filterItemList = new ArrayList();
        int i;
        if (this.filterItems != null) {
            for(i = 0; i < this.filterItems.length; ++i) {
                filterItemList.add(this.filterItems[i]);
            }
        }

        if (item1 != null) {
            filterItemList.add(item1);
        }

        if (item2 != null) {
            filterItemList.add(item2);
        }

        if (items != null) {
            for(i = 0; i < items.length; ++i) {
                filterItemList.add(items[i]);
            }
        }

        this.filterItems = (FilterItem[])((FilterItem[])filterItemList.toArray(new FilterItem[0]));
        return this;
    }

    public FilterGroup add(FilterGroup group1, FilterGroup... groups) {
        ArrayList<FilterGroup> filterGroupList = new ArrayList();
        int i;
        if (this.filterGroups != null) {
            for(i = 0; i < this.filterGroups.length; ++i) {
                filterGroupList.add(this.filterGroups[i]);
            }
        }

        if (group1 != null) {
            filterGroupList.add(group1);
        }

        if (groups != null) {
            for(i = 0; i < groups.length; ++i) {
                filterGroupList.add(groups[i]);
            }
        }

        this.filterGroups = (FilterGroup[])((FilterGroup[])filterGroupList.toArray(new FilterGroup[0]));
        return this;
    }
}
