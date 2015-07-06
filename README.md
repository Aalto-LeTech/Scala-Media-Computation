# The Scala Media Computation Library

This project is targeted mainly for instructors and students of introductory programming courses. From instructors' viewpoint, its goal is to provide means to motivate students via contextualized teaching. There are many ways to provice context, and the approach choosen here is the one of multimedia computation, that is, programmatically creating and processing multimedia content in a creative way. To provide the necessary scaffolding for novice students already challenged to learn for example logical and algorithmic thinking, programming concepts and paradigms, a new programming language (in this case, [Scala](http://www.scala-lang.org/)), and the related development tools, this library offers ways to concentrate only in the topics to be practiced while still producing hopefully interesting and attractive results.

<br />

## Repository Content
The repository contains only the files needed to create a fresh development setup of the project: source code and other resources as well as the initial settings for the build tool. For example, project files for Eclipse are not included, for they are environment-specific and can be automatically generated and updated by the build tool.

The main level folders in this repository are as follows:

| Path | Description |
| ---- | ----------- |
| `scala-library` | The *Scala Media Computation Library* source code. |



## Making a Local Copy
One possibility to clone this repository onto your local mass storage is to use [Git](https://git-scm.com/) itself. From command line this can be done like below:
```
git clone https://github.com/Aalto-LeTech/Scala-Media-Computation.git
```
This will create a `Scala-Media-Computation` folder into the folder in which it is executed. Other possibilities to clone the repository include e.g. IDE plugins and other graphical user interfaces developed to make using of Git easier.



## Building the Library

This library uses [sbt](http://www.scala-sbt.org/) as its build tool. One way to test and package the library is to issue the following command in its root folder (`Scala-Media-Computation/scala-library/`):
```
sbt clean test package
```
The packaged Java archives (.jar files) will be found under the generated `target` folder.


## Developing the Library with the Scala-IDE

The [Scala-IDE](http://scala-ide.org/) is an Eclipse-based development environment, so to use it for development, the first step is to generate the project files for Eclipse. The following command, when entered in the library's root folder, takes care of that:
```
sbt eclipse
```

Afterwards, the project can be imported into the Scala-IDE as follows:
  1. From the *File* menu, select the *Import...* item
  2. From the listbox in the *Import* dialog, select *General* / *Existing Projects into Workspace*
  3. Press *Next*
  4. Press *Browse*, navigate to the library's root folder (`Scala-Media-Computation/scala-library/`) and press *OK*
  5. From the *Projects* list, select the *Scala Media Computation Library*
  6. Press *Finish*

Now the *Scala Media Computation Library* project should open into the currently open workspace. Since the Scala-IDE does not include support for sbt (yet), it will function only as a code editor. Scala-IDE's own build functionality should be turned off e.g. by unchecking the *Build Automatically* option from the *Project* menu. If the Scala-IDE's internal build is executed, the `bin` folder will be created under the library's root folder; this folder can (and should) be deleted.

NOTE: Any environment-specific files created for or produced by the Scala-IDE, as well as any build products of any build tool, must **not** be committed into the repository!
