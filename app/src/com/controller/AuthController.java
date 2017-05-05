package com.controller;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;

import com.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.services.AuthService;
import com.services.shared.ServiceManager;
import java.util.Map;

@Controller
public class AuthController extends BaseController {

    @RequestMapping(method = RequestMethod.GET, value = "/auth")
    public ModelAndView visitAuthorizationForm(HttpServletResponse response) {
        initControllerResources(response);

        AuthService authService = ServiceManager.getInstance().getAuthService();
        if (authService.getLoggedUser() != null) {
            return redirect("/");
        } else {
            return buildModelAndView("auth");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/auth")
    public ModelAndView authorize(HttpServletResponse response) {
        initControllerResources(response);

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login != null && password != null) {
            AuthService authService = ServiceManager.getInstance().getAuthService();
            authService.login(login, password);
        }

        return buildModelAndView("auth_result");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    public ModelAndView logout(HttpServletResponse response) {
        initControllerResources(response);

        ServiceManager.getInstance().getAuthService().logout();

        return redirect("/");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/restorePassword")
    public ModelAndView restorePassword(HttpServletResponse response) {
        initControllerResources(response);
        if (ServiceManager.getInstance().getAuthService().getLoggedUser() == null){
            return buildModelAndView("restore_pass");
        }
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        model.put("msg", "Для восстановления пароля необходимо выйти из аккаунта!");
        return buildModelAndView("restore_pass_failure");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/restorePassword")
    public ModelAndView sendEmail(HttpServletResponse response) {
        initControllerResources(response);
        String loginFromRequest = request.getParameter("login");
        User userForRestorePass = ServiceManager.getInstance().getUserService().getUserByLogin(loginFromRequest);
        if ( userForRestorePass == null){
            Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
            model.put("msg", "Пользователя с таким логином не существует!");
            return buildModelAndView("restore_pass_failure");
        }
        String email = userForRestorePass.getEmail();
        //sendMail(email);
        return buildModelAndView("restore_pass_success");
    }

  /*  public static void main(String[] args) {
        sendEmail("alexander.malei@gmail.com");
    }*/
   /* private void sendMail(String emailTo){
        String from = "REAL ESTATE AGENCY";
        String host = "localhost";
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties); // default session

        try {
            MimeMessage message = new MimeMessage(session); // email message

            message.setFrom(new InternetAddress(from)); // setting header fields

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));

            message.setSubject("Submit your password restoring"); // subject line

            // actual mail body
            message.setText("Please, click following link to confirm. that eou want to restore your password");

            // Send message
            Transport.send(message); System.out.println("Email Sent successfully....");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }*/

}
