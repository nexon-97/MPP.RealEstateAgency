package com.controller;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CrudControllerOperationWrapper {

    AbstractCrudController controller;
    Method method;

    public CrudControllerOperationWrapper(AbstractCrudController controller, String methodName) {
        this.controller = controller;
        this.method = getMethod(controller.getClass(), methodName);
    }

    public ModelAndView invoke(HttpServletResponse response) {
        this.controller.initResources(response);

        // Check permissions
        if (!actionPermitted()) {
            return new ModelAndView();
        }

        try {
            boolean success = (boolean) this.method.invoke(this.controller);

            return null;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Method getMethod(Class<? extends CrudController> controllerClass, String methodName) {
        try {
            return controllerClass.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean actionPermitted() {
        return true;
    }
}
