#!/bin/bash -eu

### Config
DEV_BRANCH=develop
MAIN_BRANCH=master

if [ "$#" -ne "1" ]; then
  CURRENT_VERSION=`mvn -B -Dtycho.mode=maven help:evaluate -Dexpression=project.version | grep -v 'INFO'`
  echo -e "usage: $0 <new version>\nNote: current version = ${CURRENT_VERSION}"
  exit 1
fi

# First check access on git (will exit on error)
echo "Testing Github permission"
git ls-remote git@github.com:preesm/dftools.git > /dev/null
# then on SF with maven settings
TMPDIR=`mktemp -d`
cat > $TMPDIR/pom.xml << EOF
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <artifactId>org.ietr.test</artifactId>
  <groupId>org.ietr</groupId>
  <version>1.0.0</version>
  <packaging>pom</packaging>
  <build>
    <plugins>
      <!-- Finally upload merged metadata and new content -->
      <plugin>
        <groupId>org.preesm.maven</groupId>
        <artifactId>sftp-maven-plugin</artifactId>
        <version>1.0.0</version>
        <executions>
          <execution>
            <id>upload-repo</id>
            <phase>verify</phase>
            <configuration>
              <serverId>preesm-insa-rennes</serverId>
              <serverHost>preesm.insa-rennes.fr</serverHost>
              <serverPort>8022</serverPort>
              <strictHostKeyChecking>false</strictHostKeyChecking>
              <transferThreadCount>1</transferThreadCount>
              <mode>receive</mode>
              <remotePath>/repo/README</remotePath>
              <localPath>${TMPDIR}/to_delete</localPath>
            </configuration>
            <goals>
              <goal>sftp-transfert</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
</project>
EOF
echo "Testing Insa server permission using Maven Sftp plugin"
(cd $TMPDIR && mvn -q verify)
rm -rf $TMPDIR


#warning
echo "Warning: this script will delete ignored files and remove all changes in $DEV_BRANCH and $MAIN_BRANCH"
read -p "Do you want to conitnue ? [NO/yes] " ANS
LCANS=`echo "${ANS}" | tr '[:upper:]' '[:lower:]'`
[ "${LCANS}" != "yes" ] && echo "Aborting." && exit 1

NEW_VERSION=$1

CURRENT_BRANCH=$(cd `dirname $0` && echo `git branch`)
ORIG_DIR=`pwd`
DIR=$(cd `dirname $0` && echo `git rev-parse --show-toplevel`)
TODAY_DATE=`date +%Y.%m.%d`
#change to git root dir
cd $DIR

#move to dev branch and clean repo
git stash -u

#make sure this is last version from git for dev/main branches
git checkout -b tmpBranch$NEW_VERSION
git fetch --all -p
set +e
git branch -D $DEV_BRANCH
git branch -D $MAIN_BRANCH
set -e

git checkout $DEV_BRANCH
git reset --hard
git clean -xdf


#update version in code and stash changes
./releng/update-version.sh $NEW_VERSION
sed -i -e "s/X\.Y\.Z/$NEW_VERSION/g" release_notes.md
sed -i -e "s/XXXX\.XX\.XX/$TODAY_DATE/g" release_notes.md
git stash

# Fix headers
./releng/fix_header_copyright_and_authors.sh
# commit fixed headers (if any)
NBCHANGES=`git status --porcelain | wc -l`
if [ $NBCHANGES -ne 0 ]; then
  git add -A
  git commit -m "[RELENG] Fix headers"
fi

# make sure integration works before deploying and pushing
git stash pop
./releng/build_and_test.sh

#commit new version in develop
git add -A
git commit -m "[RELENG] Prepare version $NEW_VERSION"

#merge in master, add tag
git checkout $MAIN_BRANCH
git merge --no-ff $DEV_BRANCH -m "merge branch '$DEV_BRANCH' for new version $NEW_VERSION"
git tag v$NEW_VERSION

#move to snapshot version in develop and push
git checkout $DEV_BRANCH
./releng/update-version.sh $NEW_VERSION-SNAPSHOT
cat release_notes.md | tail -n +3 > tmp
cat > release_notes.md << EOF
DFTools Changelog
=================

## Release version X.Y.Z
*XXXX.XX.XX*

### New Feature

### Changes

### Bug fix

EOF
cat tmp >> release_notes.md
rm tmp
git add -A
git commit -m "[RELENG] Move to snapshot version"

#deploy from master
git checkout $MAIN_BRANCH
./releng/deploy.sh

#push if everything went fine
git push
git push --tags
git checkout $DEV_BRANCH
git push
