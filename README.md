bl-rest
=======

REST service implemented with Scala/Spray/MongoDB to act as an interface to the information released by the British Library at https://github.com/BL-Labs/imagedirectory

This code is deployed and running on Heroku with a publicly available interface so feel free to use it to support your own apps.
Please let me know if you are intergrating and I will try to provide some support in the case of problems.

To run the code locally you will need SBT.

<h3>Run</h3>
At the sbt console:
~ re-start

<h3>Test</h3>
At the sbt console:
test

<h3>API</h3>

Currently there are only two endpoints supported in this service. One will allow you to get the meta-data for an image
with the Flickr ID and the other will return a random image meta data.

<h2>GET http://bl-rest.herokuapp.com/images/:FLICKR_ID></h2>

Example response:

```json
{
  "flickr_id" : 11289179413,
  "flickr_url" : "http://www.flickr.com/photos/britishlibrary/11289179413",
  "book_identifier" : 1886779,
  "title" : "Battles and Leaders of the Civil War, being for the most part contributions by Union and Confederate officers, based upon “the Century War Series.” Edited by R. U. J. and C. C. B., etc. [Illustrated.]",
  "first_author" : "JOHNSON, Robert Underwood and BUEL (Clarence Clough)",
  "pubplace" : "4 vol. The Century Co.: New York, 1887, 8º",
  "publisher" : "",
  "date" : "1887",
  "volume" : 1,
  "page" : 474,
  "image_idx" : 1,
  "ARK_id_of_book" : "",
  "BL_DLS_ID" : ""
}
```

<h2>GET http://bl-rest.herokuapp.com/images/random</h2>

```json
{
  "flickr_id" : 11289179413,
  "flickr_url" : "http://www.flickr.com/photos/britishlibrary/11289179413",
  "book_identifier" : 1886779,
  "title" : "Battles and Leaders of the Civil War, being for the most part contributions by Union and Confederate officers, based upon “the Century War Series.” Edited by R. U. J. and C. C. B., etc. [Illustrated.]",
  "first_author" : "JOHNSON, Robert Underwood and BUEL (Clarence Clough)",
  "pubplace" : "4 vol. The Century Co.: New York, 1887, 8º",
  "publisher" : "",
  "date" : "1887",
  "volume" : 1,
  "page" : 474,
  "image_idx" : 1,
  "ARK_id_of_book" : "",
  "BL_DLS_ID" : ""
}
```

More APIs will be added in the future this is just a starting place.


<h3>Script</h3>

Also included is a python script to parse the British Library TSV files and load them into a MongoDB database. Feel free to use this in your own expirements. Run the script with '--help' to get the available arguments.


Thanks to the British Library for making this data available it's a very exciting data set to be able to play with!