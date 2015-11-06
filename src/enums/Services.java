package enums;

import exceptions.ServiceException;
import services.Add;
import services.List;
import services.Service;

public enum Services {
    ADD, LIST /*, UPDATE, DELETE*/;

    /**
     * Verify if a service exist
     *
     * @param strService the requested service
     * @return true if the service exist, false otherwise
     */
    public static boolean hasService(String strService) {
        for (Services service : values()) {
            if (strService.equalsIgnoreCase(String.valueOf(service))) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param service the requested service
     * @return an instance of the requested service
     * @throws ServiceException if the service does not exist
     */
    public static Service getService(String service) throws ServiceException {
        if (!hasService(service)) {
            throw new ServiceException("The requested service doesn't exist");
        }

        switch (valueOf(service.toUpperCase())) {
            case ADD:
                return new Add();
            case LIST:
                return new List();
            default:
                return null; // only make the shitty compiler happy
        }
    }
}