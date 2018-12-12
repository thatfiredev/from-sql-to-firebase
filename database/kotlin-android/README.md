# Realtime Database Android (Kotlin)

## Contents
- [Create](#create)
- [Read](#read)
- [Update](#update)
- [Delete](#delete)

## Additional classes and pre-defined variables
Other than the example from [README.md](/README.md), we'll also use the following classes:
- [User](User.kt)

And the following variables:
```kotlin
val rootRef = FirebaseDatabase.getInstance().reference
val usersRef = rootRef.child("users")
```

## Create
On the Realtime Database, each object needs to be stored under a key. This
 key should identify the object uniquely in the database.
 You can think of this key as the Primary Key on a SQL Table.
 If you already know the key beforehand, you can use:
**SQL:**
```sql
INSERT INTO users (id, fullname, email, age, city) VALUES (2, 'Joana', 'joana@email.com', 21, 'Maputo');
```

**Firebase:**
```kotlin
val newUser = User(2, "Joana", "joana@email.com", 21, "Maputo")
val key = "2"
usersRef.child(key).setValue(newUser)
```

Or if you want this key to be automatically generated:
**SQL:**

(Assuming you've enabled auto-increment in that table)
```sql
INSERT INTO User (name, email, age, city) VALUES ('Joana', 'joana@email.com', 21, 'Maputo');
```

**Firebase:**
```kotlin
val newUser = User(2, "Joana", "joana@email.com", 21, "Maputo")
val key = usersRef.push().key
usersRef.child(key).setValue(newUser);
```
Note that this generates a random Key (like "-JhQ76OEK_848CkIFhAq" for example). Learn about how these keys are
 generated [here](https://firebase.googleblog.com/2015/02/the-2120-ways-to-ensure-unique_68.html).

## Read
In order to read data, Firebase makes use of Listeners. Check the
 [Firebase Documentation](https://firebase.google.com/docs/database/android/read-and-write#listen_for_value_events)
 to know more about it. Some examples in this guide might omit the listener.

### Read all users
Please note that this is **not recommended**, because if this node contains a large number of users,
 it might use a lot of bandwidth, memory and battery.
**SQL:**
```sql
SELECT * FROM users;
```
**Firebase:**
```kotlin
usersRef.addValueEventListener(object : ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
        // Here, dataSnapshot contains all the users.
        // We can then iterate through it to get each one of them
        // and maybe add them to a Collection like List or ArrayList
        for (snap in dataSnapshot.getChildren()) {
            val user = snap.getValue(User::class.java);
            // ...
        }
        
    }
    
    override fun onCancelled(databaseError: DatabaseError) {
        // Getting Users failed, log a message
        Log.w(TAG, databaseError.toException())
    }
});
```

### Read a single user
**SQL:**
```sql
SELECT * FROM users WHERE id = 2;
```

**Firebase:**
If you know the user's key, you can call use the `child()` method:
```kotlin
usersRef.child("-JhQ76OEK_848CkIFhAq").addValueEventListener(object : ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
        // Here, dataSnapshot contains only the user we have specified,
        // thus there's no need to iterate anything
        val user = snap.getValue(User::class.java)
        
    }
    
    override fun onCancelled(databaseError: DatabaseError) {
        // Getting User failed, log a message
        Log.w(TAG, databaseError.toException())
    }
});
```

<!--
**SQL:**
```sql

```
**Firebase:**
```kotlin

```
-->

## Update
Changing the user's name from "Joana" to "John":
**SQL:**
```sql
UPDATE TABLE users SET fullname = 'John' WHERE id = 2;
```

**Firebase:**

You can either overwrite the previous name:
```kotlin
usersRef.child(2).child("fullname").setValue("John")
```
Or update the object and overwrite it: 
```kotlin
newUser.fullName = "John"
usersRef.child(2).setValue(newUser)
```

## Delete
**SQL:**
```sql
DELETE FROM users WHERE id = 2;
```

**Firebase:**

Either overwrite the object with `null`:
```kotlin
usersRef.child(2).setValue(null)
```
Or call `removeValue()`:
```kotlin
usersRef.child(2).removeValue()
```