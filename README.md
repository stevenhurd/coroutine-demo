# template-boot-msvc

This is a template to be used when creating new spring boot microservices.

It provides a simple rest controller as an example. When running, you can access the rest endpoint with:

```
curl http://localhost:8080/config
```

## creating a new app

The script requires `rename` & `gnu-sed` applications, install via `brew`.

```
brew install rename gnu-sed

```

The new app uses gradle 4.0, you will likely need IntelliJ 2016.3.6 or newer.

If you get the following error:
`Gradle project error - java.lang.ClassNotFoundException: org.springframework.boot.SpringApplication`
You need to upgrade.

### Pull and create a new repo

```bash
export APP_NAME_PREFIX=simple

# Clone the template repo
git clone git@github.com:turo/template-boot-msvc.git ${APP_NAME_PREFIX}-msvc


cd ${APP_NAME_PREFIX}-msvc

# remove the previous .git directory
rm -rf .git

# Run script to rename your porject
./rename-project.sh $APP_NAME_PREFIX
rm rename-project.sh

# create new git repo
git init .
git add .
git commit -m "Copy of template-boot-msvc into $APP_NAME_PREFIX"
```

### create appropriate github repository

http://github.com/turo

### Push repo up to github

```bash
git remote add origin git@github.com:turo/$APP_NAME_PREFIX-msvc.git
git push -u origin master
```

## Setup Teamcity

### Create a new subproject under the `Microservices` project

1. Goto [Teamcity: Microservices](https://teamcity.rr.mu/admin/editProject.html?projectId=Microservices)
2. Select create subproject
3. Select `manually`
4. Set the name to be your project `template-boot-msvc`

### Add VCS to your project

1. Add a VCS root by going to the `VCS Roots`section on the right side
2. Select `Create VCS Root`
3. Set `Type of VCS` to `Git`
4. Set the `VCS root name` to `template-boot-msvc`
5. The `VCS root ID` should automatically be `Microservices_TemplateBootMsvc_TemplateBootMsvc`
6. Copy the git ssh URL, it should look like `git@github.com:turo/template-boot-msvc.git`
7. Enter the following branch spec

```
+:refs/heads/*
+:refs/pull/(*/merge)
```

8. Set `Authentication Method` to `Uploaded key`
9. Set `Uploaded key` to `rollsbot TeamCity ssh`
10. Click `Test Connection` -- it should be successful
11. Click `Create`

### Load settings from VCS

1. Select the `Versioned settings` section on the right
2. Select `Synchronization enabled`
3. Choose the VCS root you just created in the `Project settings VCS root` drop down
4. Select `Use settings from VCS`
5. Check `Show settings changes in builds`
6. Check `Store secure values (like passwords or API tokens) outside of VCS`
7. Select `Settings format` as `kotlin`
8. Select `Apply`
9. You should see a dialog `The settings of the following projects were found in the VCS:` -- Select `Load project settings from VCS`

### Cleanup

1. update the commit status publisher in features
2. open the build configuration
3. go to the `build features` section
4. select the `commit status publisher`
5. copy the teamcity token from the github rollsbot account
6. paste it into the github token

At this point, you should see the status messages at the bottom of the page. If there are issues, you can update the config and try reloading it.

## Setup [pre-commit](https://pre-commit.com/)

1. pre-commit-jvm requires the following to run:
   [pre-commit(2.8+)](https://pre-commit.com/) `brew install pre-commit`
   [Coursier](https://get-coursier.io/) `brew install coursier/formulas/coursier`

2. Install the git hooks using `pre-commit install` to set up the git hook scripts

3. The following checks will run on git commit:
   Check Yaml...............................................................Passed
   Fix End of Files.........................................................Passed
   Trim Trailing Whitespace.................................................Passed
   Kotlin static analysis...................................................Passed
