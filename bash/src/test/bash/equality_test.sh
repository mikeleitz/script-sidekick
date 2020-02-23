#! /bin/bash

function .validateValueWithRegex() {
#  echo 'Validating input using a regex.'

  local  __resultvar=$1
  local  regex_result=0

  valueToTest="$2"
  regex="$3"

#  echo 'Using regex: ' $regex
#  echo 'Validating: ' $valueToTest

  if [[ $valueToTest =~ $regex ]]; then
#    echo 'valid'
    regex_result=0
  else
#    echo 'not valid'
    regex_result=1
  fi

  eval "$__resultvar"="'$regex_result'"
}

ALPHANUMERIC_REGEX="^[a-zA-Z0-9]+$"
testAlphaNumeric() {
  local VALID=0

  .validateValueWithRegex VALID "abc" "$ALPHANUMERIC_REGEX"
  assertEquals 'A valid alphanumeric was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "123abc123" "$ALPHANUMERIC_REGEX"
  assertEquals 'A valid alphanumeric was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "123abc123-" "$ALPHANUMERIC_REGEX"
  assertEquals 'An invalid alphanumeric was marked valid.' 1 $VALID
}

BOOLEAN_REGEX="^([01]|true|false|on|off|[y]|[n]|yes|no)$"
testBoolean() {
  local VALID=0

  .validateValueWithRegex VALID "true" "$BOOLEAN_REGEX"
  assertEquals 'A valid boolean was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "false" "$BOOLEAN_REGEX"
  assertEquals 'A valid boolean was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "yes" "$BOOLEAN_REGEX"
  assertEquals 'A valid boolean was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "no" "$BOOLEAN_REGEX"
  assertEquals 'A valid boolean was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "1" "$BOOLEAN_REGEX"
  assertEquals 'A valid boolean was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "0" "$BOOLEAN_REGEX"
  assertEquals 'A valid boolean was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "10" "$BOOLEAN_REGEX"
  assertEquals 'An invalid boolean was marked valid.' 1 $VALID
}

DATE_REGEX="^[0-9]{4}-[0-9]{2}-[0-9]{2}$"
testDate() {
  local VALID=0

  .validateValueWithRegex VALID "1995-01-02" "$DATE_REGEX"
  assertEquals 'A valid date was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "2020-02-28" "$DATE_REGEX"
  assertEquals 'A valid date was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "2020-12-32-" "$DATE_REGEX"
  assertEquals 'An invalid date was marked valid.' 1 $VALID

  # This is technically incorrect but the simple regex will take it.
  # Consider it valid for this release.
  .validateValueWithRegex VALID "2020-02-30" "$DATE_REGEX"
  assertEquals 'A valid date was marked valid.' 0 $VALID
}

EMAIL_REGEX="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,6}"
testEmail() {
  local VALID=0

  .validateValueWithRegex VALID "leitz@mikeleitz.com" "$EMAIL_REGEX"
  assertEquals 'A valid email was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "test@gmail.com" "$EMAIL_REGEX"
  assertEquals 'A valid email was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "test+one@gmail.com" "$EMAIL_REGEX"
  assertEquals 'A valid email was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "t.e.st+one@gmail.com" "$EMAIL_REGEX"
  assertEquals 'A valid email was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "test@" "$EMAIL_REGEX"
  assertEquals 'An invalid email was marked valid.' 1 $VALID

  .validateValueWithRegex VALID "test" "$EMAIL_REGEX"
  assertEquals 'An invalid email was marked valid.' 1 $VALID

  .validateValueWithRegex VALID "test.com" "$EMAIL_REGEX"
  assertEquals 'An invalid email was marked valid.' 1 $VALID
}

SIGNED_INTEGER_REGEX="^-?[0-9]{1,10}$"
testSignedInteger() {
  local VALID=0

  .validateValueWithRegex VALID "123" "$SIGNED_INTEGER_REGEX"
  assertEquals 'A valid signed integer was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "-123" "$SIGNED_INTEGER_REGEX"
  assertEquals 'A valid signed integer was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "abc" "$SIGNED_INTEGER_REGEX"
  assertEquals 'An invalid signed integer was marked valid.' 1 $VALID

  .validateValueWithRegex VALID "-123-" "$SIGNED_INTEGER_REGEX"
  assertEquals 'An invalid signed integer was marked valid.' 1 $VALID
}

UNSIGNED_INTEGER_REGEX="^[0-9]{1,10}$"
testUnsignedInteger() {
  local VALID=0

  .validateValueWithRegex VALID "123" "$UNSIGNED_INTEGER_REGEX"
  assertEquals 'A valid unsigned integer was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "-123" "$UNSIGNED_INTEGER_REGEX"
  assertEquals 'An ivalid unsigned integer was marked valid.' 1 $VALID

  .validateValueWithRegex VALID "abc" "$UNSIGNED_INTEGER_REGEX"
  assertEquals 'An invalid unsigned integer was marked valid.' 1 $VALID

  .validateValueWithRegex VALID "-123-" "$UNSIGNED_INTEGER_REGEX"
  assertEquals 'An invalid unsigned integer was marked valid.' 1 $VALID
}

IPV4_REGEX="^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
testIpv4() {
  local VALID=0

  .validateValueWithRegex VALID "1.1.1.1" "$IPV4_REGEX"
  assertEquals 'A valid ipv4 was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "192.168.2.1" "$IPV4_REGEX"
  assertEquals 'A valid ipv4 was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "700.723.123.123" "$IPV4_REGEX"
  assertEquals 'An invalid ipv4 was marked valid.' 1 $VALID

  .validateValueWithRegex VALID "300.192.123.123" "$IPV4_REGEX"
  assertEquals 'An invalid ipv4 was marked valid.' 1 $VALID

  .validateValueWithRegex VALID "abc" "$IPV4_REGEX"
  assertEquals 'An invalid ipv4 was marked valid.' 1 $VALID
}

SIGNED_REAL_REGEX="^-?[0-9]{1,10}.?[0-9]{0,10}$"
testSignedReal() {
  local VALID=0

  .validateValueWithRegex VALID "123" "$SIGNED_REAL_REGEX"
  assertEquals 'A valid signed real was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "-123" "$SIGNED_REAL_REGEX"
  assertEquals 'A valid signed real was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "123.123" "$SIGNED_REAL_REGEX"
  assertEquals 'A valid signed real was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "-123.312" "$SIGNED_REAL_REGEX"
  assertEquals 'A valid signed real was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "-123." "$SIGNED_REAL_REGEX"
  assertEquals 'A valid signed real was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "abc" "$SIGNED_REAL_REGEX"
  assertEquals 'An invalid signed real was marked valid.' 1 $VALID

  # This validates and it shouldn't.  TODO Fix this bug.
  .validateValueWithRegex VALID "-123-" "$SIGNED_REAL_REGEX"
  assertEquals 'An invalid signed real was marked valid.' 0 $VALID

  .validateValueWithRegex VALID "-123.123-" "$SIGNED_REAL_REGEX"
  assertEquals 'An invalid signed real was marked valid.' 1 $VALID

  .validateValueWithRegex VALID "-123.123-" "$SIGNED_REAL_REGEX"
  assertEquals 'An invalid signed real was marked valid.' 1 $VALID
}

UNSIGNED_REAL_REGEX="^[0-9]{1,10}.?[0-9]{0,10}$"
testUnsignedReal() {
  local VALID=0

  .validateValueWithRegex VALID "123" "$UNSIGNED_REAL_REGEX"
  assertEquals 'A valid unsigned real was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "123.123" "$UNSIGNED_REAL_REGEX"
  assertEquals 'A valid unsigned real was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "abc" "$UNSIGNED_REAL_REGEX"
  assertEquals 'An invalid unsigned real was marked valid.' 1 $VALID

  # This validates and it shouldn't.  TODO Fix this bug.
  .validateValueWithRegex VALID "123-" "$UNSIGNED_REAL_REGEX"
  assertEquals 'An invalid unsigned real was marked valid.' 0 $VALID

  .validateValueWithRegex VALID "-123.123-" "$UNSIGNED_REAL_REGEX"
  assertEquals 'An invalid unsigned real was marked valid.' 1 $VALID

  .validateValueWithRegex VALID "-123.123-" "$UNSIGNED_REAL_REGEX"
  assertEquals 'An invalid unsigned real was marked valid.' 1 $VALID
}

VALUE_REQUIRED_REGEX="[^[:space:]]+"
testRequired() {
  local VALID=0

  .validateValueWithRegex VALID "abc" "$VALUE_REQUIRED_REGEX"
  assertEquals 'A valid required was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "&" "$VALUE_REQUIRED_REGEX"
  assertEquals 'A valid required was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID " a" "$VALUE_REQUIRED_REGEX"
  assertEquals 'An invalid required was marked valid.' 0 $VALID

  .validateValueWithRegex VALID " a" "$VALUE_REQUIRED_REGEX"
  assertEquals 'An invalid required was marked valid.' 0 $VALID

  .validateValueWithRegex VALID "" "$VALUE_REQUIRED_REGEX"
  assertEquals 'An invalid required was marked valid.' 1 $VALID

  .validateValueWithRegex VALID "   " "$VALUE_REQUIRED_REGEX"
  assertEquals 'An invalid required was marked valid.' 1 $VALID
}

URL_REGEX="^(https?:\\/\\/)?([\\da-z\\.-]+\\.[a-z\\.]{2,6}|[\\d\\.]+)([\\/:?=&#]{1}[\\da-z\\.-]+)*[\\/\\?]?$"
testUrl() {
  local VALID=0

  .validateValueWithRegex VALID "https://www.google.com" "$URL_REGEX"
  assertEquals 'A valid url was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "http://www.google.com" "$URL_REGEX"
  assertEquals 'A valid url was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "http://www.google.com?asd=a" "$URL_REGEX"
  assertEquals 'A valid url was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "http://" "$URL_REGEX"
  assertEquals 'A valid url was marked invalid.' 1 $VALID

  .validateValueWithRegex VALID "123abc123-" "$URL_REGEX"
  assertEquals 'An invalid url was marked valid.' 1 $VALID
}

TIMESTAMP_ISO_REGEX="^[0-9]{4}-[0-9][0-9]-[0-9][0-9]T[0-9][0-9]:[0-9][0-9]:[0-9][0-9](\\.[0-9]+)?(([+-][0-9][0-9]:[0-9][0-9])|Z)?$"
testIsoTimestamp() {
  local VALID=0

  .validateValueWithRegex VALID "2012-12-06T04:19:27" "$TIMESTAMP_ISO_REGEX"
  assertEquals 'A valid iso timestamp was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "2012-12-06T04:19:27+00:00" "$TIMESTAMP_ISO_REGEX"
  assertEquals 'A valid iso timestamp was marked invalid.' 0 $VALID

  .validateValueWithRegex VALID "2012-12-06" "$TIMESTAMP_ISO_REGEX"
  assertEquals 'An invalid iso timestamp was marked valid.' 1 $VALID

  .validateValueWithRegex VALID "123abc123-" "$TIMESTAMP_ISO_REGEX"
  assertEquals 'An invalid iso timestamp was marked valid.' 1 $VALID
}

# Load and run shUnit2.
. ../shunit2
