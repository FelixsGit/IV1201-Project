[![Build Status](https://travis-ci.org/FelixsGit/IV1201-Project.svg?branch=master)](https://travis-ci.org/FelixsGit/IV1201-Project)

## Github workflow

### Getting started

Clone the repository using an HTTPS or SSH URL obtained from the green button on the main page of the repo.

`git clone <URL>`

You will probably only need to do this step once.

### Creating a branch

When you have decided what to start working on, it is time to make a new branch for your work. First, make sure you are on the master branch.

`git checkout master`

Second, fetch any remote changes that might have occurred since last time you fetched.

`git fetch`

`git status`

If it turns out that your local master is behind the remote master, you should update your local master with the pull command.

`git pull`

Third, create a new branch with a descriptive name and check it out. This can be done with a single command.

`git checkout -b <new branch name>`

As usual, you can use `git status` to make sure that everything worked correctly.

### Working on a branch

We have only one guideline so far for commits in this repo:

- Commit messages must be in english, present tense, and they should be short and descriptive.

Here is some general advice:

- Fetch often! Fetching is safe and does not change your branch, it just checks if changes have been made to the remote. Then you can decide if you want to merge/pull those changes into your local branch (you generally do).
- Commit often! Commit messages are great for self documentation.
- Avoid commiting irrelevant changes! I.e. try to only commit the files that you actually worked on. Use `git status` and follow the instructions to select which files to include in a commit.
