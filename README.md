# Gojimo

The task is to parse a JSON String of qualification data coming through from the CMS. 
Ordering that data in terms of the qualifications and when the user clicks on the qualification, show the subjects that these require for the user/student to study.

I use the Volley Library to get the data and cache it. The data is cached for the time mentioned in the http response header after which the local cache is invalidated.
