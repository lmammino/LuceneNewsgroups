package lucenenewsgroups.commands;

import java.io.File;
import java.util.Queue;
import lucenenewsgroups.AppInfo;
import lucenenewsgroups.Command;
import lucenenewsgroups.FileUtils;
import lucenenewsgroups.Post;
import lucenenewsgroups.crawler.DiscoveredPost;
import lucenenewsgroups.crawler.PostCrawler;
import lucenenewsgroups.parser.PostParser;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * Creates the index
 * @author Luciano Mammino <lmammino[at]oryzone[dot]com>
 */
public class CreateIndexCommand extends Command {

    /**
     * The path of the directory to index
     */
    protected String directory;


    /**
     * Constructor. Creates a new instance of the command
     * @param directory the path of the directory to index
     */
    public CreateIndexCommand(String directory)
    {
        this.directory = directory;
    }


    @Override
    public void execute()
    {
        
        try
        {
            File indexFile = new File(this.directory + "/" + AppInfo.indexFile);
            System.out.println("\nCreating index for '" + indexFile.getParentFile().getCanonicalPath() + "'");
            if (indexFile.exists())
            {
                System.out.println("This folder has been already indexed. It will be updated.");
            }

            PostCrawler crawler = new PostCrawler(this.directory);
            Queue<String> posts = crawler.discoverUsingQueue();
            PostParser parser = new PostParser();

            FSDirectory dir = FSDirectory.open(indexFile);
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_31, analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            IndexWriter writer = new IndexWriter(dir, config);

            int size = posts.size();
            int i = 1;
            String postFile = null;
            DiscoveredPost discoveredPost = null;
            Post post = null;
            Document document = null;

            System.out.println();

            while( (postFile = posts.poll() ) != null )
            {
                discoveredPost = new DiscoveredPost(new File(crawler.getDirectory() + postFile));
                Command.showProgress(i, size);
                parser.setCategory(discoveredPost.getCategory());
                parser.setFile(discoveredPost.getFile());
                post = parser.parse();
                document = post.toDocument();
                writer.addDocument(document);

                discoveredPost = null;
                post = null;
                document = null;
                System.gc();

                i++;
            }
            
            writer.optimize();
            writer.close();

            System.out.println("\n\n");
        }
        catch(Exception e)
        {
            System.err.println("CANNOT CREATE INDEX!\n");
            System.err.println(e.getMessage());
        }
    }

}
