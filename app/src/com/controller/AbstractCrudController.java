package com.controller;

import com.model.Entity;
import com.permission.EntityOperation;
import com.permission.EntityOperationDesc;
import com.services.CrudService;
import com.services.shared.ServiceManager;

import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AbstractCrudController<E extends Entity> extends BaseController implements CrudController<E> {

    private Class<E> metaclass;
    private Map<String, EntityOperationDesc> operationMap;
    private boolean permissionError;
    private E processedEntity;

    public AbstractCrudController(Class<E> metaclass) {
        this.metaclass = metaclass;

        this.operationMap = new HashMap<>();
        for (Method m : getClass().getMethods()) {
            Annotation annotation = m.getAnnotation(EntityOperation.class);
            if (annotation != null) {
                this.operationMap.put(m.getName(), new EntityOperationDesc((EntityOperation) annotation));
            }
        }
    }

    @Override
    public CrudService<E> getService() {
        return ServiceManager.getInstance().getCrudService(this.metaclass);
    }

    @Override
    protected void initResources(HttpServletResponse response) {
        super.initResources(response);

        // Get invoking method
        StackTraceElement callerStackTrace = Thread.currentThread().getStackTrace()[2];
        String methodName = callerStackTrace.getMethodName();

        EntityOperationDesc operationDesc = this.operationMap.getOrDefault(methodName, null);
        this.permissionError = false;
        if (operationDesc != null) {
            this.permissionError = !checkOperationPermission(operationDesc);

            if (this.permissionError) {
                responseData.setView(getErrorMessageView());
                responseData.setStatus(HttpServletResponse.SC_FORBIDDEN);

                putModelItem("msg", "У вас недостаточно прав для проведения данной операции");
            }
        }
    }

    protected boolean checkOperationPermission(EntityOperationDesc operationDesc) {
        switch (operationDesc.getOperation()) {
            case Create:
                this.processedEntity = getService().constructFromRequest();
                return getService().canAdd(this.processedEntity);
            case Read:
                return getService().canRead(getIdFromRequest());
            case Update:
                this.processedEntity = getService().constructFromRequest();
                return getService().canUpdate(this.processedEntity);
            case Delete:
                this.processedEntity = getService().constructFromRequest();
                return getService().canDelete(this.processedEntity);
            default:
                return false;
        }
    }

    protected boolean hasPermissionError() {
        return this.permissionError;
    }

    protected E getEntity() {
        return this.processedEntity;
    }
}
