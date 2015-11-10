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

    public User() {
        this (null, new Nicknames());
    }

    public User(String name) {
        this (name, new Nicknames());
    }

    public User(Nicknames nicknames) {
        this (null, nicknames);
    }

    public User(String name, Nicknames value) {
        this.name = name;
        this.nicknames = value;
    }

    public String getName() {
        return name;
    }

    public Nicknames getNicknames() {
        return nicknames;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNicknames(Nicknames nicknames) {
        this.nicknames = nicknames;
    }

    @Override
    public boolean equals(Object o) {
        return  o instanceof User &&
                name.equalsIgnoreCase(((User) o).getName()) &&
                nicknames.equals(((User) o).getNicknames());
    }

    @Override
    public int hashCode() {
        return 28 * name.hashCode() + nicknames.hashCode() * 3;
    }
}
