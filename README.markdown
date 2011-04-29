LuceneNewsgroups
================
Hi fellows,<br/>
LuceneNewsgroup is a simple command line software that I wrote for an university exercise.
Its sole purpose is to index a bunch of mailing list posts (stored locally) and to perform query based searches on them.
It is developed in java with [Netbeans](http://netbeans.org/) and uses the [Lucene library](http://lucene.apache.org/) (v 3.1) as indexing and searching engine.


Dependencies
------------
You should have java 1.5 or higher installed on your system.


Installation
------------
LuceneNewsgroup does not require you to perform any kind of setup. It's distributed as jar file (you can find it in the **dist** folder). You can copy the jar file wherever you want and just run it from the console by using the command `java -jar path/to/LuceneNewsgroups.jar`. Obviously you should replace the `path/to/` with the real path where you put the jar file.
As it is a command line software, you have to type every single command. Yeah, this sounds really boooring! You can ease things a bit by creating an alias to shorten the ugly `java -jar path/to/LuceneNewsgroups.jar` command. On unix-based systems you can do this with the command `alias lns='java -jar path/to/LuceneNewsgroups.jar'. This way you created an alias called **lns** and now you can just type `lns` instead of `java -jar path/to/LuceneNewsgroups.jar`.<br/>
( Thanks to my great friend [saro](https://github.com/saro) for the hint ).


The workflow
------------
To use LuceneNewsgroups you should have a set of files that represents newsgroup posts. You can find some of them in the **data** folder. These files should be placed on a directory and can be even organized in subdirectories if needed. Each subdirectory is intended as a category.
The default workflow follow these steps:

  1. Create the index for a set of posts placed inside a directory
  2. Perform searches on the indexed directory of posts
  3. Open some of the matched files

So generally you would write commands like these:
<code>
	cd path/to/my/posts/directory
	lns createIndex
	lns search god OR hell
	lns open 21580
</code>

(**N.B.** I'm supposing you've created an alias called **lns** as described above)


![Usage demo](http://img705.imageshack.us/img705/412/lns.png "demo")


Commands
--------
Here's the complete list of available commands

<table>
	<tr>
		<td><code>version</code></td><td>Shows informations about the version of the software</td>
	</tr>
	<tr>
		<td><code>createIndex [&lt;folder&gt;]</code></td><td>Creates an index for all the files contained in the <strong>folder</strong> directory. If you don't specify a folder, it will use the current working directory.</td>
	</tr>
	<tr>
		<td><code>search &lt;query&gt;</code></td><td>Search within the index of the current dir for documents that match the given <strong>query</strong>.</td>
	</tr>
	<tr>
 		<td><code>open &lt;id&gt;</code></td><td>display the content of the document indexed with the given <strong>id</strong>.</td>
	</tr>
</table>


That's all
----------
As the title says that's all for the moment! If you have any question feel free to ask: lmammino [at] oryzone [dot] com.<br/>
In the meanwhile please cross your fingers hoping I'll pass the exam :)