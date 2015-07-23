![alt tag](https://raw.github.com/JAVEO/knowler/master/docs/img/knowler.png)

Knowler is the web tool to synchronize videos from presentations with slides. It is developed on akka-http and Polymer.

1. Install and run mongoDb

http://docs.mongodb.org/master/tutorial/install-mongodb-on-ubuntu/?_ga=1.90979482.1039490802.1437608880

If you have problem 'ERROR: dbpath (/data/db) does not exist.' try this:

http://stackoverflow.com/questions/24599119/mongodb-not-working-error-dbpath-data-db-does-not-exist

2. Run mongo and create db called 'knowler':

http://docs.mongodb.org/manual/tutorial/getting-started-with-the-mongo-shell/

FLOW OF WORK WITH GITHUB:
=========================

(Assuming you have Github account)

1. Fork repo from javeo site:

https://github.com/JAVEO/knowler

2. Clone it to your local machine

3. Set remote upstream pointing to javeo:

git remote add upstream https://github.com/JAVEO/knowler

4. Each time you want to pull some new changes from javeo repo:

git pull upstream master

5. Each time you want to push some changes to javeo repo:

git push origin master(so you are pushing it to your github)

then you have to create pull request:

https://help.github.com/articles/creating-a-pull-request/

6. Say to somebody (probably only Daniel has got permission to merge in github/javeo) that you did pull request

https://help.github.com/articles/fork-a-repo/

Useful links for akka http:

docs:

http://doc.akka.io/docs/akka-stream-and-http-experimental/1.0/scala.html?_ga=1.182930466.524433361.1428532886

activators with useful examples:

https://github.com/JAVEO/play-reactivemongo-polymer/blob/master/app/backend/PostRepo.scala

https://github.com/theiterators/akka-http-microservice/blob/master/src/main/scala/AkkaHttpMicroservice.scala

reactive mongo site

http://reactivemongo.org/

Error akka-http (required marshaller):

https://github.com/akka/akka/issues/17754
