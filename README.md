### Summary

The library provides the tools for result-object creation.

### Seed
__Seed__ is a contract which must getting _code_ as String and _args_ as array of Object.
__Seed__ is part of the __Result__ object.
It needs to be passed information about the bad situation if necessary.

Implemented __DefaultSeed__(record), which is an implementation of __Seed__.

### The builder of seed
__SeedBuilder__ is a tool for a __Seed__ implementations building.
Implemented __DefaultSeedBuilder__, which is an implementation of __SeedBuilder__
for creation of __DefaultSeed__ instances.

__DefaultSeedBuilder__ providers the ability to build on an instance.
```java
Seed<Object> seed = new DefaultSeedBuilder<>()
        .code("code-0")
        .addFirstArgs(1, "hello")
        .addLastArgs('x', 123.0f)
        .build();
```
In this case the instance of DefaultSeed will look like:
```text
{
  "code": "code-0",
  "args": [
    "hello",
    1,
    'x',
    123.0f
  ]
}
```

The __DefaultSeedBuilder__ class providers the ability to create an instance through
static method __seed__ where first argument is th code and the following arguments
are sequence of arguments.
```java
Seed seed = DefaultSeedBuilder.<Object>seed("code-0", "hello", "world", 111);
```
In this case the instance of DefaultSeed will look like:
```text
{
  "code": "code-0",
  "args": [
    "hello",
    "world"
    111
  ]
}
```

The __DefaultSeedBuilder__ class provides the ability to create instance through
static method __compute__ where argument is the sequence of suppliers.
Each supplier returns either __DefaultSeed__ instance or null.

In case of every supplier is returning null then __compute__ null
else __compute__ will return first not null result of suppliers.

The __DefaultSeedBuilder__ class provides the ability to create instance through
static method __computeFromCodes__ where argument is the sequence of suppliers.
Each supplier returns either the code as String instance or null.

In case of every supplier is returning null then __computeFromCodes__ null
else __computeFromCodes__ will return the instance of __DefaultSeed__ with 
code from supplier.

### Result
__Result__ is a contract which _ok_ as boolean, _value_, _seed_ as the instance of Seed
and _causes_ the list of __Result__'s instances.
It needs to be passed information about an execution of something.
- _ok_ - success of execution
- _value_ - result of execution (in case of a failure it is null)
- _seed_ - seed of bad situation (in case of a success it is null)
- _causes_ - causes of bad situation

Implemented __DefaultResult__, which is an implementation of __Result__.

### The builder of result
__ResultBuilder__ is a tool for __Result__ implementation building.
Implemented __DefaultResultBuilder__, which is an implementation of __ResultBuilder__
for creation of __DefaultResult__ instances.

__DefaultResultBuilder__ providers the ability to build on an instance.
```java
Result<String> result = new DefaultResultBuilder<>()
        .success(false)
        .value(null)
        .seed(seed)
        .build();
```
In this case the instance of DefaultResult will look like:
```text
{
  "success": false,
  "value": null,
  "seed": {
    "code": "code-0",
    "args": [
      "hello",
      1,
      'x',
      123.0f
    ]
  }
}
```

The __DefaultResultBuilder__ class provides the ability to create of success result
through method __ok__, which has two implementations. First implementation does not
have arguments. Second implementation has argument _value_.
```java
Result<String> result = DefaultResultBuilder.<String>ok();
```
In this case result will look like:
```text
{
  "success": true,
  "value": null,
  "seed": null
}
```
```java
Result<String> result = DefaultResultBuilder.<String>ok("hello");
```
In this case result will look like:
```text
{
  "success": true,
  "value": "hello",
  "seed": null
}
```
The __DefaultResultBuilder__ class provides the ability to create of failure result
through method __fail__, which has two implementations.
First implementation has two arguments: _code_< String > & _args_< Object... >.
Second implementation once argument: _seed_< Seed >.
```java
Result<String> result = DefaultResultBuilder.<String>fail("code-0", 1, 'c');
```
In this case result will look like:
```text
{
  "success": false,
  "value": null,
  "seed": {
    "code": "code-0",
    "args": [1, 'c']
  }
}
```
```java
Result<String> result = DefaultResultBuilder.<String>fail(seed);
```
In this case result will look like:
```text
{
  "success": false,
  "value": null,
  "seed": ...
}
```
The __DefaultResultBuilder__ class provides the ability to compute result  through 
method __compute__, which has three implementations.

First implementation has the following arguments:
- value as T
- seed as Seed
```java
Result<String> result0 = DefaultResultBuilder.<String>compute("value", seed);
Result<String> result1 = DefaultResultBuilder.<String>compute(null, seed);
```
In this case result0 will look like:
```text
{
  "success": true,
  "value": "value",
  "seed": null
}
```
result1 will look like:
```text
{
  "success": false,
  "value": null,
  "seed": {...}
}
```
Second implementation has the following arguments:
- value as T
- seed as String
```java
Result<String> result0 = DefaultResultBuilder.<String>compute("value", "code");
Result<String> result1 = DefaultResultBuilder.<String>compute(null, "code");
```
In this case result0 will look like:
```text
{
  "success": true,
  "value": "value",
  "seed": null
}
```
result1 will look like:
```text
{
  "success": false,
  "value": null,
  "seed": {
    "code": "code",
    args: []
  }
}
```
Third implementation has the following arguments:
- _valueSupplier_ as supplier of __T__
- _seedSuppliers_ as array of suppliers which are returning __Seed__

For this implementation is true, if any seed supplier returned null then
result will computed on base value supplier, else on first not null result
of seed supplier.

The __DefaultResultBuilder__ class provides the ability to compute result through 
method __computeFromCodes__, which has the following arguments:

First implementation has two arguments: _code_< String > & _args_< Object... >.
Second implementation once argument: _seed_< Seed >.
- _valueSupplier_ as supplier of __T__
- _codeSuppliers_ as array of suppliers which are returning _code_ for __Seed__

If any seed supplier returned null then result will computed on base value supplier,
else on first not null result of code supplier.

### CodeRepository

The __CodeRepository__ and its implementation __DefaultCodeRepository__ are things
for binding of key objects< Object > and code< String >

Any implementation of __CodeRepository__ must implement two methods
```java
boolean update(Object key, String code);
```
Method _update_ binds key and code.
```java
String get(Object key);
```
Method _get_ allows to get code by key.

### SeedsComparator

The __SeedsComparator__ is the tool for checking of __Seed__'s implementation.
It provides the ability to check of seed through setting of modes and the 
calling of method _compare_.

```java
public SeedsComparator isNull();
```
Method _isNull_ erases all modes, which set before, and sets mode __IS_NULL__

```java
public SeedsComparator code(final String code);
```
Method __code__ erases mode __IS_NULL__, sets mode __CODE__ and value _code_
for comparison in the future.

```java
public SeedsComparator args(final Object... args);
```
Method __args__ erases mode __IS_NULL__, sets mode __ARGS__ and value _args_
for comparison in the future.

```java
public SeedsComparator argsAreEmpty();
```
Method __args__ erases mode __IS_NULL__ & __ARGS__, sets mode __ARGS_ARE_EMPTY__.

```java
public boolean compare();
```
Method _compare_ make comparison for every set mode and returns _true_
in case of success, else _false_.

#### Example of SeedsComparator usage
We hase seed which contains the following:
```text
{
  "code": "code-0",
  "args": []
}
```
```java
boolean result = new SeedsComparator(seed)
        .isNull()
        .code("code-0")
        .args(1, "2")
        .argsAreEmpty()
        .compare();
```
In this case result will be _true_.

## Seeds

The __Seeds__ is the tool for working with implementations of __Seed__.

```java
public static boolean equal(final Seed s0, final Seed s1);
```
Method _equal_ provides the ability to calculate equality of two seeds.

```java
public static SeedsComparator comparator(final Seed target);
```
Method _comparator_ returns new instance of __SeedsComparator__.

## ResultsComparator

The __ResultsComparator__ is the tool for checking of __Result__'s implementation.
It provides the ability to check of result through setting of modes and the
calling of method _compare_.

```java
public ResultsComparator isNull();
```
Method _isNull_ erases all modes, which set before, and sets mode __IS_NULL__

```java
public ResultsComparator ok(final boolean ok);
```
Method _ok_ erases mode __IS_NULL__, sets model __OK__ and value _ok_ for 
comparison in the future.

```java
public ResultsComparator isSuccess();
```
It is wrapper for method _ok_ with argument _true_.

```java
public ResultsComparator isFail();
```
It is wrapper for method _ok_ with argument _false_.


```java
public ResultsComparator value(final Object value);
```
Method __value__ erases mode __IS_NULL__, sets mode __VALUE__ and __value__ for
comparison in the future.

```java
public ResultsComparator causes(final List<Result<?>> causes);
```
Method __causes__ erases mode __IS_NULL__, sets mode __CAUSES__ and __causes__ for
comparison in the future.

```java
public SeedsComparator seedsComparator();
```
Method __seedsComparator__ erases mode __IS_NULL__, sets mode __SEED__ and 
returns seed comparator.

```java
public boolean compare();
```
Method _compare_ make comparison for every set mode and returns _true_
in case of success, else _false_.

#### Example of ResultsComparator usage
We hase seed which contains the following:
```text
{
  "ok": true,
  "value": "hello",
  "seed": null
}
```
```java
boolean result = new ResultsComparator(seed)
        .success()
        .value("hello")
        .seedsComparator().isNull().back()
        .compare();
```
In this case result will be _true_.

## Results

The __Results__ is the tool for working with implementations of __Result__.

```java
public static boolean equal(final Result<?> r0, final Result<?> r1);
```
Method _equal_ provides the ability to calculate equality of two results.

```java
public static ResultsComparator comparator(final Result<?> target);
```
Method _comparator_ returns new instance of __ResultsComparator__.

```java
public static boolean equalCauses(List<Result<?>> causes0, List<Result<?>> causes1);
```
Method _equalCauses_ provides the ability to calculate equality of two list of
causes.
