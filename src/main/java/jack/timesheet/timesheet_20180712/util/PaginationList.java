package jack.timesheet.timesheet_20180712.util;

import java.util.List;

public class PaginationList<T> {

    private int total;

    private List<T> rows;

    public PaginationList(int total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}


