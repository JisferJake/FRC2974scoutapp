package scoutapp.main;

import java.util.ArrayList;

public class CSV {
    private ArrayList<String[]> rows;

    public CSV() {
        rows = new ArrayList<>();
    }

    public CSV(String[] title) {
        rows = new ArrayList<>();
        rows.add(title);
    }

    /**
     * Separates rows by ,
     * @param data
     */
    public CSV(String data) {
        rows = new ArrayList<>();
        rows.add(data.split(","));
    }

    public void addRow(int index, String[] data) {
        if(index < rows.size()) {
            System.out.println("Index bigger than " + index + "added row at " + (rows.size() + 1));
            rows.add(data);
        }
        rows.add(index, data);
    }

    public void addRow(String[] data) {
        rows.add(data);
    }

    public void removeRow(int index) {
        rows.remove(index);
    }

    /**
     * Seperates each column by commas
     * @param data
     */
    public void addRow(String data) {
        rows.add(data.split(","));
    }

    private String splitRow(String[] row) {
        String rtn = "";
        for(String str : row) {
            rtn += str + ",";
        }
        return rtn;
    }

    public String compile() {
        String rtn = "";
        for (String[] strs : rows) {
            rtn += splitRow(strs) + "\n";
        }
        return rtn;
    }
}
