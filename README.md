Terminology:
-"arr" is short for "array".
-"elem" is short for "element".
-an "inner arr" is one of the 1d-arrs inside the "outer arr". the inner arrs
hold the elements. the "outer arr" holds the "inner arrs".
-"arrIndex" is the index of the inner arr that holds the elem that the user is looking for
(or whatever other purpose you're acessingor modifying an elem in the arr).
-"elemIndex" is the index in the inner arr that holds the elem.
<br/><br/>
When the java ArrayList runs out of space it creates a new array that is twice the size of the
current one, and it copies the elems into the new arr. I have 2 different growable arrays,
the DoublingGrowableArr and the UniformGrowableArr, that are each more efficient.

First I'll explain my DoublingGrowableArr. It is a 2d-array that starts with one inner arr,
and when the inner arr is filled an empty inner arr that's twice the size of the first one
is created, and the filled and empty inner arrs are copied into a new
outer arr that has space for 2 inner arrs. The key difference between the ArrayList and the
DoublingGrowableArr is the ArrayList copies elems when more space is needed, and my growable arr
copies arr references, which significantly decreases the amount of
copies made.
The methods allow the user to pass in an index as if it was a 1d-arr, and the index
is converted into indexes that can be used for the get the right elem from the right
inner arr without additional complexity.
 
The UniformGrowableArr is the same idea as the DoublingGrowableArr, except the inner arrs are a
uniform size. The user determines the size the inner arrs should be, and they're all that size.
If the user chooses a very bad inner arr size then the UniformGrowableArr will perform more copies
than an ArrayList would in the same situation.
