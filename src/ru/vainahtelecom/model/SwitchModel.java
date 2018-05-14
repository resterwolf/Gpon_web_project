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
        public String ontStatusOid(String index) {
            return "1.3.6.1.4.1.2011.6.128.1.1.2.62.1.22" + index + ".1";
        }

        @Override
        public String ontOpticalPowerOid(String index) {
            return "1.3.6.1.4.1.2011.6.128.1.1.2.51.1.6" + index;
        }
    },

    UNDEFINED_MODEL {
        @Override
        public String rowValue() {
            return "Undefined model";
        }

        @Override
        public String discoveryOid() {
            return null;
        }

        @Override
        public String ontStatusOid(String index) {
            return null;
        }

        @Override
        public String ontOpticalPowerOid(String index) {
            return null;
        }
    };

    public abstract String rowValue();

    public abstract String discoveryOid();

    public abstract String ontStatusOid(String index);

    public abstract String ontOpticalPowerOid(String index);

    public static SwitchModel getModelByString(String string) {
        if (string.equals(HUAWEI_MA5608T.rowValue())) {
            return HUAWEI_MA5608T;
        } else {
            return UNDEFINED_MODEL;
        }
    }

    public static boolean isSupport(String modelStr) {
        return modelStr.equals(HUAWEI_MA5608T.rowValue());
    }

}
