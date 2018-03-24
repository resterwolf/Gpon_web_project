package ru.vainahtelecom.servlets;


import org.snmp4j.smi.VariableBinding;
import org.snmp4j.util.TreeEvent;
import ru.vainahtelecom.SNMP.SnmpService;
import ru.vainahtelecom.errors.Error;
import ru.vainahtelecom.errors.ErrorListener;
import ru.vainahtelecom.model.SwitchModel;
import ru.vainahtelecom.model.Subs.Subscriber;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class IndexServlet extends HttpServlet implements ErrorListener {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/pages/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String switchIpSelectValue = req.getParameter("switch_ip_select").trim();
        String subscriberNameInputValue = req.getParameter("subscriber_name_input").trim();

        // Check received string is validate format
        if (!switchIpSelectValueIsValidate(switchIpSelectValue)) {
            showError(Error.INVALIDATE_PARAMETERS, req, resp);
            return;
        }

        // Divide string to ip address and switch model
        int dividerIndex = switchIpSelectValue.indexOf("_");
        String switchModelString = switchIpSelectValue.substring(0, dividerIndex);
        String switchIPString = switchIpSelectValue.substring(dividerIndex + 1);

        // Determinate model of switch
        SwitchModel switchModel = SwitchModel.getModelByString(switchModelString);

        // Check model is support
        if (switchModel == SwitchModel.UNKNOWN_MODEL || switchModel == SwitchModel.ELTEX) {
            showError(Error.UNSUPPORTED_SWITCH_MODEL, req, resp);
            return;
        }

        // Get all subscribers
        SnmpService snmpService = new SnmpService();
        List<TreeEvent> eventList = snmpService.walk(switchIPString, switchModel.discoveryOid());

        List<Subscriber> subscribers = new ArrayList<>();

        for (TreeEvent treeEvent : eventList) {

            if (treeEvent.isError()) {
                System.out.println(treeEvent.getErrorMessage());
                return;
            }
            for (VariableBinding variableBinding : treeEvent.getVariableBindings()) {

                String subsName = variableBinding.toValueString();
                String oid = variableBinding.getOid().toDottedString();
                String subsIndex = oid.substring(35);

                // Skip this iteration if has filtering by username
                if (!subscriberNameInputValue.isEmpty() && !subsName.contains(subscriberNameInputValue)) {
                    continue;
                }

                Subscriber subscriber = new Subscriber(subsName, oid, subsIndex);
                subscribers.add(subscriber);
            }
        }

        // Not matched subscribers
        if (subscribers.size() == 0) {
            showError(Error.SUBS_NOT_FOUND, req, resp);
            return;
        }

        // Put nested parameters in current session
        req.getSession().setAttribute("switchModel", switchModel);
        req.getSession().setAttribute("subscribers", subscribers);
        req.getSession().setAttribute("targetIpAddress", switchIPString);
        req.getRequestDispatcher("WEB-INF/pages/index.jsp").forward(req, resp);

    }


    @Override
    public void showError(Error error, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("error", error);
        req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
    }

    private Boolean switchIpSelectValueIsValidate(String string) {
        if (Objects.isNull(string) || string.length() == 0)
            return false;

        if (!string.contains("_"))
            return false;

        return true;
    }


}
