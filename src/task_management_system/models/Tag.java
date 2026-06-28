package task_management_system.models;

public class Tag {
    private final String name;

    public Tag(String name) { this.name = name; }

    public String getName() { return name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        return name.equals(((Tag) o).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
