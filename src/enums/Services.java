package enums;

import exceptions.ServiceException;
import server.commands.DeleteUser;

public enum Services {
    ADD, UPDATE, DELETE, LIST, GETNICKNAMES, QUIT;

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
            case UPDATE:
                return new client.services.UpdateUser();
            case DELETE:
                return new client.services.DeleteUser();
            case LIST:
                return new client.services.ListUsers();
            case GETNICKNAMES:
                return new client.services.GetNicknames();
            case QUIT:
                return new client.services.Quit();
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
    public static server.commands.Command getServerService(String service) throws ServiceException {
        if (!hasService(service)) {
            throw new ServiceException("The requested service doesn't exist");
        }

        switch (valueOf(service.toUpperCase())) {
            case ADD:
                return new server.commands.AddUser();
            case UPDATE:
                return new server.commands.UpdateUser();
            case DELETE:
                return new DeleteUser();
            case LIST:
                return new server.commands.ListUsers();
            case GETNICKNAMES:
                return new server.commands.GetNicknames();
            case QUIT:
                return new server.commands.Quit();
            default:
                throw new ServiceException("The requested service doesn't exist"); // only makes the shitty compiler happy
        }
    }
}