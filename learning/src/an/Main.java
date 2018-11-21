package an;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by jlgaoyuan on 2018/11/14.
 *
 */
public class Main {
    public static void main(String[] args) {
        try {
          Class clazz =   Class.forName("Demo02");
          Annotation[] annotations = clazz.getDeclaredAnnotations();
          for(Annotation a:annotations){
              System.out.println(a);
          }
          StudentTable st  = (StudentTable) clazz.getAnnotation(StudentTable.class);
            System.out.println(st.value());
            Field f = clazz.getDeclaredField("name");
            StudentField stField = f.getAnnotation(StudentField.class);
            System.out.println(stField.columnName()+"--"+stField.length());

        } catch (ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }

    }
}
