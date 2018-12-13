data class GroupChat(
    var groupName: String = "",
    var description: String = ""
)
// Note that the default values are required in order to be able to use DataSnapshot.getValue()