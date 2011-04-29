package lucenenewsgroups;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

/**
 * Class used to create a structured object that represents a newsgroup post
 * @author Luciano Mammino <lmammino[at]oryzone[dot]com>
 * @version 1.0
 */
public class Post
{

    protected String category;
    protected File file;
    protected int id;
    protected List<String> xref;
    protected List<String> newsgroups;
    protected List<String> paths;
    protected String from;
    protected String subject;
    protected String sender;
    protected String messageId;
    protected String date;
    protected String distribution;
    protected List<String> references;
    protected String organization;
    protected String text;


    /**
     * Constructor. Creates a new post that references a given file and has a
     * given id
     * @param file the file referenced by the post
     * @param id the id of the post
     */
    public Post(File file, int id)
    {
        this(file, id, null);
    }


    /**
     * Constructor. Creates a new post with a reference to a given file, a given
     * id and a given category
     * @param file the referenced file
     * @param id the id of the post
     * @param category the name of the category
     */
    public Post(File file, int id, String category)
    {
        this.category = category;
        this.id = id;
        this.file = file;
        this.xref = Collections.synchronizedList(new ArrayList<String>());
        this.newsgroups = Collections.synchronizedList(new ArrayList<String>());
        this.paths = Collections.synchronizedList(new ArrayList<String>());
        this.references = Collections.synchronizedList(new ArrayList<String>());
    }


    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the xref
     */
    public List<String> getXref() {
        return xref;
    }

    /**
     * @param xref the xref to set
     */
    public void setXref(List<String> xref) {
        this.xref = xref;
    }

    /**
     * @return the newsgroups
     */
    public List<String> getNewsgroups() {
        return newsgroups;
    }

    /**
     * @param newsgroups the newsgroups to set
     */
    public void setNewsgroups(List<String> newsgroups) {
        this.newsgroups = newsgroups;
    }

    /**
     * @return the paths
     */
    public List<String> getPaths() {
        return paths;
    }

    /**
     * @param paths the paths to set
     */
    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the distribution
     */
    public String getDistribution() {
        return distribution;
    }

    /**
     * @param distribution the distribution to set
     */
    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    /**
     * @return the references
     */
    public List<String> getReferences() {
        return references;
    }

    /**
     * @param references the references to set
     */
    public void setReferences(List<String> references) {
        this.references = references;
    }

    /**
     * @return the organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * @param organization the organization to set
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }


    /**
     * Converts the post to a lucene Document
     * @return a Lucene Document ready to be stored in the index
     */
    public Document toDocument()
    {
        Document doc = new Document();

        //file
        doc.add(new Field("file", this.getFile().getAbsolutePath(), Field.Store.YES, Field.Index.NO));

        //category
        doc.add(new Field("category", this.getCategory(), Field.Store.YES, Field.Index.ANALYZED));
        
        //id
        doc.add(new Field("id", Integer.toString(this.getId()), Field.Store.YES, Field.Index.NOT_ANALYZED ));

        //xrefs
        for(String currxref : this.xref)
            doc.add(new Field("xref", currxref, Field.Store.YES, Field.Index.NO));

        //newsgroups
        for(String newsgroup : this.newsgroups)
            doc.add(new Field("newsgroup", newsgroup, Field.Store.YES, Field.Index.NO));

        //paths
        for(String path : this.paths)
            doc.add(new Field("path", path, Field.Store.YES, Field.Index.NO));

        //from
        if( this.getFrom() != null )
            doc.add(new Field("from", this.getFrom(), Field.Store.YES, Field.Index.NO));

        //subject;
        if( this.getSubject() != null )
            doc.add(new Field("subject", this.getSubject(), Field.Store.YES, Field.Index.ANALYZED));

        //sender;
        if( this.getSender() != null )
            doc.add(new Field("sender", this.getSender(), Field.Store.YES, Field.Index.NO));

        //messageId;
        if( this.getMessageId() != null)
            doc.add(new Field("messageId", this.getMessageId(), Field.Store.YES, Field.Index.NO));

        //date;
        if( this.getDate() != null)
            doc.add(new Field("date", this.getDate(), Field.Store.YES, Field.Index.NO));
        
        //distribution;
        if( this.getDistribution() != null)
            doc.add(new Field("distribution", this.getDistribution(), Field.Store.YES, Field.Index.NO));
        
        //references;
        for(String reference : this.references)
            doc.add(new Field("reference", reference, Field.Store.YES, Field.Index.NO));

        //organization;
        if( this.getOrganization() != null )
            doc.add(new Field("organization", this.getOrganization(), Field.Store.YES, Field.Index.NO));

        
        //text;
        if( this.getText() != null )
            doc.add(new Field("text", this.getText(), Field.Store.YES, Field.Index.ANALYZED));

        return doc;
    }

    
    @Override
    public String toString()
    {
        String s = super.toString();

        s += "[\n";
        s += "\nFile: " + this.file.getAbsolutePath();
        s += "\nId: " + this.id;
        s += "\nCategory: " + this.category;
        s += "\nXref: " + this.xref.toString();
        s += "\nNewsgroups: " + this.newsgroups.toString();
        s += "\nPath: " + this.paths.toString();
        s += "\nFrom: " + this.from;
        s += "\nSubject: " + this.subject;
        s += "\nSender: " + this.sender;
        s += "\nMessage-Id: " + this.messageId;
        s += "\nDate: " + this.date;
        s += "\nDistribution: " + this.distribution;
        s += "\nReferences: " + this.references.toString();
        s += "\nOrganization: " + this.organization;
        s += "\n\n" + this.text;
        s += "\n]";

        return s;
    }


}