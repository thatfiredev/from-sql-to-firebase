public class GroupChat {
    private String groupName;
    private String description;

    public GroupChat() {
        // An empty constructor is required in order to be able to use DataSnapshot.getValue()
    }

    public GroupChat(String groupName, String description) {
        this.groupName = groupName;
        this.description = description;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "GroupChat{" +
                "groupName='" + groupName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}