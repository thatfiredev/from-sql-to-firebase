# Contributing Guidelines

We love contributions! But we'd like you to follow these guidelines to make the contribution process
 easy for everyone involved:

## Issue Tracker
The issue tracker is our main channel to suggest changes and request new languages/platforms.
 
Before submitting your issue, please search the archive. Maybe your question was already answered.

## Pull Requests

Before making any changes, consider following these steps:

1. Search for an open or closed Pull Request related to your changes.

2. Search the issue tracker for issues related to your changes.

3. Open a [new issue](https://github.com/rosariopfernandes/from-sql-to-firebase/issues/new) to discuss your changes
 with the project owners. If they approve it, send the Pull Request.
 
### Structure
When you're sending a PR, make sure it follows our project structure.
 This guide contains [Realtime Database](database/README.md) and
 [Cloud Firestore](firestore/README.md) modules.
Both modules follow this structure:
- [Module name]
  - [language-platform]
    - README.md (english)
    - README-[LANGUAGE-CODE].md (translation of README.md)

### Sending Pull Requests
If your change has been approved, follow this process:

1. [Fork](http://help.github.com/fork-a-repo/) the project, clone your fork and configure the
 remotes:

```bash
# Clone your fork into the current directory
git clone https://github.com/<your-username>/<repo-name>
# Navigate to the newly cloned directory
cd <repo-name>
# Assign the original repo to a remote called "upstream"
git remote add upstream https://github.com/rosariopfernandes/from-sql-to-firebase
```

2. Make your changes in a new branch:

```bash
git checkout -b my-fix-branch master
```

3. Commit the changes using a descriptive commit message

```bash
git commit -a
```

  Note: the optional commit `-a` command line option will automatically "add" and "rm" edited files.

4. Push your branch to GitHub:

```bash
git push origin my-fix-branch
```

5. In GitHub, [send a Pull Request](https://help.github.com/articles/using-pull-requests/) with a
 clear title and description.

* If we suggest changes then:
  * Make the required changes;
  * Rebase your branch and force push to your GitHub repository (this updates your Pull Request):
    ```bash
    # Rebase the branch
    git rebase master -i
    # Update the Pull Request
    git push origin my-fix-branch -f
    ```
That's it! Thank you for you contribution!

### After your Pull Request is merged

You can delete your branch and pull changes from the original
 (upstream) repository:

1. Delete the remote branch on GitHub either through the GitHub UI or your local shell as follows:

```bash
git push origin --delete my-fix-branch
```

2. Check out the master branch:

```bash
git checkout master -f
```

3. Delete the local branch:

```bash
git branch -D my-fix-branch
```

4. Update your master with the latest upstream version:

```bash
git pull --ff upstream master
```
