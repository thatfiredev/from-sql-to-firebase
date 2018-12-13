data class User (
    var id: Int = 0,
    var fullName: String = "",
    var email: String = "",
    var age: Int = 0,
    var city: String = ""
)
// Note that the default values are required in order to be able to use DataSnapshot.getValue()