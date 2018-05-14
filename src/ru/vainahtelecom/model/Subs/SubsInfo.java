package ru.vainahtelecom.model.Subs;

import java.util.Objects;

/**
 * Created by REsterWolf on 23.04.2017.
 */
public class SubsInfo {

    private String ontStatus = null;
    private String ontOpticalPower = null;

    public SubsInfo(String ontStatus, String ontOpticalPower) {
        this.ontStatus = ontStatus;
        this.ontOpticalPower = ontOpticalPower;
    }

    public String getOntOpticalPower() {
        return ontOpticalPower;
    }

    public String getOntStatus() {
        return ontStatus;
    }

    public OntStatus getConvertedOntStatus() {
        return OntStatus.getOntStatusByString(ontStatus);
    }

    public String getConvertedOpticalPower() {
        String value = "Unknown";
        if (Objects.nonNull(ontOpticalPower)) {
            try {
                value = String.valueOf(Double.parseDouble(ontOpticalPower) / 100 - 100);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        return value;
    }

    public enum OntStatus {
        ONLINE {
            @Override
            public String rowValue() {
                return "Online (1)";
            }
        },

        OFFLINE {
            @Override
            public String rowValue() {
                return "Offline (2)";
            }
        },

        UNKNOWN {
            @Override
            public String rowValue() {
                return "Unknown";
            }
        };

        public abstract String rowValue();

        public static OntStatus getOntStatusByString(String status) {
            if (Objects.isNull(status)) {
                return UNKNOWN;
            }

            switch (status) {
                case "1":
                    return ONLINE;
                case "2":
                    return OFFLINE;
                default:
                    return UNKNOWN;
            }
        }
    }


    public void setOntOpticalPower(String ontOpticalPower) {
        this.ontOpticalPower = ontOpticalPower;
    }


    public void setOntStatus(String ontStatus) {
        this.ontStatus = ontStatus;
    }
}
