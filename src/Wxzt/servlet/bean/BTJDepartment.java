package Wxzt.servlet.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/25.
 */
public class BTJDepartment {
    private int ID;//编号
    private int queueID;//话务组号
    private String department;//部门
    private String Note;//备注


    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getNote() {
        return Note;
    }
    public void setNote(String Note) {
        this.Note = Note;
    }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public int getQueueID() {
        return queueID;
    }
    public void setQueueID(int queueID) {
        this.queueID = queueID;
    }
}
