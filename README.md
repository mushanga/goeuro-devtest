# How To Run
> Maven and Java 8 required

1) Clone the project
```shell
   $ git clone https://github.com/mushanga/goeuro-devtest.git
   $ cd goeuro-devtest
```
2) Create the executable jar
```shell
   $ mvn package
```
3) Run the jar

```shell
   $ java -jar -Dfile.encoding=utf-8 target/goeurodevtest-0.0.1-SNAPSHOT.jar "berlin"
```
> `-Dfile.encoding=utf-8` parameter is crucial not to use the system default encoding

4) See the results

```shell
   $ cat results.csv
```

5) Run the tests

```shell
   $ mvn test
```
