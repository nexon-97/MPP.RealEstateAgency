package com.utils.request;

import com.services.shared.ServiceManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.util.Map;

public class ResponseData {

    private View view;
    private int status;

    public ResponseData(int status, View view) {
        this.status = status;
        this.view = view;
    }

    public ModelAndView getModelAndView() {
        Map<String, Object> model = ServiceManager.getInstance().getSharedResources().getModel();
        return new ModelAndView(this.view, model);
    }

    public View getView() {
        return this.view;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setView(View view) {
        this.view = view;
    }
}
