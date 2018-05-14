package ru.vainahtelecom.servlets;

import org.snmp4j.event.ResponseEvent;
import ru.vainahtelecom.SNMP.SnmpService;
import ru.vainahtelecom.errors.Error;
import ru.vainahtelecom.errors.ErrorListener;
import ru.vainahtelecom.model.SwitchModel;
import ru.vainahtelecom.model.Subs.SubsInfo;
import ru.vainahtelecom.model.Subs.Subscriber;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by REsterWolf on 4/21/2017.
 */
public class SubscribersServlet extends HttpServlet implements ErrorListener {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!req.getSession().isNew()) {
            // Extract param from session
            String subscriberIndex = req.getParameter("index");
            Object switchIP = req.getSession().getAttribute("targetIpAddress");
            SwitchModel switchModel = ((SwitchModel) req.getSession().getAttribute("switchModel"));
            List<Subscriber> subscriberList = ((List<Subscriber>) req.getSession().getAttribute("subscribers"));

            if (Objects.isNull(subscriberIndex) || Objects.isNull(switchIP) || Objects.isNull(subscriberList) || Objects.isNull(switchModel)) {
                showError(Error.PARAM_WAS_NULL, req, resp);
                return;
            }

            int index;

            try {
                index = Integer.parseInt(subscriberIndex);
            } catch (NumberFormatException ex) {
                showError(Error.PARAM_NOT_CORRECT, req, resp);
                return;
            }

            if (index < subscriberList.size()) {
                Subscriber subscriber = subscriberList.get(index);

                SnmpService snmpService = new SnmpService();

                // Prepare oid
                String ontStatusOid = switchModel.ontStatusOid(subscriber.getIndex());
                String ontOpticalPowerOid = switchModel.ontOpticalPowerOid(subscriber.getIndex());

                List<String> oidList = Arrays.asList(ontStatusOid, ontOpticalPowerOid);
                ResponseEvent responseEvent = snmpService.get((String) switchIP, oidList);

                if (responseEvent.getError() != null) {
                    return;
                }

                String ontStatus = responseEvent.getResponse().getVariableBindings().get(0).toValueString();
                String ontOpticalPower = responseEvent.getResponse().getVariableBindings().get(1).toValueString();

                subscriber.setSubsInfo(new SubsInfo(ontStatus, ontOpticalPower));

                req.setAttribute("subscriber", subscriber);
                req.getRequestDispatcher("WEB-INF/pages/subscriber_info.jsp").forward(req, resp);

            }
        } else {
            showError(Error.TIMEOUT_SESSION, req, resp);
        }
    }

    @Override
    public void showError(Error error, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (error == Error.TIMEOUT_SESSION) {

            resp.sendRedirect("/");

        } else {

            req.setAttribute("error", error);
            req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);

        }


    }

}
