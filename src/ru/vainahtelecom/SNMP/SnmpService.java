package ru.vainahtelecom.SNMP;

import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;
import ru.vainahtelecom.config.AppConfig;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SnmpService implements AutoCloseable {

    // Public class properties ...
    public static final String COMMUNITY = AppConfig.getGlobalConfig().getSnmp().getCommunity();

    // Private instance properties ...
    private final String SNMP_COMMUNITY;
    private final int SNMP_RETRIES;
    private final long SNMP_TIMEOUT;
    private final Snmp SNMP;
    private final TransportMapping TRANSPORT;

    // Constructors ...
    public SnmpService(String community, int retries, long timeout) throws IOException {
        this.SNMP_COMMUNITY = community;
        this.SNMP_RETRIES = retries;
        this.SNMP_TIMEOUT = timeout;
        this.TRANSPORT = new DefaultUdpTransportMapping();
        this.SNMP = new Snmp(TRANSPORT);
        TRANSPORT.listen();
    }

    public SnmpService(String community) throws IOException {
        this(community, 3, 3000L);
    }

    public SnmpService() throws IOException {
        this(COMMUNITY);
    }

    // Public instance methods ...
    @Override
    final public void close() throws IOException {
        try {
            TRANSPORT.close();
        } finally {
            SNMP.close();
        }
    }

    final public ResponseEvent get(String targetAddress, List<String> oids) throws IllegalArgumentException, IOException {
        final Target target = getTarget(targetAddress);
        final PDU pdu = getPDU(oids, PDU.GET);
        return SNMP.send(pdu, target);
    }

    final public ResponseEvent get(String targetAddress, String oidValue) throws IllegalArgumentException, IOException {
        final Target target = getTarget(targetAddress);
        final PDU pdu = getPDU(oidValue, PDU.GET);
        return SNMP.get(pdu, target);
    }

    final public List<TreeEvent> walk(String targetAddress, String oid) throws IllegalArgumentException {
        final Target target = getTarget(targetAddress);
        TreeUtils treeUtils = new TreeUtils(SNMP, new DefaultPDUFactory());
        return treeUtils.getSubtree(target, new OID(oid));
    }

    // Private instance methods ...
    private Target getTarget(String address) throws IllegalArgumentException {
        Address targetAddress = GenericAddress.parse("udp:" + address + "/161");
        if (Objects.isNull(targetAddress))
            throw new IllegalArgumentException("Wrong address format: " + address + ". Correct example: 10.25.24.96");

        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(SNMP_COMMUNITY));
        target.setAddress(targetAddress);
        target.setRetries(SNMP_RETRIES);
        target.setTimeout(SNMP_TIMEOUT);
        target.setVersion(SnmpConstants.version2c);
        return target;
    }

    private PDU getPDU(List<String> oids, int pduType) {
        PDU pdu = new PDU();
        for (String oid : oids)
            pdu.add(new VariableBinding(new OID(oid)));

        pdu.setType(pduType);
        return pdu;
    }

    private PDU getPDU(String oid, int pduType) {
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(pduType);
        pdu.setRequestID(new Integer32(1));
        return pdu;
    }

}
