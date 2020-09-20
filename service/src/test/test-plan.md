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
| float        | f          | number  | is number, is real             | This is a signed floating point                  |
| int          | i          | number  | is number, is integer          | This is a signed integer                         |
| between      | b          | number  | is number, is real, < 10, >-10 | Floating point number between -10 and 10         |
| switch       | s          | number  | switch                         | This is a boolean true or false                  |
| capital      | c          | other   | regex [A-Z].*                  | Must start with a capital letter                 |



