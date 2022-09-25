# git-implementation

Git-implementation is a simple and personal implementation of git. It was built for exploring the internals of git and try some things.

It is implemented in kotlin with [koin](https://github.com/InsertKoinIO/koin) for dependency injection and it follows the N-tier pattern.

## Supported commands

- `init`
- `commit`
- `add <path>...`
- `log`
- `checkout <commit-id>` 
(note: everything that is not in the tree is discarded)

## How to run

Create a jar that includes all the project dependencies:
```
./gradlew :fatJar
```
The output will appear in `build/libs`.
Run the jar:
```
java -jar <path to git-implementation-X.X-SNAPSHOT-standalone.jar> <arguments>
```

where `arguments` from the list of supported commands.
(note: make sure that the .jar is not in the same folder as the git project because it will not be ignored and will be erased on checkout)

Also it's possible to create an alias for running the jar with:
```
alias <name>="java -jar <path to git-implementation-X.X-SNAPSHOT-standalone.jar>"
```

### Example
```
// sets fgit as an alias
alias fgit="java -jar <path to git-implementation-X.X-SNAPSHOT-standalone.jar>"

// initializes git in the directory
fgit init

// create a directory and some files
mkdir dir1
touch file1 dir1/file2

// adds the files to the index
fgit add file1 dir1/file2

// creates a commit and it will output a commit hash
fgit commit

// create some other directories and another file
mkdir dir2
mkdir dir2/dir3
touch dir2/dir3/file3

// adds the new file to the index
fgit dir2/dir3/file3

// creates another commit and will output a different commit hash
fgit commit

// list the content of the directory to see all the files created so far
ls

// show the hashes of the two commits created
fgit log

// checkout to the previous commit by using the hash of the output of the first commit
fgit checkout <first-commit-hash>

// list the content of the directory to see only the files that were in the first commit
ls
```

## How to run tests
Run junit test suite:
```
./gradlew test
```
