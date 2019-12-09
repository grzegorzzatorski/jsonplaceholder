# JSONPlaceholderGrabber

JSONPlaceholderGrabber is a demo application which can fetch all the data from `/posts` REST API endpoint from [JSONPlaceholder](https://jsonplaceholder.typicode.com/). Each post is saved as a separate JSON file as `<post_id>.json`. Application is written in Scala and use Gradle as a build system.

## How to build the project

1. Create gradle wrapper by executing following command in command line:

```
$ gradle wrapper
```

2. Use gradle wrapper to build an app:

```
$ ./gradlew build
```

3. Use gradle wrapper to produce fat-jar (by shadow plugin):

```
$ ./gradlew shadow
```


## How to run tests

```
$ ./gradlew test
```

## Running the app


Run already created fat-jar which is located at `build/libs/` directory:


```
$ java -jar build/libs/JSONPlaceholderGrabber-all.jar
```

## Configuration

### Application settings

You can specify additional configuration file (override default) by passing throught `-Dconfig.file=path/to/config-file` option . For example:

```
$ java -Dconfig.file=path/to/config-file -jar build/libs/JSONPlaceholderGrabber-all.jar
```

Content of the configuration file should include the following fields:
```
api.protocol=https                          # [Mandatory] Choose either "http" or "https"
api.address=jsonplaceholder.typicode.com    # [Mandatory] Address of JSONPlaceholder API
api.endpoint=posts                          # [Mandatory] Name of JSONPlaceholder endpoint to fetch
download.directory=downloadedPosts          # [Mandatory] Path to directory in which downloaded files will be saved
download.overwrite=true                     # [Mandatory] Should be set to 'true' or 'false'. If true, downloaded files will be overriden
```

### Logging

JSONPlaceholderGrabber uses `scala-logging` library. By default `logback.xml` is provided at `/resources` directory inside a jar. If you want to override it, prepare your own `logback.xml` and pass during app run:
```
$ java -Dlogback.configurationFile=/directory/to/logback.xml -jar JSONPlaceholderGrabber-all.jar
```