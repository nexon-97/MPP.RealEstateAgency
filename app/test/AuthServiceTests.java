import com.dao.UserDAO;
import com.model.Property;
import com.model.RoleId;
import com.model.User;
import com.services.AuthService;
import com.services.impl.AuthServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class AuthServiceTests extends TestBase {

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private AuthService authService = new AuthServiceImpl();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    private User createLoggedUser(String password) {
        final int testUserId = 1;
        final String testUserLogin = "TestLogin";

        User user = constructUser(testUserId, RoleId.User);
        user.setLogin(testUserLogin);

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            user.setPasswordHash(new String(md5.digest(password.getBytes())));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return user;
    }


    @Test
    public void testLoginCorrect() {
        final String testUserLogin = "TestLogin";
        final String testUserPassword = "TestPassword";
        User testUser = createLoggedUser(testUserPassword);

        when(userDAO.getByLogin(testUserLogin)).thenReturn(testUser);

        boolean result = authService.login(testUserLogin, testUserPassword, response);
        assertTrue(result);
    }

    @Test
    public void testLoginIncorrect() {
        final String testUserLogin = "TestLogin";
        final String testUserPassword = "TestPassword";
        final String wrongPassword = "TestPassworf";

        User testUser = createLoggedUser(testUserPassword);

        when(userDAO.getByLogin(testUserLogin)).thenReturn(testUser);

        boolean result = authService.login(testUserLogin, wrongPassword, response);
        assertFalse(result);
    }

    @Test
    public void testLogout() {
        final String testUserLogin = "TestLogin";
        final String testUserPassword = "TestPassword";

        User testUser = createLoggedUser(testUserPassword);

        when(userDAO.getByLogin(testUserLogin)).thenReturn(testUser);

        authService.login(testUserLogin, testUserPassword, response);
        assertNotNull(authService.getLoggedUser());
        authService.logout(response);
        assertNull(authService.getLoggedUser());
    }

    @Test
    public void testConsistency() {
        final String testUserLogin = "TestLogin";
        final String testUserPassword = "TestPassword";

        User testUser = createLoggedUser(testUserPassword);

        when(userDAO.getByLogin(testUserLogin)).thenReturn(testUser);

        authService.login(testUserLogin, testUserPassword, response);
        assertEquals(authService.getLoggedUser() != null, authService.isUserLoggedIn());
    }

    @Test
    public void testLoginFromCookies() {
        final String testUserLogin = "TestLogin";
        final String testUserPassword = "TestPassword";

        User testUser = createLoggedUser(testUserPassword);
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("auth_login", testUserLogin);
        cookies[1] = new Cookie("auth_pass", testUser.getPasswordHash());

        when(userDAO.getByLogin(testUserLogin)).thenReturn(testUser);
        when(request.getCookies()).thenReturn(cookies);

        assertTrue(authService.loginFromCookies(request));
    }

    @Test
    public void testLoginFromInvalidCookies() {
        final String testUserLogin = "TestLogin";
        final String testUserPassword = "TestPassword";

        User testUser = createLoggedUser(testUserPassword);
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("auth_login", testUserLogin);
        cookies[1] = new Cookie("auth_pass", testUserPassword);

        when(userDAO.getByLogin(testUserLogin)).thenReturn(testUser);
        when(request.getCookies()).thenReturn(cookies);

        assertFalse(authService.loginFromCookies(request));
    }

    @Test
    public void testLoginFromEmptyCookies() {
        final String testUserLogin = "TestLogin";
        final String testUserPassword = "TestPassword";

        User testUser = createLoggedUser(testUserPassword);

        when(userDAO.getByLogin(testUserLogin)).thenReturn(testUser);

        assertFalse(authService.loginFromCookies(request));
    }
}
