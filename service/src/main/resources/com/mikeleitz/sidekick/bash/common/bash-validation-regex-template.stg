#!/usr/bin/env bash

################################################################################
# Common function that takes 2 arguments: a value to validate and a regex to
# do the validation.
#
# $1 is the value to validate
# $2 is the regex to do the validation
#
# This function returns a 0 if the value is valid and any other number if
# it's not valid.
#
# Use $? right after this function call to get the return value.
################################################################################
function .validateValueWithRegex() {
  echo "Validating input using a regex."

  valueToTest="$1"
  regex="$2"

  echo "Using regex: " "$regex"
  echo "Validating: " "$valueToTest"

  if [[ $valueToTest =~ $regex ]]; then
    return 0
  else
    return 1
  fi

  exit 0
}

VALUE=$1
VALIDATION_REGEX="^-?[0-9]{1,10}$"

.validateValueWithRegex "$VALUE" "$VALIDATION_REGEX"

# Get the return value, 0 is success anything else is validation failure.
echo $?
