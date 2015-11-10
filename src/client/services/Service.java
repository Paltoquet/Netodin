package client.services;

import exceptions.ServiceException;
import interfaces.ConvertJSON;

import java.util.Scanner;

public abstract class Service implements ConvertJSON
{
    /**
     * Initialize the service object from
     * the command line
     *
     * @param sc the list of word from the command line
     * @throws ServiceException if the command is not valid
     */
    public abstract void initialize(Scanner sc) throws ServiceException;

    /**
     *
     * @return the converted object to string
     */
    public String toString() {
        return toJSON().toString();
    }
}
