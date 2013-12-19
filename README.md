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

<h3>Implementations</h3>
The only use of this API so far is a simple web app I created that displays random images:

http://bl-web.herokuapp.com/api/images/random

I will be adding some tagging capabilities soon.

<h3>API</h3>

Currently there are only two endpoints supported in this service. One will allow you to get the meta-data for an image
with the Flickr ID and the other will return a random image meta data.

<h2>GET http://bl-rest.herokuapp.com/images/:FLICKR_ID</h2>

Example response:

```json
{
  "book_identifier" : 744707,
  "title" : "A Collection of Poems: viz. The Temple of Death: by the Marquis of Normanby. An Epistle to the Earl of Dorset: by Charles Montague, Lord Halifax. The Duel of the Stags: by Sir Robert Howard. With several original poems, never before printed, by the E. of Roscommon, the E. of Rochester, the E. of Orrery, Sir Charles Sedley, Sir George Etherege, Mr. Granville, Mr. Stepney, Mr. Dryden, &c. [A revised and enlarged reprint of “A Collection of Poems by Several Hands” published in 1693.]",
  "first_author" : "ETHEREGE, George Sir",
  "pubplace" : "London",
  "publisher" : "Daniel Brown; Benjamin Tooke",
  "date" : "1701",
  "volume" : 0,
  "page" : 272,
  "image_idx" : 1,
  "ARK_id_of_book" : "",
  "BL_DLS_ID" : "",
  "flickr" : {
    "flickr_id" : 10997917393,
    "flickr_url" : "http://www.flickr.com/photos/britishlibrary/10997917393",
    "flickr_small_source" : "http://farm8.staticflickr.com/7445/10997917393_dd83c8ace9_m.jpg",
    "flickr_small_height" : 240,
    "flickr_small_width" : 169,
    "flickr_medium_source" : "http://farm8.staticflickr.com/7445/10997917393_dd83c8ace9.jpg",
    "flickr_medium_height" : 342,
    "flickr_medium_width" : 241,
    "flickr_large_source" : "",
    "flickr_large_height" : -1,
    "flickr_large_width" : -1,
    "flickr_original_source" : "http://farm8.staticflickr.com/7445/10997917393_06519e7338_o.jpg",
    "flickr_original_height" : 342,
    "flickr_original_width" : 241
  }
}
```

<h2>GET http://bl-rest.herokuapp.com/images/random</h2>

```json
{
  "book_identifier" : 744707,
  "title" : "A Collection of Poems: viz. The Temple of Death: by the Marquis of Normanby. An Epistle to the Earl of Dorset: by Charles Montague, Lord Halifax. The Duel of the Stags: by Sir Robert Howard. With several original poems, never before printed, by the E. of Roscommon, the E. of Rochester, the E. of Orrery, Sir Charles Sedley, Sir George Etherege, Mr. Granville, Mr. Stepney, Mr. Dryden, &c. [A revised and enlarged reprint of “A Collection of Poems by Several Hands” published in 1693.]",
  "first_author" : "ETHEREGE, George Sir",
  "pubplace" : "London",
  "publisher" : "Daniel Brown; Benjamin Tooke",
  "date" : "1701",
  "volume" : 0,
  "page" : 272,
  "image_idx" : 1,
  "ARK_id_of_book" : "",
  "BL_DLS_ID" : "",
  "flickr" : {
    "flickr_id" : 10997917393,
    "flickr_url" : "http://www.flickr.com/photos/britishlibrary/10997917393",
    "flickr_small_source" : "http://farm8.staticflickr.com/7445/10997917393_dd83c8ace9_m.jpg",
    "flickr_small_height" : 240,
    "flickr_small_width" : 169,
    "flickr_medium_source" : "http://farm8.staticflickr.com/7445/10997917393_dd83c8ace9.jpg",
    "flickr_medium_height" : 342,
    "flickr_medium_width" : 241,
    "flickr_large_source" : "",
    "flickr_large_height" : -1,
    "flickr_large_width" : -1,
    "flickr_original_source" : "http://farm8.staticflickr.com/7445/10997917393_06519e7338_o.jpg",
    "flickr_original_height" : 342,
    "flickr_original_width" : 241
  }
}
```

More APIs will be added in the future this is just a starting place.


<h3>Script</h3>

Also included is a python script to parse the British Library TSV files and load them into a MongoDB database. Feel free to use this in your own expirements. Run the script with '--help' to get the available arguments.


Thanks to the British Library for making this data available it's a very exciting data set to be able to play with!