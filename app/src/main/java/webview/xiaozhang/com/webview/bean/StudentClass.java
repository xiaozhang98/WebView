package webview.xiaozhang.com.webview.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */

public class StudentClass {
    private String className;
    private List<Student>Students;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Student> getStudents() {
        return Students;
    }

    public void setStudents(List<Student> students) {
        Students = students;
    }
}
