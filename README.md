# JSONPlaceholder fetcher

## How to build the project

1. Create gradle wrapper by executing following command in command line:

```
gradle wrapper

```

2. Use gradle wrapper to build an app:

```
./gradlew build
```

3. Use gradle wrapper to produce fat-jar (by shadow plugin):

```
./gradlew shadow
```


### How to run tests

```
./gradlew test
```

## Running app


Run already created fat-jar which is located at `build/libs/` directory:


```
gzatorski@debian:~/Dokumenty/Github/JsonGrabber$ java -jar build/libs/JSONPlaceholderGrabber-all.jar 

```

## Configuration


You can specify additional configuration file (everride default) which must be located at the same directory as fat-jar file and must be named `properties.conf`. For example:

```
api.protocol=https
api.address=jsonplaceholder.typicode.com
api.endpoint=posts
download.maxposts=100
download.directory=downloadedPosts
download.overwrite=true
```
