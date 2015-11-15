#Instruction how to execute Low-tides

# Introduction #

Low-tides is an automation of YSlow recommendation #1 (Reduce Http Request) and #10 (Minimize JS/CSS). The tool accepts html path and extracts js/css includes for merging and minification.

Some of the reasons why we developed this tool are as follows:

# Improve page loads by reducing http request and minimizing js size.
# Perform step #1 with minimal action for the developer and yet maintain proper version.

Low-tides processes html files and looks for js and css includes. It then combines multiple js in sequence into 1 js file and perform minification. Based on our own test, load time has improved by at least 30% after using low-tides.

This tool is an extension of YUI Compressor.

# Details #

To use Low-tides:

Usage: java -jar low-tide.jar 

&lt;options&gt;

 <input file>

Global Options

> -h, --help                Displays this information

> --charset 

&lt;charset&gt;

       Read the input file using 

&lt;charset&gt;



> --line-break 

&lt;column&gt;

     Insert a line break after the specified column number

> -v, --verbose             Display informational messages and warnings

> -o 

&lt;file&gt;

                 Place the output into 

&lt;file&gt;

. Defaults to stdout.

JavaScript Options

> --nomunge                 Minify only, do not obfuscate

> --preserve-semi           Preserve all semicolons

> --disable-optimizations   Disable all micro optimizations