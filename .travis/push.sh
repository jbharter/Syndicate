#!/bin/sh

setup_git() {
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "Travis CI"
}

pull_files() {
  git pull origin stable

}
commit_files() {
  git checkout -b stable
  git add --all
  git commit --message "Travis build: $TRAVIS_BUILD_NUMBER"
}

upload_files() {
  git remote add origin https://${GH_TOKEN}@github.com/jbharter/Syndicate.git > /dev/null 2>&1
  git push --quiet --set-upstream origin stable
}

setup_git
commit_files
upload_files