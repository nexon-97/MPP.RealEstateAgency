import com.dao.PropertyDAO;
import com.model.Property;
import com.model.RoleId;
import com.model.User;
import com.services.AuthService;
import com.services.PermissionService;
import com.services.PropertyService;
import com.services.impl.PermissionServiceImpl;
import com.services.impl.PropertyServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PropertyServiceTests extends TestBase {

    @Mock
    private PropertyDAO propertyDAO;

    @Mock
    private AuthService authService;

    @Spy
    private PermissionService permissionService = new PermissionServiceImpl();

    @InjectMocks
    private PropertyService propertyService = new PropertyServiceImpl(propertyDAO);

    @Mock
    private List<Property> propertiesList;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Test
    public void testGetProperty()  {
        when(propertyDAO.get(Mockito.anyInt())).thenReturn(mock(Property.class));

        Property property = propertyService.get(Mockito.anyInt());

        assertNotNull(property);
    }

    @Test
    public void testListProperties() {
        when(propertyDAO.list()).thenReturn(propertiesList);

        List<Property> properties = propertyService.list();

        assertNotNull(properties);
    }

    @Test
    public void testGetUserProperties() {
        List<Property> properties = propertyService.getPropertiesOwnedByUser(null);

        assertNotNull(properties);
    }

    @Test
    public void testDeleteProperty() {
        User testUser = new User();
        final int testUserId = 1;
        testUser.setId(testUserId);

        Property testProperty = new Property();
        testProperty.setOwner(testUser);

        when(authService.getLoggedUser()).thenReturn(testUser);

        assertFalse(propertyService.delete(testProperty));
    }

    @Test
    public void testDeletePropertyAdmin() {
        final int testUserId = 1;
        final int testOwnerId = 2;

        User testUser = constructUser(testUserId, RoleId.Admin);
        User testOwner = constructUser(testOwnerId, RoleId.User);

        Property testProperty = new Property();
        testProperty.setOwner(testOwner);

        when(authService.getLoggedUser()).thenReturn(testUser);
        when(propertyDAO.delete(any(Property.class))).thenReturn(true);

        assertTrue(propertyService.delete(testProperty));
    }

    @Test
    public void testDeleteNonOwned() {
        final int testUserId = 1;
        final int testOwnerId = 2;

        User testUser = constructUser(testUserId, RoleId.User);
        User testOwner = constructUser(testOwnerId, RoleId.User);
        Property testProperty = new Property();
        testProperty.setOwner(testOwner);

        when(authService.getLoggedUser()).thenReturn(testUser);

        assertFalse(propertyService.delete(testProperty));
    }
}
