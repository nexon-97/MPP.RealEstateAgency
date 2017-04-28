import com.controller.AuthController;
import com.controller.RegisterController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AuthController.class, RegisterController.class})
public class TestingClass {

    /*@Autowired
    private ApplicationContext applicationContext;
    */
    @Autowired
    private AuthController authController;

    @Autowired
    private RegisterController registerController;

    @Test
    public void sampleTest() {
        assertNotNull(authController);
        assertNotNull(registerController);
       /* AuthController authController = applicationContext.getBean("AuthController", AuthController.class);
        Assert.assertNotNull(authController);

        authController = (AuthController) applicationContext.getBean("sampleBean");
        Assert.assertNotNull(authController);
        */


    }
}
