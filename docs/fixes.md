# Assorted Fixes
This file will document any small, assorted fixes not explictly related to the application as a whole. These include environment issues, docker issues, etc.
## Docker Credentials Issue
### Error: 
When running `docker compose up` or `docker build`, the following error surfaces: 

```
failed to resolve source metadata for docker.io/library/gradle:jdk17: error getting credentials - err: exec: "docker-credential-desktop": executable file not found in $PATH, out: ``
make: *** [app-up] Error 17
```

### Solution
Need to delete the `credsStore` from `~/.docker/config.json` (or rename to `credStore`). 

### Source
[Stack Overflow: exec: "docker-credential-desktop.exe": executable file not found in $PATH](https://stackoverflow.com/questions/65896681/exec-docker-credential-desktop-exe-executable-file-not-found-in-path)