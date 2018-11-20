package an;

/**
 * Created by jlgaoyuan on 2018/11/14.
 *
 */
@StudentTable(value = "tb_student")
public class Demo02 {
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @StudentField(columnName = "id",type = "int",length = 10)
    private int age ;
    @StudentField(columnName = "name",type = "varchar",length = 10)
    private String name;
    @StudentField(columnName = "sxt",type = "varchar",length = 10)
    private String school;
}
