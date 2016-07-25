## Testing environment

Programm was tested on HDP 2.4 sandbox.

## How to deploy

1. Download dataset http://goo.gl/lwgoxw and put files bid.20130606.txt.bz2 - bid.20130612.txt.bz2 to hdfs.
2. Correct src\main\resources\hadoop.properties file according to your claster:
```
host=<hdfs_host>
port=<hdfs_port>
dataset.folder.path=<hdfs_path_to_dataset_directory>
output.folder.path=<hdfs_path_to_output_directory>
```
3. Run project. Be ready application will process dataset for a long time (more than 20 minutes)

## Task result

As a result you should resieve data same as in bid_result.txt