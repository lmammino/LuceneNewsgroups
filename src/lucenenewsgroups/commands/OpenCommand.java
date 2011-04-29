package lucenenewsgroups.commands;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import lucenenewsgroups.AppInfo;
import lucenenewsgroups.Command;
import lucenenewsgroups.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * Command that opens an indexed document with a given id
 * @author Luciano Mammino <lmammino[at]oryzone[dot]com>
 * @version 1.0
 */
public class OpenCommand extends Command
{

    /**
     * The id of the file to open
     */
    protected String id;

    /**
     * Constructor. Creates a new Open command with a given document id
     * @param id the id of the document to open
     */
    public OpenCommand(String id)
    {
        this.id = id;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void execute()
    {
        File indexFile = new File(AppInfo.indexFile);
        if(!indexFile.exists())
        {
            System.out.println("The directory '" + indexFile.getAbsolutePath() + "' has not been indexed yet");
        }
        else
        {
            try {
                FSDirectory index = FSDirectory.open(indexFile);

                Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);
                Query q = new QueryParser(Version.LUCENE_31, "id", analyzer).parse(this.id);

                int hitsPerPage = 1;
                IndexSearcher searcher = new IndexSearcher(index, true);
                TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
                searcher.search(q, collector);
                ScoreDoc[] hits = collector.topDocs().scoreDocs;

                if( hits.length == 0)
                    System.out.println("Document '"+this.id+"' not found!");
                else
                {
                    Document d = searcher.doc(hits[0].doc);
                    System.out.println(FileUtils.readFileContent(d.get("file")) );
                }

            } catch (Exception ex) {
                Logger.getLogger(SearchCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        

    }



}
