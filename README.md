Installation
============
This assumes that ant and the JDK are available on the system.

Go to the project directory and run

> $ ant

This will run the matcher on the default dataset and store the results in results.txt.

To customize the data set or results file, include ant arguments as follows:

> $ ant -Dproducts=path/to/products.txt -Dlistings=path/to/listings.txt -Dresults=path/to/results.txt
