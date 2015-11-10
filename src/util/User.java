package util;

public class User
{
    /**
     * The name of the new user
     */
    private String name;

    /**
     * The aliases of the new user
     */
    private Nicknames nicknames;

    /**
     * Default constructor
     */
    public User() {
        this (null, new Nicknames());
    }

    /**
     * Constructor with name
     *
     * @param name the username
     */
    public User(String name) {
        this (name, new Nicknames());
    }

    /**
     * Constructor with nicknames
     *
     * @param nicknames the nicknames of the user
     */
    public User(Nicknames nicknames) {
        this (null, nicknames);
    }

    /**
     * Full constructor
     *
     * @param name the username
     * @param value the nicknames of the user
     */
    public User(String name, Nicknames value) {
        this.name = name;
        this.nicknames = value;
    }

    /**
     *
     * @return the username
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the nicknames of the user
     */
    public Nicknames getNicknames() {
        return nicknames;
    }

    /**
     *
     * @param name the username
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param nicknames the nicknames of the user
     */
    public void setNicknames(Nicknames nicknames) {
        this.nicknames = nicknames;
    }

    @Override
    public boolean equals(Object o) {
        return  this == o ||
                (o instanceof User &&
                name.equalsIgnoreCase(((User) o).getName()) &&
                nicknames.equals(((User) o).getNicknames()));
    }

    @Override
    public int hashCode() {
        return 28 * name.hashCode() + nicknames.hashCode() * 3;
    }
}
