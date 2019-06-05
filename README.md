# Extending abstract classes with abstract classes in Java

# The example issue

When I was creating the Java::Geci abstract class `AbstractFieldsGenerator` and `AbstractFilteredFieldsGenerator` then I faced a not too complex design issue. I would like to emphasize that this issue and the design may seem obvious for some of you, but my recent conversation with some junior developer (my son, Mihály specifically, who also reviews my articles because his English is way better than mine) I realized that this topic may still be a value.

Anyway. I had these two classes, fields and filtered fields generator. The second class extends the first one 

```java
abstract class AbstractFilteredFieldsGenerator
                  extends AbstractFieldsGenerator {...
```

adding extra functionality and the same time it should provide the same signature for concrete implementation. What does it mean?

These generators help to generate code for a specific class using reflection. Therefore the input information they work on is a `Class` object. The fields generator class has an abstract method `process()`, which is invoked for every field. It is invoked from an implemented method that loops over the fields and does the invocation separately for each. When a concrete class `extends AbstractFieldsGenerator` and thus implements this abstract method then it will be called. When the same concrete class is changed so that it `extends AbstractFilteredFieldsGenerator` then the concrete method will be invoked only for the filtered method. I wanted a design so that the ONLY change that was needed in the concrete class is to change the name.

# Abstract <del datetime="2019-06-05T06:54:33+00:00">class</del> problem definition

The same problem described in a more abstract way: There are two abstract classes `A` and `F` so that `F extends A` and `F` provides some extra functionality. Both declare the abstract method `m()` that a concrete class should implement. When the concrete class `C` declaration is changed from `C extends A` to `C extends F` then the invocation of the method `m()` should change, but there should be no other change in the class `C`. The method `m()` is invoked from method `p()` defined in class `A`. How to design `F`?

What is the problem with this?

Extending `A` can be done in two significantly different ways:

* `F` overrides `m()` making it concrete implementing the extra functionality in `m()` and calls a new abstract method, say `mx()`
* `F` overrides the method `p()` with a version that provides the extra functionality (filtering in the example above) and calls the still abstract method `m()`

The first approach does not fulfill the requirement that the signature to be implemented by the concrete class `C` should remain the same. The second approach throws the already implemented functionality of `A` to the garbage and reimplements it a bit different way. In practice this is possible, but it definitely is going to be some copy/paste programming. This is problematic, let me not explain why.

# The root of the problem

In engineering when we face a problem like that, it usually means that the problem or the structure is not well described and the solution is somewhere in a totally different area. In other words, there are some assumptions driving our way of thinking that are false. In this case, the problem is that we assume that the abstract classes provide ONE extension "API" to extend them. Note that the API is not only something that you can invoke. In the case of an abstract class, the API is what you implement when you extend the abstract class. Just as libraries may provide different APIs for different ways to be used (Java 9 HTTP client can `send()` and also `sendAsync()`) abstract (and for the matter of fact also non-abstract) classes can also provide different ways to be extended for different purposes.

There is no way to code `F` reaching our design goal without modifying `A`. We need a version of `A` that provides different API to create a concrete implementation and another, not necessarily disjunct/orthogonal one to create a still abstract extension.

The difference between the APIs in this case is that the concrete implementation aims to be at the end of a call-chain while the abstract extension wants to hook on the last but one element of the chain. The implementation of `A` has to provide API to be hooked on the last but one element of the call-chain. This is already the solution.

# Solution

We implement the method `ma()` in the class `F` and we want to call `p()` our `ma()` instead of directly calling `m()`. Modifying `A` we can do that. We define `ma()` in `A` and we call `ma()` from `p()`. The version of `ma()` implemented in `A` should call `m()` without further ado to provide the original "API" for concrete implementations of `A`. The implementation of `ma()` in `F` contains the extra functionality (filtering in the example) and then it calls `m()`. That way any concrete class can extend either `A` or `F` and can implement `m()` with exactly the same signature. We also avoided copy/paste coding with the exception that calling `m()` is a code that is the same in the two versions of `ma()`.

If we want the class `F` extendable with more abstract classes then the `F::ma` implementation should not directly call `m()` but rather a new `mf()` that calls `m()`. That way a new abstract class can override `mf()` giving again new functionality and invoke the abstract `m()`.

# Takeaway

1. Programming abstract classes is complex and sometimes it is difficult to have a clear overview of who is calling who and which implementation. You can overcome this challenge if you realize that it may be a complex matter. Document, visualize, discuss whatever way may help you.
2. When you cannot solve a problem (in the example, how to code `F`) you should challenge the environment (the class `A` we implicitly assumed to be unchangeable by the wording of the question: "How to implement `F`?").
3. Avoid copy/paste programming. (Pasta contains a lot of CH and makes your code fat, the arteries get clogged and finally, the heart of your application will stop beating.)
4. Although not detailed in this article, be aware that the deeper the hierarchy of abstraction is the more difficult it is to have a clear overview of who calls whom (see also point number 1).
