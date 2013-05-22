package net.nucleus.rss.opml;

import com.sun.syndication.feed.opml.Opml;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.impl.OPML10Parser;
import net.nucleus.rss.model.Outline;
import net.nucleus.rss.model.OutlineType;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * User: starasov
 * Date: 5/14/13
 * Time: 8:58 PM
 */
public class OpmlImporter {

    private static final String RSS = "rss";

    @NotNull
    public static Outline fromStream(@NotNull InputStream inputStream) throws OpmlImporterException {
        try {
            OpmlImporter opmlImporter = new OpmlImporter();

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(inputStream);

            return opmlImporter.importOpml(document);
        } catch (JDOMException e) {
            throw new OpmlImporterException("OPML import from file failed.", e);
        } catch (IOException e) {
            throw new OpmlImporterException("OPML import from file failed.", e);
        }
    }

    @SuppressWarnings("unchecked")
    public Outline importOpml(Document document) throws OpmlImporterException {
        try {
            OPML10Parser parser = new OPML10Parser();
            Opml opml = (Opml) parser.parse(document, false);

            Outline root = new Outline();
            root.setType(OutlineType.FOLDER);

            transform(root, (List<com.sun.syndication.feed.opml.Outline>) opml.getOutlines());

            return root;
        } catch (FeedException e) {
            throw new OpmlImporterException("Failed to parse OPML document.", e);
        }
    }

    @SuppressWarnings("unchecked")
    private void transform(Outline parent, List<com.sun.syndication.feed.opml.Outline> outlines) {
        if (outlines.isEmpty()) {
            return;
        }

        for (com.sun.syndication.feed.opml.Outline outline : outlines) {
            Outline child = new Outline();

            child.setParent(parent);
            child.setHtmlUrl(outline.getHtmlUrl());
            child.setXmlUrl(outline.getXmlUrl());
            child.setTitle(outline.getTitle());
            child.setDescription(outline.getText());
            child.setType(parseOutlineType(outline.getType()));

            parent.addChild(child);

            if (child.getType() == OutlineType.FOLDER) {
                transform(child, (List<com.sun.syndication.feed.opml.Outline>) outline.getChildren());
            }
        }
    }

    private static OutlineType parseOutlineType(String type) {
        return RSS.equals(type) ? OutlineType.FEED : OutlineType.FOLDER;
    }
}
