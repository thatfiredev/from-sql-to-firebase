---
title: From SQL to Firebase
---
# [WIP] From SQL to Firebase

Simple guide to help SQL developers understand how to use Firebase.
This guide contains most SQL queries and it's Firebase equivalent.
  
## Contents
- [Cloud Firestore](firestore/README.md)
  - [Java (Android)](firestore/java-android/README.md)
  - [Kotlin (Android)](firestore/kotlin-android/README.md)
- [Realtime Database](database/README.md)
  - [Java (Android)](database/java-android/README.md)
  - [Kotlin (Android)](database/kotlin-android/README.md)
  
## Our Example
To help you understand Firebase, we'll be using a SQL Database containing 3 tables: `Users`, `GroupChats` and
 `Participants`.

The `Users` table contains the users registered on our app. For each user, we'll register their: id, full name, email,
 age and city name.

The `GroupChats` table contains a list of group chats created on the app. For each of them,
 we'll need to know their: id, name and short description. 

The `Participants` table establishes the relation between users and group chats. It should show what groups each user
 participates in and which users are participating in each group. This table contains the attributes:
 `userId` and `groupId`.

## Available Languages
- [English](README.md)
- [Portuguese](README-PT.md)

## Contributing
Contributions are welcome! Please read our [guidelines](CONTRIBUTING.md) to know how to contribute.

## Acknowledgements
This project was inspired by and based on:
- [Firebase Database for SQL Developers]() Youtube video series presented by [David East](https://github.com/davideast);
- [Firebase Database para Desenvolvedores SQL](https://medium.com/android-dev-moz/firebasesql-4ee3d26a3d15) blog posts
 by [Rosário P. Fernandes](https://github.com/rosariopfernandes);
- Various answers from [#AskFirebase]() and [StackOverflow](https://stackoverflow.com/tags/firebase/).

## LICENSE
This project is licensed under the MIT License - see the [license file](LICENSE) for details.
