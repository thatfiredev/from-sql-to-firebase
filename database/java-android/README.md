# Realtime Database Android (Java)

## Contents
- [Create](#create)
- [Read](#read)
- [Update](#update)
- [Delete](#delete)

## Additional classes and pre-defined variables
Other than the example from [README.md](/README.md) and the [initial data](../initial_data.json), we'll also use the
 following classes:
- [User](User.java)
- [GroupChat](GroupChat.java)

And the following variables:
```java
DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
DatabaseReference usersRef = rootRef.child("users");
DatabaseReference groupsRef = rootRef.child("groupChats");
DatabaseReference participantsRef = rootRef.child("participants");
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
```java
User newUser = new User(2, "Joana", "joana@email.com", 21, "Maputo");
String key = "2";
usersRef.child(key).setValue(newUser);
```

Or if you want this key to be automatically generated:

**SQL:**

(Assuming you've enabled auto-increment in that table)
```sql
INSERT INTO User (name, email, age, city) VALUES ('Joana', 'joana@email.com', 21, 'Maputo');
```

**Firebase:**
```java
User newUser = new User(2, "Joana", "joana@email.com", 21, "Maputo");
String key = usersRef.push().getKey();
usersRef.child(key).setValue(newUser);
```
Note that this generates a random key (like "-JhQ76OEK_848CkIFhAq" for example). Learn about how these keys are
 generated [here](https://firebase.googleblog.com/2015/02/the-2120-ways-to-ensure-unique_68.html).

## Read
This is the part where it gets tricky. That's because in SQL development, the data structure is independent from the
views. Which means you can design your view not worrying about how the database is structured and vice-versa.
 
Now in the Realtime Database, things don't work that way. Yo need to **STRUCTURE YOUR DATA AFTER YOUR VIEW**. This
 means that your structure may vary depending on how you want to display your data. That's why we have a few
  different examples bellow.

You should also know that in order to read data, Firebase makes use of Listeners. Check the
 Firebase Documentation to know more about the different listeners and the difference between
 [Value Event Listeners](https://firebase.google.com/docs/database/android/read-and-write#listen_for_value_events) and
 [Child Event Listeners](https://firebase.google.com/docs/database/android/lists-of-data#child-events)
 to know more about it. Please note that some examples in this guide might omit the listener.
 
Finally, refer to [Sorting and Filtering Data](https://firebase.google.com/docs/database/android/lists-of-data#sorting_and_filtering_data)
 to understand our examples about querying, sorting and filtering data on the Realtime Database.

### Read all users
Please note that this is **not recommended**, because if this node contains a large number of users,
 it might use a lot of bandwidth, memory and battery.
 
**SQL:**
```sql
SELECT * FROM users;
```
**Firebase:**
```java
usersRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        // Here, dataSnapshot contains all the users.
        // We can then iterate through it to get each one of them
        // and maybe add them to a Collection like List or ArrayList
        for (DataSnapshot snap : dataSnapshot.getChildren()) {
            User user = snap.getValue(User.class);
            // ...
        }
        
    }
    
    @Override
    public void onCancelled(DatabaseError databaseError) {
        // Getting Users failed, log a message
        Log.w(TAG, databaseError.toException());
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
```java
usersRef.child("-JhQ76OEK_848CkIFhAq").addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        // Here, dataSnapshot contains only the user we have specified,
        // thus there's no need to iterate anything
        User user = snap.getValue(User.class);
        
    }
    
    @Override
    public void onCancelled(DatabaseError databaseError) {
        // Getting User failed, log a message
        Log.w(TAG, databaseError.toException());
    }
});
```

### Find a user by email
**SQL:**
```sql
SELECT * FROM users WHERE email = "joana@email.com";
```
**Firebase:**
```java
usersRef.orderByChild("email").equalTo("joana@email.com");
```
Please note that while this was supposed to return a single user, it actually
 returns a list containing 1 user, thus you'll need to either iterate through the
 DataSnapshot or get the user at index 0 from the list. 


### Read 5 users from a specific city
**SQL:**
```sql
SELECT * FROM users WHERE city = "Maputo" LIMIT 5;
```
**Firebase:**

Limit to the first 5 results:
```java
usersRef.orderByChild("city").equalTo("Maputo").limitToFirst(5);
```
Limit to the last 5 results:
```java
usersRef.orderByChild("city").equalTo("Maputo").limitToLast(5);
```

### Find users who are not older than 18 years
**SQL:**
```sql
SELECT * FROM users WHERE age < 18;
```
**Firebase:**
```java
usersRef.orderByChild("age").endAt(17);
```

The example above finds the users who's age is **less than** 18. If you'd like to find the
 users who's age is **greater than or equal to** 18, you'd use:
 
```java
usersRef.orderByChild("age").startAt(18);
```

### Find users who are between 30 and 60 years old
**SQL:**
```sql
SELECT * FROM users WHERE age >= 30 AND age <= 60;
```
**Firebase:**
```java
usersRef.orderByChild("age").startAt(30).endAt(60);
```

### Find users who are 21 years old and live in a specific city
**SQL:**
```sql
SELECT * FROM users WHERE age = 21 AND city = "Maputo";
```
**Firebase:**

After seeing the previous queries, you might think this can be done by putting
 together `orderByChild("city")` and `orderByChild("age")`. Sorry to disappoint you,
 but the Realtime Database does not let you chain `orderByChild()` methods.
 
 As a workaround, you can add a new attribute to the database (and a new property to the `User` class),
 let's say `age_city`. Then it's value would be composed by "[age]_[city]" (e.g. "19_Maputo" or "30_Beira").
 So than we can run the query:
```java
usersRef.orderByChild("age_city").equalTo("21_Maputo");
``` 

### Read data from users who have joined the "java" group
**SQL:**

Something similar to:
```sql
SELECT g.name AS groupName, u.fullName AS userName, u.age, u.email, u.city
FROM groupChats AS g
INNER JOIN participants AS p ON g.groupId = p.groupId
INNER JOIN users AS u ON u.id = p.userId
WHERE g.groupId = "java"; 
```
**Firebase:**

`JOIN` operations are not supported in the Realtime Database. As a workaround, we can
 place a second query inside the listener of the first query.
```java
participantsRef.child("java").addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot snap : dataSnapshot.getChildren()) {
            // We stored the user's key as a participant's key 
            String userKey = snap.getKey();
            // The value we stored under that key was the user's name
            // So, if you only need their name, you can stop in following line:
            String userName = snap.child(userKey).getValue(String.class);
            
            
            // Now to get all the user data:
            usersRef.child(userKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = snap.getValue(User.class);
                    // TODO: Add the user data to the UI
                    
                }
                
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting User failed, log a message
                    Log.w(TAG, databaseError.toException());
                }
            });
        }
        
    }
    
    @Override
    public void onCancelled(DatabaseError databaseError) {
        // Getting Participants failed, log a message
        Log.w(TAG, databaseError.toException());
    }
});
```
Please note that this is a **bad practice** and should be avoided, after all, you should always
 **STRUCTURE YOUR DATA AFTER YOUR VIEW**. Be careful when using nested queries, as they may result in bad performance
 and may incur higher costs on your billing plan.
 
The alternative may be scary for SQL Developers, but it's perfectly normal on the NoSQL world. It's called
 **De-normalization**. It consists in duplicating data to simplify queries. So instead of
```json
"participants": {
    "java": {
      "john": "John Doe",
      "maria": "Maria Sitoe"
    }
}
```
We'd have:
```json
"participants": {
    "java": {
      "john": {
        "fullName": "John Doe",
        "email": "johndoe@email.com",
        "age": 21,
        "city": "Gaborone"
      },
      "maria": {
        "fullName": "Maria Sitoe",
        "email": "mariasitoe@email.com",
        "age": 17,
        "city": "Tokyo"
      }
    }
}
```
So now we only need a single query:
```java
participantsRef.child("java").addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot snap : dataSnapshot.getChildren()) {
            User user = snap.getValue(User.class);
            // TODO: Add the user data to the UI
        }
        
    }
    
    @Override
    public void onCancelled(DatabaseError databaseError) {
        // Getting Participants failed, log a message
        Log.w(TAG, databaseError.toException());
    }
});
```
Note that duplicating data means that you'll need to update the data in all the different nodes
 that it has been copied to.
 

<!--
**SQL:**
```sql

```
**Firebase:**
```java

```
-->

## Update
### Changing the user's name from "Joana" to "John":

**SQL:**
```sql
UPDATE TABLE users SET fullname = 'John' WHERE id = 2;
```

**Firebase:**

You can either overwrite the previous name:
```java
usersRef.child(2).child("fullname").setValue("John");
```
Or update the object and overwrite it: 
```java
newUser.setFullname("John");
usersRef.child(2).setValue(newUser);
```

## Delete
**SQL:**
```sql
DELETE FROM users WHERE id = 2;
```

**Firebase:**

Either overwrite the object with `null`:
```java
usersRef.child(2).setValue(null);
```
Or call `removeValue()`:
```java
usersRef.child(2).removeValue();
```