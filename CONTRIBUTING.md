# Contributing to Kelp

> Guidelines for project contributors 



## Adding new features to the core module

If you add new features to the core module please develop a rough model in advance to make sure it does not contain any version-specific code. Write implementations for your feature for all versions and test your feature directly on the server and with unit tests if possible. 



## Writing version implementations

Version implementations work a lot with packets and the Minecraft protocol. Here you can make use of useful sites like [Wiki.vg](https://wiki.vg). It contains reverse-engineered data about the Minecraft protocol for the latest version. Use the page history feature to find out more about older versions. Although the version implementation code does not seem so important for development with the framework, please document and test your code. 



## Conventions 

- To make development easier for other developers, ever contributor should follow the same conventions.
- **Do not use Lombok!** It is a forbidden dependency in this project!
- Try to maintain the standards of the [Google Style Guide](https://google.github.io/styleguide/javaguide.html) for Java.



## Contributor License Agreement

Whenever you contribute to this project, remember that it is still open-source and none of the copyright belongs to you. PXAV and the Kelp-project have the right to administrate and distribute your contributions as part of the project. 

