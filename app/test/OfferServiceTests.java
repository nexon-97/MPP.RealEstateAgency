import com.dao.OfferDAO;
import com.model.*;
import com.services.AuthService;
import com.services.OfferService;
import com.services.PermissionService;
import com.services.impl.OfferServiceImpl;
import com.services.impl.PermissionServiceImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OfferServiceTests extends TestBase {

    @Mock
    OfferDAO offerDAO;

    @Mock
    AuthService authService;

    @Spy
    PermissionService permissionService = new PermissionServiceImpl();

    @InjectMocks
    private OfferService offerService = new OfferServiceImpl();

    private Offer validOffer;
    private Offer invalidOffer;
    private User validOwner;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Before
    public void before() {
        final BigDecimal cost = new BigDecimal(1);
        final int testUserId = 1;

        validOwner = constructUser(testUserId, RoleId.User);
        Property property = new Property();
        property.setOwner(validOwner);

        validOffer = new Offer();
        validOffer.setCost(cost);
        validOffer.setOfferType(OfferType.Sale);
        validOffer.setProperty(property);

        invalidOffer = new Offer();
        invalidOffer.setCost(cost);
        invalidOffer.setProperty(property);
    }

    @Test
    public void testAddOffer() {
        when(authService.getLoggedUser()).thenReturn(validOwner);
        when(offerDAO.add(validOffer)).thenReturn(true);

        assertTrue(offerService.addOffer(validOffer));
    }

    @Test
    public void testAddInvalidOffer() {
        assertFalse(offerService.addOffer(invalidOffer));
    }

    @Test
    public void testAddOfferNonOwner() {
        User otherUser = constructUser(2, RoleId.User);
        validOffer.getProperty().setOwner(otherUser);

        assertFalse(offerService.addOffer(invalidOffer));
    }

    @Test
    public void testDeleteOffer() {
        when(authService.getLoggedUser()).thenReturn(validOwner);
        when(offerDAO.delete(validOffer)).thenReturn(true);

        assertTrue(offerService.deleteOffer(validOffer));
    }

    @Test
    public void testDeleteOfferNonOwner() {
        when(authService.getLoggedUser()).thenReturn(validOwner);

        User otherUser = constructUser(2, RoleId.User);
        validOffer.getProperty().setOwner(otherUser);

        assertFalse(offerService.deleteOffer(validOffer));
    }

    @Test
    public void testDeleteOfferNull() {
        when(authService.getLoggedUser()).thenReturn(validOwner);

        assertFalse(offerService.deleteOffer(null));
    }

    @Test
    public void testDeleteOfferByAdmin() {
        when(authService.getLoggedUser()).thenReturn(validOwner);
        when(offerDAO.delete(validOffer)).thenReturn(true);

        User otherUser = constructUser(2, RoleId.User);
        validOffer.getProperty().setOwner(otherUser);
        validOwner.setRoleId(RoleId.Admin);

        assertTrue(offerService.deleteOffer(validOffer));
    }

    @Test
    public void testGetOffer() {
        when(offerDAO.get(validOffer.getId())).thenReturn(validOffer);

        Offer offerClone = offerService.getOfferById(validOffer.getId());

        assertEquals(offerClone.getId(), validOffer.getId());
    }

    @Test
    public void testEditOffer() {
        when(authService.getLoggedUser()).thenReturn(validOwner);
        when(offerDAO.get(validOffer.getId())).thenReturn(validOffer);
        when(offerDAO.update(validOffer)).thenReturn(true);

        BigDecimal oldCost = validOffer.getCost();
        BigDecimal newCost = new BigDecimal(2500);
        validOffer.setCost(newCost);

        assertNotEquals(oldCost, newCost);
        assertTrue(offerService.updateOffer(validOffer));
        assertEquals(offerService.getOfferById(validOffer.getId()).getCost(), newCost);
    }

    @Test
    public void testEditOfferNonOwner() {
        when(authService.getLoggedUser()).thenReturn(validOwner);
        when(offerDAO.get(validOffer.getId())).thenReturn(validOffer);
        when(offerDAO.update(validOffer)).thenReturn(true);

        User otherUser = constructUser(2, RoleId.User);
        validOffer.getProperty().setOwner(otherUser);

        assertFalse(offerService.updateOffer(validOffer));
    }

    @Test
    public void testEditOfferAdmin() {
        when(authService.getLoggedUser()).thenReturn(validOwner);
        when(offerDAO.get(validOffer.getId())).thenReturn(validOffer);
        when(offerDAO.update(validOffer)).thenReturn(true);

        User otherUser = constructUser(2, RoleId.User);
        validOffer.getProperty().setOwner(otherUser);
        validOwner.setRoleId(RoleId.Admin);

        assertFalse(offerService.updateOffer(validOffer));
    }
}
