DFTools Changelog
=================

## Release version X.Y.Z
*XXXX.XX.XX*

### New Feature

### Changes

### Bug fix


## Release version 1.7.4
*2018.06.13*

### New Feature

### Changes
* Enable travis_retry when fetching dependencies;
* Force externaldeps to [3.2.0,4.0.0);
* Force Graphiti to [1.8.1,2.0.0);
* Fix checkstyle configuration;

### Bug fix


## Release version 1.7.3
*2018.06.01*

### New Feature

### Changes
* Fix manifest files;
* Fix warnings;

### Bug fix


## Release version 1.7.2
*2018.05.25*

### New Feature

### Changes
* Force Graphiti to 1.7.0+;

### Bug fix


## Release version 1.7.1
*2018.05.18*

### New Feature

### Changes
* Adding persistence level to DAGInit properties;

### Bug fix


## Release version 1.7.0
*2018.05.16*

### New Feature

### Changes
* Replace most of the integer occurrences with longs;

### Bug fix


## Release version 1.6.0
*2018.05.14*

### New Feature

### Changes
* Adding vertex end reference to dag init vertices; 
* Setting the INIT_SIZE property of a DAGInitVertex to the same value of a SDFInitVertex;
* Copy all properties of an SDFVertex to a DAGVertex;
* Retrieving Sink and Source port name of SDFVertex to DAGVertex;
* Using DAGEdge in the aggregate instead of SDFEdge now;

### Bug fix
* Force activation of UI plugin to have the properties tested properly;

## Release version 1.5.0
*2018.04.24*

### New Feature

### Changes
* Add data size property to SDF edges;
* Use the data size property when computing DAG edges weight in DAG transformation;

### Bug fix


## Release version 1.4.1
*2018.04.23*

### New Feature

### Changes
* DAG transformation now properly sets the DAG vertex refinement;

### Bug fix


## Release version 1.4.0
*2018.03.29*

### New Feature

### Changes
* Update mailmap file;
* Move deploy server to preesm.insa-rennes.fr;
* Update project info (URL);
* Force externaldeps to 3.0.0+;
* Force graphiti to 1.6.0+;

### Bug fix


## Release version 1.3.0
*2018.02.05*

### New Feature

### Changes
* Force external deps to 2.0.0+

### Bug fix


## Release version 1.2.10
*2018.01.11*

### New Feature

### Changes
* fix visibility of GMLImporter;
* Update coding policies to 1.1.0;

### Bug fix


## Release version 1.2.9
*2018.01.11*

### New Feature

### Changes
* Update releng scripts;
* update checkstyle to 8.5
* update coding policies: max line length is now 120 chars
* use Maven plugins and coding policies from Maven Central instead of Preesm own maven repo

### Bug fix


## Release version 1.2.8
*2017.12.20*

### New Feature

### Changes
* Update releng scripts;
* Add dependency to RCPTT in the dev feature;
* disable javadoc generation (source is available via the Dev Features and source plugins);

### Bug fix


## Release version 1.2.7
*2017.12.04*

### New Feature

### Changes
* Update releng scripts;
* Minor refactoring in the workflow runner parts;
* DFTools console now activates upon output;
* Update external deps to 1.4.0;

### Bug fix
* Fix special actors port indexing (see https://github.com/preesm/preesm/issues/50);


## Release version 1.2.6
*2017.11.14*

### New Feature

### Changes
* add release script

### Bug fix
* Fix SlamUserFactory

## Release version 1.2.5
*2017.11.14*

### New Feature

### Changes
* Change Slam Component API;
* Add dependencies to Ecore Diagram Tools, Xcore and Sonarlint (dev feature), and CDT (dev + normal feature);
* Add Slam user factory;
* Force version of external deps to 1.3.0+

### Bug fix

## Release version 1.2.4
*2017.10.31*

### New Feature

### Changes
* Add utility functions in FileUtils. Simplify code according to;
* Fix DAG Transform : rethrow exception on error;
* Fix logger: do not dupplicate error message;
* Enable travis;
* Cleanup code;

### Bug fix

## Release version 1.2.3
*2017.10.17*

### New Feature
* Add feature dependency to TM Terminal to have easy terminal access;
* Add feature dependency to Graphiti SDK Plus source;

### Changes
* Workflow execution now trigger a refresh of the workspace before and after execution;
* Remove unused code and related unecessary dependencies;
* Fix log time formatter (prefix digit with 0 instead of a blank space);
* Update to Graphiti 1.4.3

### Bug fix
* Fix clone() method of SDFSinkInterfaceVertex class (see #3);

## Release version 1.2.2
*2017.08.17*

### New Feature

### Changes
* Upgrade to Eclipse Oxygen;
* Use new Graphiti extension point name (new version);

### Bug fix

## Release version 1.2.1
*2017.07.18*

### Changes
* Add explicit verions for Xtend dependencies to avoid bugs due to API break;
* Cleanup releng files
* Normalize feature licenses
* Update Graphiti to 1.4.1

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
