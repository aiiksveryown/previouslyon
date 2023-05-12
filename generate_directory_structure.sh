#!/bin/bash

# Name of the output file
output_file="directory_structure.txt"

# Remove the output file if it exists
if [ -f "$output_file" ]; then
  rm "$output_file"
fi

# Find and print the relative path of all files, excluding directories
find ./app/src/main/java -type f -print0 | while IFS= read -r -d '' path; do
  # Remove the leading "./" from the path
  relative_path="${path:2}"
  echo "$relative_path" >> "$output_file"
done

echo "File paths saved to $output_file"
