package enums;

import exceptions.ServiceException;
import server.modify.Command;

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
    public static client.services.Service getClientService(String service) throws ServiceException {
        if (!hasService(service)) {
            throw new ServiceException("The requested service doesn't exist");
        }

        switch (valueOf(service.toUpperCase())) {
            case ADD:
                return new client.services.AddUser();
            case LIST:
                return new client.services.ListUsers();
            default:
                throw new ServiceException("The requested service doesn't exist"); // only make the shitty compiler happy
        }
    }

    /**
     *
     * @param service the requested service
     * @return an instance of the requested service
     * @throws ServiceException if the service does not exist
     */
    public static Command getServerService(String service) throws ServiceException {
        if (!hasService(service)) {
            throw new ServiceException("The requested service doesn't exist");
        }

        switch (valueOf(service.toUpperCase())) {
            case ADD:
                return new server.modify.AddUser();
            case LIST:
                return new server.modify.ListUsers();
            default:
                throw new ServiceException("The requested service doesn't exist"); // only make the shitty compiler happy
        }
    }
}