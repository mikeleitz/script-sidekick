# Test 1

## Script Name			

### test-script

| Input Name   | Short Name | Type    | Validations                    | Help Text                                        |
|--------------|------------|---------|--------------------------------|--------------------------------------------------|
| verbose      | v          | default |                                |                                                  |
| quiet        | q          | default |                                |                                                  |
| alphanumeric | a          | string  | required - alphanumeric        | This field is required and an alphanumeric value |
| email        | e          | string  | email                          | This is a email address and isn't required       |
| url          | u          | string  | url                            | This is a url value                              |
| int          | i          | number  | is number, is integer          | This is a unsigned integer                       |
| between      | b          | number  | is number, is int , < 10, >-10 | Integer int number between -10 and 10            |
| switch       | s          | number  | switch                         | This is a boolean true or false                  |

#### verbose

```bash
# Shows all variables printed
test-script.sh -v
```

#### quiet

#### alphanumeric

```bash
# No error message
test-script.sh -a A1Bb23

# No error message
test-script.sh --alphanumeric A1Bb23

# Error message
test-script.sh --alphanumeric -123

# Error message
test-script.sh --alphanumeric 12~sd
```

#### email

```bash
# No error message
test-script.sh -e example@gmail.com

# No error message
test-script.sh -e example+extra@gmail.com

# Error message
test-script.sh -e example@

# Error message
test-script.sh -e example@asd
```

#### url

```bash
# No error message
test-script.sh -u http://google.com

# No error message
test-script.sh -u https://google.com

# No error message
test-script.sh -u https://www.google.com

# Error message
test-script.sh -u //www.google.com
```

#### int

```bash
# No error message
test-script.sh -i 123

# Error message -- must be unsigned
test-script.sh -i -123

# Error message
test-script.sh -i -123asd

# Error message
test-script.sh -i ~
```

#### between

```bash
# No error message
test-script.sh -b 5

# No error message
test-script.sh -b -10

# No error message
test-script.sh -b 10

# Error message
test-script.sh -b 11

```

#### switch

```bash
# No error message
test-script.sh -s
```

#### capital

```bash
# No error message
test-script.sh -c Aads

# No error message
test-script.sh -c Q123

# No error message
test-script.sh -c Q

# Error message
test-script.sh -c q123

# Error message
test-script.sh -c 

```

## Phase 2


| Input Name   | Short Name | Type    | Validations                    | Help Text                                        |
|--------------|------------|---------|--------------------------------|--------------------------------------------------|
| verbose      | v          | default |                                |                                                  |
| quiet        | q          | default |                                |                                                  |
| alphanumeric | a          | string  | required - alphanumeric        | This field is required and an alphanumeric value |
| email        | e          | string  | email                          | This is a email address and isn't required       |
| url          | u          | string  | url                            | This is a url value                              |
| float        | f          | number  | is number, is real             | This is a signed floating point                  |
| int          | i          | number  | is number, is integer          | This is a signed integer                         |
| between      | b          | number  | is number, is real, < 10, >-10 | Floating point number between -10 and 10         |
| switch       | s          | number  | switch                         | This is a boolean true or false                  |
| capital      | c          | other   | regex [A-Z].*                  | Must start with a capital letter                 |
