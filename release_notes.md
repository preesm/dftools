DFTools Changelog
=================

## Release version 1.1.9
*2017.04.19*

### New Feature
* Add test plug-in fragments for future test campaigns
* Build process now produces
  * a source feature (include source code)
  * a 'meta' feature including all development requirements for DFTools
  * The aggregated Javadoc
* Maven build process allows to automatically deploy on SourceForge server

### Changes
* The build process does not require Graphiti and Preesm source code anymore
  * It uses the Preesm complete repo to lookup missing OSGi dependencies (see URL in pom.xml)
* Third party dependencies are moved to external OSGi dependencies instead of jar archives within projects. See https://github.com/preesm/externaldeps
* Add checkstyle hook on Maven build to enforce code style
  * Config file is ./releng/VAADER_checkstyle.xml
  * Installable pre-commit hook in ./releng/hooks/
* Cleanup and Format code using Eclipse template that respects checkstyle config file
  * Eclipse preferences under ./releng/VAADER_eclipse_preferences.epf
* Move Ecore generated files to ecore-gen
* Move Ecore compliance level to 8.0
* Update charset and line endings to UTF-8 and LF
* DFTools has its own release note
* .gitignore updated
* Unused Maven dependencies removed
* Add LICENCE file
* Update README.md
* Fix copyright header on most files (see ./releng/ scripts)
* Add .mailmap file for prettier git logs
* Modifications in the API of some exceptions

### Bug fix
* Fix Checkstyle and Findbugs issues
* Fix few warnings that raised after Eclipse cleanup

## Release version 1.1.8
* 2016.09.28 - See PREESM 2.2.4 release notes
