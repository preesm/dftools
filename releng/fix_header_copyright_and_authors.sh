#!/bin/bash

#########
##	
## 	Automatically replace dates and author list
##  in files containing the corresponding patterns
##  (see {LOWDATE|UPPDATE|AUTHORS}PATTERN variable
##  below). Information is fetched from the git 
##  repository.
#*******************************************************************************
# Copyright or © or Copr. IETR/INSA - Rennes (%%DATE%%) :
#
# %%AUTHORS%%
#
# This software is a computer program whose purpose is to [describe
# functionalities and technical features of your software].
#
# This software is governed by the CeCILL  license under French law and
# abiding by the rules of distribution of free software.  You can  use, 
# modify and/ or redistribute the software under the terms of the CeCILL
# license as circulated by CEA, CNRS and INRIA at the following URL
# "http://www.cecill.info". 
#
# As a counterpart to the access to the source code and  rights to copy,
# modify and redistribute granted by the license, users are provided only
# with a limited warranty  and the software's author,  the holder of the
# economic rights,  and the successive licensors  have only  limited
# liability. 
#
# In this respect, the user's attention is drawn to the risks associated
# with loading,  using,  modifying and/or developing or reproducing the
# software by the user in light of its specific status of free software,
# that may mean  that it is complicated to manipulate,  and  that  also
# therefore means  that it is reserved for developers  and  experienced
# professionals having in-depth computer knowledge. Users are therefore
# encouraged to load and test the software's suitability as regards their
# requirements in conditions enabling the security of their systems and/or 
# data to be ensured and,  more generally, to use and operate it in the 
# same conditions as regards security. 
#
# The fact that you are presently reading this means that you have had
# knowledge of the CeCILL license and that you accept its terms.
#*******************************************************************************
#########

echo ""

DATEPATTERN="2017"
AUTHORSPATTERN="Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)

TMPFILE=`mktemp --suffix=biglisttosed`
grep "Antoine Morvan <antoine.morvan@insa-rennes.fr> (2017)

echo " Starting" 

while read -r line
do (
	file="$line"
    BASENAME=$(basename "$file")
    EXTENSION="${BASENAME##*.}"
    EXTENSION=`echo $EXTENSION | tr [a-z] [A-Z]`
    
	case $EXTENSION in
		BAT)
			#"@rem "
			COMMENT="@rem "
			;;
		C |	CPP | H | JAVA | XTEND)
			#" * "
			COMMENT=" * "
			;;
		MAK | PROPERTIES)
			#"# "
			COMMENT="# "
			;;
		XML)
			#"    "
			COMMENT="    "
			;;
		*)
			#echo "Unsupported file extension $EXTENSION"
			;;
	esac
	
    FILEAUTHORLIST=`git log --follow --use-mailmap --date=format:'%Y' --format='%aE' "$file" | sort -u`
    for AUTHOR in $FILEAUTHORLIST; do 
		AUTHORUPPERDATE=`git log --follow --use-mailmap --date=format:'%Y' --format='%ad %aE' "$file" | sort -u | grep $AUTHOR | tail -n 1 | cut -d' ' -f1`
		AUTHORLOWERDATE=`git log --follow --use-mailmap --date=format:'%Y' --format='%ad %aE' "$file" | sort -u | grep $AUTHOR | head -n 1 | cut -d' ' -f1`
		if [ "$AUTHORLOWERDATE" == "$AUTHORUPPERDATE" ]; then
			AUTHORDATE="($AUTHORLOWERDATE)"
		else
			AUTHORDATE="($AUTHORLOWERDATE - $AUTHORUPPERDATE)"
		fi
		
		LINE=`git log --follow --use-mailmap --date=format:'%Y' --format='%aN <%aE>' "$file" | sort -u | grep $AUTHOR`
		sed -i -e "s/$AUTHORSPATTERN/${LINE} ${AUTHORDATE}\n$COMMENT$AUTHORSPATTERN/g" "$file"
    done
    
	TMPFILE2=`mktemp --suffix=tosed`
	cat "$file" | grep -v "$AUTHORSPATTERN" > $TMPFILE2
	
	
    LOWDATE=`git log --follow --date=format:'%Y' --format='%ad' "$file" | sort -u | head -n 1`
    UPPDATE=`git log --follow --date=format:'%Y' --format='%ad' "$file" | sort -u | tail -n 1`
	
	if [ "$LOWDATE" == "$UPPDATE" ]; then
		GLOBDATE="$LOWDATE"
	else
		GLOBDATE="$LOWDATE - $UPPDATE"
	fi
		
    cat "$TMPFILE2" | sed -e "s/$DATEPATTERN/$GLOBDATE/g" > "$file" 
    rm $TMPFILE2
) & done < $TMPFILE

sleep 2
echo " Waiting for the threads to finish ..."

wait
rm $TMPFILE

echo " Done."
echo ""
