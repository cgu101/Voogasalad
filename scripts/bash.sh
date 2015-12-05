#!/bin/bash


function1() {
	PATH1="/Users/christopherstreiffer/Desktop/CS308/voogasalad_IllegalTeamNameException"
	PATH2="${PATH1}/$1"
	FILES="${PATH1}/src/authoring/files"
	TYPE="$2"

	for i in $( ls ${PATH2} ); do
		FILE="$(echo $i | egrep -o ".*[^java]")"
		touch "${FILES}/${TYPE}/${FILE}properties"
	done
}

function2() {
	PATH1="/Users/christopherstreiffer/Desktop/CS308/voogasalad_IllegalTeamNameException"
	FILES="${PATH1}/src/authoring/files"

	for i in $( ls ${FILES} ); do
		VAL="${i}="
		for j in $( ls ${FILES}/${i} ); do
			FILE="$(echo ${j} | egrep -o ".*[^\.properties]")"
			VAL="${VAL},${FILE//./}"
		done
		echo $VAL
	done
}

function3() {
	PATH1="/Users/christopherstreiffer/Desktop/CS308/voogasalad_IllegalTeamNameException"
	FILES="${PATH1}/src/authoring/files/triggers"

	for i in $( ls ${FILES} ); do
		# > "${FILES}/${i}"
		# echo "className=" >> "${FILES}/${i}"
		# echo "numActors=" >> "${FILES}/${i}"
		# echo "actors.1=" >> "${FILES}/${i}"
		# echo "param.1.string=" >> "${FILES}/${i}"
		# echo "param.1.type=" >> "${FILES}/${i}"
		echo "param.0.actorindex=" >> "${FILES}/${i}"
		echo "param.1.string=" >> "${FILES}/${i}"
		echo "param.1.type=" >> "${FILES}/${i}"
		echo "param.1.actorindex=" >> "${FILES}/${i}"	
	done
}

function4() {
	PATH1="/Users/christopherstreiffer/Desktop/CS308/voogasalad_IllegalTeamNameException"
	FILES="${PATH1}/src/authoring/files/actions"

	for i in $( ls ${FILES} ); do
		# > "${FILES}/${i}"
		# echo "className=" >> "${FILES}/${i}"
		# echo "numActors=" >> "${FILES}/${i}"
		# echo "actors.1=" >> "${FILES}/${i}"
		# echo "param.1.string=" >> "${FILES}/${i}"
		# echo "param.1.type=" >> "${FILES}/${i}"
		echo "param.0.actorindex=" >> "${FILES}/${i}"
		echo "param.1.string=" >> "${FILES}/${i}"
		echo "param.1.type=" >> "${FILES}/${i}"
		echo "param.1.actorindex=" >> "${FILES}/${i}"
	done
}

function3
function4
