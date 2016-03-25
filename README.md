![alt tag](https://raw.github.com/JAVEO/knowler/master/docs/img/knowler.png)

Knowler is the web tool to synchronize videos from presentations with slides. It is developed on Polymer and Firebase.

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

RUN CLIENT
==============
cd client/web/

npm install

gulp serve

DEPLOY
==============
gulp

firebase deploy

