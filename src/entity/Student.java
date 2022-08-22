package entity;

public class Student {
    private Integer id;
    private String name;
    private String address;
    private Integer classLevel;
    private Integer rollNumber;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClassLevel(Integer classLevel) {
        this.classLevel = classLevel;
    }

    public void setRollNumber(Integer rollNumber) {
        this.rollNumber = rollNumber;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getClassLevel() {
        return classLevel;
    }

    public Integer getRollNumber() {
        return rollNumber;
    }
}
