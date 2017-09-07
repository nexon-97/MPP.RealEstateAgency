import com.model.User;
import com.services.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceTests {

    @Mock private ApplicationContext context;
    @Mock private SessionFactory sessionFactory;
    @Mock private Session session;
    @Mock private UserService service;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testGetUserById()  {
        when(context.getBean(SessionFactory.class)).thenReturn(sessionFactory);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.close()).thenReturn(null);

        User user = service.getUserByID(Mockito.anyInt());

        assertNotNull(user);
    }
}
