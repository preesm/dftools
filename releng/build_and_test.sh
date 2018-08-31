#!/bin/bash -eu

DIR=$(cd `dirname $0` && echo `git rev-parse --show-toplevel`)

FETCH=NO
FAST=NO
CHECK=NO
if [ ! -z ${1+x} ]; then
  if [ "$1" == "--fetch" ]; then
    FETCH=YES
  fi
  if [ "$1" == "--check" ]; then
    CHECK=YES
  fi
  if [ "$1" == "--fast" ]; then
    FAST=YES
  fi
fi

# enable Sonar on Travis
if [ ! -z ${TRAVIS+x} ]; then
  SONAR="sonar:sonar"
else
  SONAR=
fi

#fetch version:
if [ "$FETCH" == "YES" ]; then
  echo "Fetch dependencies ..."
  time (
    (cd $DIR && mvn -U -e -C -B -P doUpdateSite -Dtycho.mode=maven dependency:go-offline)
    (cd $DIR && mvn -U -e -C -B -P doUpdateSite help:help)
  )
  exit 0
fi

#check version:
if [ "$CHECK" == "YES" ]; then
  echo "Check code ..."
  time (cd $DIR && mvn  -e -C -B -P doUpdateSite -Dtycho.mode=maven checkstyle:check)
  exit 0
fi

#fast version:
if [ "$FAST" == "YES" ]; then
  echo "Fast build ..."
  time (cd $DIR && mvn -e -C -B -P doUpdateSite clean verify ${SONAR} -fae)
  exit 0
fi

time (
  #validate POM
  echo ""
  echo "Validate POM"
  echo ""
  (cd $DIR && mvn -U -e -C -B -V -P doUpdateSite -Dtycho.mode=maven help:help -q) || exit 1
  #fetch maven deps
  echo ""
  echo "Fetch Maven Deps"
  echo ""
  (cd $DIR && mvn -U -e -C -B -V -P doUpdateSite -Dtycho.mode=maven dependency:go-offline) || exit 2
  #CHECKSTYLE (offline)
  echo ""
  echo "Checkstyle"
  echo ""
  (cd $DIR && mvn -e -C -B -V -Dtycho.mode=maven checkstyle:check) || exit 3
  #fetch P2 deps
  echo ""
  echo "Fetch P2 Deps"
  echo ""
  (cd $DIR && mvn -U -e -C -B -V -P doUpdateSite help:help) || exit 4
  #clean (offline)
  echo ""
  echo "Clean"
  echo ""
  (cd $DIR && mvn -e -C -B -V -P doUpdateSite -Dtycho.mode=maven clean) || exit 5
  #build code and package (offline, no tests)
  echo ""
  echo "Build & Package"
  echo ""
  (cd $DIR && mvn -e -C -B -V package -fae -Dmaven.test.skip=true) || exit 6
  # build and run tests (offline)
  echo ""
  echo "Test all & Run Sonar"
  echo ""
  (cd $DIR && mvn -e -C -B -V verify ${SONAR} -fae) || exit 7
  #package update site (offline, no tests)
  echo ""
  echo "Package update site"
  echo ""
  (cd $DIR && mvn -e -C -B -V -P doUpdateSite package -fae -Dmaven.test.skip=true) || exit 8
)


exit 0
