Terminology:
<br/>-"arr" is short for "array".
<br/>-"elem" is short for "element".
<br/>-an "inner arr" is one of the 1d-arrs inside the "outer arr". the inner arrs hold the elements. the "outer arr" holds the "inner arrs".
<br/>-"arrIndex" is the index of the inner arr that holds the elem that the user is looking for (or whatever other purpose you're acessingor modifying an elem in the arr).
<br/>-"elemIndex" is the index in the inner arr that holds the elem.
<br/>
<br/>
<br/>
&nbsp;&nbsp;&nbsp;&nbsp;When the java ArrayList runs out of space it creates a new array that is twice the size of the current one, and it copies the elems into the new arr. I have 3 different growable arrays, the DoublingArr, the UniformArr, and the DoublingUniformArr that are each more efficient. The key idea of all of them is that there's a 2d-arr, and elems are stored in the inner arrs, so when more space is needed a new 2d arr is created, but the elems aren't copied into it, rather the references of the inner arrs holding the elems are copied. As a result, less copies are done when growing the arr, and they do so while allowing the user to pass indexes into the methods as if the implementation was a 1d-arr.<br/>

&nbsp;&nbsp;&nbsp;&nbsp;When an inner arr is filled a new inner arr that is twice the size of the previous inner arr is created, and the new and filled inner arrs are copied to a new outer arr that can hold them the inner arrs.<br/>
For example (using letters for elems) this is how the arr would grow (I demonstrate what a filled arr looks like when grown):<br/>
[[a]] becomes [[a], [null, null]].<br/>
[[a], [b, c]] becomes [[a], [b, c], [null, null, null, null]].<br/>
[[a], [b, c], [d, e, f, g]] becomes [[a], [b, c], [d, e, f, g], [null, null, null, null, null, null, null, null]].

&nbsp;&nbsp;&nbsp;&nbsp;The UniformArr is the same idea as the DoublingArr, except the inner arrs are a uniform size determined by the user. When space runs out, a new inner arr of the uniform size is created, and added to a new 2d arr along with the filled inner arrs. If the user chooses a very bad inner arr size then the UniformGrowableArr will perform more copies than an ArrayList would in the same situation.<br/>
For example, where the inner arr size is 2 (this number is chosen for simplicity):<br/>
[[a, b]] becomes [[a, b], [null, null]].<br/>
[[a, b], [c, d]] becomes [[a, b], [c, d], [null, null]].<br/>
[[a, b], [c, d], [e, f]] becomes [[a, b], [c, d], [e, f], [null, null]].<br/>

&nbsp;&nbsp;&nbsp;&nbsp;The DoublingUniformArr creates new arrays that are all a uniform size, but when space runs out, twice the amount of inner arrs worth of space is created in the new 2d-arr. This class creates twice the amount of space there was in total, and just like the ArrayList, half of it gets filled with copies (.In the ArrayList half of that space was filed with elems being copied in, and in DoublingUniformArr the half is of inner arrs being copied in).<br/>
For example, where the inner arr size is 1 (this number is chosen for simplicity):<br/>
[[a]] becomes [[a], [null]].<br/>
[[a], [b]] becomes [[a], [b], [null], [null]].<br/>
[[a], [b], [c], [d]] becomes [[a], [b], [c], [d], [null], [null], [null], [null]].
<br/>
<br/>
<br/>
&nbsp;&nbsp;&nbsp;&nbsp;For the UniformArr and DoublingUniformArr, the user chooses how big the arr should initially be. Although the UniformArr could end up doing more copies if a very bad inner arr size is chosen, the user choosing the intial size (the same number the user would choose for an ArrayList's initial size, if the user was using an ArrayList) of the DoublingUniformArr ensures that it will never do more copies than the ArrayList.<br/>
&nbsp;&nbsp;&nbsp;&nbsp;The DoublingUniformArr is a more efficient ArrayList, but the DoublingArr and the UniformArr are growable arr options that than it in certain cases. The UniformArr is best when the user has a better idea of how many elems they would need to store, and therefore wouldn't need the arr to grow twice the size if the user underestimated. Also, it's better when the user knows that data will need to be stored in similar amounts spread out over the life of the program, which would mean a large part of memory is just waiting to be used if the arr was doubled each time.<br/>
&nbsp;&nbsp;&nbsp;&nbsp;The DoublingArr provides the option to the user to not having to deal with initial and inner arr sizes. The 2-arr starts with a single inner arr of size 1.
