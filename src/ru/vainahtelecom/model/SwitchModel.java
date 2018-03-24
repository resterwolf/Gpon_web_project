package ru.vainahtelecom.model;

public enum SwitchModel {

    HUAWEI_MA5608T {
        @Override
        public String rowValue() {
            return "HuaweiMA5608T";
        }

        @Override
        public String discoveryOid() {
            return "1.3.6.1.4.1.2011.6.128.1.1.2.43.1.9";
        }

        @Override
        public String ontStatusOid() {
            return "1.3.6.1.4.1.2011.6.128.1.1.2.62.1.22";
        }

        @Override
        public String ontOpticalPowerOid() {
            return "1.3.6.1.4.1.2011.6.128.1.1.2.51.1.6";
        }
    },

    ELTEX {
        @Override
        public String rowValue() {
            return "ELTX";
        }
    },

    UNKNOWN_MODEL {
        @Override
        public String rowValue() {
            return "Unknown model";
        }
    };

    public String rowValue() {
        return null;
    }

    public String discoveryOid() {
        return null;
    }

    public String ontStatusOid() {
        return null;
    }

    public String ontOpticalPowerOid() {
        return null;
    }

    public static SwitchModel getModelByString(String string) {
        if (string.equals(HUAWEI_MA5608T.rowValue())) {
            return HUAWEI_MA5608T;
        } else if (string.equals(ELTEX.rowValue())) {
            return ELTEX;
        } else {
            return UNKNOWN_MODEL;
        }
    }

}
