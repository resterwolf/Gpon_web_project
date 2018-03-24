package ru.vainahtelecom.errors;

/**
 * Created by REsterWolf on 4/24/2017.
 */
public enum Error {

    UNSUPPORTED_SWITCH_MODEL {
        @Override
        public String getMessage() {
            return "Данная модель коммутатора не поддерживается!";
        }
    },

    INVALIDATE_PARAMETERS {
        @Override
        public String getMessage() {
            return "";
        }
    },

    PARAM_NOT_CORRECT {
        @Override
        public String getMessage() {
            return "Индекс отсутствует или неверного формата!";
        }
    },

    RESPONSE_IS_NULL {
        @Override
        public String getMessage() {
            return "Пустой ответ на snmp get request!";
        }
    },

    SUBS_NOT_FOUND {
        @Override
        public String getMessage() {
            return "По вашему запросу ничего не найдено!";
        }
    },

    TIMEOUT_SESSION {
        @Override
        public String getMessage() {
            return "Время сессии истекло!";
        }
    },

    PARAM_WAS_NULL {
        @Override
        public String getMessage() {
            return "Некоторые параметры не имеют значений!";
        }
    };

    public String getMessage() {
        return "Неизвестная ошибка!";
    }

}