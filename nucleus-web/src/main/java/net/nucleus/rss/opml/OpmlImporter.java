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

    public static void fromStream(@NotNull InputStream inputStream, Outline root) throws OpmlImporterException {
        try {
            OpmlImporter opmlImporter = new OpmlImporter();

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(inputStream);

            opmlImporter.importOpml(document, root);
        } catch (JDOMException e) {
            throw new OpmlImporterException("OPML import from file failed.", e);
        } catch (IOException e) {
            throw new OpmlImporterException("OPML import from file failed.", e);
        }
    }

    @SuppressWarnings("unchecked")
    public void importOpml(Document document, Outline root) throws OpmlImporterException {
        try {
            OPML10Parser parser = new OPML10Parser();
            Opml opml = (Opml) parser.parse(document, false);

            transform(root, (List<com.sun.syndication.feed.opml.Outline>) opml.getOutlines());
        } catch (FeedException e) {
            throw new OpmlImporterException("Failed to parse OPML document.", e);
        }
    }

    @SuppressWarnings("unchecked")
    private void transform(Outline parent, List<com.sun.syndication.feed.opml.Outline> outlines) {
        if (outlines.isEmpty()) {
            return;
        }

        int ordinal = 0;
        for (com.sun.syndication.feed.opml.Outline outline : outlines) {
            Outline child = new Outline();

            child.setParent(parent);
            child.setUser(parent.getUser());
            child.setHtmlUrl(outline.getHtmlUrl());
            child.setXmlUrl(outline.getXmlUrl());
            child.setTitle(outline.getTitle());
            child.setDescription(outline.getText());
            child.setType(parseOutlineType(outline.getType()));
            child.setOrdinal(ordinal);

            parent.addChild(child);

            if (child.getType() == OutlineType.FOLDER) {
                transform(child, (List<com.sun.syndication.feed.opml.Outline>) outline.getChildren());
            }

            ordinal++;
        }
    }

    private static OutlineType parseOutlineType(String type) {
        return RSS.equals(type) ? OutlineType.FEED : OutlineType.FOLDER;
    }
}
