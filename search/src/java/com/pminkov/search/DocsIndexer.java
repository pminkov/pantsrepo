package com.pminkov.search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.google.common.base.Stopwatch;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class DocsIndexer {
  private final TextCorpus corpus;
  public DocsIndexer(TextCorpus corpus) {
    this.corpus = corpus;
  }

  private Path createIndexDirectory() throws IOException {
    String indexUuid = UUID.randomUUID().toString();

    final Path indexPathLocation = Paths.get(Search.DIR_BASE, "index", indexUuid);
    Path indexPath = Files.createDirectory(indexPathLocation);

    return indexPath;
  }

  public Directory makeIndex(int numDocs) throws IOException {
    Stopwatch stopwatch = Stopwatch.createStarted();
    Path indexPath = createIndexDirectory();
    System.out.println("Location: " + indexPath.toString());

    Directory directory = FSDirectory.open(indexPath);
    StandardAnalyzer analyzer = new StandardAnalyzer();
    IndexWriterConfig config = new IndexWriterConfig(analyzer);
    IndexWriter iwriter = new IndexWriter(directory, config);

    for (int di = 0; di < numDocs; di++) {
      Document doc = new Document();
      String text = corpus.generateTweet();
      doc.add(new Field("text", text, TextField.TYPE_STORED));
      iwriter.addDocument(doc);
    }
    iwriter.close();

    System.out.println("Index generated in: " + stopwatch.toString());
    return directory;
  }
}
