package lucenenewsgroups.crawler;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Pattern;

/**
 * Class used to discover all the post contained in a given directory (or in its
 * subtree)
 * @author Luciano Mammino <lmammino[at]oryzone[dot]com>
 * @version 1.0
 */
public class PostCrawler
{

    /**
     * The directory path to scan
     */
    protected String directory;


    /**
     * An array of patters used as ignore patterns
     */
    protected String[] ignorePatterns;


    /**
     * The instance of the PostCrawlerFilter used to filter the filenames
     */
    protected PostCrawlerFilter filter;


    /**
     * Constructor. Creates a new post crawler
     * @param directory the directory where to scan
     */
    public PostCrawler(String directory)
    {
        this(directory, null);
    }


    /**
     * Constructor. Creates a new post crawler using a custom array of ignore
     * patterns
     * @param directory the directory to scan
     * @param ignorePatterns an array of ignore patterns
     */
    public PostCrawler(String directory, String[] ignorePatterns)
    {
        if(ignorePatterns == null)
            ignorePatterns = PostCrawler.getDefaultIgnorePatterns();

        this.directory = directory;
        this.ignorePatterns = ignorePatterns;
        this.filter = new PostCrawlerFilter(ignorePatterns);
    }

    /**
     * @return the directory
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * @param directory the directory to set
     */
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    /**
     * @return the ignorePatterns
     */
    public String[] getIgnorePatterns() {
        return ignorePatterns;
    }

    /**
     * @param ignorePatterns the ignorePatterns to set
     */
    public void setIgnorePatterns(String[] ignorePatterns) {
        this.ignorePatterns = ignorePatterns;
        this.filter.setIgnorePatterns(ignorePatterns);
    }


    /**
     * Gets the default list of ignore patters (ignores .git, .svn, .DS_Store
     * and many others)
     * @return an array of ignore patterns
     */
    public static String[] getDefaultIgnorePatterns()
    {
        return new String[]
        {
          ".svn", ".SVN", "thumbs.db", ".DS_Store", ".git", ".lucene_newsgroups_index"
        };
    }


    /**
     * Execute the scan and discover all the post whithin the given folder
     * @return A list of the discovered post
     */
    public List<DiscoveredPost> discover()
    {
        List<DiscoveredPost> discovered = Collections.synchronizedList(new ArrayList<DiscoveredPost>());

        this.addPostToList(new File(this.getDirectory()), discovered);

        return discovered;
    }


    /**
     * Function used to recursively scan the directory to discover posts
     * @param directory the directory to scan
     * @param list
     */
    protected void addPostToList(File directory, List<DiscoveredPost> list)
    {
        final File[] children = directory.listFiles(this.filter);
        if (children != null)
        {
            for (File child : children)
            {
                if (!child.isDirectory())
                    list.add(new DiscoveredPost(child));
                else
                    this.addPostToList(child, list);
            }
        }
    }


    /**
     * Discover all the files and stores its names on a queue
     * @return a queue of strings
     */
    public Queue<String> discoverUsingQueue()
    {
        Queue<String> discovered = new LinkedList<String>();

        this.addPostToQueue(new File(this.getDirectory()), discovered, "");

        return discovered;
    }


    /**
     * Function used to recursively scan the directory to discover posts
     * @param directory
     * @param queue
     * @param relativePath
     */
    protected void addPostToQueue(File directory, Queue<String> queue, String relativePath)
    {
        final File[] children = directory.listFiles(this.filter);

        if (children != null)
        {
            for (File child : children)
            {
                if (!child.isDirectory())
                    queue.add(relativePath + "/" + child.getName());
                else
                {
                    this.addPostToQueue(child, queue, relativePath + "/" + child.getName());
                }
            }
        }
    }


    /**
     * Class used to filter the filenames using hte ignore patterns
     * @author Luciano Mammino <lmammino[at]oryzone[dot]com>
     * @version 1.0
     */
    class PostCrawlerFilter implements FilenameFilter
    {
        /**
         * The list of the ignore patterns
         */
        protected String[] ignorePatterns;

        /**
         * Creates a new filter using a given set of ignore patterns
         * @param ignorePatterns
         */
        public PostCrawlerFilter(String[] ignorePatterns)
        {
            this.ignorePatterns = ignorePatterns;
        }

        /**
         * The accept function as described by the FilenameFilter interface
         * @param dir the current directory
         * @param name the current file name
         * @return true if the file is accepted, false if the file should be
         * skipped
         */
        public boolean accept(File dir, String name)
        {
            Pattern p = null;

            for( String pattern : this.getIgnorePatterns() )
            {

                p = Pattern.compile(pattern);
                if( p.matcher(name).matches() )
                    return false;
            }

            return true;
        }

        /**
         * @return the ignorePatterns
         */
        public String[] getIgnorePatterns() {
            return ignorePatterns;
        }

        /**
         * @param ignorePatterns the ignorePatterns to set
         */
        public void setIgnorePatterns(String[] ignorePatterns) {
            this.ignorePatterns = ignorePatterns;
        }
    }

}
