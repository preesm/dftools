DFTools Changelog
=================

## Release version X.Y.Z
*XXXX.XX.XX*

### New Feature

### Changes
* Add explicit verions for Xtend dependencies to avoid bugs due to API break;
* Cleanup releng files

### Bug fix
* Fix bug in the Logger throwing exceptions on errors

## Release version 1.2.0
*2017.06.26*

### New Feature
* Add test plug-in fragments for future test campaigns
* Build process now produces
  * a source feature (include source code)
  * a 'meta' feature including all development requirements for DFTools
  * The aggregated Javadoc
* Maven build process allows to automatically deploy on SourceForge server
* Add debug mode in WorkflowExecutors (print stacktraces)

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
* Workflow Task parameters can now be omitted in workflow definition. Their default value will be used without make the flow fail;
* Popup window to select scenario now shows folders containing scenario files only;
* Update Workflow launch configuration. new format is `<project name> [<workflow file name>] [<scenario file name>]`;
* For XTend version to 2.11+
* Use feature import instead of inclusion
* Add discovery sites in dev feature
* Remove unsupported target environments
* Update Checkstyle config file path in parent POM
* Add Eclipse profile in parent POM to disable m2e configuration outside Eclipse
* Update wrapper scripts
* Cleanup releng files
* Update licensing
* Update headers
* Remove use of composite P2 repositories
* Add Jenkinsfile for Multibranch Pipeline projects
* Replace HashMap/Sets with LinkedHashMap/Sets
* Add TMF updates repo for latest XTend lib
* Close Java resources
* Update Graphiti to 1.4.0

### Bug fix
* Include .exsd schemas in the binaries
* Fix a bug in the Workflow due to Graphiti issue
* Fix Checkstyle and Findbugs issues
* Fix few warnings that raised after Eclipse cleanup

## Release version 1.1.8 and earlier
* 2016.09.28 - See PREESM 2.2.4 release notes
