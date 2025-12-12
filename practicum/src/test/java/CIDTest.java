import cc.carm.study.practicum.student.utils.Validators;
import org.junit.Test;

public class CIDTest {

    @Test
    public void test() {
        System.out.println("CHECK: " + Validators.validateID("11010519491231002X"));
        System.out.println("CHECK: " + Validators.validateID("11010519491231502X"));

    }

}
