package Repository;

import Domain.Client;
import Exceptions.ValidatorException;
import Validator.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientXmlRepository extends InMemoRepo<Integer, Client> {
    private String filename;

    public ClientXmlRepository(Validator<Client> validator, String filename) throws Exception {
        super(validator);
        this.filename = filename;
        loadData();
    }

    private static String getTextByTagName(Element element, String tag) {
        return element.getElementsByTagName(tag).item(0).getTextContent();
    }

    private void loadData() throws Exception {
        List<Client> clients = new ArrayList<>();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
        Document xmlDoc = dbBuilder.parse("./Data/Clients.xml");
        Element root = xmlDoc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            if (child instanceof Element) {
                Element clientElement = (Element) child;
                String id = clientElement.getAttribute("id");

                String name = getTextByTagName(clientElement, "name");
                String spentMoney = getTextByTagName(clientElement, "spentMoney");

                Client client = new Client(Integer.parseInt(id), name, Integer.parseInt(spentMoney));
                clients.add(client);
            }
        }

        for (Client client : clients) {
            try {
                super.save(client);
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Optional<Client> findOne(Integer integer) {
        Optional<Client> optional = super.findOne(integer);
        if (optional.isPresent()) {
            return optional;
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Client> findAll() {
        return super.findAll();
    }

    private static void appendChildToElement(Document doc, Element parent, String tag, String value) {
        Element element = doc.createElement(tag);
        element.setTextContent(value);
        parent.appendChild(element);
    }

    public Optional<Client> save(Client client) throws Exception, ValidatorException {
        Optional<Client> optional = super.save(client);
        if (optional.isPresent()) {
            return optional;
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
        Document xmlDoc = dbBuilder.parse("./Data/Clients.xml");
        Element root = xmlDoc.getDocumentElement();

        Element ourClient = xmlDoc.createElement("client");
        ourClient.setAttribute("id", String.valueOf(client.getIdEntity()));
        appendChildToElement(xmlDoc, ourClient, "name", client.getName());
        appendChildToElement(xmlDoc, ourClient, "spentMoney", String.valueOf(client.getSpentMoney()));

        root.appendChild(ourClient);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root), new StreamResult("./Data/Clients.xml"));

        return Optional.empty();
    }

    @Override
    public Optional<Client> delete(Integer id) throws Exception {
        Optional<Client> optional = super.delete(id);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
        Document xmlDoc = dbBuilder.parse("./Data/Clients.xml");
        Element root = xmlDoc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            if (child instanceof Element) {
                Element clientElement = (Element) child;
                String idClient = clientElement.getAttribute("id");
                if (Integer.parseInt(idClient) == id) {
                    root.removeChild(child);
                }
            }
        }
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root), new StreamResult("./Data/Clients.xml"));

        return Optional.empty();
    }

    private static void replaceNode(Document doc, Node old, Element parent, String tag, String value) {
        Element element = doc.createElement(tag);
        element.setTextContent(value);
        parent.replaceChild(element, old);
    }

    private static Node old(Element element, String tag) {
        return element.getElementsByTagName(tag).item(0);
    }

    @Override
    public Optional<Client> update(Client entity) throws ValidatorException, Exception {
        Optional<Client> optional = super.update(entity);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
        Document xmlDoc = dbBuilder.parse("./Data/Clients.xml");
        Element root = xmlDoc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            if (child instanceof Element) {
                Element clientElement = (Element) child;
                String idClient = clientElement.getAttribute("id");
                if (Integer.parseInt(idClient) == entity.getIdEntity()) {
                    Node auth = old(clientElement, "name");
                    replaceNode(xmlDoc, auth, clientElement, "name", entity.getName());
                    Node titl = old(clientElement, "spentMoney");
                    replaceNode(xmlDoc, titl, clientElement, "spentMoney", String.valueOf(entity.getSpentMoney()));
                }
            }
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root), new StreamResult("./Data/Clients.xml"));

        return Optional.empty();
    }
}
