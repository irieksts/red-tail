Red-tail is a REST interface to hawk.  

Source code: https://github.com/irieksts/red-tail

Downloads: http://code.google.com/p/red-tail/downloads/list

## Prerequisite 

### Mysql

1. Install mysql (A good VM is http://www.turnkeylinux.org/mysql)

2. Create a mysql database

3. Edit jdbc in `conf/red-tail.yaml`

### Pellet

1. Download pellet http://clarkparsia.com/pellet/download/

2.   `pellet.bat dig` `pellet.sh dig` Runs on 8080 and use 8081 too

## Run

1. `java -jar red-tail-${project.version}.jar server conf/red-tail.yaml`